package com.dragon.ide.objects.complexblockcontent;

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
    value.append(new String(getValue()));
    value.append(new String(getSurrounder()));
    return value.toString();
  }
}
