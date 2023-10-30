package com.dragon.ide.utils;

import android.view.ViewGroup;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.Event;
import com.dragon.ide.ui.view.BlockDefaultView;
import com.dragon.ide.ui.view.ComplexBlockView;
import java.util.ArrayList;

public class BlocksHandler {
  public static void loadBlocksIntoObjects(ViewGroup view, Event event) {
    ArrayList<Block> arr = new ArrayList<Block>();
    for (int i = 0; i < view.getChildCount(); ++i) {
      if (view.getChildAt(i) instanceof BlockDefaultView) {
        arr.add(((BlockDefaultView) view.getChildAt(i)).getBlock());
      } else if (view.getChildAt(i) instanceof ComplexBlockView) {
        arr.add(((ComplexBlockView) view.getChildAt(i)).getComplexBlock());
      }
    }
    event.setBlocks(arr);
  }
}
