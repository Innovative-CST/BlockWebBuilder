package com.dragon.ide.objects.blockcontent;

import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.ComplexBlockContent;

public class SourceContent extends ComplexBlockContent {
  public SourceContent() {
    setAcceptance("");
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
}
