package com.dragon.ide.ui.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.ComplexBlock;
import com.dragon.ide.objects.DoubleComplexBlock;
import com.dragon.ide.ui.view.BlockDefaultView;
import com.dragon.ide.ui.view.ComplexBlockView;
import java.util.ArrayList;

public class BlockListAdapter extends RecyclerView.Adapter<BlockListAdapter.ViewHolder> {

  public ArrayList<Block> list;
  public Activity activity;

  public BlockListAdapter(ArrayList<Block> _arr, Activity activity) {
    list = _arr;
    this.activity = activity;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    HorizontalScrollView _v = new HorizontalScrollView(activity);
    RecyclerView.LayoutParams _lp =
        new RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    _v.setLayoutParams(_lp);
    return new ViewHolder(_v);
  }

  @Override
  public void onBindViewHolder(ViewHolder _holder, int _position) {
    HorizontalScrollView v = (HorizontalScrollView) _holder.itemView;
    v.setPadding(8, 8, 8, 8);
    if (!(list.get(_position) instanceof DoubleComplexBlock)) {

      if ((list.get(_position) instanceof ComplexBlock)) {
        if (list.get(_position).getBlockType() == Block.BlockType.complexBlock) {
          ComplexBlockView complexBlockView = new ComplexBlockView(activity);
          complexBlockView.setComplexBlock((ComplexBlock) list.get(_position));
          v.addView(complexBlockView);
        }
      } else if (list.get(_position) instanceof Block) {
        BlockDefaultView blockView = new BlockDefaultView(activity);
        blockView.setBlock(list.get(_position));
        v.addView(blockView);
      }
    }
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
