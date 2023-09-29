package com.dragon.ide;

import android.app.Application;
import com.google.android.material.color.DynamicColors;

public class MyApplication extends Application {
  @Override
  public void onCreate() {
    DynamicColors.applyToActivitiesIfAvailable(this);
    super.onCreate();
  }
}
