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

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.block.web.builder.R;
import com.block.web.builder.core.WebFile;
import com.block.web.builder.databinding.LayoutFileListItemBinding;
import com.block.web.builder.listeners.ProjectBuildListener;
import com.block.web.builder.listeners.TaskListener;
import com.block.web.builder.ui.activities.EventListActivity;
import com.block.web.builder.ui.activities.FileManagerActivity;
import com.block.web.builder.ui.activities.WebViewActivity;
import com.block.web.builder.ui.bottomsheet.FileOperationBottomSheet;
import com.block.web.builder.utils.ProjectBuilder;
import com.block.web.builder.utils.ProjectFileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FileListAdapterItem extends RecyclerView.Adapter<FileListAdapterItem.ViewHolder> {

  public ArrayList<WebFile> _data;
  public AppCompatActivity activity;
  private String projectName;
  private String projectPath;
  ArrayList<String> filePathList;

  public FileListAdapterItem(
      ArrayList<WebFile> _arr,
      ArrayList<String> filePathList,
      AppCompatActivity activity,
      String projectName,
      String projectPath) {
    _data = _arr;
    this.activity = activity;
    this.projectName = projectName;
    this.projectPath = projectPath;
    this.filePathList = filePathList;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutFileListItemBinding item =
        LayoutFileListItemBinding.inflate(activity.getLayoutInflater());
    View _v = item.getRoot();
    RecyclerView.LayoutParams _lp =
        new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    _v.setLayoutParams(_lp);
    return new ViewHolder(_v);
  }

  @Override
  public void onBindViewHolder(ViewHolder _holder, int _position) {
    LayoutFileListItemBinding binding = LayoutFileListItemBinding.bind(_holder.itemView);
    binding.tvFileName.setText(
        _data
            .get(_position)
            .getFilePath()
            .concat(WebFile.getSupportedFileSuffix(_data.get(_position).getFileType())));
    switch (_data.get(_position).getFileType()) {
      case WebFile.SupportedFileType.FOLDER:
        binding.icon.setImageResource(R.drawable.folder);
        break;
      case WebFile.SupportedFileType.HTML:
        binding.icon.setImageResource(R.drawable.language_html);
        binding.execute.setVisibility(View.VISIBLE);
        break;
      case WebFile.SupportedFileType.CSS:
        binding.icon.setImageResource(R.drawable.language_css);
        break;
      case WebFile.SupportedFileType.JS:
        binding.icon.setImageResource(R.drawable.language_javascript);
        break;
    }
    binding
        .getRoot()
        .setOnClickListener(
            (view) -> {
              if (_data.get(_position).getFileType() == WebFile.SupportedFileType.FOLDER) {
                Intent i = new Intent();
                i.setClass(activity, FileManagerActivity.class);
                i.putExtra("projectName", projectName);
                i.putExtra("projectPath", projectPath);
                i.putExtra(
                    "ListPath",
                    new File(
                            new File(
                                new File(((FileManagerActivity) activity).getListPath()),
                                _data
                                    .get(_position)
                                    .getFilePath()
                                    .concat(
                                        WebFile.getSupportedFileSuffix(
                                            _data.get(_position).getFileType()))),
                            ProjectFileUtils.PROJECT_FILES_DIRECTORY)
                        .getAbsolutePath());
                i.putExtra(
                    "outputDirectory",
                    new File(
                            new File(((FileManagerActivity) activity).getOutputDirectory()),
                            _data
                                .get(_position)
                                .getFilePath()
                                .concat(
                                    WebFile.getSupportedFileSuffix(
                                        _data.get(_position).getFileType())))
                        .getAbsolutePath());
                activity.startActivity(i);
              } else {
                ((FileManagerActivity) activity)
                    .saveFileList(
                        new TaskListener() {

                          @Override
                          public void onSuccess(Object result) {
                            activity.runOnUiThread(
                                () -> {
                                  Intent i = new Intent();
                                  i.setClass(activity, EventListActivity.class);
                                  i.putExtra("projectName", projectName);
                                  i.putExtra("projectPath", projectPath);
                                  i.putExtra("fileName", _data.get(_position).getFilePath());
                                  i.putExtra("fileType", _data.get(_position).getFileType());
                                  i.putExtra("webFile", filePathList.get(_position));
                                  i.putExtra(
                                      "fileOutputPath",
                                      new File(
                                              new File(
                                                  ((FileManagerActivity) activity)
                                                      .getOutputDirectory()),
                                              _data
                                                  .get(_position)
                                                  .getFilePath()
                                                  .concat(
                                                      WebFile.getSupportedFileSuffix(
                                                          _data.get(_position).getFileType())))
                                          .getAbsolutePath());
                                  activity.startActivity(i);
                                });
                          }
                        });
              }
            });
    binding
        .getRoot()
        .setOnLongClickListener(
            v -> {
              FileOperationBottomSheet mFileOperationBottomSheet =
                  new FileOperationBottomSheet(_position, (FileManagerActivity) activity);
              mFileOperationBottomSheet.show(
                  activity.getSupportFragmentManager(), "ModalBottomSheet");
              return false;
            });
    binding.execute.setOnClickListener(
        (view) -> {
          Executor executor = Executors.newSingleThreadExecutor();
          executor.execute(
              () -> {
                // Save file list first of all
                for (int i = 0; i < ((FileManagerActivity) activity).getFileList().size(); ++i) {
                  try {
                    File webFileDestination =
                        ProjectFileUtils.getProjectWebFile(
                            new File(
                                new File(((FileManagerActivity) activity).getListPath()),
                                ((FileManagerActivity) activity)
                                    .getFileList()
                                    .get(i)
                                    .getFilePath()
                                    .concat(
                                        WebFile.getSupportedFileSuffix(
                                            ((FileManagerActivity) activity)
                                                .getFileList()
                                                .get(i)
                                                .getFileType()))));
                    if (!webFileDestination.getParentFile().exists()) {
                      webFileDestination.getParentFile().mkdirs();
                    }

                    FileOutputStream fos = new FileOutputStream(webFileDestination);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(((FileManagerActivity) activity).getFileList().get(i));
                    fos.close();
                    oos.close();
                  } catch (Exception e) {
                  }
                }

                ProjectBuilder.generateProjectCode(
                    new File(projectPath),
                    new ProjectBuildListener() {
                      @Override
                      public void onLog(String log, int type) {}

                      @Override
                      public void onBuildComplete() {
                        activity.runOnUiThread(
                            () -> {
                              // See preview in WebView
                              Intent i = new Intent();
                              i.setClass(activity, WebViewActivity.class);
                              i.putExtra("type", "file");
                              i.putExtra(
                                  "root",
                                  new File(new File(projectPath), ProjectFileUtils.BUILD_DIRECTORY)
                                      .getAbsolutePath());
                              i.putExtra(
                                  "data",
                                  new File(
                                          new File(
                                              ((FileManagerActivity) activity)
                                                  .getOutputDirectory()),
                                          _data
                                              .get(_position)
                                              .getFilePath()
                                              .concat(
                                                  WebFile.getSupportedFileSuffix(
                                                      _data.get(_position).getFileType())))
                                      .getAbsolutePath());
                              activity.startActivity(i);
                            });
                      }

                      @Override
                      public void onBuildStart() {}
                    },
                    activity);
              });
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
