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
    for (int i = 0; i < block.getBlockContent().size(); ++i) {
      if (block.getBlockContent().get(i) instanceof ComplexBlockContent) {
        if (block.getBlockContent().get(i) instanceof SourceContent) {
          LinearLayout ll_source = new LinearLayout(getContext());
          ll_source.setPadding(25, 0, 25, 0);
          ll_source.setBackgroundColor(Color.WHITE);
          TextView tvTextContent = new TextView(getContext());
          tvTextContent.setText(((SourceContent) block.getBlockContent().get(i)).getValue());
          ll_source.addView(tvTextContent, getChildCount() - 1);

          LinearLayout.LayoutParams layoutParams =
              new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          int startMargin = 8;
          int endMargin = 8;
          layoutParams.setMarginStart(startMargin);
          layoutParams.setMarginEnd(endMargin);
          ll_source.setLayoutParams(layoutParams);

          addView(ll_source, getChildCount());

          final SourceContent sc = (SourceContent)block.getBlockContent().get(i);

          ll_source.setOnClickListener(
              (v) -> {
                if (getEnableEdit()) {
                  ValueEditorDialog valueEditorDialog =
                      new ValueEditorDialog(
                          activity,
                          sc.getValue(),
                          getLanguage(),
                          new ValueListener() {

                            @Override
                            public void onSubmitted(String value) {
                              sc.setValue(value);
                              tvTextContent.setText(sc.getValue());
                            }

                            @Override
                            public void onError(String error) {}
                          });
                  valueEditorDialog.show();
                }
              });
        }
      } else if (block.getBlockContent().get(i) instanceof BlockContent) {
        TextView tvTextContent = new TextView(getContext());
        tvTextContent.setText(((BlockContent) block.getBlockContent().get(i)).getText());

        int backgroundColor = Color.parseColor(block.getColor());
        int red = Color.red(backgroundColor);
        int green = Color.green(backgroundColor);
        int blue = Color.blue(backgroundColor);

        double luminance = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255;
        double contrastRatioThreshold = 1.4;
        int textColor = (luminance > 0.5 * 255) ? Color.BLACK : Color.WHITE;
        double contrastRatio = (luminance + 0.05) / (0.05 + 0.05);

        if (contrastRatio < contrastRatioThreshold) {
          tvTextContent.setTextColor(textColor);
        }

        addView(tvTextContent, getChildCount() - 1);
      }
    }
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
