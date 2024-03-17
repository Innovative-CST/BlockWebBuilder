/*
 * This file is part of BlockWeb Builder [https://github.com/TS-Code-Editor/BlockWebBuilder].
 *
 * License Agreement
 * This software is licensed under the terms and conditions outlined below. By accessing, copying, modifying, or using this software in any way, you agree to abide by these terms.
 *
 * 1. **  Copy and Modification Restrictions  **
 *    - You are not permitted to copy or modify the source code of this software without the permission of the owner, which may be granted publicly on GitHub Discussions or on Discord.
 *    - If permission is granted by the owner, you may copy the software under the terms specified in this license agreement.
 *    - You are not allowed to permit others to copy the source code that you were allowed to copy by the owner.
 *    - Modified or copied code must not be further copied.
 * 2. **  Contributor Attribution  **
 *    - You must attribute the contributors by creating a visible list within the application, showing who originally wrote the source code.
 *    - If you copy or modify this software under owner permission, you must provide links to the profiles of all contributors who contributed to this software.
 * 3. **  Modification Documentation  **
 *    - All modifications made to the software must be documented and listed.
 *    - the owner may incorporate the modifications made by you to enhance this software.
 * 4. **  Consistent Licensing  **
 *    - All copied or modified files must contain the same license text at the top of the files.
 * 5. **  Permission Reversal  **
 *    - If you are granted permission by the owner to copy this software, it can be revoked by the owner at any time. You will be notified at least one week in advance of any such reversal.
 *    - In case of Permission Reversal, if you fail to acknowledge the notification sent by us, it will not be our responsibility.
 * 6. **  License Updates  **
 *    - The license may be updated at any time. Users are required to accept and comply with any changes to the license.
 *    - In such circumstances, you will be given 7 days to ensure that your software complies with the updated license.
 *    - We will not notify you about license changes; you need to monitor the GitHub repository yourself (You can enable notifications or watch the repository to stay informed about such changes).
 * By using this software, you acknowledge and agree to the terms and conditions outlined in this license agreement. If you do not agree with these terms, you are not permitted to use, copy, modify, or distribute this software.
 *
 * Copyright © 2024 Dev Kumar
 */

package com.block.web.builder.ui.utils.BlocksLoader.BlocksLoaders;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.block.web.builder.R;
import com.block.web.builder.core.Block;
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
