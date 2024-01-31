package com.block.web.builder.core.utils;

import java.util.regex.Pattern;

public class CodeReplacer {
  public static String getReplacer(String name) {
    StringBuilder builder = new StringBuilder();
    builder.append("%%%%DevKumar BlockWebBuilder ");
    builder.append(new String(name));
    builder.append(" DevKumar%%%%");
    return builder.toString();
  }

  public static String removeBlockWebBuilderString(String input) {
    return input.replaceAll("%%%%DevKumar BlockWebBuilder .*? DevKumar%%%%", "");
  }
}
