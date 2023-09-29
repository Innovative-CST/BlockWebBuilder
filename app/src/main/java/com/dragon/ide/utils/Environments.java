package com.dragon.ide.utils;

import android.os.Environment;
import java.io.File;

public final class Environments {
  public static File PROJECTS_FOLDER;
  public static File PROJECTS;

  public static void init() {
    PROJECTS_FOLDER =
        new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".DragonIDE");
    PROJECTS = new File(PROJECTS_FOLDER, "Projects");
  }
}
