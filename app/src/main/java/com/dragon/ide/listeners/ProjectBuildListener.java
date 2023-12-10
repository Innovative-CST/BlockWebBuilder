package com.dragon.ide.listeners;

public interface ProjectBuildListener {
  void onLog(String log, int type);

  void onBuildComplete();

  void onBuildStart();
}
