package com.block.web.builder.utils;

public class DeserializationException extends Exception {
  public static final int FILE_NOT_FOUND_EXCEPTION = 0;
  public static final int FOLDER_FOUND_EXCEPTION = 1;
  public static final int DESERIALIZATION_EXCEPTION = 2;
  public static final int OBJECT_TYPE_EXCEPTION = 3;

  private int errorType;

  public DeserializationException(String message) {
    super(message);
  }

  public int getErrorType() {
    return errorType;
  }

  public void setErrorType(int errorType) {
    this.errorType = errorType;
  }
}
