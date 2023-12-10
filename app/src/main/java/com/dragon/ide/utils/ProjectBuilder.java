package com.dragon.ide.utils;

import android.app.Activity;
import android.code.editor.common.interfaces.FileDeleteListener;
import android.code.editor.common.utils.FileDeleteUtils;
import android.code.editor.common.utils.FileUtils;
import android.content.Context;
import com.dragon.ide.listeners.LogListener;
import com.dragon.ide.listeners.TaskListener;
import com.dragon.ide.objects.Event;
import com.dragon.ide.objects.WebFile;
import java.io.File;
import java.util.ArrayList;

public class ProjectBuilder {
  public static void generateProjectCode(
      File projectPath, LogListener listener, Activity activity) {

    /*
     * Clean Build directory to run a new refreshed website
     */

    listener.onLog("Cleaning build directory", 0);
    FileDeleteUtils.delete(
        new File(projectPath, ProjectFileUtils.BUILD_DIRECTORY),
        new ProjectBuilder().new FDL(activity),
        true,
        activity);

    /*
     * Generate files
     */
    generateFiles(
        projectPath, listener, activity, new File(projectPath, ProjectFileUtils.BUILD_DIRECTORY));
  }

  public static void generateFiles(
      File projectPath, LogListener listener, Activity activity, File destinationFolder) {
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

  public class FDL implements FileDeleteListener {
    private Context context;

    public FDL(Context c) {
      context = c;
    }

    @Override
    public void onProgressUpdate(int deleteDone) {}

    @Override
    public void onTotalCount(int total) {}

    @Override
    public void onDeleting(File path) {}

    @Override
    public void onDeleteComplete(File path) {}

    @Override
    public void onTaskComplete() {}
  }
}
