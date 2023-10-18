package com.dragon.ide.listeners;

import com.dragon.ide.objects.WebFile;

public interface FileCreationListener {

  void onFileCreated(WebFile webFile);

  void onFileCreationFailed(String error);
}
