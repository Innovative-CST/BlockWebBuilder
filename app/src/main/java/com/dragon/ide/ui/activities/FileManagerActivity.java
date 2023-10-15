package com.dragon.ide.ui.activities;

import static com.dragon.ide.utils.Environments.PROJECTS;

import android.os.Bundle;
import android.view.View;
import com.dragon.ide.R;
import com.dragon.ide.databinding.ActivityFileManagerBinding;
import com.dragon.ide.objects.WebFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FileManagerActivity extends BaseActivity {
  private ActivityFileManagerBinding binding;
  private ArrayList<WebFile> fileList;
  private String projectName;

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);

    // Inflate and get instance of binding.
    binding = ActivityFileManagerBinding.inflate(getLayoutInflater());

    // set content view to binding's root.
    setContentView(binding.getRoot());

    // Initialize fileList to avoid null error
    fileList = new ArrayList<WebFile>();

    // Setup toolbar.
    binding.toolbar.setTitle(R.string.app_name);
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    binding.toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {
            onBackPressed();
          }
        });

    if (getIntent().hasExtra("projectName")) {
      projectName = getIntent().getStringExtra("projectName");
    } else {
      showSection(5);
      binding.errorText.setText(getString(R.string.project_name_not_passed));
    }
    /*
     * Ask for storage permission if not granted.
     * Load projects if storage permission is granted.
     */
    if (!MainActivity.isStoagePermissionGranted(this)) {
      showSection(3);
      MainActivity.showStoragePermissionDialog(this);
    } else {
      showFileList();
    }
  }

  private void showFileList() {
    // List is loading, so it shows loading view.
    showSection(1);

    // Load project list in a saparate thread to avoid UI freeze.
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          if (PROJECTS.exists()) {
            if (!new File(PROJECTS, projectName).exists()) {
              showSection(5);
              binding.errorText.setText(getString(R.string.project_not_found));
            } else {
              try {
                FileInputStream fis =
                    new FileInputStream(new File(new File(PROJECTS, projectName), "Files.txt"));
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();
                if (obj instanceof ArrayList) {
                  fileList = (ArrayList<WebFile>) obj;
                }
                fis.close();
                ois.close();
              } catch (Exception e) {
                runOnUiThread(
                    () -> {
                      showSection(5);
                      binding.errorText.setText(
                          getString(R.string.an_error_occured_while_parsing_file_list));
                    });
              }
            }
          } else {
            runOnUiThread(
                () -> {
                  showSection(5);
                  binding.errorText.setText(getString(R.string.project_not_found));
                });
          }
        });
  }

  private void showSection(int section) {
    binding.loading.setVisibility(View.GONE);
    binding.noFilesYet.setVisibility(View.GONE);
    binding.permissionDenied.setVisibility(View.GONE);
    binding.fileList.setVisibility(View.GONE);
    binding.error.setVisibility(View.GONE);
    switch (section) {
      case 1:
        binding.loading.setVisibility(View.VISIBLE);
        break;
      case 2:
        binding.noFilesYet.setVisibility(View.VISIBLE);
        break;
      case 3:
        binding.permissionDenied.setVisibility(View.VISIBLE);
        break;
      case 4:
        binding.fileList.setVisibility(View.VISIBLE);
        break;
      case 5:
        binding.error.setVisibility(View.VISIBLE);
        break;
    }
  }
}
