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

import android.view.ViewGroup;
import com.block.web.builder.core.Block;
import com.block.web.builder.core.ComplexBlock;
import com.block.web.builder.ui.view.blocks.BlockDefaultView;
import com.block.web.builder.ui.view.blocks.ComplexBlockView;
import java.util.ArrayList;

public class BlocksHandler {
  public static ArrayList<Block> loadBlocksIntoObject(ViewGroup view) {
    ArrayList<Block> arr = new ArrayList<Block>();
    if (view.getChildCount() != 0) {
      for (int i = 0; i < view.getChildCount(); ++i) {
        if (view.getChildAt(i) instanceof ComplexBlockView) {
          ComplexBlock complexBlock = new ComplexBlock();
          try {
            complexBlock = ((ComplexBlockView) view.getChildAt(i)).getComplexBlock().clone();
          } catch (CloneNotSupportedException e) {
          }
          complexBlock.setBlocks(
              loadBlocksIntoObject(((ComplexBlockView) view.getChildAt(i)).getBlocksView()));
          arr.add(complexBlock);
        } else if (view.getChildAt(i) instanceof BlockDefaultView) {
          try {
            Block block = ((BlockDefaultView) view.getChildAt(i)).getBlock().clone();
            if (block.getEnableSideAttachableBlock()) {
              ArrayList<Block> attachedBlocks = new ArrayList<Block>();
              for (int i2 = 0; i2 < ((BlockDefaultView) view.getChildAt(i)).getChildCount(); ++i2) {
                if (((BlockDefaultView) view.getChildAt(i)).getChildAt(i2)
                    instanceof BlockDefaultView) {
                  if (((BlockDefaultView) ((BlockDefaultView) view.getChildAt(i)).getChildAt(i2))
                          .getBlock()
                          .getBlockType()
                      == Block.BlockType.sideAttachableBlock) {
                    attachedBlocks.add(
                        ((BlockDefaultView) ((BlockDefaultView) view.getChildAt(i)).getChildAt(i2))
                            .getBlock()
                            .clone());
                  }
                }
              }
              block.setSideAttachableBlock(attachedBlocks);
            }
            arr.add(block);
          } catch (CloneNotSupportedException e) {
            arr.add(new Block());
          }
        }
      }
    }
    return arr;
  }
}
