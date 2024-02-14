/*
 *  This file is part of BlockWeb Builder.
 *
 *  BlockWeb Builder is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BlockWeb Builder is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with BlockWeb Builder.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.block.web.builder.ui.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.block.web.builder.databinding.LayoutEventListAdapterBinding;
import com.block.web.builder.core.Event;
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
