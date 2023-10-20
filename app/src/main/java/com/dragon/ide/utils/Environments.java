package com.dragon.ide.utils;

import android.os.Environment;
import java.io.File;

public final class Environments {
  public static File IDEDIR;
  public static File PROJECTS;
  public static File RESOURCES;
  public static File BLOCKS;

  public static void init() {
    IDEDIR = new File(Environment.getExternalStorageDirectory(), ".DragonIDE");
    PROJECTS = new File(IDEDIR, "Projects");
    RESOURCES = new File(IDEDIR, "res");
    BLOCKS = new File(RESOURCES, "block");
  }
}
