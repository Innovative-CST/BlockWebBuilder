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
