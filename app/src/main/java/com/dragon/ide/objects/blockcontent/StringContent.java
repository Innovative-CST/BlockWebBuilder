package com.dragon.ide.objects.blockcontent;

import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.ComplexBlockContent;

public class StringContent extends ComplexBlockContent implements Cloneable {
  public StringContent() {
    setAcceptance("string");
    setSurrounder("\"");
    setText("");
    setValue("");
    setType(BlockContent.BlockContentType.String);
    setSupportCodeEditor(true);
    setOnClick(ComplexBlockContent.onClickTypes.valueEditor);
  }

  @Override
  public String getValue() {
    StringBuilder value = new StringBuilder();
    value.append(new String(getSurrounder()));
    value.append(new String(getValue()));
    value.append(new String(getSurrounder()));
    return value.toString();
  }

  @Override
  public StringContent clone() throws CloneNotSupportedException {
    return (StringContent) super.clone();
  }
}
