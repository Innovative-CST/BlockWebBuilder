package com.dragon.ide.objects.blockcontent;

import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.ComplexBlockContent;

public class SourceContent extends ComplexBlockContent implements Cloneable {
  public SourceContent() {
    setAcceptance(new String[0]);
    setSurrounder("");
    setText("");
    setValue("");
    setType(BlockContent.BlockContentType.InputSourceCode);
    setSupportCodeEditor(true);
    setOnClick(ComplexBlockContent.onClickTypes.valueEditor);
  }

  @Override
  public String getValue() {
    StringBuilder value = new StringBuilder();
    value.append(new String(getSurrounder()));
    value.append(new String(super.getValue()));
    value.append(new String(getSurrounder()));
    return value.toString();
  }

  @Override
  public SourceContent clone() throws CloneNotSupportedException {
    SourceContent mComplexBlockContent = new SourceContent();
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
      mId = new String("");
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
      mType = BlockContent.BlockContentType.InputSourceCode;
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
