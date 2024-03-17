/*
 * This file is part of BlockWeb Builder [https://github.com/TS-Code-Editor/BlockWebBuilder].
 *
 * License Agreement
 * This software is licensed under the terms and conditions outlined below. By accessing, copying, modifying, or using this software in any way, you agree to abide by these terms.
 *
 * 1. **  Copy and Modification Restrictions  **
 *    - You are not permitted to copy or modify the source code of this software without the permission of the owner, which may be granted publicly on GitHub Discussions or on Discord.
 *    - If permission is granted by the owner, you may copy the software under the terms specified in this license agreement.
 *    - You are not allowed to permit others to copy the source code that you were allowed to copy by the owner.
 *    - Modified or copied code must not be further copied.
 * 2. **  Contributor Attribution  **
 *    - You must attribute the contributors by creating a visible list within the application, showing who originally wrote the source code.
 *    - If you copy or modify this software under owner permission, you must provide links to the profiles of all contributors who contributed to this software.
 * 3. **  Modification Documentation  **
 *    - All modifications made to the software must be documented and listed.
 *    - the owner may incorporate the modifications made by you to enhance this software.
 * 4. **  Consistent Licensing  **
 *    - All copied or modified files must contain the same license text at the top of the files.
 * 5. **  Permission Reversal  **
 *    - If you are granted permission by the owner to copy this software, it can be revoked by the owner at any time. You will be notified at least one week in advance of any such reversal.
 *    - In case of Permission Reversal, if you fail to acknowledge the notification sent by us, it will not be our responsibility.
 * 6. **  License Updates  **
 *    - The license may be updated at any time. Users are required to accept and comply with any changes to the license.
 *    - In such circumstances, you will be given 7 days to ensure that your software complies with the updated license.
 *    - We will not notify you about license changes; you need to monitor the GitHub repository yourself (You can enable notifications or watch the repository to stay informed about such changes).
 * By using this software, you acknowledge and agree to the terms and conditions outlined in this license agreement. If you do not agree with these terms, you are not permitted to use, copy, modify, or distribute this software.
 *
 * Copyright © 2024 Dev Kumar
 */

package com.block.web.builder.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.block.web.builder.core.Event;
import com.block.web.builder.databinding.LayoutEventListItemBinding;
import com.block.web.builder.ui.activities.EventEditorActivity;
import com.block.web.builder.ui.activities.EventListActivity;
import com.block.web.builder.ui.bottomsheet.EventOperationBottomSheet;
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
