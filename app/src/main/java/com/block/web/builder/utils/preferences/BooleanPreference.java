package com.block.web.builder.utils.preferences;

import java.io.Serializable;

public class BooleanPreference extends BasePreference implements Serializable {
  public static final long serialVersionUID = 428383844L;

  private boolean preferenceValue = false;

  public boolean getPreferenceValue() {
    return preferenceValue;
  }

  public void setPreferenceValue(boolean preferenceValue) {
    this.preferenceValue = preferenceValue;
  }
}
