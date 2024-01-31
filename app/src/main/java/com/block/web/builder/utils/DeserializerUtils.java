package com.block.web.builder.utils;

import com.block.web.builder.listeners.TaskListener;
import com.block.web.builder.core.Event;
import com.block.web.builder.core.WebFile;
import com.block.web.builder.utils.preferences.BasePreference;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DeserializerUtils {
  public static void deserializeWebfile(File webfile, TaskListener listener)
      throws DeserializationException {
    if (webfile.exists()) {
      if (webfile.isFile()) {
        try {
          FileInputStream mFileInputStream = new FileInputStream(webfile);
          ObjectInputStream mObjectInputStream = new ObjectInputStream(mFileInputStream);
          Object mObject = mObjectInputStream.readObject();
          mFileInputStream.close();
          mObjectInputStream.close();
          if (mObject instanceof WebFile) {
            listener.onSuccess((WebFile) mObject);
          } else {
            DeserializationException error =
                new DeserializationException(
                    "The file you are trying to deserialize is not a instance of WebFile");
            error.setErrorType(DeserializationException.OBJECT_TYPE_EXCEPTION);
            throw error;
          }
        } catch (Exception e) {
          DeserializationException error = new DeserializationException(e.getMessage());
          error.setErrorType(DeserializationException.DESERIALIZATION_EXCEPTION);
          throw error;
        }
      } else {
        DeserializationException error =
            new DeserializationException(
                "The WebFile you are trying to deserialoze is a folder and it must be a file.");
        error.setErrorType(DeserializationException.FOLDER_FOUND_EXCEPTION);
        throw error;
      }
    } else {
      DeserializationException error = new DeserializationException("File not found");
      error.setErrorType(DeserializationException.FILE_NOT_FOUND_EXCEPTION);
      throw error;
    }
  }

  public static void deserializeEvent(File event, TaskListener listener)
      throws DeserializationException {
    if (event.exists()) {
      if (event.isFile()) {
        try {
          FileInputStream mFileInputStream = new FileInputStream(event);
          ObjectInputStream mObjectInputStream = new ObjectInputStream(mFileInputStream);
          Object mObject = mObjectInputStream.readObject();
          mFileInputStream.close();
          mObjectInputStream.close();
          if (mObject instanceof Event) {
            listener.onSuccess((Event) mObject);
          } else {
            DeserializationException error =
                new DeserializationException(
                    "The file you are trying to deserialize is not a instance of Event");
            error.setErrorType(DeserializationException.OBJECT_TYPE_EXCEPTION);
            throw error;
          }
        } catch (Exception e) {
          DeserializationException error = new DeserializationException(e.getMessage());
          error.setErrorType(DeserializationException.DESERIALIZATION_EXCEPTION);
          throw error;
        }
      } else {
        DeserializationException error =
            new DeserializationException(
                "The Event you are trying to deserialoze is a folder and it must be a file.");
        error.setErrorType(DeserializationException.FOLDER_FOUND_EXCEPTION);
        throw error;
      }
    } else {
      DeserializationException error = new DeserializationException("File not found");
      error.setErrorType(DeserializationException.FILE_NOT_FOUND_EXCEPTION);
      throw error;
    }
  }

  public static void deserializePreferences(File preferencesFile, TaskListener listener)
      throws DeserializationException {
    if (preferencesFile.exists()) {
      if (preferencesFile.isFile()) {
        try {
          FileInputStream mFileInputStream = new FileInputStream(preferencesFile);
          ObjectInputStream mObjectInputStream = new ObjectInputStream(mFileInputStream);
          Object mObject = mObjectInputStream.readObject();
          mFileInputStream.close();
          mObjectInputStream.close();
          if (mObject instanceof ArrayList) {
            listener.onSuccess((ArrayList<BasePreference>) mObject);
          } else {
            DeserializationException error =
                new DeserializationException(
                    "The file you are trying to deserialize is not a instance of preferences");
            error.setErrorType(DeserializationException.OBJECT_TYPE_EXCEPTION);
            throw error;
          }
        } catch (Exception e) {
          DeserializationException error = new DeserializationException(e.getMessage());
          error.setErrorType(DeserializationException.DESERIALIZATION_EXCEPTION);
          throw error;
        }
      } else {
        DeserializationException error =
            new DeserializationException(
                "The preferences you are trying to deserialoze is a folder and it must be a file.");
        error.setErrorType(DeserializationException.FOLDER_FOUND_EXCEPTION);
        throw error;
      }
    } else {
      DeserializationException error = new DeserializationException("File not found");
      error.setErrorType(DeserializationException.FILE_NOT_FOUND_EXCEPTION);
      throw error;
    }
  }
}
