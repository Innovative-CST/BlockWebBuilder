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
    } else if (type == Block.BlockType.returnWithTypeInteger) {
      if (view.getTag() != null) {
        if (view.getTag() instanceof String[]) {
          boolean containsTargetString = false;

          for (String str : (String[]) view.getTag()) {
            if (str.equals(returns)) {
              if (str.equals("int")) {
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
