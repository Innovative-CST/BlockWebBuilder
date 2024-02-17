/*
 *  This file is part of BlockWeb Builder.
 *
 *  BlockWeb Builder is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BlockWeb Builder is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with BlockWeb Builder.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.block.web.builder.ui.activities;

import static com.block.web.builder.utils.Environments.PROJECTS;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.MainThread;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.block.web.builder.R;
import com.block.web.builder.databinding.ActivityFileManagerBinding;
import com.block.web.builder.listeners.TaskListener;
import com.block.web.builder.core.WebFile;
import com.block.web.builder.ui.adapters.FileListAdapterItem;
import com.block.web.builder.ui.dialogs.filemanager.CreateFileDialog;
import com.block.web.builder.utils.DeserializationException;
import com.block.web.builder.utils.DeserializerUtils;
import com.block.web.builder.utils.ProjectFileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FileManagerActivity extends BaseActivity {
  private ActivityFileManagerBinding binding;
  private ArrayList<WebFile> fileList;
  private ArrayList<String> filePath;
  private String projectName;
  private String projectPath;
  private String ListPath;
  private String outputDirectory;
  private boolean isLoaded = false;

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);

    // Inflate and get instance of binding.
    binding = ActivityFileManagerBinding.inflate(getLayoutInflater());

    // set content view to binding's root.
    setContentView(binding.getRoot());

    // Initialize fileList and filePath to avoid null error
    fileList = new ArrayList<WebFile>();
    filePath = new ArrayList<String>();

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
      ListPath = getIntent().getStringExtra("ListPath");
      outputDirectory = getIntent().getStringExtra("outputDirectory");
      // Set toolbar title to project name
      binding.toolbar.setTitle(projectName);
    } else {
      showSection(5);
      binding.errorText.setText(getString(R.string.project_name_not_passed));
    }
    binding.fab.setOnClickListener(
        (view) -> {
          CreateFileDialog createFileDialog =
              new CreateFileDialog(
                  FileManagerActivity.this, filePath, fileList, projectName, projectPath);
          createFileDialog.create().show();
        });
    /*
     * Ask for storage permission if not granted.
     * Load projects if storage permission is granted.
     */
    if (!MainActivity.isStoagePermissionGranted(this)) {
      showSection(3);
      MainActivity.showStoragePermissionDialog(this);
      return;
    }
    showFileList();
  }

  public RecyclerView getFileListRecyclerView() {
    return binding.list;
  }

  public void showFileList() {
    // List is loading, so it shows loading view.
    showSection(1);

    // Load project list in a saparate thread to avoid UI freeze.
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          if (PROJECTS.exists()) {
            if (!new File(projectPath).exists()) {
              showSection(5);
              binding.errorText.setText(getString(R.string.project_not_found));
            } else {
              if (new File(ListPath).exists()) {
                ArrayList<WebFile> fileList = new ArrayList<WebFile>();
                ArrayList<String> filePath = new ArrayList<String>();
                for (File fileDirectory : new File(ListPath).listFiles()) {
                  try {
                    DeserializerUtils.deserializeWebfile(
                        ProjectFileUtils.getProjectWebFile(fileDirectory),
                        new TaskListener() {
                          @Override
                          public void onSuccess(Object webFile) {
                            fileList.add((WebFile) webFile);
                            filePath.add(
                                ProjectFileUtils.getProjectWebFile(fileDirectory)
                                    .getAbsolutePath());
                          }
                        });
                  } catch (DeserializationException e) {
                  }
                }
                if (fileList.size() > 0) {
                  runOnUiThread(
                      () -> {
                        isLoaded = true;
                        binding.list.setAdapter(
                            new FileListAdapterItem(
                                fileList,
                                filePath,
                                FileManagerActivity.this,
                                projectName,
                                projectPath));
                        binding.list.setLayoutManager(
                            new LinearLayoutManager(FileManagerActivity.this));
                        showSection(4);
                      });
                  FileManagerActivity.this.fileList = fileList;
                  FileManagerActivity.this.filePath = filePath;
                } else {
                  isLoaded = true;
                  runOnUiThread(
                      () -> {
                        FileManagerActivity.this.fileList = fileList;
                        FileManagerActivity.this.filePath = filePath;
                        showSection(5);
                        binding.errorText.setText(getString(R.string.no_files_yet));
                      });
                }
              } else {
                isLoaded = true;
                runOnUiThread(
                    () -> {
                      showSection(5);
                      binding.errorText.setText(getString(R.string.no_files_yet));
                    });
              }
            }
          } else {
            runOnUiThread(
                () -> {
                  isLoaded = true;
                  showSection(5);
                  binding.errorText.setText(getString(R.string.project_not_found));
                });
          }
        });
  }

  /*
   * Method for switching display of various layouts
   */
  public void showSection(int section) {
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

  public void saveFileList(TaskListener listener) {
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          for (int i = 0; i < fileList.size(); ++i) {
            try {
              File webFileDestination =
                  ProjectFileUtils.getProjectWebFile(
                      new File(
                          new File(ListPath),
                          fileList
                              .get(i)
                              .getFilePath()
                              .concat(
                                  WebFile.getSupportedFileSuffix(fileList.get(i).getFileType()))));
              if (!webFileDestination.getParentFile().exists()) {
                webFileDestination.getParentFile().mkdirs();
              }

              FileOutputStream fos = new FileOutputStream(webFileDestination);
              ObjectOutputStream oos = new ObjectOutputStream(fos);
              oos.writeObject(fileList.get(i));
              fos.close();
              oos.close();
            } catch (Exception e) {
            }
          }
          if (listener != null) {
            listener.onSuccess(null);
          }
        });
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (isLoaded) {
      saveFileList(null);
    }
  }

  @Override
  @MainThread
  public void onBackPressed() {
    super.onBackPressed();
    if (isLoaded) {
      saveFileList(
          new TaskListener() {
            @Override
            public void onSuccess(Object result) {
              finish();
            }
          });
    }
  }

  @Override
  protected void onResume() {
    showFileList();
    super.onResume();
  }

  public ArrayList<WebFile> getFileList() {
    return this.fileList;
  }

  public void setFileList(ArrayList<WebFile> fileList) {
    this.fileList = fileList;
  }

  public String getListPath() {
    return this.ListPath;
  }

  public void setListPath(String ListPath) {
    this.ListPath = ListPath;
  }

  public String getOutputDirectory() {
    return this.outputDirectory;
  }

  public void setOutputDirectory(String outputDirectory) {
    this.outputDirectory = outputDirectory;
  }
}
