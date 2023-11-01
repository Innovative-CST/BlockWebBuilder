package com.dragon.ide.ui.utils;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.dragon.ide.R;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.ComplexBlock;
import com.dragon.ide.ui.view.BlockDefaultView;
import com.dragon.ide.ui.view.ComplexBlockView;
import java.util.ArrayList;

public class BlocksLoader {
  public static void loadBlockViews(
      ViewGroup view, ArrayList<Block> blocks, String language, Activity activity) {
    if (blocks.size() != 0) {
      for (int i = 0; i < blocks.size(); ++i) {
        if (blocks.get(i) instanceof ComplexBlock) {
          if (blocks.get(i).getBlockType() == Block.BlockType.complexBlock) {
            ComplexBlockView blockView = new ComplexBlockView(activity);
            blockView.setLanguage(language);
            blockView.setEnableEdit(true);
            try {
              blockView.setComplexBlock((ComplexBlock) blocks.get(i).clone());
            } catch (CloneNotSupportedException e) {
              blockView.setComplexBlock(new ComplexBlock());
            }
            view.addView(blockView);
            if (blockView.getLayoutParams() != null) {
              if (view.getId() == R.id.blockListEditorArea) {
                ((LinearLayout.LayoutParams) blockView.getLayoutParams()).setMargins(0, -26, 0, 0);
              } else {
                if (i != 0) {
                  ((LinearLayout.LayoutParams) blockView.getLayoutParams())
                      .setMargins(0, -26, 0, 0);
                }
              }
              ((LinearLayout.LayoutParams) blockView.getLayoutParams()).width =
                  LinearLayout.LayoutParams.WRAP_CONTENT;
            }
            loadBlockViews(
                blockView.getBlocksView(),
                blockView.getComplexBlock().getBlocks(),
                blockView.getLanguage(),
                activity);

            if (blockView.getBlocksView().getChildCount() == 0) {
              if (blockView.getBlocksView().getLayoutParams() != null) {
                ((LinearLayout.LayoutParams) blockView.getBlocksView().getLayoutParams()).setMargins(0, -10, 0, 0);
              }
            }
          }
        } else if (blocks.get(i) instanceof Block) {
          if (blocks.get(i).getBlockType() == Block.BlockType.defaultBlock) {
            BlockDefaultView blockView = new BlockDefaultView(activity);
            blockView.setLanguage(language);
            blockView.setEnableEdit(true);
            try {
              blockView.setBlock(blocks.get(i).clone());
            } catch (CloneNotSupportedException e) {
              blockView.setBlock(new Block());
            }
            view.addView(blockView);
            if (blockView.getLayoutParams() != null) {
              if (view.getId() == R.id.blockListEditorArea) {
                ((LinearLayout.LayoutParams) blockView.getLayoutParams()).setMargins(0, -26, 0, 0);
              } else {
                if (i != 0) {
                  ((LinearLayout.LayoutParams) blockView.getLayoutParams())
                      .setMargins(0, -26, 0, 0);
                }
              }
              ((LinearLayout.LayoutParams) blockView.getLayoutParams()).width =
                  LinearLayout.LayoutParams.WRAP_CONTENT;
            }
          }
        }
      }
    }
  }
}
