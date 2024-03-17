/*
 * This file is part of BlockWeb Builder [https://github.com/TS-Code-Editor/BlockWebBuilder].
 *
 * License Agreement
 * This software is licensed under the terms and conditions outlined below. By accessing, copying, modifying, or using this software in any way, you agree to abide by these terms.
 *
 * 1. **  Copy and Modification Restrictions  **
 *    - You are not permitted to copy or modify the source code of this software without the permission of the owner, which may be granted publicly on GitHub Discussions or on Discord.
 *    - If permission is granted by the owner, you may copy the software under the terms specified in this license agreement.
 *    - You are not allowed to permit others to copy the source code that you were allowed to copy by the owner.
 *    - Modified or copied code must not be further copied.
 * 2. **  Contributor Attribution  **
 *    - You must attribute the contributors by creating a visible list within the application, showing who originally wrote the source code.
 *    - If you copy or modify this software under owner permission, you must provide links to the profiles of all contributors who contributed to this software.
 * 3. **  Modification Documentation  **
 *    - All modifications made to the software must be documented and listed.
 *    - the owner may incorporate the modifications made by you to enhance this software.
 * 4. **  Consistent Licensing  **
 *    - All copied or modified files must contain the same license text at the top of the files.
 * 5. **  Permission Reversal  **
 *    - If you are granted permission by the owner to copy this software, it can be revoked by the owner at any time. You will be notified at least one week in advance of any such reversal.
 *    - In case of Permission Reversal, if you fail to acknowledge the notification sent by us, it will not be our responsibility.
 * 6. **  License Updates  **
 *    - The license may be updated at any time. Users are required to accept and comply with any changes to the license.
 *    - In such circumstances, you will be given 7 days to ensure that your software complies with the updated license.
 *    - We will not notify you about license changes; you need to monitor the GitHub repository yourself (You can enable notifications or watch the repository to stay informed about such changes).
 * By using this software, you acknowledge and agree to the terms and conditions outlined in this license agreement. If you do not agree with these terms, you are not permitted to use, copy, modify, or distribute this software.
 *
 * Copyright © 2024 Dev Kumar
 */

package com.block.web.builder.ui.activities;

import static com.block.web.builder.utils.Environments.PROJECTS;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.block.web.builder.R;
import com.block.web.builder.databinding.ActivityMainBinding;
import com.block.web.builder.listeners.ProjectCreationListener;
import com.block.web.builder.objects.Project;
import com.block.web.builder.ui.adapters.ProjectsManagerListAdapter;
import com.block.web.builder.ui.dialogs.CreateProjectDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * MainActivity is the entry point of Dragon IDE.
 * This activity is used to open and create projects.
 */

public class MainActivity extends BaseActivity {
  private ActivityMainBinding binding;
  private CreateProjectDialog dialog;
  public String croppedImagePath = "";
  public static final int PICK_IMAGE_REQUEST = 1;
  public static final int IMAGE_CROP_REQUEST = 2;

  // Project list will be stored in this ArrayList.
  public ArrayList<HashMap<String, Object>> projects;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Inflate and get instance of binding.
    binding = ActivityMainBinding.inflate(getLayoutInflater());

    // set content view to binding's root.
    setContentView(binding.getRoot());

    // Initialize projects to avoid null errors.
    projects = new ArrayList<HashMap<String, Object>>();

