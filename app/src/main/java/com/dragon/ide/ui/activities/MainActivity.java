package com.dragon.ide.ui.activities;

import static com.dragon.ide.utils.Environments.PROJECTS;

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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.dragon.ide.R;
import com.dragon.ide.databinding.ActivityMainBinding;
import com.dragon.ide.listeners.ProjectCreationListener;
import com.dragon.ide.objects.Project;
import com.dragon.ide.ui.adapters.ProjectsManagerListAdapter;
import com.dragon.ide.ui.dialogs.CreateProjectDialog;
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
    binding.toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {
            onBackPressed();
          }
        });

    // Create project dialog when fab is clicked.
    binding.fab.setOnClickListener(
        (view) -> {
          CreateProjectDialog dialog =
              new CreateProjectDialog(
                  MainActivity.this,
                  new ProjectCreationListener() {
                    @Override
                    public void onProjectCreated(String projectName) {
                      loadProjectInList();
                    }
                  });
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
           * Check if .DragonIDE/Projects folder exists.
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

            // Displays no projects yet.
            binding.loading.setVisibility(View.GONE);
            binding.noProjectsYet.setVisibility(View.VISIBLE);
            binding.permissionDenied.setVisibility(View.GONE);
            binding.projectList.setVisibility(View.GONE);
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
  protected void onActivityResult(int arg0, int arg1, Intent arg2) {
    super.onActivityResult(arg0, arg1, arg2);
    if (isStoagePermissionGranted(this)) {
      startActivtyLogic();
    } else {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        showRationaleOfStoragePermissionDialog(this);
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
