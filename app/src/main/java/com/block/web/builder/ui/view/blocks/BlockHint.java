package com.block.web.builder.ui.view.blocks;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;
import com.block.web.builder.utils.ColorUtils;

public class BlockHint extends LinearLayout {
  public BlockHint(Context context, int resource) {
    super(context);

    setTag("shadow");

    Drawable backgroundDrawable = getResources().getDrawable(resource, null);
    backgroundDrawable.setTint(
        ColorUtils.getColor(context, com.google.android.material.R.attr.colorSurfaceInverse));
    backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
    setBackground(backgroundDrawable);
  }

  public void setBlockResource(int resource) {
    Drawable backgroundDrawable = getResources().getDrawable(resource, null);
    backgroundDrawable.setTint(ColorUtils.getColor(getContext(), com.google.android.material.R.attr.colorSurfaceInverse));
    backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
    setBackground(backgroundDrawable);
  }
}
