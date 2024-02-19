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
import com.block.web.builder.databinding.LayoutBottomsheetProjectOperationBinding;
import com.block.web.builder.listeners.TaskListener;
import com.block.web.builder.ui.activities.MainActivity;
import com.block.web.builder.ui.dialogs.project.DeleteProjectDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ProjectOperationBottomSheet extends BottomSheetDialogFragment {
  public int position;
  public ArrayList<HashMap<String, Object>> projectList;
  public MainActivity mMainActivity;

  public ProjectOperationBottomSheet(
      int position, ArrayList<HashMap<String, Object>> projectList, MainActivity mMainActivity) {
    this.position = position;
    this.projectList = projectList;
    this.mMainActivity = mMainActivity;
  }

  @Override
  public View onCreateView(LayoutInflater inflator, ViewGroup layout, Bundle bundle) {
    LayoutBottomsheetProjectOperationBinding binding =
        LayoutBottomsheetProjectOperationBinding.inflate(inflator);
    binding.deleteFile.setOnClickListener(
        v -> {
          new DeleteProjectDialog(
                  mMainActivity,
                  projectList.get(position),
                  new TaskListener() {

                    @Override
                    public void onSuccess(Object result) {
                      FileDeleteUtils.delete(
                          ((File) projectList.get(position).get("Path")),
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
                              mMainActivity.loadProjectInList();
                            }
                          },
                          true,
                          mMainActivity);
                    }
                  })
              .create()
              .show();
        });

    return binding.getRoot();
  }
}
