package com.block.web.builder.core;

import java.io.Serializable;
import java.util.ArrayList;

public class BlocksHolder implements Serializable {
  public static final long serialVersionUID = 428383836L;
  public String name;
  public String color;
  public ArrayList<Block> blocks;
  public String[] tags;

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

  public ArrayList<Block> getBlocks() {
    if (blocks != null) {
      return this.blocks;
    }
    return new ArrayList<Block>();
  }

  public void setBlocks(ArrayList<Block> blocks) {
    this.blocks = blocks;
  }

  public String[] getTags() {
    return this.tags;
  }

  public void setTags(String[] tags) {
    this.tags = tags;
  }

  public final class Tags {
    public static final String HTML = "html";
    public static final String CSS = "css";
    public static final String JavaScript = "js";
    public static final String Developer = "developer";
    public static final String DeveloperOnly = "developerOnly";
  }
}
