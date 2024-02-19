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

package com.block.web.builder.ui.adapters;

import android.app.Activity;
import android.code.editor.ui.bottomsheet.EventOperationBottomSheet;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.block.web.builder.databinding.LayoutEventListItemBinding;
import com.block.web.builder.core.Event;
import com.block.web.builder.ui.activities.EventEditorActivity;
import com.block.web.builder.ui.activities.EventListActivity;
import com.block.web.builder.utils.ProjectFileUtils;
import java.io.File;
import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
  public ArrayList<Event> _data;
  public Activity activity;
  private String projectName;
  private String projectPath;
  private String webFilePath;

  public EventListAdapter(
      ArrayList<Event> _arr,
      Activity activity,
      String projectName,
      String projectPath,
      String webFilePath) {
    _data = _arr;
    this.activity = activity;
    this.projectName = projectName;
    this.projectPath = projectPath;
    this.webFilePath = webFilePath;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutEventListItemBinding item =
        LayoutEventListItemBinding.inflate(activity.getLayoutInflater());
    View _v = item.getRoot();
    RecyclerView.LayoutParams _lp =
        new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    _v.setLayoutParams(_lp);
    return new ViewHolder(_v);
  }

  @Override
  public void onBindViewHolder(ViewHolder _holder, int _position) {
    LayoutEventListItemBinding binding = LayoutEventListItemBinding.bind(_holder.itemView);
    binding.title.setText(_data.get(_position).getName());
    binding.desc.setText(_data.get(_position).getDesc());
    binding
        .getRoot()
        .setOnClickListener(
            (view) -> {
              Intent i = new Intent();
              i.setClass(activity, EventEditorActivity.class);
              i.putExtra("projectName", projectName);
              i.putExtra("projectPath", projectPath);
              i.putExtra("webFilePath", webFilePath);
              i.putExtra(
                  "eventFilePath",
                  new File(
                          new File(
                              new File(webFilePath).getParentFile(),
                              ProjectFileUtils.EVENTS_DIRECTORY),
                          _data.get(_position).getName())
                      .getAbsolutePath());
              i.putExtra("outputDirectory", ((EventListActivity) activity).getFileOutputPath());
              activity.startActivity(i);
            });
    binding
        .getRoot()
        .setOnLongClickListener(
            v -> {
              EventOperationBottomSheet mEventOperationBottomSheet =
                  new EventOperationBottomSheet(_position, _data, (EventListActivity) activity);
              mEventOperationBottomSheet.show(
                  ((EventListActivity) activity).getSupportFragmentManager(), "ModalBottomSheet");
              return false;
            });
  }

  @Override
  public int getItemCount() {
    return _data.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View v) {
      super(v);
    }
  }
}
