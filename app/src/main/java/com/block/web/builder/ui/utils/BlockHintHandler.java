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

package com.block.web.builder.ui.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.block.web.builder.R;
import com.block.web.builder.ui.activities.EventEditorActivity;
import com.block.web.builder.ui.view.blocks.BlockHint;
import com.block.web.builder.utils.Utils;

public final class BlockHintHandler {

  public static void handleAddBlockHint(
      final View v, int index, BlockHint blockHint, Activity activity) {
    if (v.getTag() != null) {
      if (v.getTag() instanceof String) {
        if (((String) v.getTag()).equals("blockDroppingArea")) {
          blockHint.setBlockResource(R.drawable.block_default);
          ((ViewGroup) v).addView(blockHint, index);
          Utils.setMargins(
              blockHint,
              0,
              Utils.dpToPx(activity, EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
              0,
              0);
          if (v.getId() != R.id.blockListEditorArea
              || v.getId() != R.id.relativeBlockListEditorArea) {
            if (index == 0) {
              if (blockHint.getLayoutParams() != null) {
                Utils.setMargins(blockHint, 0, 0, 0, 0);
                if (((LinearLayout) v).getChildCount() > 1) {
                  Utils.setMargins(
                      ((LinearLayout) v).getChildAt(1),
                      0,
                      Utils.dpToPx(
                          activity, EventEditorActivity.BlocksMargin.defaultBlockAboveMargin),
                      0,
                      0);
                }
              }
            }
          }
          if (((LinearLayout.LayoutParams) blockHint.getLayoutParams()) != null) {
            ((LinearLayout.LayoutParams) blockHint.getLayoutParams()).width =
                LinearLayout.LayoutParams.WRAP_CONTENT;
          }
        } else if (((String) v.getTag()).equals("sideAttachableDropArea")) {
          blockHint.setBlockResource(R.drawable.side_attachable);
          if (v.getId() != R.id.relativeBlockListEditorArea) {
            if (index == 0) {
              index = 1;
            }
          }
          ((ViewGroup) v).addView(blockHint, index);
          Utils.setMargins(
              blockHint,
              Utils.dpToPx(activity, EventEditorActivity.BlocksMargin.sideAttachableBlock),
              0,
              0,
              0);
          if (((LinearLayout.LayoutParams) blockHint.getLayoutParams()) != null) {
            ((LinearLayout.LayoutParams) blockHint.getLayoutParams()).height =
                LinearLayout.LayoutParams.MATCH_PARENT;
          }
        }
      } else if (v.getTag() instanceof String[]) {
        for (String str : (String[]) v.getTag()) {
          if (str.equals("boolean") || str.equals("int")) {
            blockHint.setBlockResource(
                str.equals("boolean") ? R.drawable.block_boolean : R.drawable.number);
            if (((ViewGroup) v).getChildCount() > 0) {
              ((ViewGroup) v).getChildAt(0).setVisibility(View.GONE);
            }
            if (blockHint.getParent() != null) {
              ((ViewGroup) blockHint.getParent()).removeView(blockHint);
            }
            ((ViewGroup) v).addView(blockHint, 0);
          }
        }
        if (((LinearLayout.LayoutParams) blockHint.getLayoutParams()) != null) {
          ((LinearLayout.LayoutParams) blockHint.getLayoutParams()).width =
              LinearLayout.LayoutParams.WRAP_CONTENT;
        }
      }
    }
  }

  public static void handleRemoveHint(ViewGroup targetView, BlockHint blockHint) {

    ViewGroup blockHintParent = ((ViewGroup) blockHint.getParent());

    if (targetView.getTag() != null) {
      if (targetView.getTag() instanceof String) {
        if (((String) targetView.getTag()).equals("blockDroppingArea")) {
          if (blockHint.getParent() != null) {
            if (blockHintParent.getChildCount() > 1) {
              if (blockHintParent.getChildAt(0).getTag() != null) {
                if (blockHintParent.getChildAt(0).getTag() instanceof String) {
                  if (blockHintParent.getChildAt(0).getTag().equals("shadow")) {
                    if (blockHintParent.getId() != R.id.relativeBlockListEditorArea) {
                      if (blockHintParent.getChildAt(1).getLayoutParams() != null) {
                        Utils.setMargins(blockHintParent.getChildAt(1), 0, 0, 0, 0);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      } else if (targetView.getTag() instanceof String[]) {
        for (String str : (String[]) targetView.getTag()) {
          if (str.equals("boolean") || str.equals("int")) {
            if (targetView.getChildCount() == 2) {
              targetView.getChildAt(1).setVisibility(View.VISIBLE);
            }
          }
        }
      }
    }
    if (blockHint.getParent() != null) {
      blockHintParent.removeView(blockHint);
    }
  }
}
