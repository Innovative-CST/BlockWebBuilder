package com.block.web.builder.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.color.utilities.DynamicColor;

public class ColorUtils {

  public static int getColor(Context context, int res) {
    int color;

    if (DynamicColors.isDynamicColorAvailable()) {
      Resources.Theme theme = context.getTheme();
      TypedArray typedArray = theme.obtainStyledAttributes(new int[] {res});
      color = typedArray.getColor(0, 0);
      typedArray.recycle();
      if (color != 0) {
        return color;
      }
    }
    return MaterialColors.getColor(context, res, 0);
  }
}
