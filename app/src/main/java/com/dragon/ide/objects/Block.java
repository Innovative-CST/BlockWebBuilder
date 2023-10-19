package com.dragon.ide.objects;

import java.io.Serializable;

public class Block implements Serializable {
  public static final long serialVersionUID = 428383837L;
  public String color;
  public String name;
  private Object[] blockSpec1;
  private Object[] blockSpec2;
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

  public Object[] getBlockSpec1() {
    return this.blockSpec1;
  }

  public void setBlockSpec1(Object[] blockSpec1) {
    this.blockSpec1 = blockSpec1;
  }

  public Object[] getBlockSpec2() {
    return this.blockSpec2;
  }

  public void setBlockSpec2(Object[] blockSpec2) {
    this.blockSpec2 = blockSpec2;
  }

  public int getBlockType() {
    return this.BlockType;
  }

  public void setBlockType(int BlockType) {
    this.BlockType = BlockType;
  }
}
