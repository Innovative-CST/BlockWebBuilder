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
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.block.web.builder.R;
import com.block.web.builder.databinding.LayoutProjectsManagerListItemBinding;
import com.block.web.builder.objects.Project;
import com.block.web.builder.ui.activities.FileManagerActivity;
import com.block.web.builder.ui.activities.MainActivity;
import com.block.web.builder.ui.bottomsheet.ProjectOperationBottomSheet;
import com.block.web.builder.utils.ProjectFileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ProjectsManagerListAdapter
    extends RecyclerView.Adapter<ProjectsManagerListAdapter.ViewHolder> {

  public ArrayList<HashMap<String, Object>> _data;
  public Activity activity;

  public ProjectsManagerListAdapter(ArrayList<HashMap<String, Object>> _arr, Activity activity) {
    _data = _arr;
    this.activity = activity;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutProjectsManagerListItemBinding item =
        LayoutProjectsManagerListItemBinding.inflate(activity.getLayoutInflater());
    View _v = item.getRoot();
    RecyclerView.LayoutParams _lp =
        new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    _v.setLayoutParams(_lp);
    return new ViewHolder(_v);
  }

  @Override
  public void onBindViewHolder(ViewHolder _holder, final int _position) {
    LayoutProjectsManagerListItemBinding binding =
        LayoutProjectsManagerListItemBinding.bind(_holder.itemView);
    binding.projectName.setText(((Project) _data.get(_position).get("Project")).getProjectName());

    if (((Project) _data.get(_position).get("Project")).getProjectPhotoPath().equals("")) {
      binding.projectPhoto.setImageResource(R.drawable.banner);
    } else {
      binding.projectPhoto.setImageURI(
          Uri.parse(
              new File(
                      ((File) _data.get(_position).get("Path")),
                      ((Project) _data.get(_position).get("Project")).getProjectPhotoPath())
                  .getAbsolutePath()));
    }

    binding
        .getRoot()
        .setOnClickListener(
            (view) -> {
              Intent i = new Intent();
              i.setClass(activity, FileManagerActivity.class);
              i.putExtra(
                  "projectName", ((Project) _data.get(_position).get("Project")).getProjectName());
              i.putExtra(
                  "projectPath", ((File) _data.get(_position).get("Path")).getAbsolutePath());
              i.putExtra(
                  "ListPath",
                  ProjectFileUtils.getProjectFilesDirectory(
                          ((File) _data.get(_position).get("Path")))
                      .getAbsolutePath());
              i.putExtra("outputDirectory", "");
              activity.startActivity(i);
            });
    binding
        .getRoot()
        .setOnLongClickListener(
            v -> {
              ProjectOperationBottomSheet mProjectOperationBottomSheet =
                  new ProjectOperationBottomSheet(_position, _data, (MainActivity) activity);
              mProjectOperationBottomSheet.show(
                  ((MainActivity) activity).getSupportFragmentManager(), "ModalBottomSheet");
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
