package com.block.web.builder.utils;

import android.os.Environment;
import java.io.File;

public final class Environments {
  public static File IDEDIR;
  public static File PROJECTS;
  public static File RESOURCES;
  public static File BLOCKS;
  public static File CACHE;
  public static File CROPED_IMAGE_CACHE;

  public static void init() {
    IDEDIR = new File(Environment.getExternalStorageDirectory(), ".BlockWebBuilder");
    PROJECTS = new File(IDEDIR, "Projects");
    RESOURCES = new File(IDEDIR, "res");
    BLOCKS = new File(RESOURCES, "block");
    CACHE = new File(IDEDIR, ".cache");
    CROPED_IMAGE_CACHE = new File(CACHE, "croppedImage");
  }
}
