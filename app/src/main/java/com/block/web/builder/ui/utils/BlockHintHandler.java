package com.block.web.builder.ui.utils;

import android.view.View;
import android.view.ViewGroup;
import com.block.web.builder.R;
import com.block.web.builder.ui.view.blocks.BlockHint;
import com.block.web.builder.utils.Utils;

public final class BlockHintHandler {

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
