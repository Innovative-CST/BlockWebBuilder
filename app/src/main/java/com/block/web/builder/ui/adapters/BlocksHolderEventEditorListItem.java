package com.block.web.builder.ui.adapters;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.block.web.builder.databinding.LayoutEventBlocksHolderListItemBinding;
import com.block.web.builder.objects.BlocksHolder;
import com.block.web.builder.ui.activities.EventEditorActivity;
import com.block.web.builder.utils.FilterBlocks;
import java.util.ArrayList;

public class BlocksHolderEventEditorListItem
    extends RecyclerView.Adapter<BlocksHolderEventEditorListItem.ViewHolder> {

  public ArrayList<BlocksHolder> list;
  public EventEditorActivity activity;

  public BlocksHolderEventEditorListItem(
      ArrayList<BlocksHolder> _arr, EventEditorActivity activity) {
    list = _arr;
    this.activity = activity;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutEventBlocksHolderListItemBinding item =
        LayoutEventBlocksHolderListItemBinding.inflate(activity.getLayoutInflater());
    View _v = item.getRoot();
    RecyclerView.LayoutParams _lp =
        new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    _v.setLayoutParams(_lp);
    return new ViewHolder(_v);
  }

  @Override
  public void onBindViewHolder(ViewHolder _holder, int _position) {
    LayoutEventBlocksHolderListItemBinding binding =
        LayoutEventBlocksHolderListItemBinding.bind(_holder.itemView);
    binding.holderName.setText(list.get(_position).getName());
    int blocksCount = list.get(_position).getBlocks().size();
    binding.color.setBackgroundColor(Color.parseColor(list.get(_position).getColor()));
    binding
        .getRoot()
        .setOnClickListener(
            (view) -> {
              activity.binding.blockList.setAdapter(
                  new BlockListAdapter(
                      FilterBlocks.filterBlocksWithTag(
                          list.get(_position).getBlocks(), activity.getLanguage()),
                      activity));
              activity.binding.blockList.setLayoutManager(new LinearLayoutManager(activity));
            });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View v) {
      super(v);
    }
  }
}
