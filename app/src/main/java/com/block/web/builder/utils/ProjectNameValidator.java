package com.block.web.builder.utils;

import java.util.regex.Pattern;

public class ProjectNameValidator {
  public static boolean isValidProjectName(String projectName) {
    Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");
    if ((projectName.trim().length() >= 0)
        && (pattern.matcher(projectName).matches())
        && (projectName.toString().trim().length() < 20)
        && (Character.isLetter(projectName.charAt(0)))) {
        return true;
    }
    return false;
  }
}
