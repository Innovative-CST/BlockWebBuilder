package com.block.web.builder.utils;

import java.util.regex.Pattern;

public class FileNameValidator {
  private static final String FILE_NAME_PATTERN = "^[a-zA-Z0-9-_\\.]+$";
  private static final Pattern pattern = Pattern.compile(FILE_NAME_PATTERN);

  public static boolean isValidFileName(String fileName) {
    return pattern.matcher(fileName).matches();
  }
}
