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

public class ComplexBlockContent extends BlockContent implements Serializable, Cloneable {
  public static final long serialVersionUID = 428383841L;
  private String value;
  private String id; // similar to replacer
  private String surrounder;
  private int type;
  private String[] acceptance; // Which type of block can replace this
  private boolean supportCodeEditor;
  private int onClick;
  private Block block;

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
    this.block = null;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getSurrounder() {
    return this.surrounder;
  }

  public void setSurrounder(String surrounder) {
    this.surrounder = surrounder;
  }

  public String[] getAcceptance() {
    return this.acceptance;
  }

  public void setAcceptance(String[] acceptance) {
    this.acceptance = acceptance;
  }

  public boolean getSupportCodeEditor() {
    return this.supportCodeEditor;
  }

  public void setSupportCodeEditor(boolean supportCodeEditor) {
    this.supportCodeEditor = supportCodeEditor;
  }

  public int getOnClick() {
    return this.onClick;
  }

  public void setOnClick(int onClick) {
    this.onClick = onClick;
  }

  public class onClickTypes {
    public static final int valueEditor = 0;
    public static final int numberEditor = 1;
    public static final int noAction = 2;
  }

  @Override
  public ComplexBlockContent clone() throws CloneNotSupportedException {
    ComplexBlockContent mComplexBlockContent = new ComplexBlockContent();
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

  public Block getBlock() {
    return this.block;
  }

  public void setBlock(Block block) {
    this.value = "";
    this.block = block;
  }

  public String getCode() {
    if (block != null) {
      return new String(block.getCode());
    } else if (getValue() != "") {
      return value;
    } else {
      return getValue();
    }
  }
}
