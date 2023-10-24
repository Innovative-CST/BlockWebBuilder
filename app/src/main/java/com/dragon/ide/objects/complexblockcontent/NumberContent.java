package com.dragon.ide.objects.complexblockcontent;

import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.ComplexBlockContent;

public class NumberContent extends ComplexBlockContent {
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
}
