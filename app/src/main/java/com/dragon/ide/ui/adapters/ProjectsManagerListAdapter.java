package com.dragon.ide.ui.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.dragon.ide.databinding.LayoutProjectsManagerListItemBinding;
import com.dragon.ide.objects.Project;
import java.util.ArrayList;

public class ProjectsManagerListAdapter
    extends RecyclerView.Adapter<ProjectsManagerListAdapter.ViewHolder> {

  public ArrayList<Project> _data;
  public Activity activity;

  public ProjectsManagerListAdapter(ArrayList<Project> _arr, Activity activity) {
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
    binding.projectName.setText(_data.get(_position).getProjectName());
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
