/*
 * This file is part of BlockWeb Builder [https://github.com/TS-Code-Editor/BlockWebBuilder].
 *
 * License Agreement
 * This software is licensed under the terms and conditions outlined below. By accessing, copying, modifying, or using this software in any way, you agree to abide by these terms.
 *
 * 1. **  Copy and Modification Restrictions  **
 *    - You are not permitted to copy or modify the source code of this software without the permission of the owner, which may be granted publicly on GitHub Discussions or on Discord.
 *    - If permission is granted by the owner, you may copy the software under the terms specified in this license agreement.
 *    - You are not allowed to permit others to copy the source code that you were allowed to copy by the owner.
 *    - Modified or copied code must not be further copied.
 * 2. **  Contributor Attribution  **
 *    - You must attribute the contributors by creating a visible list within the application, showing who originally wrote the source code.
 *    - If you copy or modify this software under owner permission, you must provide links to the profiles of all contributors who contributed to this software.
 * 3. **  Modification Documentation  **
 *    - All modifications made to the software must be documented and listed.
 *    - the owner may incorporate the modifications made by you to enhance this software.
 * 4. **  Consistent Licensing  **
 *    - All copied or modified files must contain the same license text at the top of the files.
 * 5. **  Permission Reversal  **
 *    - If you are granted permission by the owner to copy this software, it can be revoked by the owner at any time. You will be notified at least one week in advance of any such reversal.
 *    - In case of Permission Reversal, if you fail to acknowledge the notification sent by us, it will not be our responsibility.
 * 6. **  License Updates  **
 *    - The license may be updated at any time. Users are required to accept and comply with any changes to the license.
 *    - In such circumstances, you will be given 7 days to ensure that your software complies with the updated license.
 *    - We will not notify you about license changes; you need to monitor the GitHub repository yourself (You can enable notifications or watch the repository to stay informed about such changes).
 * By using this software, you acknowledge and agree to the terms and conditions outlined in this license agreement. If you do not agree with these terms, you are not permitted to use, copy, modify, or distribute this software.
 *
 * Copyright © 2024 Dev Kumar
 */

package com.block.web.builder.ui.view.editor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.block.web.builder.utils.Utils;

public class EditorScrollView extends FrameLayout {
  private float x;
  private float y;
  private float initialX;
  private float initialY;
  private float initialXTE;
  private float initialYTE;
  private float validScroll = 0;
  private boolean useScroll = true;
  private boolean isScrollValueAchieved = false;

  public EditorScrollView(final Context context) {
    super(context);
    validScroll = ViewConfiguration.get(context).getScaledTouchSlop();
    initialXTE = -1.0f;
    initialYTE = -1.0f;
    useScroll = true;
    isScrollValueAchieved = false;
    initialX = 0.0f;
    initialY = 0.0f;
  }

  public EditorScrollView(final Context context, final AttributeSet set) {
    super(context, set);
    validScroll = ViewConfiguration.get(context).getScaledTouchSlop();
    initialXTE = -1.0f;
    initialYTE = -1.0f;
    useScroll = true;
    isScrollValueAchieved = false;
    initialX = 0.0f;
    initialY = 0.0f;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent motion) {
    if (!getEnableScroll()) {
      if (getScaleY() == 0) {
        return false;
      }
    }
    if (!useScroll) {
      return false;
    }
    int action = motion.getAction();

    if (action == MotionEvent.ACTION_DOWN) {
      initialX = motion.getX();
      initialY = motion.getY();
    }
    if (action == MotionEvent.ACTION_UP) {
      isScrollValueAchieved = false;
    }

    if (action == MotionEvent.ACTION_MOVE) {
      if (isScrollValueAchieved) {
        return true;
      }
      if (Math.abs(initialX - motion.getX()) > validScroll
          || Math.abs(initialY - motion.getY()) > validScroll) {
        isScrollValueAchieved = true;
      }
    }
    return isScrollValueAchieved;
  }

  @Override
  public boolean onTouchEvent(MotionEvent motion) {
    int n = 0;
    int n2 = 0;
    if (!getEnableScroll()) {
      if (getScrollY() == 0) {
        return false;
      } else {
        final View child = this.getChildAt(0);
        final int action = motion.getAction();
        final float x = motion.getX();
        final float y = motion.getY();
        if (action == MotionEvent.ACTION_DOWN) {
          initialXTE = x;
          initialYTE = y;
        }
        if (action == MotionEvent.ACTION_UP) {
          initialXTE = -1.0f;
          initialYTE = -1.0f;
        }
        if (action == MotionEvent.ACTION_MOVE) {
          if (initialXTE < 0.0f) {
            initialXTE = x;
          }
          if (initialYTE < 0.0f) {
            initialYTE = y;
          }
          final int b2 = (int) (initialYTE - y);
          initialXTE = x;
          initialYTE = y;

          if (b2 < 0) {
            if ((Math.abs(b2) <= getScrollY())) {
              scrollBy(0, b2);
            }
          }
        }
        return true;
      }
    }
    if (!useScroll) {
      return false;
    }
    final View child = this.getChildAt(0);
    final int action = motion.getAction();
    final float x = motion.getX();
    final float y = motion.getY();
    if (action == MotionEvent.ACTION_DOWN) {
      initialXTE = x;
      initialYTE = y;
    }
    if (action == MotionEvent.ACTION_UP) {
      initialXTE = -1.0f;
      initialYTE = -1.0f;
    }
    if (action == MotionEvent.ACTION_MOVE) {
      if (initialXTE < 0.0f) {
        initialXTE = x;
      }
      if (initialYTE < 0.0f) {
        initialYTE = y;
      }
      final int b = (int) (initialXTE - x);
      final int b2 = (int) (initialYTE - y);
      initialXTE = x;
      initialYTE = y;
      if (b < 0) {
        if ((Math.abs(b) <= getScrollX())) {
          scrollBy(b, 0);
        }
      } else {
        if (getScrollX() + ((View) getParent()).getWidth() < getWidth()) {
          scrollBy(b, 0);
        }
      }

      if (b2 < 0) {
        if ((Math.abs(b2) <= getScrollY())) {
          scrollBy(0, b2);
        }
      } else {
        if (getParent() != null) {
          if (getScrollY() + ((View) getParent()).getHeight() < getHeight()) {
            scrollBy(0, b2);
          }
        } else {
          if (getScrollY() + Utils.dpToPx(getContext(), 300f) < getHeight()) {
            scrollBy(0, b2);
          }
        }
      }
    }
    return true;
  }

  public boolean getUseScroll() {
    return this.useScroll;
  }

  public void setUseScroll(boolean useScroll) {
    this.useScroll = useScroll;
  }

  public boolean getEnableScroll() {
    boolean b = false;
    if (getParent() != null) {
      final int childCount = getChildCount();
      if (0 < childCount) {
        for (int i = 0; i < childCount; ++i) {
          final View child = getChildAt(i);
          final int width = child.getWidth();
          final int height = child.getHeight();
          if (((ViewGroup) getParent()).getWidth()
                  < getPaddingLeft()
                      + child.getX()
                      + child.getPaddingRight()
                      + width
                      + child.getPaddingLeft()
                      + getPaddingRight()
              || ((ViewGroup) getParent()).getHeight()
                  < getPaddingTop()
                      + child.getY()
                      + child.getPaddingTop()
                      + height
                      + child.getPaddingBottom()
                      + getPaddingBottom()) {
            b = true;
          }
        }
      }
    }
    return b;
  }
}
