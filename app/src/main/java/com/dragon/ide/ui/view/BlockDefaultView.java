package com.dragon.ide.ui.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dragon.ide.R;
import com.dragon.ide.listeners.ValueListener;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.ComplexBlock;
import com.dragon.ide.objects.ComplexBlockContent;
import com.dragon.ide.objects.DoubleComplexBlock;
import com.dragon.ide.objects.blockcontent.SourceContent;
import com.dragon.ide.ui.dialogs.eventeditor.ValueEditorDialog;
import com.dragon.ide.utils.BlockContentLoader;
import java.util.ArrayList;

public class BlockDefaultView extends LinearLayout {
  public String returns;
  public Block block;
  public boolean enableEdit = false;
  public String language;
  public Activity activity;

  public BlockDefaultView(Activity context) {
    super(context);
    setOrientation(LinearLayout.HORIZONTAL);
    this.activity = context;
  }

  public void setBlock(Block block) {
    this.block = block;

    returns = block.getReturns();

    if (!(block instanceof DoubleComplexBlock) && !(block instanceof ComplexBlock)) {
      if (block instanceof Block) {
        if (block.getBlockType() == Block.BlockType.defaultBlock) {
          setBackgroundResource(R.drawable.block_default);

          Drawable backgroundDrawable = getBackground();
          backgroundDrawable.setTint(Color.parseColor(block.getColor()));
          backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
          setBackground(backgroundDrawable);
        }
      }
    }
    BlockContentLoader.loadBlockContent(
    block.getBlockContent(), this, block.getColor(), getLanguage(), activity, getEnableEdit());
    invalidate();
  }

  public String getReturns() {
    if (returns != null) {
      return this.returns;
    }
    return "";
  }

  public Block getBlock() {
    return this.block;
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
}
