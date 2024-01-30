package com.block.web.builder.ui.view.blocks;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

public class BlockHint extends LinearLayout {
  public BlockHint(Context context, int resource) {
    super(context);

    setTag("shadow");

    Drawable backgroundDrawable = getResources().getDrawable(resource, null);
    backgroundDrawable.setTint(Color.BLACK);
    backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
    setBackground(backgroundDrawable);
  }

  public void setBlockResource(int resource) {
    Drawable backgroundDrawable = getResources().getDrawable(resource, null);
    backgroundDrawable.setTint(Color.BLACK);
    backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
    setBackground(backgroundDrawable);
  }
}
