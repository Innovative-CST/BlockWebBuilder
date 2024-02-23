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

package com.block.web.builder.ui.view.blocks;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.blankj.utilcode.util.VibrateUtils;
import com.block.web.builder.R;
import com.block.web.builder.core.Block;
import com.block.web.builder.core.ComplexBlock;
import com.block.web.builder.core.DoubleComplexBlock;
import com.block.web.builder.ui.activities.EventEditorActivity;
import com.block.web.builder.utils.BlockContentLoader;
import com.block.web.builder.utils.DropTargetUtils;
import com.block.web.builder.utils.Utils;

public class ComplexBlockView extends LinearLayout {
  public ComplexBlock block;
  public boolean enableEdit = false;
  public String language;
  public Activity activity;
  public LinearLayout blocksView;
  public LinearLayout blockContent;
  public LinearLayout blockBottomView;

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
          blockBottomView = new LinearLayout(getContext());
          blockContent = new LinearLayout(getContext());
          blockContent
              .getViewTreeObserver()
              .addOnGlobalLayoutListener(
                  () -> {
                    ViewGroup.LayoutParams layoutParams = blockBottomView.getLayoutParams();
                    layoutParams.width = blockContent.getWidth();
                    blockBottomView.setLayoutParams(layoutParams);
                  });

          if (!block.getEnableSideAttachableBlock()) {
            Drawable backgroundDrawable = getResources().getDrawable(R.drawable.complex_block);
            backgroundDrawable.setTint(Color.parseColor(block.getColor()));
            backgroundDrawable.setTintMode(PorterDuff.Mode.MULTIPLY);
            blockContent.setBackground(backgroundDrawable);
          }

          BlockContentLoader.loadBlockContent(
              block.getBlockContent(),
              blockContent,
              block.getColor(),
              getLanguage(),
              activity,
              getEnableEdit());

          addView(blockContent);

          if (blockContent.getLayoutParams() != null) {
            blockContent.getLayoutParams().width = -2;
            blockContent.getLayoutParams().height = -2;
          }
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
                          .setMargins(
                              0,
                              Utils.dpToPx(
                                  getContext(),
                                  EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
                              0,
                              0);
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
                          .setMargins(
                              0,
                              Utils.dpToPx(
                                  getContext(),
                                  EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
                              0,
                              0);
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
                          .setMargins(
                              0,
                              Utils.dpToPx(
                                  getContext(),
                                  EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
                              0,
                              0);
                    }
                  }
                }
              };
          blocksView.setTag("blockDroppingArea");
          blocksView.setBackgroundResource(R.drawable.block_joint);

          Drawable blocksViewBackgroundDrawable = blocksView.getBackground();
          blocksViewBackgroundDrawable.setTint(Color.parseColor(block.getColor()));
          blocksViewBackgroundDrawable.setTintMode(PorterDuff.Mode.MULTIPLY);
          blocksView.setBackground(blocksViewBackgroundDrawable);
          blocksView.setMinimumHeight(EventEditorActivity.BlocksMargin.bottomBlockHeight);

          blocksView.setOrientation(LinearLayout.VERTICAL);
          addView(blocksView);
          if (blocksView.getLayoutParams() != null) {
            blocksView.getLayoutParams().width = -2;
            blocksView.getLayoutParams().height = -2;
          }

          Drawable blockBottomViewDrawable =
              getResources().getDrawable(R.drawable.complex_block_bottom);
          blockBottomViewDrawable.setTint(Color.parseColor(block.getColor()));
          blockBottomViewDrawable.setTintMode(PorterDuff.Mode.MULTIPLY);
          blockBottomView.setBackground(blockBottomViewDrawable);
          addView(blockBottomView);

          if (blockBottomView.getLayoutParams() != null) {
            ((LinearLayout.LayoutParams) blockBottomView.getLayoutParams())
                .setMargins(
                    0,
                    Utils.dpToPx(
                        getContext(), EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
                    0,
                    0);
          }

          if (getEnableEdit()) {
            if (getBlocksView().getChildCount() == 0) {
              if (blocksView.getLayoutParams() != null) {
                ((LinearLayout.LayoutParams) blocksView.getLayoutParams()).setMargins(0, -10, 0, 0);
              }
            } else {
              if (blocksView.getLayoutParams() != null) {
                ((LinearLayout.LayoutParams) blocksView.getLayoutParams())
                    .setMargins(
                        0,
                        Utils.dpToPx(
                            getContext(), EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
                        0,
                        0);
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
        }
      }
    }
    if (activity instanceof EventEditorActivity) {
      setOnLongClickListener(
          (view) -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadow = new View.DragShadowBuilder(this);

            DropTargetUtils.addDragTarget(
                ((EventEditorActivity) activity).binding.relativeBlockListEditorArea,
                (EventEditorActivity) activity,
                block.getReturns(),
                block.getBlockType());

            getBlocksView().setOnDragListener(null);

            if (Build.VERSION.SDK_INT >= 24) {
              startDragAndDrop(data, shadow, this, 1);
            } else {
              startDrag(data, shadow, this, 1);
            }

            VibrateUtils.vibrate(100);
            ((EventEditorActivity) activity)
                .blockDragSoundEffect.play(
                    ((EventEditorActivity) activity).blockDragSoundEffectId,
                    1.0f,
                    1.0f,
                    1,
                    0,
                    1.0f);
            return false;
          });
    }
    invalidate();
  }

  public void updateLayout() {
    blocksView.setMinimumWidth(blockContent.getWidth());
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
