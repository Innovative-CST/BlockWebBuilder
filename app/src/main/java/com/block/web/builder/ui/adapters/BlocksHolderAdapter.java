package com.block.web.builder.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.block.web.builder.R;
import com.block.web.builder.databinding.LayoutBlocksHolderListItemBinding;
import com.block.web.builder.objects.BlocksHolder;
import com.block.web.builder.ui.activities.BlockManagerActivity;
import java.util.ArrayList;

public class BlocksHolderAdapter extends RecyclerView.Adapter<BlocksHolderAdapter.ViewHolder> {

  public ArrayList<BlocksHolder> list;
  public Activity activity;

  public BlocksHolderAdapter(ArrayList<BlocksHolder> _arr, Activity activity) {
    list = _arr;
    this.activity = activity;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutBlocksHolderListItemBinding item =
        LayoutBlocksHolderListItemBinding.inflate(activity.getLayoutInflater());
    View _v = item.getRoot();
    RecyclerView.LayoutParams _lp =
        new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    _v.setLayoutParams(_lp);
    return new ViewHolder(_v);
  }

  @Override
  public void onBindViewHolder(ViewHolder _holder, int _position) {
    LayoutBlocksHolderListItemBinding binding =
        LayoutBlocksHolderListItemBinding.bind(_holder.itemView);
    binding.holderName.setText(list.get(_position).getName());
    int blocksCount = list.get(_position).getBlocks().size();
    if (blocksCount == 0) {
      binding.blocksCount.setText(activity.getString(R.string.no_blocks_yet));
    } else {
      binding.blocksCount.setText(
          String.valueOf(blocksCount).concat(activity.getString(R.string.blocks)));
    }
    binding.color.setBackgroundColor(Color.parseColor(list.get(_position).getColor()));
    binding
        .getRoot()
        .setOnClickListener(
            (view) -> {
              Intent blockManager = new Intent();
              blockManager.putExtra("BlocksHolder", list.get(_position).getName());
              blockManager.setClass(activity, BlockManagerActivity.class);
              activity.startActivity(blockManager);
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
