package com.block.web.builder.ui.activities;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle arg0) {
    EdgeToEdge.enable(this);
    super.onCreate(arg0);
  }
}
