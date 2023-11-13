package com.dragon.ide.ui.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import com.dragon.ide.R;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.ComplexBlock;
import com.dragon.ide.objects.DoubleComplexBlock;
import com.dragon.ide.utils.BlockContentLoader;

public class ComplexBlockView extends LinearLayout {
  public ComplexBlock block;
  public boolean enableEdit = false;
  public String language;
  public Activity activity;
  public LinearLayout blocksView;

  public ComplexBlockView(Activity context) {
    super(context);
    setOrientation(LinearLayout.VERTICAL);
    this.activity = context;
  }

  public void setComplexBlock(ComplexBlock mBlock) {
    try {
      this.block = mBlock.clone();
    } catch (CloneNotSupportedException e) {
      this.block = new ComplexBlock();
    }

    if (!(block instanceof DoubleComplexBlock)) {
      if (block instanceof ComplexBlock) {
        if (block.getBlockType() == Block.BlockType.complexBlock) {
          LinearLayout blockContent = new LinearLayout(getContext());
          blockContent.setBackgroundResource(R.drawable.complex_block);

          Drawable backgroundDrawable = blockContent.getBackground();
          backgroundDrawable.setTint(Color.parseColor(block.getColor()));
          backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
          blockContent.setBackground(backgroundDrawable);

          BlockContentLoader.loadBlockContent(
              block.getBlockContent(),
              blockContent,
              block.getColor(),
              getLanguage(),
              activity,
              getEnableEdit());

          addView(blockContent);
          blockContent.setGravity(Gravity.CENTER_VERTICAL);
          blocksView =
              new LinearLayout(getContext()) {
                @Override
                public void addView(View arg0, int arg1) {
                  super.addView(arg0, arg1);
                  if (getBlocksView().getChildCount() == 0) {
                    if (getBlocksView().getLayoutParams() != null) {
                      ((LinearLayout.LayoutParams) getBlocksView().getLayoutParams())
                          .setMargins(0, -10, 0, 0);
                    }
                  } else {
                    if (getBlocksView().getLayoutParams() != null) {
                      ((LinearLayout.LayoutParams) getBlocksView().getLayoutParams())
                          .setMargins(0, -26, 0, 0);
                    }
                  }
                }

                @Override
                public void addView(View arg0) {
                  super.addView(arg0);
                  if (getBlocksView().getChildCount() == 0) {
                    if (getBlocksView().getLayoutParams() != null) {
                      ((LinearLayout.LayoutParams) getBlocksView().getLayoutParams())
                          .setMargins(0, -10, 0, 0);
                    }
                  } else {
                    if (getBlocksView().getLayoutParams() != null) {
                      ((LinearLayout.LayoutParams) getBlocksView().getLayoutParams())
                          .setMargins(0, -26, 0, 0);
                    }
                  }
                }

                @Override
                public void removeView(View arg0) {
                  super.removeView(arg0);
                  if (getBlocksView().getChildCount() == 0) {
                    if (getBlocksView().getLayoutParams() != null) {
                      ((LinearLayout.LayoutParams) getBlocksView().getLayoutParams())
                          .setMargins(0, -10, 0, 0);
                    }
                  } else {
                    if (getBlocksView().getLayoutParams() != null) {
                      ((LinearLayout.LayoutParams) getBlocksView().getLayoutParams())
                          .setMargins(0, -26, 0, 0);
                    }
                  }
                }
              };
          blocksView.setTag("blockDropN");
          blocksView.setBackgroundResource(R.drawable.complex_block_bottom);

          Drawable blocksViewBackgroundDrawable = blocksView.getBackground();
          blocksViewBackgroundDrawable.setTint(Color.parseColor(block.getColor()));
          blocksViewBackgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
          blocksView.setBackground(blocksViewBackgroundDrawable);

          blocksView.setOrientation(LinearLayout.VERTICAL);
          addView(blocksView);

          if (getEnableEdit()) {
            if (getBlocksView().getChildCount() == 0) {
              if (blocksView.getLayoutParams() != null) {
                ((LinearLayout.LayoutParams) blocksView.getLayoutParams()).setMargins(0, -10, 0, 0);
              }
            } else {
              if (blocksView.getLayoutParams() != null) {
                ((LinearLayout.LayoutParams) blocksView.getLayoutParams()).setMargins(0, -26, 0, 0);
              }
            }
          } else {
            if (blocksView.getLayoutParams() != null) {
              ((LinearLayout.LayoutParams) blocksView.getLayoutParams()).setMargins(0, -10, 0, 0);
            }
          }
          if (getBlocksView().getChildCount() == 0) {
            if (getBlocksView().getLayoutParams() != null) {
              ((LinearLayout.LayoutParams) getBlocksView().getLayoutParams())
                  .setMargins(0, -10, 0, 0);
            }
          }

          blocksView.setPadding(
              blocksView.getPaddingLeft() + 3,
              blocksView.getPaddingTop(),
              blocksView.getPaddingRight(),
              blocksView.getPaddingBottom());
        }
      }
    }
    invalidate();
  }

  public ComplexBlock getComplexBlock() {
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

  public LinearLayout getBlocksView() {
    return this.blocksView;
  }

  public void setBlocksView(LinearLayout blocksView) {
    this.blocksView = blocksView;
  }
}
