package com.dragon.ide.utils;

import java.util.regex.Pattern;

public class PackageNameValidator {
  public static boolean isValidPackageName(String pkgName) {
    Pattern pattern = Pattern.compile("([a-zA-Z][a-zA-Z\\d]*\\.)*[a-zA-Z][a-zA-Z\\d]*");
    if ((pkgName.length() < 50) && (pattern.matcher(pkgName).matches())) {
      if (pkgName.indexOf(".") > 0) {
        return true;
      }
    }
    return false;
  }
}
