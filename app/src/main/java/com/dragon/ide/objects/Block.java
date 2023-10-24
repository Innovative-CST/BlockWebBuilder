package com.dragon.ide.objects;

import com.dragon.ide.utils.CodeReplacer;
import java.io.Serializable;
import java.util.ArrayList;

public class Block implements Serializable {
  private static final long serialVersionUID = 428383837L;
  private String color;
  private String name;
  private ArrayList<Object> blockContent;
  private int BlockType;
  private String rawCode;
  private String replacer;

  public String getColor() {
    if (this.color != null) {
      return this.color;
    }
    return "#000000";
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getName() {
    if (this.name != null) {
      return this.name;
    }
    return "";
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getBlockType() {
    return this.BlockType;
  }

  public void setBlockType(int BlockType) {
    this.BlockType = BlockType;
  }

  public String getCode() {
    String blockRawCode = new String(getRawCode());
    for (int i = 0; i < getBlockContent().size(); ++i) {
      if (getBlockContent().get(i) instanceof ComplexBlockContent) {
        blockRawCode =
            blockRawCode.replaceAll(
                CodeReplacer.getReplacer(
                    ((ComplexBlockContent) getBlockContent().get(i)).getId()),
                ((ComplexBlockContent) getBlockContent().get(i)).getValue());
      }
    }
    blockRawCode = CodeReplacer.removeDragonIDEString(blockRawCode);
    return new String(blockRawCode);
  }

  public String getRawCode() {
    return this.rawCode;
  }

  public void setRawCode(String rawCode) {
    this.rawCode = rawCode;
  }

  public final class BlockType {
    public final int defaultBlock = 0;
    public final int complexBlock = 1;
    public final int doubleComplexBlock = 2;
  }

  public String getReplacer() {
    return this.replacer;
  }

  public void setReplacer(String replacer) {
    this.replacer = replacer;
  }

  public ArrayList<Object> getBlockContent() {
    return this.blockContent;
  }

  public void setBlockContent(ArrayList<Object> blockContent) {
    this.blockContent = blockContent;
  }
}
