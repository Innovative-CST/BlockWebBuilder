package com.dragon.ide.utils;

import android.os.Environment;
import java.io.File;

public final class Environments {
  public static File IDEDIR;
  public static File PROJECTS_FOLDER;
  public static File PROJECTS;
  public static File RESOURCES;
  public static File BLOCKS;

  public static void init() {
    IDEDIR = Environment.getExternalStorageDirectory();
    PROJECTS_FOLDER = new File(IDEDIR, ".DragonIDE");
    PROJECTS = new File(PROJECTS_FOLDER, "Projects");
    RESOURCES = new File(IDEDIR, "res");
    BLOCKS = new File(RESOURCES, "block");
  }
}
