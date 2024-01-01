package com.block.web.builder.utils;

import android.app.Activity;
import android.code.editor.common.interfaces.FileDeleteListener;
import android.code.editor.common.utils.FileDeleteUtils;
import android.code.editor.common.utils.FileUtils;
import com.block.web.builder.listeners.ProjectBuildListener;
import com.block.web.builder.listeners.TaskListener;
import com.block.web.builder.objects.Event;
import com.block.web.builder.objects.WebFile;
import java.io.File;
import java.util.ArrayList;

public class ProjectBuilder {
  public static void generateProjectCode(
      File projectPath, ProjectBuildListener listener, Activity activity) {

    /*
     * Clean Build directory to run a new refreshed website
     */

    listener.onBuildStart();
    listener.onLog("Cleaning build directory", 0);
    FileDeleteUtils.delete(
        new File(projectPath, ProjectFileUtils.BUILD_DIRECTORY),
        new FileDeleteListener() {
          @Override
          public void onProgressUpdate(int deleteDone) {}

          @Override
          public void onTotalCount(int total) {}

          @Override
          public void onDeleting(File path) {}

          @Override
          public void onDeleteComplete(File path) {}

          @Override
          public void onTaskComplete() {
            /*
             * Generate files
             */
            generateFiles(
                projectPath,
                listener,
                activity,
                new File(projectPath, ProjectFileUtils.BUILD_DIRECTORY));
            listener.onBuildComplete();
          }
        },
        true,
        activity);
  }

  public static void generateFiles(
      File projectPath, ProjectBuildListener listener, Activity activity, File destinationFolder) {
    if (ProjectFileUtils.getProjectFilesDirectory(projectPath).exists()) {
      for (File fileDirectory :
          ProjectFileUtils.getProjectFilesDirectory(projectPath).listFiles()) {
        try {
          DeserializerUtils.deserializeWebfile(
              ProjectFileUtils.getProjectWebFile(fileDirectory),
              new TaskListener() {
                @Override
                public void onSuccess(Object webFile) {
                  if (((WebFile) webFile).getFileType() == WebFile.SupportedFileType.FOLDER) {
                    generateFiles(
                        fileDirectory,
                        listener,
                        activity,
                        new File(destinationFolder, ((WebFile) webFile).getFilePath()));
                  } else {
                    listener.onLog(
                        "Deserialized file: "
                            .concat(
                                ProjectFileUtils.getProjectWebFile(fileDirectory)
                                    .getAbsolutePath()),
                        0);

                    if (!destinationFolder.exists()) {
                      destinationFolder.mkdirs();
                    }

                    ArrayList<Event> eventList = new ArrayList<Event>();
                    if (new File(fileDirectory, ProjectFileUtils.EVENTS_DIRECTORY).exists()) {
                      for (File event :
                          new File(fileDirectory, ProjectFileUtils.EVENTS_DIRECTORY).listFiles()) {
                        try {
                          DeserializerUtils.deserializeEvent(
                              event,
                              new TaskListener() {
                                @Override
                                public void onSuccess(Object mEvent) {
                                  listener.onLog(
                                      "Deserialized event: ".concat(event.getAbsolutePath()), 0);
                                  eventList.add((Event) mEvent);
                                }
                              });
                        } catch (DeserializationException e) {

                        }
                      }
                    }

                    FileUtils.writeFile(
                        new File(
                                destinationFolder,
                                ((WebFile) webFile)
                                    .getFilePath()
                                    .concat(
                                        WebFile.getSupportedFileSuffix(
                                            ((WebFile) webFile).getFileType())))
                            .getAbsolutePath(),
                        ((WebFile) webFile).getCode(eventList));
                  }
                }
              });
        } catch (DeserializationException e) {
        }
      }
    } else {
    }
  }
}
