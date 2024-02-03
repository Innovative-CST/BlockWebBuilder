package com.block.web.builder.ui.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.block.web.builder.R;
import com.block.web.builder.ui.activities.EventEditorActivity;
import com.block.web.builder.ui.view.blocks.BlockHint;
import com.block.web.builder.utils.Utils;

public final class BlockHintHandler {

  public static void handleAddBlockHint(final View v, int index, BlockHint blockHint, Activity activity) {
    if (v.getTag() != null) {
      if (v.getTag() instanceof String) {
        if (((String) v.getTag()).equals("blockDroppingArea")) {
          blockHint.setBlockResource(R.drawable.block_default);
          ((ViewGroup) v).addView(blockHint, index);
          Utils.setMargins(
              blockHint,
              0,
              Utils.dpToPx(activity, EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
              0,
              0);
          if (v.getId() != R.id.blockListEditorArea
              || v.getId() != R.id.relativeBlockListEditorArea) {
            if (index == 0) {
              if (blockHint.getLayoutParams() != null) {
                Utils.setMargins(blockHint, 0, 0, 0, 0);
                if (((LinearLayout) v).getChildCount() > 1) {
                  Utils.setMargins(
                      ((LinearLayout) v).getChildAt(1),
                      0,
                      Utils.dpToPx(activity, EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
                      0,
                      0);
                }
              }
            }
          }
          if (((LinearLayout.LayoutParams) blockHint.getLayoutParams()) != null) {
            ((LinearLayout.LayoutParams) blockHint.getLayoutParams()).width =
                LinearLayout.LayoutParams.WRAP_CONTENT;
          }
        } else if (((String) v.getTag()).equals("sideAttachableDropArea")) {
          blockHint.setBlockResource(R.drawable.side_attachable);
          if (v.getId() != R.id.relativeBlockListEditorArea) {
            if (index == 0) {
              index = 1;
            }
          }
          ((ViewGroup) v).addView(blockHint, index);
          Utils.setMargins(
              blockHint,
              Utils.dpToPx(activity, EventEditorActivity.BlocksMargin.sideAttachableBlock),
              0,
              0,
              0);
          if (((LinearLayout.LayoutParams) blockHint.getLayoutParams()) != null) {
            ((LinearLayout.LayoutParams) blockHint.getLayoutParams()).height =
                LinearLayout.LayoutParams.MATCH_PARENT;
          }
        }
      } else if (v.getTag() instanceof String[]) {
        for (String str : (String[]) v.getTag()) {
          ((ViewGroup) v).addView(blockHint, 0);
          if (str.equals("boolean")) {
            blockHint.setBlockResource(R.drawable.block_boolean);
            if (((ViewGroup) v).getChildCount() > 0) {
              ((ViewGroup) v).getChildAt(0).setVisibility(View.GONE);
            }
            if (v.getParent() != null) {
              ((ViewGroup) blockHint.getParent()).removeView(blockHint);
            }
            ((ViewGroup) v).addView(blockHint, 0);
          }
        }
        if (((LinearLayout.LayoutParams) blockHint.getLayoutParams()) != null) {
          ((LinearLayout.LayoutParams) blockHint.getLayoutParams()).width =
              LinearLayout.LayoutParams.WRAP_CONTENT;
        }
      }
    }
  }

  public static void handleRemoveHint(ViewGroup targetView, BlockHint blockHint) {

    ViewGroup blockHintParent = ((ViewGroup) blockHint.getParent());

    if (targetView.getTag() != null) {
      if (targetView.getTag() instanceof String) {
        if (((String) targetView.getTag()).equals("blockDroppingArea")) {
          if (blockHint.getParent() != null) {
            if (blockHintParent.getChildCount() > 1) {
              if (blockHintParent.getChildAt(0).getTag() != null) {
                if (blockHintParent.getChildAt(0).getTag() instanceof String) {
                  if (blockHintParent.getChildAt(0).getTag().equals("shadow")) {
                    if (blockHintParent.getId() != R.id.relativeBlockListEditorArea) {
                      if (blockHintParent.getChildAt(1).getLayoutParams() != null) {
                        Utils.setMargins(blockHintParent.getChildAt(1), 0, 0, 0, 0);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      } else if (targetView.getTag() instanceof String[]) {
        for (String str : (String[]) targetView.getTag()) {
          if (str.equals("boolean")) {
            if (targetView.getChildCount() == 2) {
              targetView.getChildAt(1).setVisibility(View.VISIBLE);
            }
          }
        }
      }
    }
    if (blockHint.getParent() != null) {
      blockHintParent.removeView(blockHint);
    }
  }
}
