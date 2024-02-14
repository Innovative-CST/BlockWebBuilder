/*
 *  This file is part of BlockWeb Builder.
 *
 *  BlockWeb Builder is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BlockWeb Builder is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with BlockWeb Builder.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.block.web.builder.core;

import com.block.web.builder.core.utils.CodeReplacer;
import java.io.Serializable;
import java.util.ArrayList;

public class WebFile implements Serializable {
  public static final long serialVersionUID = 428383835L;
  private String filePath;
  private int fileType;
  private ArrayList<Event> events;
  private String rawCode;

  public String getFilePath() {
    return this.filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public int getFileType() {
    return this.fileType;
  }

  public void setFileType(int fileType) {
    this.fileType = fileType;
  }

  public ArrayList<Event> getEvents() {
    if (events != null) {
      return this.events;
    }
    return new ArrayList<Event>();
  }

  public void setEvents(ArrayList<Event> events) {
    this.events = events;
  }

  public String getRawCode() {
    if (rawCode != null) {
      return this.rawCode;
    }
    return "";
  }

  public void setRawCode(String rawCode) {
    this.rawCode = rawCode;
  }

  public String getCode(ArrayList<Event> events) {
    String fileRawCode = new String(getRawCode());
    if (!(getFileType() == WebFile.SupportedFileType.FOLDER)) {
      for (int i = 0; i < events.size(); ++i) {
        String formatter = "";
        String[] lines = getRawCode().split("\n");
        for (int i2 = 0; i2 < lines.length; ++i2) {
          if (lines[i2].contains(events.get(i).getEventReplacer())) {
            formatter =
                lines[i2].substring(
                    0,
                    lines[i2].indexOf(CodeReplacer.getReplacer(events.get(i).getEventReplacer())));
          }
        }

        String eventCode = events.get(i).getFormattedCode(formatter);
        String eventReplacer = events.get(i).getEventReplacer();
        fileRawCode = fileRawCode.replace(CodeReplacer.getReplacer(eventReplacer), eventCode);
      }
    }
    fileRawCode = CodeReplacer.removeBlockWebBuilderString(fileRawCode);
    return fileRawCode;
  }

  public static String getSupportedFileSuffix(int type) {
    switch (type) {
      case WebFile.SupportedFileType.HTML:
        return ".html";
      case WebFile.SupportedFileType.CSS:
        return ".css";
      case WebFile.SupportedFileType.JS:
        return ".js";
    }
    return "";
  }

  public class SupportedFileType {
    public static final int FOLDER = -1;
    public static final int HTML = 0;
    public static final int CSS = 1;
    public static final int JS = 2;
  }
}
