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

package com.block.web.builder.utils.eventeditor;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.block.web.builder.R;
import com.block.web.builder.core.Block;
import com.block.web.builder.core.ComplexBlock;
import com.block.web.builder.databinding.ActivityEventEditorBinding;
import com.block.web.builder.ui.activities.EventEditorActivity;
import com.block.web.builder.ui.view.blocks.BlockDefaultView;
import com.block.web.builder.ui.view.blocks.ComplexBlockView;
import com.block.web.builder.utils.Utils;

public class BlockDropUtils {
  public static void onDrop(
      EventEditorActivity activity,
      ActivityEventEditorBinding binding,
      View v,
      View dragView,
      String language,
      float dropX,
      float dropY,
      int index) {
    if (v.getTag() != null) {
      if (v.getTag() instanceof String) {
        if (((String) v.getTag()).equals("blockDroppingArea")) {
          if ((dragView instanceof BlockDefaultView)) {
            if (((BlockDefaultView) dragView).getBlock().getBlockType()
                == Block.BlockType.defaultBlock) {

              BlockDefaultView blockView = null;
              if (!(((BlockDefaultView) dragView).getEnableEdit())) {
                blockView = new BlockDefaultView(activity);
                blockView.setLanguage(language);
                blockView.setEnableEdit(true);
                try {
                  Block block = ((BlockDefaultView) dragView).getBlock().clone();
                  blockView.setBlock(block);
                } catch (CloneNotSupportedException e) {
                  blockView.setBlock(new Block());
                }
              } else {
                blockView = (BlockDefaultView) dragView;
                if (dragView.getParent() != null) {
                  if (((ViewGroup) dragView.getParent()).getId()
                      != R.id.relativeBlockListEditorArea) {
                    int index2 = ((ViewGroup) dragView.getParent()).indexOfChild(dragView);
                    if (index2 < index) {
                      if (((ViewGroup) dragView.getParent()).getId() != R.id.blockListEditorArea) {
                        if (index2 == 0) {
                          if (((ViewGroup) dragView.getParent()).getChildCount() > 1) {
                            if (((ViewGroup) dragView.getParent()).getChildAt(1).getLayoutParams()
                                != null) {
                              ((LinearLayout.LayoutParams)
                                      ((ViewGroup) dragView.getParent())
                                          .getChildAt(1)
                                          .getLayoutParams())
                                  .setMargins(0, 0, 0, 0);
                            }
                          }
                        }
                      }

                      if (((ViewGroup) v).getId() != R.id.relativeBlockListEditorArea) {
                        index = index - 1;
                      }
                    }
                  }
                  ((ViewGroup) blockView.getParent()).removeView(blockView);
                }
              }
              ((LinearLayout) v).addView(blockView, index);
              if (blockView.getLayoutParams() != null) {
                ((LinearLayout.LayoutParams) blockView.getLayoutParams())
                    .setMargins(
                        0,
                        Utils.dpToPx(
                            activity, EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
                        0,
                        0);
                ((LinearLayout.LayoutParams) blockView.getLayoutParams()).width =
                    LinearLayout.LayoutParams.WRAP_CONTENT;
              }
              if (v.getId() != R.id.relativeBlockListEditorArea
                  || v.getId() != R.id.blockListEditorArea) {
                if (index == 0) {
                  if (((LinearLayout.LayoutParams) blockView.getLayoutParams()) != null) {
                    ((LinearLayout.LayoutParams) blockView.getLayoutParams())
                        .setMargins(0, 0, 0, 0);
                    if (((LinearLayout) v).getChildCount() > 1) {
                      if (((LinearLayout) v).getChildAt(1).getLayoutParams() != null) {
                        ((LinearLayout.LayoutParams)
                                ((LinearLayout) v).getChildAt(1).getLayoutParams())
                            .setMargins(
                                0,
                                Utils.dpToPx(
                                    activity,
                                    EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
                                0,
                                0);
                      }
                    }
                  }
                }
              }
              activity.blockDropSoundEffect.play(
                  activity.blockDragSoundEffectId, 1.0f, 1.0f, 1, 0, 1.0f);
            }
          }

          if ((dragView instanceof ComplexBlockView)) {
            if (((ComplexBlockView) dragView).getComplexBlock().getBlockType()
                == Block.BlockType.complexBlock) {
              ComplexBlockView blockView = null;
              if (!(((ComplexBlockView) dragView).getEnableEdit())) {
                blockView = new ComplexBlockView(activity);
                blockView.setLanguage(language);
                blockView.setEnableEdit(true);
                try {
                  ComplexBlock complexBlock =
                      ((ComplexBlockView) dragView).getComplexBlock().clone();
                  blockView.setComplexBlock(complexBlock);
                } catch (CloneNotSupportedException e) {
                  blockView.setComplexBlock(new ComplexBlock());
                }
              } else {
                blockView = (ComplexBlockView) dragView;
                if (dragView.getParent() != null) {
                  if (((ViewGroup) dragView.getParent()).getId()
                      != R.id.relativeBlockListEditorArea) {
                    int index2 = ((ViewGroup) dragView.getParent()).indexOfChild(dragView);
                    if (index2 < index) {
                      if (((ViewGroup) dragView.getParent()).getId() != R.id.blockListEditorArea) {
                        if (index2 == 0) {
                          if (((ViewGroup) dragView.getParent()).getChildCount() > 1) {
                            if (((ViewGroup) dragView.getParent()).getChildAt(1).getLayoutParams()
                                != null) {
                              ((LinearLayout.LayoutParams)
                                      ((ViewGroup) dragView.getParent())
                                          .getChildAt(1)
                                          .getLayoutParams())
                                  .setMargins(0, 0, 0, 0);
                            }
                          }
                        }
                      }

                      if (((ViewGroup) v).getId() != R.id.relativeBlockListEditorArea) {
                        index = index - 1;
                      }
                    }
                  }
                  ((ViewGroup) blockView.getParent()).removeView(blockView);
                }
              }
              ((LinearLayout) v).addView(blockView, index);
              blockView.updateLayout();
              if (blockView.getLayoutParams() != null) {
                ((LinearLayout.LayoutParams) blockView.getLayoutParams())
                    .setMargins(
                        0,
                        Utils.dpToPx(
                            activity, EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
                        0,
                        0);
                ((LinearLayout.LayoutParams) blockView.getLayoutParams()).width =
                    LinearLayout.LayoutParams.WRAP_CONTENT;
              }

              if (v.getId() != R.id.relativeBlockListEditorArea
                  || v.getId() != R.id.blockListEditorArea) {
                if (index == 0) {
                  if (((LinearLayout.LayoutParams) blockView.getLayoutParams()) != null) {
                    ((LinearLayout.LayoutParams) blockView.getLayoutParams())
                        .setMargins(0, 0, 0, 0);
                    if (((LinearLayout) v).getChildCount() > 1) {
                      if (((LinearLayout) v).getChildAt(1).getLayoutParams() != null) {
                        ((LinearLayout.LayoutParams)
                                ((LinearLayout) v).getChildAt(1).getLayoutParams())
                            .setMargins(
                                0,
                                Utils.dpToPx(
                                    activity,
                                    EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
                                0,
                                0);
                      }
                    }
                  }
                }
              }
              activity.blockDropSoundEffect.play(
                  activity.blockDragSoundEffectId, 1.0f, 1.0f, 1, 0, 1.0f);
            }
          }
        } else if (((String) v.getTag()).equals("sideAttachableDropArea")) {
          if (dragView instanceof BlockDefaultView) {
            BlockDefaultView blockDefaultView = (BlockDefaultView) dragView;
            if (blockDefaultView.getBlock().getBlockType() == Block.BlockType.sideAttachableBlock
                || blockDefaultView.getBlock().getBlockType() == Block.BlockType.defaultBlock) {
              BlockDefaultView attachableBlockView = null;
              if (index == 0) {
                index = 1;
              }
              if (blockDefaultView.getEnableEdit()) {
                attachableBlockView = blockDefaultView;
                if (attachableBlockView.getParent() != null) {
                  int index2 =
                      ((ViewGroup) attachableBlockView.getParent())
                          .indexOfChild(attachableBlockView);
                  if (index2 < index) {
                    if (((ViewGroup) attachableBlockView.getParent()).getId()
                        != R.id.relativeBlockListEditorArea) {
                      index = index - 1;
                    }
                  }
                  ((ViewGroup) attachableBlockView.getParent()).removeView(attachableBlockView);
                }
              } else {
                attachableBlockView = new BlockDefaultView(activity);
                attachableBlockView.setLanguage(language);
                attachableBlockView.setEnableEdit(true);
                attachableBlockView.setBlock(blockDefaultView.getBlock());
              }
              ((ViewGroup) v).addView(attachableBlockView, index);
              Utils.setMargins(
                  attachableBlockView,
                  Utils.dpToPx(activity, EventEditorActivity.BlocksMargin.sideAttachableBlock),
                  0,
                  0,
                  0);
              activity.blockDropSoundEffect.play(
                  activity.blockDragSoundEffectId, 1.0f, 1.0f, 1, 0, 1.0f);
            }
          }
        }
      } else if (v.getTag() instanceof String[]) {
        for (String str : (String[]) v.getTag()) {
          if (str.equals("boolean") || str.equals("int")) {
            BlockDefaultView blockView = new BlockDefaultView(activity);
            blockView.setLanguage(language);
            blockView.setEnableEdit(true);
            try {
              Block block = ((BlockDefaultView) dragView).getBlock().clone();
              blockView.setBlock(block);
            } catch (CloneNotSupportedException e) {
              blockView.setBlock(new Block());
            }
            if (((ViewGroup) v).getChildCount() != 0) {
              View view = ((ViewGroup) v).getChildAt(0);
              if (((ViewGroup) view).getParent() != null) {
                ((ViewGroup) ((ViewGroup) view).getParent()).removeView(view);
              }
              binding.relativeBlockListEditorArea.addView(view);
              FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
              lp.setMargins(
                  (int) dropX + binding.relativeBlockListEditorArea.getScrollX(),
                  (int) dropY + binding.relativeBlockListEditorArea.getScrollY(),
                  0,
                  0);
              view.setLayoutParams(lp);
            }

            if (((BlockDefaultView) dragView).getEnableEdit()) {
              if (((BlockDefaultView) dragView).getParent() != null) {
                ((ViewGroup) ((BlockDefaultView) dragView).getParent()).removeView(dragView);
              }
            }

            ((ViewGroup) v).addView(blockView);
            if (blockView.getLayoutParams() != null) {
              ((LinearLayout.LayoutParams) blockView.getLayoutParams()).width =
                  LinearLayout.LayoutParams.WRAP_CONTENT;
            }
            activity.blockDropSoundEffect.play(
                activity.blockDragSoundEffectId, 1.0f, 1.0f, 1, 0, 1.0f);
          }
        }
      }
    } else if (v.getId() == R.id.relativeBlockListEditorArea) {
      if ((dragView instanceof BlockDefaultView)) {
        BlockDefaultView blockView = null;
        blockView = new BlockDefaultView(activity);
        blockView.setLanguage(language);
        blockView.setEnableEdit(true);
        try {
          Block block = ((BlockDefaultView) dragView).getBlock().clone();
          blockView.setBlock(block);
        } catch (CloneNotSupportedException e) {
          blockView.setBlock(new Block());
        }
        if (((BlockDefaultView) dragView).getEnableEdit()) {
          if (dragView.getParent() != null) {
            ((ViewGroup) dragView.getParent()).removeView(dragView);
          }
        }
        ((FrameLayout) v).addView(blockView);
        if (blockView.getLayoutParams() != null) {
          blockView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
          blockView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
          blockView.requestLayout();
          ((FrameLayout.LayoutParams) blockView.getLayoutParams())
              .setMargins(
                  (int) dropX
                      + binding.relativeBlockListEditorArea.getScrollX()
                      - ((8
                          * (blockView.getWidth()
                              + blockView.getPaddingLeft()
                              + blockView.getPaddingRight()))),
                  (int) dropY
                      + binding.relativeBlockListEditorArea.getScrollY()
                      - ((2
                          * (blockView.getHeight()
                              + blockView.getPaddingTop()
                              + blockView.getPaddingBottom()))),
                  0,
                  0);
        }
        activity.blockDropSoundEffect.play(activity.blockDragSoundEffectId, 1.0f, 1.0f, 1, 0, 1.0f);
      }

      if ((dragView instanceof ComplexBlockView)) {
        if (((ComplexBlockView) dragView).getComplexBlock().getBlockType()
            == Block.BlockType.complexBlock) {
          ComplexBlockView blockView = null;
          blockView = new ComplexBlockView(activity);
          blockView.setLanguage(language);
          blockView.setEnableEdit(true);
          try {
            ComplexBlock complexBlock = ((ComplexBlockView) dragView).getComplexBlock().clone();
            blockView.setComplexBlock(complexBlock);
          } catch (CloneNotSupportedException e) {
            blockView.setComplexBlock(new ComplexBlock());
          }
          if (((ComplexBlockView) dragView).getEnableEdit()) {
            if (dragView.getParent() != null) {
              ((ViewGroup) dragView.getParent()).removeView(dragView);
            }
          }
          ((FrameLayout) v).addView(blockView);
          if (blockView.getLayoutParams() != null) {
            blockView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            blockView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            blockView.requestLayout();
            ((FrameLayout.LayoutParams) blockView.getLayoutParams())
                .setMargins(
                    (int) dropX
                        + binding.relativeBlockListEditorArea.getScrollX()
                        - ((8
                            * (blockView.getWidth()
                                + blockView.getPaddingLeft()
                                + blockView.getPaddingRight()))),
                    (int) dropY
                        + binding.relativeBlockListEditorArea.getScrollY()
                        - ((2
                            * (blockView.getHeight()
                                + blockView.getPaddingTop()
                                + blockView.getPaddingBottom()))),
                    0,
                    0);
          }
          activity.blockDropSoundEffect.play(
              activity.blockDragSoundEffectId, 1.0f, 1.0f, 1, 0, 1.0f);
        }
      }
    }
  }
}
