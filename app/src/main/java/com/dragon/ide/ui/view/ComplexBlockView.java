package com.dragon.ide.ui.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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

public class ComplexBlockView extends LinearLayout {
  public ComplexBlock block;
  public boolean enableEdit = false;
  public String language;
  public Activity activity;

  public ComplexBlockView(Activity context) {
    super(context);
    setOrientation(LinearLayout.VERTICAL);
    this.activity = context;
  }

  public void setComplexBlock(ComplexBlock block) {
    this.block = block;

    if (!(block instanceof DoubleComplexBlock)) {
      if (block instanceof ComplexBlock) {
        if (block.getBlockType() == Block.BlockType.defaultBlock) {
          LinearLayout blockContent = new LinearLayout(getContext());
          blockContent.setBackgroundResource(R.drawable.complex_block);

          Drawable backgroundDrawable = blockContent.getBackground();
          backgroundDrawable.setTint(Color.parseColor(block.getColor()));
          backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
          blockContent.setBackground(backgroundDrawable);

          BlockContentLoader.loadBlockContent(
              block.getBlockContent(),
              blockContent,
              block.getColor(),
              getLanguage(),
              activity,
              getEnableEdit());

          addView(blockContent);
        }
      }
    }
    invalidate();
  }

  public ComplexBlock getBlock() {
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
