package com.dragon.ide.ui.activities;

import static com.dragon.ide.utils.Environments.PROJECTS;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.dragon.ide.R;
import com.dragon.ide.databinding.ActivityEventEditorBinding;
import com.dragon.ide.objects.BlocksHolder;
import com.dragon.ide.objects.WebFile;
import com.dragon.ide.ui.adapters.BlocksHolderEventEditorListItem;
import com.dragon.ide.utils.eventeditor.BlocksListLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventEditorActivity extends BaseActivity {
  private ActivityEventEditorBinding binding;
  private ArrayList<WebFile> fileList;
  private ArrayList<BlocksHolder> blocksHolder;
  private String projectName;
  private String projectPath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityEventEditorBinding.inflate(getLayoutInflater());
    // set content view to binding's root.
    setContentView(binding.getRoot());

    // Initialize to avoid null error
    fileList = new ArrayList<WebFile>();
    blocksHolder = new ArrayList<BlocksHolder>();

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
      projectPath = getIntent().getStringExtra("projectPath");
    } else {
      showSection(2);
      binding.tvInfo.setText(getString(R.string.project_name_not_passed));
    }
    /*
     * Ask for storage permission if not granted.
     * Load projects if storage permission is granted.
     */
    if (!MainActivity.isStoagePermissionGranted(this)) {
      showSection(2);
      binding.tvInfo.setText(R.string.storage_permission_denied);
      MainActivity.showStoragePermissionDialog(this);
    } else {
      loadFileList();
    }

    /*
     * Loads blocks holder
     */
    BlocksListLoader blocksListLoader = new BlocksListLoader();
    blocksListLoader.loadBlocks(
        EventEditorActivity.this,
        new BlocksListLoader.Progress() {

          @Override
          public void onCompleteLoading(ArrayList<BlocksHolder> holder) {
            binding.blocksHolderList.setAdapter(
                new BlocksHolderEventEditorListItem(holder, EventEditorActivity.this));
            binding.blocksHolderList.setLayoutManager(new LinearLayoutManager(EventEditorActivity.this));
          }
        });

    binding.fab.setOnClickListener(
        (view) -> {
          if (binding.blockArea.getVisibility() == View.GONE) {
            binding.blockArea.setVisibility(View.VISIBLE);
          } else if (binding.blockArea.getVisibility() == View.VISIBLE) {
            binding.blockArea.setVisibility(View.GONE);
          }
        });
  }

  public void showSection(int section) {
    binding.loading.setVisibility(View.GONE);
    binding.info.setVisibility(View.GONE);
    binding.editor.setVisibility(View.GONE);
    switch (section) {
      case 1:
        binding.loading.setVisibility(View.VISIBLE);
        break;
      case 2:
        binding.info.setVisibility(View.VISIBLE);
        break;
      case 3:
        binding.editor.setVisibility(View.VISIBLE);
        break;
    }
  }

  public void loadFileList() {
    // List is loading, so it shows loading view.
    showSection(1);

    // Load project list in a saparate thread to avoid UI freeze.
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          if (PROJECTS.exists()) {
            if (!new File(projectPath).exists()) {
              showSection(2);
              binding.tvInfo.setText(getString(R.string.project_not_found));
            } else {
              if (new File(new File(projectPath), "Files.txt").exists()) {
                try {
                  FileInputStream fis =
                      new FileInputStream(new File(new File(projectPath), "Files.txt"));
                  ObjectInputStream ois = new ObjectInputStream(fis);
                  Object obj = ois.readObject();
                  if (obj instanceof ArrayList) {
                    fileList = (ArrayList<WebFile>) obj;
                  }
                  fis.close();
                  ois.close();

                  showSection(3);
                } catch (Exception e) {
                  runOnUiThread(
                      () -> {
                        showSection(2);
                        binding.tvInfo.setText(
                            getString(R.string.an_error_occured_while_parsing_file_list));
                      });
                }
              } else {
                runOnUiThread(
                    () -> {
                      showSection(2);
                      binding.tvInfo.setText(getString(R.string.no_files_yet));
                    });
              }
            }
          } else {
            runOnUiThread(
                () -> {
                  showSection(2);
                  binding.tvInfo.setText(getString(R.string.project_not_found));
                });
          }
        });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
  }
}
