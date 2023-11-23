package com.dragon.ide.utils;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.Toast;
import com.dragon.ide.MyApplication;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.ComplexBlock;
import com.dragon.ide.objects.Event;
import com.dragon.ide.ui.view.BlockDefaultView;
import com.dragon.ide.ui.view.ComplexBlockView;
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
