package com.dragon.ide.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dragon.ide.R;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.ComplexBlock;
import com.dragon.ide.objects.ComplexBlockContent;
import com.dragon.ide.objects.DoubleComplexBlock;
import com.dragon.ide.objects.blockcontent.SourceContent;

public class BlockDefaultView extends LinearLayout {
  public String returns;

  public BlockDefaultView(Context context) {
    super(context);
    setOrientation(LinearLayout.HORIZONTAL);
  }

  public void setBlock(Block block) {
    returns = block.getReturns();

    if (!(block instanceof DoubleComplexBlock) && !(block instanceof ComplexBlock)) {
      if (block instanceof Block) {
        if (block.getBlockType() == Block.BlockType.defaultBlock) {
          setBackgroundResource(R.drawable.block_default);

          Drawable backgroundDrawable = getBackground();
          backgroundDrawable.setTint(Color.parseColor(block.getColor()));
          backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
          setBackground(backgroundDrawable);
        }
      }
    }
    for (int i = 0; i < block.getBlockContent().size(); ++i) {
      if (block.getBlockContent().get(i) instanceof ComplexBlockContent) {
        if (block.getBlockContent().get(i) instanceof SourceContent) {
          LinearLayout ll_source = new LinearLayout(getContext());
          ll_source.setPadding(2, 2, 2, 2);
          setBackgroundColor(Color.WHITE);
          TextView tvTextContent = new TextView(getContext());
          tvTextContent.setText(((SourceContent) block.getBlockContent().get(i)).getValue());
          ll_source.addView(tvTextContent, getChildCount());
          addView(ll_source, getChildCount());
        }

        if (block.getBlockContent().get(i) instanceof SourceContent) {
          LinearLayout ll_source = new LinearLayout(getContext());
          ll_source.setPadding(2, 2, 2, 2);
          setBackgroundColor(Color.WHITE);
          TextView tvTextContent = new TextView(getContext());
          tvTextContent.setText(((SourceContent) block.getBlockContent().get(i)).getValue());
          ll_source.addView(tvTextContent, getChildCount());
          addView(ll_source, getChildCount());
        }
      } else if (block.getBlockContent().get(i) instanceof BlockContent) {
        TextView tvTextContent = new TextView(getContext());
        tvTextContent.setText(((BlockContent) block.getBlockContent().get(i)).getText());
        addView(tvTextContent, getChildCount());
      }
    }
  }

  public String getReturns() {
    if (returns != null) {
      return this.returns;
    }
    return "";
  }
}
