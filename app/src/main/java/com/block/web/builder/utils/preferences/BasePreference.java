package com.block.web.builder.utils.preferences;

import java.io.Serializable;

public class BasePreference implements Serializable {
  public static final long serialVersionUID = 428383843L;
  private PeferenceType preferenceType;

  private String preferenceKey;

  /*
   * Defalt values
   */

  public enum PeferenceType {
    BooleanPreference
  }

  public PeferenceType getPreferenceType() {
    return preferenceType;
  }

  public void setPreferenceType(PeferenceType preferenceType) {
    this.preferenceType = preferenceType;
  }

  public String getPreferenceKey() {
    return preferenceKey;
  }

  public void setPreferenceKey(String preferenceKey) {
    this.preferenceKey = preferenceKey;
  }
}
