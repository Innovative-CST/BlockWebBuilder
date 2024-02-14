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

package com.block.web.builder.core.blockcontent;

import com.block.web.builder.core.Block;
import com.block.web.builder.core.BlockContent;
import com.block.web.builder.core.ComplexBlockContent;

public class NumberContent extends ComplexBlockContent implements Cloneable {
  public NumberContent() {
    setAcceptance(new String[] {"int"});
    setText("");
    setType(BlockContent.BlockContentType.Integer);
    setSupportCodeEditor(false);
    setOnClick(ComplexBlockContent.onClickTypes.numberEditor);
  }

  @Override
  public String getValue() {
    if (super.getValue() != null) {
      StringBuilder value = new StringBuilder();
      value.append(new String(super.getValue()));
      return value.toString();
    }
    return "0";
  }

  @Override
  public NumberContent clone() throws CloneNotSupportedException {
    NumberContent mComplexBlockContent = new NumberContent();
    String mValue;
    if (getValue() != null) {
      mValue = new String(getValue());
    } else {
      mValue = new String("");
    }
    String mId;
    if (getId() != null) {
      mId = new String(getId());
    } else {
      mId = new String();
    }
    String mSurrounder;
    if (getSurrounder() != null) {
      mSurrounder = new String(getSurrounder());
    } else {
      mSurrounder = new String("");
    }
    int mType;
    if (getType() == 0) {
      mType = new Integer(getType());
    } else {
      mType = 0;
    }
    String[] mAcceptance;
    if (getAcceptance() != null) {
      mAcceptance = getAcceptance().clone();
    } else {
      mAcceptance = new String[0];
    }
    boolean mSupportCodeEditor;
    mSupportCodeEditor = new Boolean(getSupportCodeEditor());
    int onClick;
    onClick = new Integer(getOnClick());
    String mText;
    if (getText() != null) {
      mText = new String(getText());
    } else {
      mText = new String("");
    }
    Block mBlock;
    if (getBlock() != null) {
      mBlock = getBlock().clone();
    } else {
      mBlock = null;
    }

    mComplexBlockContent.setText(mText);
    mComplexBlockContent.setValue(mValue);
    mComplexBlockContent.setId(mId);
    mComplexBlockContent.setSurrounder(mSurrounder);
    mComplexBlockContent.setType(mType);
    mComplexBlockContent.setAcceptance(mAcceptance);
    mComplexBlockContent.setSupportCodeEditor(mSupportCodeEditor);
    if (mBlock != null) {
      mComplexBlockContent.setBlock(mBlock);
    }

    return mComplexBlockContent;
  }
}
