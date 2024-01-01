package com.block.web.builder.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexColorValidator {
  private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6})$";
  private static final Pattern pattern = Pattern.compile(HEX_PATTERN);

  public static boolean isValidateHexColor(String hexColor) {
    Matcher matcher = pattern.matcher(hexColor);
    return matcher.matches();
  }
}
