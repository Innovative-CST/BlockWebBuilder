package com.block.web.builder.ui.utils.BlocksLoader;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.block.web.builder.objects.Block;
import com.block.web.builder.objects.ComplexBlock;
import com.block.web.builder.ui.utils.BlocksLoader.BlocksLoaders.ComplexBlockLoader;
import com.block.web.builder.ui.utils.BlocksLoader.BlocksLoaders.DefaultBlockLoader;
import java.util.ArrayList;

public class BlocksLoader {
  public static void loadBlockViews(
      ViewGroup view, ArrayList<Block> blocks, String language, Activity activity) {
    if (blocks.size() != 0) {
      for (int i = 0; i < blocks.size(); ++i) {
        if (blocks.get(i) instanceof ComplexBlock) {
          ComplexBlockLoader.loadComplexBlock(
              view, ((ComplexBlock) blocks.get(i)), language, activity, i);
        } else if (blocks.get(i) instanceof Block) {
          DefaultBlockLoader.loadDefaultBlock(blocks.get(i), view, language, activity, i);
        }
      }
    }
  }

  public static void setAboveMargin(View view, int aboveMargin) {
    if (view.getLayoutParams() != null) {
      ((LinearLayout.LayoutParams) view.getLayoutParams()).setMargins(0, aboveMargin, 0, 0);
    }
  }

  public static void setLeftMargin(View view, int leftMargin) {
    if (view.getLayoutParams() != null) {
      ((LinearLayout.LayoutParams) view.getLayoutParams()).setMargins(leftMargin, 0, 0, 0);
    }
  }

  public static void setViewWidth(View view, int width) {
    if (view.getLayoutParams() != null) {
      ((LinearLayout.LayoutParams) view.getLayoutParams()).width = width;
    }
  }
}
