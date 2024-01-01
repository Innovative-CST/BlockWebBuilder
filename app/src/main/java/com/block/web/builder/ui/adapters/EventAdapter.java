package com.block.web.builder.ui.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.block.web.builder.databinding.LayoutEventListAdapterBinding;
import com.block.web.builder.objects.Event;
import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
  public ArrayList<Event> _data;
  public Activity activity;
  public boolean[] selectedCheckboxes;

  public EventAdapter(ArrayList<Event> _arr, Activity activity) {
    _data = _arr;
    this.activity = activity;
    selectedCheckboxes = new boolean[_arr.size()];
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutEventListAdapterBinding item =
        LayoutEventListAdapterBinding.inflate(activity.getLayoutInflater());
    View _v = item.getRoot();
    RecyclerView.LayoutParams _lp =
        new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    _v.setLayoutParams(_lp);
    return new ViewHolder(_v);
  }

  @Override
  public void onBindViewHolder(ViewHolder _holder, int _position) {
    LayoutEventListAdapterBinding binding = LayoutEventListAdapterBinding.bind(_holder.itemView);
    binding.title.setText(_data.get(_position).getName());
    binding.desc.setText(_data.get(_position).getDesc());
    binding.getRoot().setOnClickListener((view) -> {});
    binding.addCheckbox.setOnCheckedChangeListener(
        (button, isChecked) -> {
          selectedCheckboxes[_position] = isChecked;
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
