package com.dragon.ide.objects.complexblockcontent;

import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.ComplexBlockContent;

public class StringContent extends ComplexBlockContent {
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
}
