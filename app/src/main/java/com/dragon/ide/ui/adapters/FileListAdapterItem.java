package com.dragon.ide.ui.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutFileListItemBinding;
import com.dragon.ide.objects.WebFile;
import java.util.ArrayList;

public class FileListAdapterItem extends RecyclerView.Adapter<FileListAdapterItem.ViewHolder> {

  public ArrayList<WebFile> _data;
  public Activity activity;

  public FileListAdapterItem(ArrayList<WebFile> _arr, Activity activity) {
    _data = _arr;
    this.activity = activity;
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
    binding.tvFileName.setText(_data.get(_position).getFilePath().concat(getSupportedFileSuffix(_data.get(_position).getFileType())));
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

  public String getSupportedFileSuffix(int type) {
    switch (type) {
      case WebFile.SupportedFileType.HTML:
        return ".html";
      case WebFile.SupportedFileType.CSS:
        return ".css";
      case WebFile.SupportedFileType.JS:
        return ".js";
    }
    return "";
  }
}
