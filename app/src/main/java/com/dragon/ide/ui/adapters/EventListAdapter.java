package com.dragon.ide.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.dragon.ide.databinding.LayoutEventListItemBinding;
import com.dragon.ide.objects.Event;
import com.dragon.ide.ui.activities.EventEditorActivity;
import com.dragon.ide.utils.ProjectFileUtils;
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
                          new File(webFilePath).getParentFile(), ProjectFileUtils.EVENTS_DIRECTORY),
                      _data.get(_position).getName()).getAbsolutePath());
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
