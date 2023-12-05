package com.dragon.ide.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutFileListItemBinding;
import com.dragon.ide.objects.WebFile;
import com.dragon.ide.ui.activities.EventListActivity;
import java.util.ArrayList;

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
        binding.icon.setImageResource(R.drawable.ic_folder_black_24dp);
        break;
      case WebFile.SupportedFileType.HTML:
        binding.icon.setImageResource(R.drawable.language_html);
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
              Intent i = new Intent();
              i.setClass(activity, EventListActivity.class);
              i.putExtra("projectName", projectName);
              i.putExtra("projectPath", projectPath);
              i.putExtra("fileName", _data.get(_position).getFilePath());
              i.putExtra("fileType", _data.get(_position).getFileType());
              i.putExtra("webFile", filePathList.get(_position));
              activity.startActivity(i);
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
