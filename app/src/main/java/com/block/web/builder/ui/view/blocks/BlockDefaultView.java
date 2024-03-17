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

package com.block.web.builder.ui.view.blocks;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.blankj.utilcode.util.VibrateUtils;
import com.block.web.builder.R;
import com.block.web.builder.core.Block;
import com.block.web.builder.core.ComplexBlock;
import com.block.web.builder.core.DoubleComplexBlock;
import com.block.web.builder.ui.activities.EventEditorActivity;
import com.block.web.builder.utils.BlockContentLoader;
import com.block.web.builder.utils.DropTargetUtils;

public class BlockDefaultView extends LinearLayout {
  public String returns;
  public Block block;
  public boolean enableEdit = false;
  public String language;
  public Activity activity;
  public LinearLayout blockContent;

  public BlockDefaultView(Activity context) {
    super(context);
    setOrientation(LinearLayout.HORIZONTAL);
    this.activity = context;
  }

  public void setBlock(Block mBlock) {
    try {
      this.block = mBlock.clone();
    } catch (CloneNotSupportedException e) {
      this.block = new Block();
    }

    returns = new String(block.getReturns());

    blockContent = new LinearLayout(getContext());

    if (!(block instanceof DoubleComplexBlock) && !(block instanceof ComplexBlock)) {
      if (block instanceof Block) {
        if (block.getBlockType() == Block.BlockType.defaultBlock) {
          if (!block.getEnableSideAttachableBlock()) {
            Drawable backgroundDrawable = getResources().getDrawable(R.drawable.block_default);
            backgroundDrawable.setTint(Color.parseColor(block.getColor()));
            backgroundDrawable.setTintMode(PorterDuff.Mode.MULTIPLY);
            blockContent.setBackground(backgroundDrawable);
          } else {
            setTag("sideAttachableDropArea");
            Drawable backgroundDrawable =
                getResources().getDrawable(R.drawable.default_block_attachable);
            backgroundDrawable.setTint(Color.parseColor(block.getColor()));
            backgroundDrawable.setTintMode(PorterDuff.Mode.MULTIPLY);
            blockContent.setBackground(backgroundDrawable);
          }
        } else if (block.getBlockType() == Block.BlockType.returnWithTypeBoolean) {
          Drawable backgroundDrawable = getResources().getDrawable(R.drawable.block_boolean);
          backgroundDrawable.setTint(Color.parseColor(block.getColor()));
          backgroundDrawable.setTintMode(PorterDuff.Mode.MULTIPLY);
          blockContent.setBackground(backgroundDrawable);
        } else if (block.getBlockType() == Block.BlockType.sideAttachableBlock) {
          Drawable backgroundDrawable = getResources().getDrawable(R.drawable.side_attachable);
          backgroundDrawable.setTint(Color.parseColor(block.getColor()));
          backgroundDrawable.setTintMode(PorterDuff.Mode.MULTIPLY);
          blockContent.setBackground(backgroundDrawable);
        } else if (block.getBlockType() == Block.BlockType.returnWithTypeInteger) {
          Drawable backgroundDrawable = getResources().getDrawable(R.drawable.number);
          backgroundDrawable.setTint(Color.parseColor(block.getColor()));
          backgroundDrawable.setTintMode(PorterDuff.Mode.MULTIPLY);
          blockContent.setBackground(backgroundDrawable);
        }
      }
    }
    blockContent.setGravity(Gravity.CENTER_VERTICAL);
    BlockContentLoader.loadBlockContent(
        block.getBlockContent(),
        blockContent,
        block.getColor(),
        getLanguage(),
        activity,
        getEnableEdit());
    if (activity instanceof EventEditorActivity) {
      setOnLongClickListener(
          (view) -> {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadow = new View.DragShadowBuilder(this);

            DropTargetUtils.addDragTarget(
                ((EventEditorActivity) activity).binding.relativeBlockListEditorArea,
                (EventEditorActivity) activity,
                block.getReturns(),
                block.getBlockType());

            if (Build.VERSION.SDK_INT >= 24) {
              startDragAndDrop(data, shadow, this, 1);
            } else {
              startDrag(data, shadow, this, 1);
            }

            VibrateUtils.vibrate(100);
            ((EventEditorActivity) activity)
                .blockDragSoundEffect.play(
                    ((EventEditorActivity) activity).blockDragSoundEffectId,
                    1.0f,
                    1.0f,
                    1,
                    0,
                    1.0f);
            return false;
          });
    }
    addView(blockContent);
    ((LinearLayout.LayoutParams) blockContent.getLayoutParams()).height =
        LinearLayout.LayoutParams.MATCH_PARENT;
    invalidate();
  }

  public LinearLayout getBlockContent() {
    return this.blockContent;
  }

  public String getReturns() {
    if (returns != null) {
      return this.returns;
    }
    return new String("");
  }

  public Block getBlock() {
    return block;
  }

  public boolean getEnableEdit() {
    return this.enableEdit;
  }

  public void setEnableEdit(boolean enableEdit) {
    this.enableEdit = enableEdit;
  }

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent arg0) {
    return !getEnableEdit();
  }

  @Override
  protected void onAttachedToWindow() {
    if (block.getEnableSideAttachableBlock()) {
      if (getLayoutParams() != null) {
        getLayoutParams().height = -1;
      }
    }
    super.onAttachedToWindow();
  }
}
