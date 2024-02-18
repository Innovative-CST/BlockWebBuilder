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

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.MainThread;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.block.web.builder.R;
import com.block.web.builder.core.BlocksHolder;
import com.block.web.builder.core.Event;
import com.block.web.builder.core.WebFile;
import com.block.web.builder.databinding.ActivityEventEditorBinding;
import com.block.web.builder.listeners.ProjectBuildListener;
import com.block.web.builder.listeners.TaskListener;
import com.block.web.builder.ui.adapters.BlocksHolderEventEditorListItem;
import com.block.web.builder.ui.dialogs.eventList.ShowSourceCodeDialog;
import com.block.web.builder.ui.utils.BlockHintHandler;
import com.block.web.builder.ui.utils.BlocksLoader.BlocksLoader;
import com.block.web.builder.ui.view.blocks.BlockHint;
import com.block.web.builder.utils.BlocksHandler;
import com.block.web.builder.utils.DeserializationException;
import com.block.web.builder.utils.DeserializerUtils;
import com.block.web.builder.utils.ProjectBuilder;
import com.block.web.builder.utils.ProjectFileUtils;
import com.block.web.builder.utils.eventeditor.BlockDropUtils;
import com.block.web.builder.utils.eventeditor.BlocksListLoader;
import com.block.web.builder.utils.preferences.BasePreference;
import com.block.web.builder.utils.preferences.PreferencesUtils;
import editor.tsd.tools.Language;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventEditorActivity extends BaseActivity implements View.OnDragListener {
  public ActivityEventEditorBinding binding;
  private WebFile file;
  private String fileOutputPath;
  private ArrayList<BlocksHolder> blocksHolder;
  private ArrayList<BasePreference> preferences;
  private String projectName;
  private String projectPath;
  private String webFilePath;
  private boolean isLoaded;
  private boolean useScroll = true;

  // Event
  private String eventFilePath;
  private Event event;
  private String language;

  // BlockHint
  private BlockHint blockHint;

  private MenuItem preview;
  private MenuItem lockCanva;

  // Effects
  public SoundPool blockDragSoundEffect;
  public SoundPool blockDropSoundEffect;
  public int blockDragSoundEffectId;
  public int blockDropSoundEffectId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityEventEditorBinding.inflate(getLayoutInflater());
    // set content view to binding's root.
    setContentView(binding.getRoot());

    // Initialize to avoid null error
    blocksHolder = new ArrayList<BlocksHolder>();

    // Setup toolbar.
    binding.toolbar.setTitle(R.string.app_name);
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

    projectName = "";
    projectPath = "";
    webFilePath = "";
    eventFilePath = "";
    isLoaded = false;
    blockHint = new BlockHint(this, R.drawable.block_default);
    blockDragSoundEffect = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    blockDragSoundEffectId = blockDragSoundEffect.load(this, R.raw.block_drag, 1);
    blockDropSoundEffect = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    blockDropSoundEffectId = blockDropSoundEffect.load(this, R.raw.block_drop, 1);

    if (getIntent().hasExtra("projectName")) {
      projectName = getIntent().getStringExtra("projectName");
      projectPath = getIntent().getStringExtra("projectPath");
      webFilePath = getIntent().getStringExtra("webFilePath");
      eventFilePath = getIntent().getStringExtra("eventFilePath");
      fileOutputPath = getIntent().getStringExtra("outputDirectory");
      try {
        DeserializerUtils.deserializeWebfile(
            new File(webFilePath),
            mWebFile -> {
              file = (WebFile) mWebFile;
              language =
                  switch (WebFile.getSupportedFileSuffix(file.getFileType())) {
                    case ".html" -> Language.HTML;
                    case ".css" -> Language.CSS;
                    case ".js" -> Language.JavaScript;
                    default -> "";
                  };
              if (preview != null) {
                preview.setVisible(language.equals(Language.HTML));
              }
            });
      } catch (DeserializationException e) {
      }

      try {
        DeserializerUtils.deserializeEvent(
            new File(eventFilePath),
            mEvent -> {
              event = (Event) mEvent;
            });
      } catch (DeserializationException e) {
      }

      try {
        DeserializerUtils.deserializePreferences(
            PREFERENCES,
            result -> {
              preferences = (ArrayList<BasePreference>) result;
            });
      } catch (DeserializationException e) {
        preferences = new ArrayList<BasePreference>();
      }
    } else {
      showSection(2);
      binding.tvInfo.setText(getString(R.string.project_name_not_passed));
    }

    binding.relativeBlockListEditorArea.setOnDragListener(this);

    /*
     * Ask for storage permission if not granted.
     * Load event if storage permission is granted.
     */
    if (!MainActivity.isStoagePermissionGranted(this)) {
      showSection(2);
      binding.tvInfo.setText(R.string.storage_permission_denied);
      MainActivity.showStoragePermissionDialog(this);
    } else {
      if (event != null && file != null) {
        showSection(1);
        isLoaded = true;
        loadBlocks(event);
        showSection(3);
      } else {
        showSection(2);
        isLoaded = false;
        binding.tvInfo.setText(getText(R.string.an_error_occured_while_parsing_event));
      }
    }

    /*
     * Loads blocks holder
     */
    BlocksListLoader blocksListLoader = new BlocksListLoader();
    blocksListLoader.loadBlocks(
        EventEditorActivity.this,
        language,
        new BlocksListLoader.Progress() {

          @Override
          public void onCompleteLoading(ArrayList<BlocksHolder> holder) {
            binding.blocksHolderList.setAdapter(
                new BlocksHolderEventEditorListItem(holder, EventEditorActivity.this));
            binding.blocksHolderList.setLayoutManager(
                new LinearLayoutManager(EventEditorActivity.this));
          }
        });

    binding.fab.setOnClickListener(
        (view) -> {
          binding.blockArea.setVisibility(
              binding.blockArea.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });
  }

  public void showSection(int section) {
    binding.loading.setVisibility(section == 1 ? View.VISIBLE : View.GONE);
    binding.info.setVisibility(section == 2 ? View.VISIBLE : View.GONE);
    binding.editor.setVisibility(section == 3 ? View.VISIBLE : View.GONE);
    binding.blockArea.setVisibility(section == 3 ? View.VISIBLE : View.GONE);
    binding.appbar.setVisibility(section == 3 ? View.VISIBLE : View.GONE);
    binding.fab.setVisibility(section == 3 ? View.VISIBLE : View.GONE);
    binding.stringEditor.setVisibility(section == 4 ? View.VISIBLE : View.GONE);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
    blockDragSoundEffect.release();
    blockDropSoundEffect.release();
    blockDragSoundEffect = null;
    blockDropSoundEffect = null;
  }

  @Override
  public boolean onDrag(View v, DragEvent dragEvent) {
    final int action = dragEvent.getAction();
    View dragView = (View) dragEvent.getLocalState();
    int index = 0;
    float dropX = dragEvent.getX();
    float dropY = dragEvent.getY();

    if (v instanceof LinearLayout) {
      BlockHintHandler.handleRemoveHint((LinearLayout) v, blockHint);
    }

    for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
      View child = ((ViewGroup) v).getChildAt(i);
      if (dropY > child.getY() + child.getHeight() / 2) {
        index = i + 1;
      } else {
        break;
      }
    }
    if (v instanceof LinearLayout) {
      if (((LinearLayout) v).getOrientation() == LinearLayout.HORIZONTAL) {
        index = 0;
        for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
          View child = ((ViewGroup) v).getChildAt(i);
          if (dropX > child.getX() + child.getWidth() / 2) {
            index = i + 1;
          } else {
            break;
          }
        }
      }
    }

    if (v.getId() == R.id.blockListEditorArea) {
      if (index == 0) {
        index = 1;
      }
    }

    switch (action) {
      case DragEvent.ACTION_DRAG_STARTED:
        return true;
      case DragEvent.ACTION_DRAG_ENTERED:
        v.invalidate();
        return true;
      case DragEvent.ACTION_DRAG_LOCATION:
        if (v instanceof LinearLayout) {
          BlockHintHandler.handleAddBlockHint(v, index, blockHint, this);
        }
        return true;
      case DragEvent.ACTION_DRAG_EXITED:
        return true;
      case DragEvent.ACTION_DROP:
        BlockDropUtils.onDrop(this, binding, v, dragView, language, dropX, dropY, index);
        v.invalidate();
        return true;
      case DragEvent.ACTION_DRAG_ENDED:
        v.invalidate();

        return true;
      default:
        break;
    }
    return false;
  }

  public void loadBlocks(Event e) {
    BlocksLoader.loadBlockViews(
        binding.getRoot().findViewById(R.id.blockListEditorArea), e.getBlocks(), language, this);
  }

  public void saveFileList(boolean exitAfterSave) {
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          try {
            FileOutputStream fos = new FileOutputStream(new File(eventFilePath));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(event);
            fos.close();
            oos.close();
            if (exitAfterSave) {
              finish();
            }
          } catch (Exception e) {
          }
        });
  }

  public void saveEvent(TaskListener listener) {
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          try {
            FileOutputStream fos = new FileOutputStream(new File(eventFilePath));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(event);
            fos.close();
            oos.close();
            listener.onSuccess(null);
          } catch (Exception e) {
          }
        });
  }

  @Override
  @MainThread
  public void onBackPressed() {
    if (isLoaded) {
      updateBlocks(binding.getRoot().findViewById(R.id.blockListEditorArea));
      saveFileList(true);
    }
  }

  @Override
  protected void onPause() {
    if (isLoaded) {
      updateBlocks(binding.getRoot().findViewById(R.id.blockListEditorArea));
      saveFileList(false);
    }
    super.onPause();
  }

  // Handle option menu
  @Override
  public boolean onCreateOptionsMenu(Menu arg0) {
    super.onCreateOptionsMenu(arg0);
    getMenuInflater().inflate(R.menu.activity_event_editor_menu, arg0);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem arg0) {
    if (arg0.getItemId() == R.id.show_source_code) {
      updateBlocks(binding.getRoot().findViewById(R.id.blockListEditorArea));
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
            new ShowSourceCodeDialog(this, event.getCode(), language, useSoraEditor);
        showSourceCodeDialog.show();
      }
    }
    if (arg0.getItemId() == R.id.executor) {
      if (language.equals(Language.HTML)) {
        updateBlocks(binding.getRoot().findViewById(R.id.blockListEditorArea));
        saveEvent(
            new TaskListener() {
              @Override
              public void onSuccess(Object result) {
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
                                    i.setClass(EventEditorActivity.this, WebViewActivity.class);
                                    i.putExtra("type", "file");
                                    i.putExtra(
                                        "root",
                                        new File(
                                                new File(projectPath),
                                                ProjectFileUtils.BUILD_DIRECTORY)
                                            .getAbsolutePath());
                                    i.putExtra("data", fileOutputPath);
                                    startActivity(i);
                                  });
                            }

                            @Override
                            public void onBuildStart() {}
                          },
                          EventEditorActivity.this);
                    });
              }
            });
      } else {
        preview.setVisible(false);
      }
    }

    if (arg0.getItemId() == R.id.lockCanva) {
      binding.relativeBlockListEditorArea.setUseScroll(!useScroll);
      useScroll = !useScroll;
      if (useScroll) {
        lockCanva.setIcon(R.drawable.gesture_tap_hold_tertiary);
      } else {
        lockCanva.setIcon(R.drawable.gesture_tap_hold);
      }
    }

    return super.onOptionsItemSelected(arg0);
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu arg0) {
    preview = arg0.findItem(R.id.executor);
    lockCanva = arg0.findItem(R.id.lockCanva);

    preview.setVisible(language.equals(Language.HTML));

    lockCanva.setVisible(
        preferences != null
            && ((boolean)
                PreferencesUtils.getPreferencesValue(preferences, "Canva Manual Lock", false)));

    return super.onPrepareOptionsMenu(arg0);
  }

  public void updateBlocks(ViewGroup view) {
    if (isLoaded) {
      event.setBlocks(BlocksHandler.loadBlocksIntoObject(view));
    }
  }

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public ArrayList<BasePreference> getPreferences() {
    return this.preferences;
  }

  public void setPreferences(ArrayList<BasePreference> preferences) {
    this.preferences = preferences;
  }

  public class BlocksMargin {
    public static final int defaultBlockAboveMargin = -5;
    public static final int sideAttachableBlock = -16;
    public static final int bottomBlockHeight = 30;
  }
}
