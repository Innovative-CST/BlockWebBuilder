package com.dragon.ide.objects;

import java.io.Serializable;

public class BlockContent implements Serializable, Cloneable {
  public static final long serialVersionUID = 428383840L;
  private String text;

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public class BlockContentType {
    public static final int String = 0;
    public static final int InputSourceCode = 1;
    public static final int Integer = 2;
    public static final int Boolean = 3;
  }

  @Override
  public BlockContent clone() throws CloneNotSupportedException {
    BlockContent mBlockContent = new BlockContent();
    mBlockContent.setText(new String(getText()));
    return mBlockContent;
  }
}
