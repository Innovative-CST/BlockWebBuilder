package com.dragon.ide.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class BlocksHolder implements Serializable {
  public static final long serialVersionUID = 428383836L;
  public String name;
  public String color;
  public ArrayList<Block> blocks;

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
}
