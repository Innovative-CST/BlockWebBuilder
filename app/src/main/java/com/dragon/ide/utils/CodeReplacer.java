package com.dragon.ide.utils;
import java.util.regex.Pattern;

public class CodeReplacer {
  public static String getReplacer(String name) {
    StringBuilder builder = new StringBuilder();
    builder.append("DevKumar DragonIDE ");
    builder.append(new String(name));
    builder.append(" DevKumar");
    return builder.toString();
  }

  public static String removeDragonIDEString(String input) {
    return input.replaceAll("DevKumar DragonIDE .*? DevKumar", "");
  }
}
