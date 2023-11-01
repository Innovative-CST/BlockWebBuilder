package com.dragon.ide.utils;

import android.app.Activity;
import android.view.ViewGroup;
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
            arr.add(((BlockDefaultView) view.getChildAt(i)).getBlock().clone());
          } catch (CloneNotSupportedException e) {
            arr.add(new Block());
          }
        }
      }
    }
    return arr;
  }
}
