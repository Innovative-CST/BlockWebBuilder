/*
 * This file is part of BlockWeb Builder [https://github.com/TS-Code-Editor/BlockWebBuilder].
 *
 * License Agreement
 * This software is licensed under the terms and conditions outlined below. By accessing, copying, modifying, or using this software in any way, you agree to abide by these terms.
 *
 * 1. **  Copy and Modification Restrictions  **
 *    - You are not permitted to copy or modify the source code of this software without the permission of the owner, which may be granted publicly on GitHub Discussions or on Discord.
 *    - If permission is granted by the owner, you may copy the software under the terms specified in this license agreement.
 *    - You are not allowed to permit others to copy the source code that you were allowed to copy by the owner.
 *    - Modified or copied code must not be further copied.
 * 2. **  Contributor Attribution  **
 *    - You must attribute the contributors by creating a visible list within the application, showing who originally wrote the source code.
 *    - If you copy or modify this software under owner permission, you must provide links to the profiles of all contributors who contributed to this software.
 * 3. **  Modification Documentation  **
 *    - All modifications made to the software must be documented and listed.
 *    - the owner may incorporate the modifications made by you to enhance this software.
 * 4. **  Consistent Licensing  **
 *    - All copied or modified files must contain the same license text at the top of the files.
 * 5. **  Permission Reversal  **
 *    - If you are granted permission by the owner to copy this software, it can be revoked by the owner at any time. You will be notified at least one week in advance of any such reversal.
 *    - In case of Permission Reversal, if you fail to acknowledge the notification sent by us, it will not be our responsibility.
 * 6. **  License Updates  **
 *    - The license may be updated at any time. Users are required to accept and comply with any changes to the license.
 *    - In such circumstances, you will be given 7 days to ensure that your software complies with the updated license.
 *    - We will not notify you about license changes; you need to monitor the GitHub repository yourself (You can enable notifications or watch the repository to stay informed about such changes).
 * By using this software, you acknowledge and agree to the terms and conditions outlined in this license agreement. If you do not agree with these terms, you are not permitted to use, copy, modify, or distribute this software.
 *
 * Copyright © 2024 Dev Kumar
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
