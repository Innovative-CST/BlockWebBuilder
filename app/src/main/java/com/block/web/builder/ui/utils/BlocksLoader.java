package com.block.web.builder.ui.utils;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.block.web.builder.R;
import com.block.web.builder.objects.Block;
import com.block.web.builder.objects.ComplexBlock;
import com.block.web.builder.ui.view.BlockDefaultView;
import com.block.web.builder.ui.view.ComplexBlockView;
import com.block.web.builder.utils.Utils;
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
                } else {
                  ((LinearLayout.LayoutParams) blockView.getLayoutParams()).setMargins(0, 0, 0, 0);
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
                } else {
                  ((LinearLayout.LayoutParams) blockView.getLayoutParams()).setMargins(0, 0, 0, 0);
                }
              }
              ((LinearLayout.LayoutParams) blockView.getLayoutParams()).width =
                  LinearLayout.LayoutParams.WRAP_CONTENT;
            }
            if (blockView.getBlock().getEnableSideAttachableBlock()) {
              for (int i2 = 0; i2 < blockView.getBlock().getSideAttachableBlock().size(); ++i2) {
                BlockDefaultView sideBlockView = new BlockDefaultView(activity);
                sideBlockView.setLanguage(language);
                sideBlockView.setEnableEdit(true);
                try {
                  sideBlockView.setBlock(
                      blockView.getBlock().getSideAttachableBlock().get(i2).clone());
                } catch (CloneNotSupportedException e) {
                  sideBlockView.setBlock(new Block());
                }
                blockView.addView(sideBlockView);
                Utils.setMargins(sideBlockView, -16, 0, 0, 0);
              }
            }
          }
        }
      }
    }
  }
}
