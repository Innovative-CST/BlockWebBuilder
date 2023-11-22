package com.dragon.ide.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class Utils {
  public static int dpToPx(Context context, float dp) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    return px;
  }

  public static void setMargins(View view, int left, int up, int right, int bottom) {
    if (view.getLayoutParams() != null) {
      if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
        ((LinearLayout.LayoutParams) view.getLayoutParams()).setMargins(left, up, right, bottom);
      }
      if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
        ((FrameLayout.LayoutParams) view.getLayoutParams()).setMargins(left, up, right, bottom);
      }
    }
  }

  public static String setWordLimitOnString(int limit, String word) {
    String[] words = word.split("(?<=.)");
    if (words.length > limit) {
      StringBuilder truncatedText = new StringBuilder();
      for (int i = 0; i < limit; i++) {
        truncatedText.append(words[i]);
      }
      truncatedText.append("...");

      return truncatedText.toString();
    } else {
      return word;
    }
  }
}
