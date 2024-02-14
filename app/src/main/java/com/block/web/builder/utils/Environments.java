/*
 *  This file is part of BlockWeb Builder.
 *
 *  BlockWeb Builder is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BlockWeb Builder is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with BlockWeb Builder.  If not, see <https://www.gnu.org/licenses/>.
 */

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
  public static File PREFERENCES;

  public static void init() {
    IDEDIR = new File(Environment.getExternalStorageDirectory(), ".BlockWebBuilder");
    PROJECTS = new File(IDEDIR, "Projects");
    RESOURCES = new File(IDEDIR, "res");
    BLOCKS = new File(RESOURCES, "block");
    CACHE = new File(IDEDIR, ".cache");
    CROPED_IMAGE_CACHE = new File(CACHE, "croppedImage");
    PREFERENCES = new File(IDEDIR, "settings");
  }
}
