package com.dragon.ide.utils;

public class CodeReplacer {
  public static String getReplacer(String name) {
    StringBuilder builder = new StringBuilder();
    builder.append("%%%% DragonIDE ");
    builder.append(new String(name));
    builder.append(" %%%%");
    return builder.toString();
  }

  public static String removeDragonIDEString(String input) {
    return input.replaceAll("%%%% DragonIDE .*? %%%%", "");
  }
}
