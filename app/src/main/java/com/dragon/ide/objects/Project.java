package com.dragon.ide.objects;

import java.io.Serializable;

public class Project implements Serializable {
  public String packageName;
  public String projectName;

  public String getPackageName() {
    return this.packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getProjectName() {
    return this.projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }
}
