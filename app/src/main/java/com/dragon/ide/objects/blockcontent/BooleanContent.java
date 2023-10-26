package com.dragon.ide.objects.blockcontent;

import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.ComplexBlockContent;

public class BooleanContent extends ComplexBlockContent {
  public BooleanContent() {
    setAcceptance("boolean");
    setText("");
    setType(BlockContent.BlockContentType.Boolean);
    setSupportCodeEditor(false);
    setOnClick(ComplexBlockContent.onClickTypes.noAction);
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
