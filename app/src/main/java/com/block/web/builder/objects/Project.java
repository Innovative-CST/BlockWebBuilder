package com.block.web.builder.objects;

import java.io.Serializable;

public class Project implements Serializable {
  public static final long serialVersionUID = 428383834L;
  public String projectName;
  public String projectPhotoPath;

  public String getProjectName() {
    return this.projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getProjectPhotoPath() {
    if (projectPhotoPath != null) {
      return this.projectPhotoPath;
    }
    return "";
  }

  public void setProjectPhotoPath(String projectPhotoPath) {
    this.projectPhotoPath = projectPhotoPath;
  }
}
