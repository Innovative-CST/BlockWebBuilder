package com.dragon.ide.ui.activities;

import static com.dragon.ide.utils.Environments.PROJECTS;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.dragon.ide.R;
import com.dragon.ide.databinding.ActivityEventListBinding;
import com.dragon.ide.listeners.EventAddListener;
import com.dragon.ide.listeners.TaskListener;
import com.dragon.ide.objects.Event;
import com.dragon.ide.objects.WebFile;
import com.dragon.ide.ui.adapters.EventListAdapter;
import com.dragon.ide.ui.dialogs.eventList.AddEventDialog;
import com.dragon.ide.ui.dialogs.eventList.ShowSourceCodeDialog;
import com.dragon.ide.utils.DeserializationException;
import com.dragon.ide.utils.DeserializerUtils;
import com.dragon.ide.utils.ProjectFileUtils;
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
  private ArrayList<Event> eventList;
  private String projectName;
  private String projectPath;
  private String fileName;
  private String webFilePath;
  private int fileType;
  private boolean isLoaded;

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
      try {
        DeserializerUtils.deserializeWebfile(
            new File(webFilePath),
            new TaskListener() {
              @Override
              public void onSuccess(Object mWebFile) {
                file = (WebFile) mWebFile;
                isLoaded = true;
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
        ShowSourceCodeDialog showSourceCodeDialog =
            new ShowSourceCodeDialog(this, file.getCode(eventList), language);
        showSourceCodeDialog.show();
      }
    }
    return super.onOptionsItemSelected(arg0);
  }

  @Override
  protected void onResume() {
    showEventList();
    super.onResume();
  }
}
