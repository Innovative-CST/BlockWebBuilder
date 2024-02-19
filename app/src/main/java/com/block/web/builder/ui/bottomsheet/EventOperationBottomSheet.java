/*
 *  This file is part of BlockWeb Builder.
 *
 *  BlockWeb Builder is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BlockWeb Builder is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with BlockWeb Builder.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.block.web.builder.ui.bottomsheet;

import android.code.editor.common.interfaces.FileDeleteListener;
import android.code.editor.common.utils.FileDeleteUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.block.web.builder.core.Event;
import com.block.web.builder.databinding.LayoutBottomsheetEventOperationBinding;
import com.block.web.builder.listeners.TaskListener;
import com.block.web.builder.ui.activities.EventListActivity;
import com.block.web.builder.ui.dialogs.eventList.DeleteEventDialog;
import com.block.web.builder.utils.ProjectFileUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.io.File;
import java.util.ArrayList;

public class EventOperationBottomSheet extends BottomSheetDialogFragment {
  public int position;
  public ArrayList<Event> eventList;
  public EventListActivity mEventListActivity;

  public EventOperationBottomSheet(
      int position, ArrayList<Event> eventList, EventListActivity mEventListActivity) {
    this.position = position;
    this.eventList = eventList;
    this.mEventListActivity = mEventListActivity;
  }

  @Override
  public View onCreateView(LayoutInflater inflator, ViewGroup layout, Bundle bundle) {
    LayoutBottomsheetEventOperationBinding binding =
        LayoutBottomsheetEventOperationBinding.inflate(inflator);
    binding.deleteFile.setOnClickListener(
        v -> {
          new DeleteEventDialog(
                  mEventListActivity,
                  eventList.get(position),
                  new TaskListener() {

                    @Override
                    public void onSuccess(Object result) {
                      FileDeleteUtils.delete(
                          new File(
                              new File(
                                  new File(mEventListActivity.getWebFilePath()).getParentFile(),
                                  ProjectFileUtils.EVENTS_DIRECTORY),
                              eventList.get(position).getName()),
                          new FileDeleteListener() {
                            @Override
                            public void onProgressUpdate(int deleteDone) {}

                            @Override
                            public void onTotalCount(int total) {}

                            @Override
                            public void onDeleting(File path) {}

                            @Override
                            public void onDeleteComplete(File path) {}

                            @Override
                            public void onTaskComplete() {
                              mEventListActivity.showEventList();
                            }
                          },
                          true,
                          mEventListActivity);
                    }
                  })
              .create()
              .show();
        });

    return binding.getRoot();
  }
}
