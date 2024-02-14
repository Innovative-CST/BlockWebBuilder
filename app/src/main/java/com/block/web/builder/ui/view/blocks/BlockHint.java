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
