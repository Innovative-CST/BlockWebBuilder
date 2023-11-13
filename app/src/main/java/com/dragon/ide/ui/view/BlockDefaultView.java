package com.dragon.ide.ui.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.dragon.ide.R;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.ComplexBlock;
import com.dragon.ide.objects.DoubleComplexBlock;
import com.dragon.ide.utils.BlockContentLoader;

public class BlockDefaultView extends LinearLayout {
  public String[] returns;
  public Block block;
  public boolean enableEdit = false;
  public String language;
  public Activity activity;

  public BlockDefaultView(Activity context) {
    super(context);
    setOrientation(LinearLayout.HORIZONTAL);
    this.activity = context;
  }

  public void setBlock(Block mBlock) {
    try {
      this.block = mBlock.clone();
    } catch (CloneNotSupportedException e) {
      this.block = new Block();
    }

    returns = block.getReturns().clone();

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
    setGravity(Gravity.CENTER_VERTICAL);
    BlockContentLoader.loadBlockContent(
        block.getBlockContent(), this, block.getColor(), getLanguage(), activity, getEnableEdit());
    invalidate();
  }

  public String[] getReturns() {
    if (returns != null) {
      return this.returns;
    }
    return new String[0];
  }

  public Block getBlock() {
    return block;
  }

  public boolean getEnableEdit() {
    return this.enableEdit;
  }

  public void setEnableEdit(boolean enableEdit) {
    this.enableEdit = enableEdit;
  }

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent arg0) {
    return !getEnableEdit();
  }
}
