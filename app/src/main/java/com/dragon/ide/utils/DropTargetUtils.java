package com.dragon.ide.utils;

import android.view.View;
import android.view.ViewGroup;
import com.dragon.ide.objects.Block;

public class DropTargetUtils {
  public static void addDragTarget(
      ViewGroup view, View.OnDragListener listener, String returns, int type) {
    for (int i = 0; i < view.getChildCount(); ++i) {
      boolean isDropable = false;
      if (view.getChildAt(i) instanceof ViewGroup) {
        addDragTarget((ViewGroup) view.getChildAt(i), listener, returns, type);
      }
      if (type == Block.BlockType.defaultBlock
          || type == Block.BlockType.complexBlock
          || type == Block.BlockType.doubleComplexBlock) {
        if (view.getChildAt(i).getTag() != null) {
          if (view.getChildAt(i).getTag() instanceof String) {
            if (((String) view.getChildAt(i).getTag()).equals("blockDroppingArea")) {
              view.getChildAt(i).setOnDragListener(listener);
              isDropable = true;
            }
          }
        }
      } else if (type == Block.BlockType.returnWithTypeBoolean) {
        if (view.getChildAt(i).getTag() != null) {
          if (view.getChildAt(i).getTag() instanceof String[]) {
            boolean containsTargetString = false;

            for (String str : (String[]) view.getChildAt(i).getTag()) {
              if (str.equals(returns)) {
                if (str.equals("boolean")) {
                  containsTargetString = true;
                  break;
                }
              }
            }
            if (containsTargetString) {
              view.getChildAt(i).setOnDragListener(listener);
              isDropable = true;
            }
          }
        }
      }
      if (!isDropable) {
        view.getChildAt(i).setOnDragListener(null);
      }
    }
  }
}
