package com.dragon.ide.objects;

import java.io.Serializable;

public class Project implements Serializable {
  public String projectName;

  public String getProjectName() {
    return this.projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
}
