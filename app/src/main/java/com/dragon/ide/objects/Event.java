package com.dragon.ide.objects;

import com.dragon.ide.utils.CodeReplacer;
import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {
  private static final long serialVersionUID = 428383838L;
  private String rawCode;
  private ArrayList<Block> blocks;
  private String name;
  private String desc;
  private String replacer;
  private String eventReplacer;

  public ArrayList<Block> getBlocks() {
    if (blocks != null) {
      return this.blocks;
    }
    return new ArrayList<Block>();
  }

  public void setBlocks(ArrayList<Block> blocks) {
    this.blocks = blocks;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return this.desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getRawCode() {
    return this.rawCode;
  }

  public void setRawCode(String rawCode) {
    this.rawCode = rawCode;
  }

  public String getCode() {
    StringBuilder eventCode = new StringBuilder();
    for (int i = 0; i < getBlocks().size(); ++i) {
      if (getBlocks().get(i) instanceof Block) {
        eventCode.append(new String(getBlocks().get(i).getCode()));
        eventCode.append("\n");
      }
    }
    String eventFinalCode = new String(getRawCode());
    eventFinalCode =
        eventFinalCode.replaceAll(CodeReplacer.getReplacer(getReplacer()), eventCode.toString());
    eventFinalCode = CodeReplacer.removeDragonIDEString(eventFinalCode);
    return new String(eventFinalCode.toString());
  }

  public String getFormattedCode(String s) {
    StringBuilder sb = new StringBuilder();
    String[] lines = getCode().split("\n");
    for (int i = 0; i < lines.length; ++i) {
      if (i != 0) {
        sb.append(s);
      }
      sb.append(lines[i]);
      sb.append("\n");
    }
    return sb.toString();
  }

  public String getReplacer() {
    return this.replacer;
  }

  public void setReplacer(String replacer) {
    this.replacer = replacer;
  }

  public String getEventReplacer() {
    return new String(this.eventReplacer);
  }

  public void setEventReplacer(String eventReplacer) {
    this.eventReplacer = eventReplacer;
  }
}
