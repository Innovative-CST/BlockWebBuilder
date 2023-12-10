package com.dragon.ide.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutFileListItemBinding;
import com.dragon.ide.listeners.LogListener;
import com.dragon.ide.objects.WebFile;
import com.dragon.ide.ui.activities.EventListActivity;
import com.dragon.ide.ui.activities.FileManagerActivity;
import com.dragon.ide.ui.activities.WebViewActivity;
import com.dragon.ide.utils.ProjectBuilder;
import com.dragon.ide.utils.ProjectFileUtils;
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
                            new File(((FileManagerActivity) activity).getOutputDirectory()),
                            _data
                                .get(_position)
                                .getFilePath()
                                .concat(
                                    WebFile.getSupportedFileSuffix(
                                        _data.get(_position).getFileType())))
                        .getAbsolutePath());
                activity.startActivity(i);
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
                    new LogListener() {
                      @Override
                      public void onLog(String log, int type) {}
                    },
                    activity);

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
                                  new File(((FileManagerActivity) activity).getOutputDirectory()),
                                  _data
                                      .get(_position)
                                      .getFilePath()
                                      .concat(
                                          WebFile.getSupportedFileSuffix(
                                              _data.get(_position).getFileType())))
                              .getAbsolutePath());
                      activity.startActivity(i);
                    });
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
