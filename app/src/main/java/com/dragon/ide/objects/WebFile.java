package com.dragon.ide.objects;

import java.util.ArrayList;

public class WebFile {
  private String filePath;
  private int fileType;
  private ArrayList<WebFile> fileList;

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

  public ArrayList<WebFile> getFileList() {
    return this.fileList;
  }

  public void setFileList(ArrayList<WebFile> fileList) {
    this.fileList = fileList;
  }

  public class SupportedFileType {
    public static final int FOLDER = -1;
    public static final int HTMl = 0;
    public static final int CSS = 1;
    public static final int JS = 2;
  }
}
