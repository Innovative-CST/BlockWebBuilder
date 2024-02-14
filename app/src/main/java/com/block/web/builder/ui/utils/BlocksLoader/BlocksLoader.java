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

package com.block.web.builder.ui.utils.BlocksLoader;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.block.web.builder.core.Block;
import com.block.web.builder.core.ComplexBlock;
import com.block.web.builder.ui.utils.BlocksLoader.BlocksLoaders.ComplexBlockLoader;
import com.block.web.builder.ui.utils.BlocksLoader.BlocksLoaders.DefaultBlockLoader;
import java.util.ArrayList;

public class BlocksLoader {
  public static void loadBlockViews(
      ViewGroup view, ArrayList<Block> blocks, String language, Activity activity) {
    if (blocks.size() != 0) {
      for (int i = 0; i < blocks.size(); ++i) {
        if (blocks.get(i) instanceof ComplexBlock) {
          ComplexBlockLoader.loadComplexBlock(
              view, ((ComplexBlock) blocks.get(i)), language, activity, i);
        } else if (blocks.get(i) instanceof Block) {
          DefaultBlockLoader.loadDefaultBlock(blocks.get(i), view, language, activity, i);
        }
      }
    }
  }

  public static void setAboveMargin(View view, int aboveMargin) {
    if (view.getLayoutParams() != null) {
      ((LinearLayout.LayoutParams) view.getLayoutParams()).setMargins(0, aboveMargin, 0, 0);
    }
  }

  public static void setLeftMargin(View view, int leftMargin) {
    if (view.getLayoutParams() != null) {
      ((LinearLayout.LayoutParams) view.getLayoutParams()).setMargins(leftMargin, 0, 0, 0);
    }
  }

  public static void setViewWidth(View view, int width) {
    if (view.getLayoutParams() != null) {
      ((LinearLayout.LayoutParams) view.getLayoutParams()).width = width;
    }
  }
}
