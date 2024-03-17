/*
 * This file is part of BlockWeb Builder [https://github.com/TS-Code-Editor/BlockWebBuilder].
 *
 * License Agreement
 * This software is licensed under the terms and conditions outlined below. By accessing, copying, modifying, or using this software in any way, you agree to abide by these terms.
 *
 * 1. **  Copy and Modification Restrictions  **
 *    - You are not permitted to copy or modify the source code of this software without the permission of the owner, which may be granted publicly on GitHub Discussions or on Discord.
 *    - If permission is granted by the owner, you may copy the software under the terms specified in this license agreement.
 *    - You are not allowed to permit others to copy the source code that you were allowed to copy by the owner.
 *    - Modified or copied code must not be further copied.
 * 2. **  Contributor Attribution  **
 *    - You must attribute the contributors by creating a visible list within the application, showing who originally wrote the source code.
 *    - If you copy or modify this software under owner permission, you must provide links to the profiles of all contributors who contributed to this software.
 * 3. **  Modification Documentation  **
 *    - All modifications made to the software must be documented and listed.
 *    - the owner may incorporate the modifications made by you to enhance this software.
 * 4. **  Consistent Licensing  **
 *    - All copied or modified files must contain the same license text at the top of the files.
 * 5. **  Permission Reversal  **
 *    - If you are granted permission by the owner to copy this software, it can be revoked by the owner at any time. You will be notified at least one week in advance of any such reversal.
 *    - In case of Permission Reversal, if you fail to acknowledge the notification sent by us, it will not be our responsibility.
 * 6. **  License Updates  **
 *    - The license may be updated at any time. Users are required to accept and comply with any changes to the license.
 *    - In such circumstances, you will be given 7 days to ensure that your software complies with the updated license.
 *    - We will not notify you about license changes; you need to monitor the GitHub repository yourself (You can enable notifications or watch the repository to stay informed about such changes).
 * By using this software, you acknowledge and agree to the terms and conditions outlined in this license agreement. If you do not agree with these terms, you are not permitted to use, copy, modify, or distribute this software.
 *
 * Copyright © 2024 Dev Kumar
 */

package com.block.web.builder.core;

import com.block.web.builder.core.utils.CodeReplacer;
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
        if (i + 1 != getBlocks().size()) {
          eventCode.append("\n");
        }
      }
    }
    String eventFinalCode = new String(getRawCode());
    eventFinalCode =
        eventFinalCode.replace(CodeReplacer.getReplacer(getReplacer()), eventCode.toString());
    eventFinalCode = CodeReplacer.removeBlockWebBuilderString(eventFinalCode);
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
      if (i + 1 != lines.length) {
        sb.append("\n");
      }
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
