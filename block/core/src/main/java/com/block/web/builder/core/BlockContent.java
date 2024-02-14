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

package com.block.web.builder.core;

import java.io.Serializable;

public class BlockContent implements Serializable, Cloneable {
  public static final long serialVersionUID = 428383840L;
  private String text;

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public class BlockContentType {
    public static final int String = 0;
    public static final int InputSourceCode = 1;
    public static final int Integer = 2;
    public static final int Boolean = 3;
  }

  @Override
  public BlockContent clone() throws CloneNotSupportedException {
    BlockContent mBlockContent = new BlockContent();
    mBlockContent.setText(new String(getText()));
    return mBlockContent;
  }
}
