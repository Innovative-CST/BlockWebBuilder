package com.block.web.builder.ui.activities;

import android.code.editor.common.utils.FileUtils;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import com.block.web.builder.R;
import com.block.web.builder.databinding.ActivityLicenseReaderBinding;

public class LicenseReaderActivity extends BaseActivity {
  private ActivityLicenseReaderBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityLicenseReaderBinding.inflate(getLayoutInflater());
    // set content view to binding's root.
    setContentView(binding.getRoot());

    binding.toolbar.setTitle(R.string.app_name);
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

    binding.LicenseText.setAutoLinkMask(Linkify.WEB_URLS);
    binding.LicenseText.setMovementMethod(LinkMovementMethod.getInstance());
    binding.LicenseText.setText(
        FileUtils.readFileFromAssets(getAssets(), getIntent().getStringExtra("Path")));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
  }
}
