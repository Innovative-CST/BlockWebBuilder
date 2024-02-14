/*
 *  This file is part of BlockWeb Builder.
 *
 *  BlockWeb Builder is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BlockWeb Builder is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with BlockWeb Builder.  If not, see <https://www.gnu.org/licenses/>.
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
