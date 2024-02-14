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

package com.block.web.builder.utils;

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
