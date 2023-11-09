package com.dragon.ide.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
  public static int dpToPx(Context context, float dp) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    return px;
  }
}
