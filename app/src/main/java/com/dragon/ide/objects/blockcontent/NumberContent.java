package com.dragon.ide.objects.blockcontent;

import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.ComplexBlockContent;

public class NumberContent extends ComplexBlockContent implements Cloneable {
  public NumberContent() {
    setAcceptance("int");
    setText("");
    setType(BlockContent.BlockContentType.Integer);
    setSupportCodeEditor(false);
    setOnClick(ComplexBlockContent.onClickTypes.numberEditor);
  }

  @Override
  public String getValue() {
    if (super.getValue() != null) {
      StringBuilder value = new StringBuilder();
      value.append(new String(getValue()));
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
    String mAcceptance;
    if (getAcceptance() != null) {
      mAcceptance = new String(getAcceptance());
    } else {
      mAcceptance = new String("");
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

    mComplexBlockContent.setText(mText);
    mComplexBlockContent.setValue(mValue);
    mComplexBlockContent.setId(mId);
    mComplexBlockContent.setSurrounder(mSurrounder);
    mComplexBlockContent.setType(mType);
    mComplexBlockContent.setAcceptance(mAcceptance);
    mComplexBlockContent.setSupportCodeEditor(mSupportCodeEditor);

    return mComplexBlockContent;
  }
}
