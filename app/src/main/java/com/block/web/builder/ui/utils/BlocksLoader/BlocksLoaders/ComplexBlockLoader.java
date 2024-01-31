package com.block.web.builder.ui.utils.BlocksLoader.BlocksLoaders;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.block.web.builder.R;
import com.block.web.builder.core.Block;
import com.block.web.builder.core.ComplexBlock;
import com.block.web.builder.ui.activities.EventEditorActivity;
import com.block.web.builder.ui.utils.BlocksLoader.BlocksLoader;
import com.block.web.builder.ui.view.blocks.ComplexBlockView;
import com.block.web.builder.utils.Utils;

public class ComplexBlockLoader {
  public static void loadComplexBlock(
      ViewGroup view, ComplexBlock complexBlock, String language, Activity activity, int position) {
    if (complexBlock.getBlockType() == Block.BlockType.complexBlock) {
      ComplexBlockView blockView = new ComplexBlockView(activity);
      blockView.setLanguage(language);
      blockView.setEnableEdit(true);
      try {
        blockView.setComplexBlock(complexBlock.clone());
      } catch (CloneNotSupportedException e) {
        blockView.setComplexBlock(new ComplexBlock());
      }
      view.addView(blockView);
      if (view.getId() == R.id.blockListEditorArea) {
        BlocksLoader.setAboveMargin(
            blockView,
            Utils.dpToPx(activity, EventEditorActivity.BlocksMargin.defaultBlockAboveMargin));
      } else {
        if (position != 0) {
          BlocksLoader.setAboveMargin(
              blockView,
              Utils.dpToPx(activity, EventEditorActivity.BlocksMargin.defaultBlockAboveMargin));
        } else {
          BlocksLoader.setAboveMargin(blockView, 0);
        }
      }
      BlocksLoader.setViewWidth(blockView, LinearLayout.LayoutParams.WRAP_CONTENT);
      BlocksLoader.loadBlockViews(
          blockView.getBlocksView(),
          blockView.getComplexBlock().getBlocks(),
          blockView.getLanguage(),
          activity);
    }
  }
}
