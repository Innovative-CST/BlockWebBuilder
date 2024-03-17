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
import com.block.web.builder.utils.preferences.PreferencesUtils;
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

  public void showEventList() {
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
              PreferencesUtils.getPreferencesValue(preferences, "Use Sora Editor", false)) {
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

  public String getWebFilePath() {
    return this.webFilePath;
  }

  public ArrayList<Event> getEventList() {
    return this.eventList;
  }

  public void setEventList(ArrayList<Event> eventList) {
    this.eventList = eventList;
  }
}