    // Setup toolbar.
    binding.toolbar.setTitle(R.string.app_name);
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar, R.string.app_name, R.string.app_name);
    binding.toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {
            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
              binding.drawerLayout.closeDrawer(GravityCompat.START);
            } else {
              binding.drawerLayout.openDrawer(GravityCompat.START);
            }
          }
        });
    binding.drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    binding.navigationView.setNavigationItemSelectedListener(
        menuItem -> {
          if (menuItem.getItemId() == R.id.new_project) {
            // Create project dialog when create project is clicked.
            createNewProject();
          }
          if (menuItem.getItemId() == R.id.blocks_manager) {
            Intent blockManager = new Intent();
            blockManager.setClass(this, BlocksHolderManagerActivity.class);
            startActivity(blockManager);
          } else if (menuItem.getItemId() == R.id.settings) {
            Intent setting = new Intent();
            setting.setClass(this, SettingActivity.class);
            startActivity(setting);
          } else if (menuItem.getItemId() == R.id.source_License) {
            Intent LicenseActivity = new Intent();
            LicenseActivity.setClass(this, LicenseActivity.class);
            startActivity(LicenseActivity);
          }
          return true;
        });

    // Create project dialog when fab is clicked.
    binding.fab.setOnClickListener(
        (view) -> {
          createNewProject();
        });

    binding.createNewProject.setOnClickListener(
        (view) -> {
          createNewProject();
        });

    /*
     * Ask for storage permission if not granted.
     * Load projects if storage permission is granted.
     */
    if (isStoagePermissionGranted(this)) {
      loadProjectInList();
    } else {
      binding.loading.setVisibility(View.GONE);
      binding.noProjectsYet.setVisibility(View.GONE);
      binding.permissionDenied.setVisibility(View.VISIBLE);
      binding.projectList.setVisibility(View.GONE);
      showStoragePermissionDialog(this);
    }
  }

  public void createNewProject() {
    dialog =
        new CreateProjectDialog(
            MainActivity.this,
            projects,
            new ProjectCreationListener() {
              @Override
              public void onProjectCreated(String projectName) {
                loadProjectInList();
              }
            });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    this.binding = null;
  }

  /*
   * Loads the project list from Project directory.
   * If list is loading then it shows loading view.
   * Once list is ready it displays it in a RecyclerView
   * If there is not project available then it shows "No projects yet"
   */
  public void loadProjectInList() {

    // List is loading, so it shows loading view.
    binding.loading.setVisibility(View.VISIBLE);
    binding.noProjectsYet.setVisibility(View.GONE);
    binding.permissionDenied.setVisibility(View.GONE);
    binding.projectList.setVisibility(View.GONE);

    // Load project list in a saparate thread to avoid UI freeze.
    ExecutorService backgroundThread = Executors.newSingleThreadExecutor();
    backgroundThread.execute(
        () -> {

          /*
           * Check if .BlockWebBuilder/Projects folder exists.
           * Displays no projects yet if it doesn't exists
           * Load projects if it exists.
           */
          if (PROJECTS.exists()) {

            // Clear project list before adding from file to insure projects are not doubled.
            projects.clear();

            /*
             * Lists all directory from project directory.
             * 1. Checks if path is directory.
             * 2. Checks if it contains Project.txt file in that path.
             * 3. Checks if the Project.txt file is an Object and instanceof Project.
             * If 1, 2, 3 conditions are met then add the path in project list.
             */
            for (File project : PROJECTS.listFiles()) {
              try {
                FileInputStream fis = new FileInputStream(new File(project, "Project.txt"));
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();
                if (obj instanceof Project) {
                  HashMap<String, Object> projectListItem = new HashMap<String, Object>();
                  projectListItem.put("Project", (Project) obj);
                  projectListItem.put("Path", project);
                  projects.add(projectListItem);
                }
                fis.close();
                ois.close();
              } catch (Exception e) {
              }
            }

            // Showing changes in UI thread.
            MainActivity.this.runOnUiThread(
                () -> {
                  if (projects != null) {
                    if (projects.size() == 0) {

                      // Displays no projects yet.
                      binding.loading.setVisibility(View.GONE);
                      binding.noProjectsYet.setVisibility(View.VISIBLE);
                      binding.permissionDenied.setVisibility(View.GONE);
                      binding.projectList.setVisibility(View.GONE);
                    } else {

                      // Shows project list in RecyclerView
                      binding.loading.setVisibility(View.GONE);
                      binding.noProjectsYet.setVisibility(View.GONE);
                      binding.permissionDenied.setVisibility(View.GONE);
                      binding.projectList.setVisibility(View.VISIBLE);

                      // Load projects in RecyclerView
                      binding.list.setAdapter(
                          new ProjectsManagerListAdapter(projects, MainActivity.this));
                      binding.list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }
                  } else {

                    // Displays no projects yet.
                    binding.loading.setVisibility(View.GONE);
                    binding.noProjectsYet.setVisibility(View.VISIBLE);
                    binding.permissionDenied.setVisibility(View.GONE);
                    binding.projectList.setVisibility(View.GONE);
                  }
                });
          } else {
            // Showing changes in UI thread.
            MainActivity.this.runOnUiThread(
                () -> {
                  // Displays no projects yet.
                  binding.loading.setVisibility(View.GONE);
                  binding.noProjectsYet.setVisibility(View.VISIBLE);
                  binding.permissionDenied.setVisibility(View.GONE);
                  binding.projectList.setVisibility(View.GONE);
                });
          }
        });
  }

  private void startActivtyLogic() {
    // Loads project list.
    loadProjectInList();
  }

  // Checks if storage permission is granted or not.
  public static boolean isStoagePermissionGranted(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      return Environment.isExternalStorageManager();
    } else {
      if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
              == PackageManager.PERMISSION_DENIED
          || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
              == PackageManager.PERMISSION_DENIED) {
        return false;
      } else {
        return true;
      }
    }
  }

  /*
   * Requests to grant storage permission.
   * If android version is 10 and below then it shows storage permission dialog.
   * If android version is 11 and above then it redirects to settings.
   */
  public static void _requestStoragePermission(Activity activity, int reqCode) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      try {
        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, reqCode);
      } catch (Exception e) {

      }
    } else {
      ActivityCompat.requestPermissions(
          activity,
          new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
          },
          reqCode);
    }
  }

  // Storage permission result after user actions.
  @Override
  public void onRequestPermissionsResult(int arg0, String[] arg1, int[] arg2) {
    super.onRequestPermissionsResult(arg0, arg1, arg2);
    switch (arg0) {
      case 1:
      case -1:
      case 10:
        int Denied = 0;
        for (int position = 0; position < arg2.length; position++) {
          if (arg2[position] == PackageManager.PERMISSION_DENIED) {
            Denied++;
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(arg1[position])) {
              if (Build.VERSION.SDK_INT >= 23) {
                if (shouldShowRequestPermissionRationale(arg1[position])) {
                  showRationaleOfStoragePermissionDialog(this);
                } else {
                  showStoragePermissionDialogForGoToSettings(this);
                }
              }
            }
          }
        }
        if (Denied == 0) {
          startActivtyLogic();
        }
        break;
    }
  }

  @Override
  @CallSuper
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (isStoagePermissionGranted(this)) {
      startActivtyLogic();
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        showRationaleOfStoragePermissionDialog(this);
      }
    }
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
      Uri selectedImage = data.getData();
      Intent intent = new Intent();
      intent.setClass(this, ImageCropperActivity.class);
      intent.putExtra("pickedImageUri", selectedImage);
      startActivityForResult(intent, IMAGE_CROP_REQUEST);
    }
    if (requestCode == IMAGE_CROP_REQUEST && resultCode == RESULT_OK && data != null) {
      Uri selectedImage = Uri.parse(data.getStringExtra("path"));
      if (dialog != null) {
        if (dialog.dialogBinding != null) {
          if (dialog.mBottomSheetDialog != null) {
            if (dialog.mBottomSheetDialog.isShowing()) {
              dialog.mBottomSheetDialog.dismiss();
            }
          }
          dialog.isAddedProjectPhoto = true;
          dialog.dialogBinding.websiteIcon.setImageURI(selectedImage);
          croppedImagePath = data.getStringExtra("path");
        }
      }
    }
  }

  public static void showStoragePermissionDialog(Activity activity) {
    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
    dialog.setTitle("Storage permission required");
    dialog.setMessage(
        "Storage permission is required please allow app to use storage in next page.");
    dialog.setPositiveButton(
        "Continue",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            _requestStoragePermission(activity, 1);
          }
        });
    dialog.setNegativeButton(
        "No thanks",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            activity.finishAffinity();
          }
        });
    dialog.create().show();
  }

  public static void showRationaleOfStoragePermissionDialog(Activity activity) {
    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
    dialog.setTitle("Storage permission required");
    dialog.setMessage(
        "Storage permissions is highly recommend for storing and reading files in device.Without this permission you can't use this app.");
    dialog.setPositiveButton(
        "Continue",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            _requestStoragePermission(activity, 1);
          }
        });
    dialog.setNegativeButton(
        "No thanks",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            activity.finishAffinity();
          }
        });
    dialog.create().show();
  }

  public static void showStoragePermissionDialogForGoToSettings(Activity context) {
    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
    dialog.setTitle("Storage permission required");
    dialog.setMessage(
        "Storage permissions is highly recommend for storing and reading files in device.Without this permission you can't use this app.");
    dialog.setPositiveButton(
        "Setting",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            Intent intent = new Intent();
            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
          }
        });
    dialog.setNegativeButton(
        "No thanks",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface _dialog, int _which) {
            context.finishAffinity();
          }
        });
    dialog.create().show();
  }
}
