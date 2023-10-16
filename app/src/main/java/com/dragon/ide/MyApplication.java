package com.dragon.ide;

import android.app.Application;
import com.dragon.ide.utils.Environments;
import com.google.android.material.color.DynamicColors;

public class MyApplication extends Application {
  @Override
  public void onCreate() {

    // Initiate all static imports of Environments
    Environments.init();

    // Apply dynamic colors
    DynamicColors.applyToActivitiesIfAvailable(this);
    super.onCreate();
  }
}
