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
