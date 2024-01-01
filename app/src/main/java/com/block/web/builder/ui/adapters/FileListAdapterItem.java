package com.block.web.builder.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.block.web.builder.R;
import com.block.web.builder.databinding.LayoutFileListItemBinding;
import com.block.web.builder.listeners.ProjectBuildListener;
import com.block.web.builder.listeners.TaskListener;
import com.block.web.builder.objects.WebFile;
import com.block.web.builder.ui.activities.EventListActivity;
import com.block.web.builder.ui.activities.FileManagerActivity;
import com.block.web.builder.ui.activities.WebViewActivity;
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
  public Activity activity;
  private String projectName;
  private String projectPath;
  ArrayList<String> filePathList;

  public FileListAdapterItem(
      ArrayList<WebFile> _arr,
      ArrayList<String> filePathList,
      Activity activity,
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
                ((FileManagerActivity)activity).saveFileList(
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
