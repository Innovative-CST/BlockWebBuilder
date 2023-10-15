package com.dragon.ide.ui.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
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
    return new ViewHolder(new View(activity));
  }

  @Override
  public void onBindViewHolder(ViewHolder _holder, final int _position) {}

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
