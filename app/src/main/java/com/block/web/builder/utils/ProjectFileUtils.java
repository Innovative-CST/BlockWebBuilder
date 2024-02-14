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

import static com.block.web.builder.utils.Environments.PROJECTS;

import java.io.File;

public class ProjectFileUtils {
  public static final String PROJECT_FILES_DIRECTORY = "files";
  public static final String PROJECT_CONFIGRATION_FILE = "Project.txt";
  public static final String WEBFILE_FILE = "file";
  public static final String EVENTS_DIRECTORY = "events";
  public static final String BUILD_DIRECTORY = "build";

  public static File getProjectFile(String projectDirectoryName) {
    return new File(new File(PROJECTS, projectDirectoryName), PROJECT_CONFIGRATION_FILE);
  }

  public static File getProjectFilesDirectory(File projectPath) {
    return new File(projectPath, PROJECT_FILES_DIRECTORY);
  }

  /*
   * getProjectWebFile returns WebFile File.
   * where, fileDirectory = Project/files/$Directory
   */
  public static File getProjectWebFile(File fileDirectory) {
    return new File(fileDirectory, WEBFILE_FILE);
  }
}
