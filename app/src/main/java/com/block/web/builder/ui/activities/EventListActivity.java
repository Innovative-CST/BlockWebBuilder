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

import static com.block.web.builder.utils.Environments.PREFERENCES;
import static com.block.web.builder.utils.Environments.PROJECTS;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.block.web.builder.R;
import com.block.web.builder.databinding.ActivityEventListBinding;
import com.block.web.builder.listeners.EventAddListener;
import com.block.web.builder.listeners.ProjectBuildListener;
import com.block.web.builder.listeners.TaskListener;
import com.block.web.builder.core.Event;
import com.block.web.builder.core.WebFile;
import com.block.web.builder.ui.adapters.EventListAdapter;
import com.block.web.builder.ui.dialogs.eventList.AddEventDialog;
import com.block.web.builder.ui.dialogs.eventList.ShowSourceCodeDialog;
import com.block.web.builder.utils.DeserializationException;
import com.block.web.builder.utils.DeserializerUtils;
import com.block.web.builder.utils.ProjectBuilder;
import com.block.web.builder.utils.ProjectFileUtils;
import com.block.web.builder.utils.preferences.BasePreference;
import editor.tsd.tools.Language;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventListActivity extends BaseActivity {
  private ActivityEventListBinding binding;
  private WebFile file;
  private String fileOutputPath;
  private ArrayList<Event> eventList;
  private ArrayList<BasePreference> preferences;
  private String projectName;
  private String projectPath;
  private String fileName;
  private String webFilePath;
  private int fileType;
  private boolean isLoaded;

  private MenuItem preview;

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);

    // Inflate and get instance of binding.
    binding = ActivityEventListBinding.inflate(getLayoutInflater());

    // set content view to binding's root.
    setContentView(binding.getRoot());

    // Initialize to avoid null error
    eventList = new ArrayList<Event>();
    projectName = "";
    projectPath = "";
    fileName = "";
    fileType = 0;
    isLoaded = false;
    file = new WebFile();

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
      fileName = getIntent().getStringExtra("fileName");
      fileType = getIntent().getIntExtra("fileType", 1);
      webFilePath = getIntent().getStringExtra("webFile");
      fileOutputPath = getIntent().getStringExtra("fileOutputPath");
      try {
        DeserializerUtils.deserializePreferences(
            PREFERENCES,
            new TaskListener() {
              @Override
              public void onSuccess(Object result) {
                preferences = (ArrayList<BasePreference>) result;
              }
            });
      } catch (DeserializationException e) {
        preferences = new ArrayList<BasePreference>();
      }
      try {
        DeserializerUtils.deserializeWebfile(
            new File(webFilePath),
            new TaskListener() {
              @Override
              public void onSuccess(Object mWebFile) {
                file = (WebFile) mWebFile;
                isLoaded = true;
                if (!(file.getFileType() == WebFile.SupportedFileType.HTML)) {
                  if (preview != null) {
                    preview.setVisible(false);
                  }
                } else {
                  if (preview != null) {
                    preview.setVisible(true);
                  }
                }
              }
            });
      } catch (DeserializationException e) {
        showSection(2);
        binding.tvInfo.setText(e.getMessage());
      }
    } else {
      showSection(2);
      binding.tvInfo.setText(getString(R.string.error));
    }

    binding.fab.setOnClickListener(
        (view) -> {
          AddEventDialog createFileDialog =
              new AddEventDialog(
                  EventListActivity.this,
                  file,
                  new EventAddListener() {
                    @Override
                    public void onAdd(ArrayList<Event> events) {
                      Executor executor = Executors.newSingleThreadExecutor();
                      executor.execute(
                          () -> {
                            for (int i = 0; i < events.size(); ++i) {
                              File eventFilePath =
                                  new File(
                                      new File(webFilePath).getParentFile(),
                                      ProjectFileUtils.EVENTS_DIRECTORY);
                              if (!eventFilePath.exists()) {
                                eventFilePath.mkdirs();
                              }
                              if (!new File(eventFilePath, events.get(i).getName()).exists()) {
                                try {
                                  FileOutputStream fos =
                                      new FileOutputStream(
                                          new File(eventFilePath, events.get(i).getName()));
                                  ObjectOutputStream oos = new ObjectOutputStream(fos);
                                  oos.writeObject(events.get(i));
                                  fos.close();
                                  oos.close();
                                } catch (Exception e) {
                                }
                              }
                            }
                            runOnUiThread(
                                () -> {
                                  showEventList();
                                });
                          });
                    }
                  });
        });
    /*
     * Ask for storage permission if not granted.
     * Load events if storage permission is granted.
     */
    if (!MainActivity.isStoagePermissionGranted(this)) {
      showSection(3);
      MainActivity.showStoragePermissionDialog(this);
    } else {
      showEventList();
    }
  }

  private void showEventList() {
    // List is loading, so it shows loading view.
    showSection(1);

    // Load event list in a saparate thread to avoid UI freeze.
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          if (PROJECTS.exists()) {
            if (!new File(projectPath).exists()) {
              runOnUiThread(
                  () -> {
                    showSection(2);
                    binding.tvInfo.setText(getString(R.string.project_not_found));
                  });
            } else {
              if (new File(new File(webFilePath).getParentFile(), ProjectFileUtils.EVENTS_DIRECTORY)
                  .exists()) {
                ArrayList<Event> eventList = new ArrayList<Event>();
                for (File event :
                    new File(
                            new File(webFilePath).getParentFile(),
                            ProjectFileUtils.EVENTS_DIRECTORY)
                        .listFiles()) {
                  try {
                    DeserializerUtils.deserializeEvent(
                        event,
                        new TaskListener() {
                          @Override
                          public void onSuccess(Object mEvent) {
                            eventList.add((Event) mEvent);
                          }
                        });
                  } catch (DeserializationException e) {
                  }
                }
                EventListActivity.this.eventList = eventList;
                runOnUiThread(
                    () -> {
                      if (eventList.size() == 0) {
                        showSection(2);
                        binding.tvInfo.setText(getString(R.string.no_events_yet));
                      } else {
                        showSection(3);
                        binding.list.setAdapter(
                            new EventListAdapter(
                                eventList,
                                EventListActivity.this,
                                projectName,
                                projectPath,
                                webFilePath));
                        binding.list.setLayoutManager(
                            new LinearLayoutManager(EventListActivity.this));
                      }
                    });
              } else {
                runOnUiThread(
                    () -> {
                      showSection(2);
                      binding.tvInfo.setText(getString(R.string.no_events_yet));
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

  public void showSection(int section) {
    binding.loading.setVisibility(View.GONE);
    binding.info.setVisibility(View.GONE);
    binding.eventList.setVisibility(View.GONE);
    switch (section) {
      case 1:
        binding.loading.setVisibility(View.VISIBLE);
        break;
      case 2:
        binding.info.setVisibility(View.VISIBLE);
        break;
      case 3:
        binding.eventList.setVisibility(View.VISIBLE);
        break;
    }
  }

  // Handle option menu
  @Override
  public boolean onCreateOptionsMenu(Menu arg0) {
    super.onCreateOptionsMenu(arg0);
    getMenuInflater().inflate(R.menu.activity_event_list_menu, arg0);
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu arg0) {
    preview = arg0.findItem(R.id.executor);
    if (!(file.getFileType() == WebFile.SupportedFileType.HTML)) {
      if (preview != null) {
        preview.setVisible(false);
      }
    } else {
      if (preview != null) {
        preview.setVisible(true);
      }
    }
    return super.onPrepareOptionsMenu(arg0);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem arg0) {
    if (arg0.getItemId() == R.id.show_source_code) {
      if (isLoaded) {
        String language = "";
        switch (WebFile.getSupportedFileSuffix(file.getFileType())) {
          case ".html":
            language = Language.HTML;
            break;
          case ".css":
            language = Language.CSS;
            break;
          case ".js":
            language = Language.JavaScript;
            break;
        }
        boolean useSoraEditor = false;
        if (preferences != null) {
          if ((boolean)
              SettingActivity.getPreferencesValue(preferences, "Use Sora Editor", false)) {
            useSoraEditor = true;
          }
        }
        ShowSourceCodeDialog showSourceCodeDialog =
            new ShowSourceCodeDialog(this, file.getCode(eventList), language, useSoraEditor);
        showSourceCodeDialog.show();
      }
    }

    if (arg0.getItemId() == R.id.executor) {
      if (file.getFileType() == WebFile.SupportedFileType.HTML) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(
            () -> {
              ProjectBuilder.generateProjectCode(
                  new File(projectPath),
                  new ProjectBuildListener() {
                    @Override
                    public void onLog(String log, int type) {}

                    @Override
                    public void onBuildComplete() {
                      runOnUiThread(
                          () -> {
                            Intent i = new Intent();
                            i.setClass(EventListActivity.this, WebViewActivity.class);
                            i.putExtra("type", "file");
                            i.putExtra(
                                "root",
                                new File(new File(projectPath), ProjectFileUtils.BUILD_DIRECTORY)
                                    .getAbsolutePath());
                            i.putExtra("data", fileOutputPath);
                            startActivity(i);
                          });
                    }

                    @Override
                    public void onBuildStart() {}
                  },
                  EventListActivity.this);
            });
      } else {
        preview.setVisible(false);
      }
    }

    return super.onOptionsItemSelected(arg0);
  }

  @Override
  protected void onResume() {
    showEventList();
    super.onResume();
  }

  public String getFileOutputPath() {
    return this.fileOutputPath;
  }

  public void setFileOutputPath(String fileOutputPath) {
    this.fileOutputPath = fileOutputPath;
  }
}
