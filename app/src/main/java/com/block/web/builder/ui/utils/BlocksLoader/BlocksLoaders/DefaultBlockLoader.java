package com.block.web.builder.ui.utils.BlocksLoader.BlocksLoaders;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.block.web.builder.R;
import com.block.web.builder.objects.Block;
import com.block.web.builder.ui.activities.EventEditorActivity;
import com.block.web.builder.ui.utils.BlocksLoader.BlocksLoader;
import com.block.web.builder.ui.view.blocks.BlockDefaultView;
import com.block.web.builder.utils.Utils;

public class DefaultBlockLoader {

  public static void loadDefaultBlock(
      Block block, ViewGroup view, String language, Activity activity, int position) {
    if (block.getBlockType() == Block.BlockType.defaultBlock) {
      BlockDefaultView blockView = new BlockDefaultView(activity);
      blockView.setLanguage(language);
      blockView.setEnableEdit(true);
      try {
        blockView.setBlock(block.clone());
      } catch (CloneNotSupportedException e) {
        blockView.setBlock(new Block());
      }
      view.addView(blockView);
      if (view.getId() == R.id.blockListEditorArea) {
        BlocksLoader.setLeftMargin(
            blockView,
            Utils.dpToPx(activity, EventEditorActivity.BlocksMargin.defaultBlockAboveMargin));
      } else {
        if (position != 0) {
          BlocksLoader.setLeftMargin(
              blockView,
              Utils.dpToPx(activity, EventEditorActivity.BlocksMargin.defaultBlockAboveMargin));
        } else {
          BlocksLoader.setLeftMargin(blockView, 0);
        }
      }
      BlocksLoader.setViewWidth(blockView, LinearLayout.LayoutParams.WRAP_CONTENT);

      if (blockView.getBlock().getEnableSideAttachableBlock()) {
        for (int i2 = 0; i2 < blockView.getBlock().getSideAttachableBlock().size(); ++i2) {
          BlockDefaultView sideBlockView = new BlockDefaultView(activity);
          sideBlockView.setLanguage(language);
          sideBlockView.setEnableEdit(true);
          try {
            sideBlockView.setBlock(blockView.getBlock().getSideAttachableBlock().get(i2).clone());
          } catch (CloneNotSupportedException e) {
            sideBlockView.setBlock(new Block());
          }
          blockView.addView(sideBlockView);
          BlocksLoader.setLeftMargin(
              sideBlockView,
              Utils.dpToPx(activity, EventEditorActivity.BlocksMargin.sideAttachableBlock));
        }
      }
    }
  }
}
