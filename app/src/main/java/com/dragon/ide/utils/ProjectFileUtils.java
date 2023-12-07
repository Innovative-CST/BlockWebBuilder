package com.dragon.ide.utils;

import static com.dragon.ide.utils.Environments.PROJECTS;

import java.io.File;

public class ProjectFileUtils {
  public static final String PROJECT_FILES_DIRECTORY = "files";
  public static final String PROJECT_CONFIGRATION_FILE = "Project.txt";
  public static final String WEBFILE_FILE = "file.event";
  public static final String EVENTS_DIRECTORY = "events";

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
