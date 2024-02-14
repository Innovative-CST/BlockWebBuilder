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
