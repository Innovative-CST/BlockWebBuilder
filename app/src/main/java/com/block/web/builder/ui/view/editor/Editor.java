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

package com.block.web.builder.ui.view.editor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.block.web.builder.R;
import com.block.web.builder.utils.ColorUtils;
import com.block.web.builder.utils.Utils;

public class Editor extends EditorScrollView {

  private boolean b1 = false;

  public Editor(final Context context) {
    super(context);
    initEditor();
  }

  public Editor(final Context context, final AttributeSet set) {
    super(context, set);
    initEditor();
  }

  public void setUpDimension() {
    getLayoutParams().width = getRight() - getLeft();
    getLayoutParams().height = getBottom() - getTop();

    final int childCount = getChildCount();
    int width = 0;
    int width2 = 0;
    width = getLayoutParams().width;
    width2 = getLayoutParams().height;
    int max;
    int max2;

    for (int i = 0; i < getChildCount(); ++i) {
      final View child = getChildAt(i);
      final float x = child.getX();
      max =
          Math.max(
              (int)
                  (child.getX()
                      + child.getPaddingLeft()
                      + child.getWidth()
                      + child.getPaddingRight()
                      + 150),
              width);
      width = max;
    }
    for (int i = 0; i < getChildCount(); ++i) {
      final View child = getChildAt(i);
      final float x = child.getX();
      max2 =
          Math.max(
              (int)
                  (child.getY()
                      + child.getPaddingTop()
                      + child.getHeight()
                      + child.getPaddingBottom()
                      + 150),
              width2);
      width2 = max2;
    }
    getLayoutParams().width = width;
    getLayoutParams().height = width2;
    requestLayout();
  }

  @Override
  protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
    super.onLayout(arg0, arg1, arg2, arg3, arg4);
    setUpDimension();
  }

  @Override
  protected void onScrollChanged(int arg0, int arg1, int arg2, int arg3) {
    super.onScrollChanged(arg0, arg1, arg2, arg3);
    setUpDimension();
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent motion) {
    if (!super.onInterceptTouchEvent(motion)) {
      setUpDimension();
    }
    return super.onInterceptTouchEvent(motion);
  }

  @Override
  public boolean onTouchEvent(MotionEvent motion) {
    if (!super.onTouchEvent(motion)) {
      setUpDimension();
    }
    return super.onTouchEvent(motion);
  }

  public void initEditor() {
    LinearLayout blockListEditorArea = new LinearLayout(getContext());
    blockListEditorArea.setLayoutParams(
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    blockListEditorArea.setOrientation(LinearLayout.VERTICAL);
    blockListEditorArea.setId(R.id.blockListEditorArea);
    blockListEditorArea.setTag("blockDroppingArea");
    blockListEditorArea.setPadding(0, 0, 0, Utils.dpToPx(getContext(), 8f));

    LinearLayout defineBlockLayout = new LinearLayout(getContext());
    defineBlockLayout.setLayoutParams(
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    defineBlockLayout.setBackgroundResource(R.drawable.define_block);
    defineBlockLayout.setBackgroundTintList(
        ColorStateList.valueOf(
            ColorUtils.getColor(getContext(), com.google.android.material.R.attr.colorPrimary)));

    LinearLayout innerLayout = new LinearLayout(getContext());
    innerLayout.setLayoutParams(
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    TextView defineTextView = new TextView(getContext());
    defineTextView.setLayoutParams(
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    defineTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    defineTextView.setTextColor(
        ColorUtils.getColor(getContext(), com.google.android.material.R.attr.colorOnPrimary));
    defineTextView.setText("Define your event here");

    innerLayout.addView(defineTextView);
    defineBlockLayout.addView(innerLayout);
    blockListEditorArea.addView(defineBlockLayout);
    addView(blockListEditorArea);
  }
}
