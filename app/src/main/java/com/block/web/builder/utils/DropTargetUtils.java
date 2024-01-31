package com.block.web.builder.utils;

import android.view.View;
import android.view.ViewGroup;
import com.block.web.builder.R;
import com.block.web.builder.core.Block;

public class DropTargetUtils {
  public static void addDragTarget(
      ViewGroup view, View.OnDragListener listener, String returns, int type) {
    for (int i = 0; i < view.getChildCount(); ++i) {
      if (view.getChildAt(i) instanceof ViewGroup) {
        addDragTarget((ViewGroup) view.getChildAt(i), listener, returns, type);
      }
    }
    boolean isDropable = false;
    if (type == Block.BlockType.defaultBlock
        || type == Block.BlockType.complexBlock
        || type == Block.BlockType.doubleComplexBlock) {
      if (view.getTag() != null) {
        if (view.getTag() instanceof String) {
          if (((String) view.getTag()).equals("blockDroppingArea")) {
            view.setOnDragListener(listener);
            isDropable = true;
          }
        }
      }
    } else if (type == Block.BlockType.returnWithTypeBoolean) {
      if (view.getTag() != null) {
        if (view.getTag() instanceof String[]) {
          boolean containsTargetString = false;

          for (String str : (String[]) view.getTag()) {
            if (str.equals(returns)) {
              if (str.equals("boolean")) {
                containsTargetString = true;
                break;
              }
            }
          }
          if (containsTargetString) {
            view.setOnDragListener(listener);
            isDropable = true;
          }
        }
      }
    } else if (type == Block.BlockType.sideAttachableBlock) {
      if (view.getTag() != null) {
        if (view.getTag() instanceof String) {
          if (((String) view.getTag()).equals("sideAttachableDropArea")) {
            view.setOnDragListener(listener);
            isDropable = true;
          }
        }
      }
    }
    if (view.getId() == R.id.relativeBlockListEditorArea) {
      view.setOnDragListener(listener);
      isDropable = true;
    }
    if (!isDropable) {
      view.setOnDragListener(null);
    }
  }
}
