package com.dragon.ide.objects;

import java.io.Serializable;

public class Block implements Serializable {
  public static final long serialVersionUID = 428383837L;
  public String color;
  public String name;
  private Object[] blockData1;
  private Object[] blockData2;
  private int BlockType;

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

  public Object[] getBlockData1() {
    return this.blockData1;
  }

  public void setBlockSpec1(Object[] blockSpec1) {
    this.blockData1 = blockSpec1;
  }

  public Object[] getBlockData2() {
    return this.blockData2;
  }

  public void setBlockData2(Object[] blockSpec2) {
    this.blockData2 = blockSpec2;
  }

  public int getBlockType() {
    return this.BlockType;
  }

  public void setBlockType(int BlockType) {
    this.BlockType = BlockType;
  }
}
