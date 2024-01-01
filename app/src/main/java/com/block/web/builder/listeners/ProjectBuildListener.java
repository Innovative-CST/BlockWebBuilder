package com.block.web.builder.listeners;

public interface ProjectBuildListener {
  void onLog(String log, int type);

  void onBuildComplete();

  void onBuildStart();
}
