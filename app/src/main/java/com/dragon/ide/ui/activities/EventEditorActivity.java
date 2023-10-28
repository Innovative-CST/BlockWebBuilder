package com.dragon.ide.ui.activities;

import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.MainThread;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.Event;
import com.dragon.ide.ui.dialogs.eventList.ShowSourceCodeDialog;
import com.dragon.ide.ui.view.BlockDefaultView;
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
import editor.tsd.tools.Language;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventEditorActivity extends BaseActivity implements View.OnDragListener {
  public ActivityEventEditorBinding binding;
  private ArrayList<WebFile> fileList;
  private WebFile file;
  private ArrayList<BlocksHolder> blocksHolder;
  private String projectName;
  private String projectPath;
  private String fileName;
  private int fileType;
  private boolean isLoaded;
  private String eventName;
  private Event event;

  // private View DraggingView;

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

    projectName = "";
    projectPath = "";
    fileName = "";
    fileType = 0;
    isLoaded = false;

    if (getIntent().hasExtra("projectName")) {
      projectName = getIntent().getStringExtra("projectName");
      projectPath = getIntent().getStringExtra("projectPath");
      fileName = getIntent().getStringExtra("fileName");
      fileType = getIntent().getIntExtra("fileType", 1);
      eventName = getIntent().getStringExtra("eventName");
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
            binding.blocksHolderList.setLayoutManager(
                new LinearLayoutManager(EventEditorActivity.this));
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

                    for (int i = 0; i < fileList.size(); ++i) {
                      if (fileList
                          .get(i)
                          .getFilePath()
                          .toLowerCase()
                          .equals(fileName.toLowerCase())) {
                        if (fileList.get(i).getFileType() == fileType) {
                          file = fileList.get(i);
                        }
                      }
                    }
                  }

                  for (int i2 = 0; i2 < file.getEvents().size(); ++i2) {
                    Event loopEvent = file.getEvents().get(i2);
                    if (eventName.toLowerCase().equals(loopEvent.getName().toLowerCase())) {
                      event = file.getEvents().get(i2);
                      isLoaded = true;
                      runOnUiThread(
                          () -> {
                            loadBlocks(loopEvent);
                            showSection(3);
                          });
                    }
                  }

                  fis.close();
                  ois.close();
                } catch (Exception e) {
                  runOnUiThread(
                      () -> {
                        showSection(2);
                        binding.tvInfo.setText(e.getMessage());
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

  @Override
  public boolean onDrag(View v, DragEvent dragEvent) {
    final int action = dragEvent.getAction();
    View dragView = (View) dragEvent.getLocalState();
    int index = 0;
    float dropX = dragEvent.getX();
    float dropY = dragEvent.getY();
    switch (action) {
      case DragEvent.ACTION_DRAG_STARTED:
        return true;
      case DragEvent.ACTION_DRAG_ENTERED:
        v.invalidate();
        return true;
      case DragEvent.ACTION_DRAG_LOCATION:
        return true;
      case DragEvent.ACTION_DRAG_EXITED:
        return true;
      case DragEvent.ACTION_DROP:
        for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
          View child = ((ViewGroup) v).getChildAt(i);
          if (dropY > child.getY() + child.getHeight() / 2) {
            index = i + 1;
          } else {
            break;
          }
        }

        if ((dragView instanceof BlockDefaultView)) {
          BlockDefaultView blockView = new BlockDefaultView(this);
          blockView.setBlock(((BlockDefaultView) dragView).getBlock());
          ((LinearLayout) v).addView(blockView, index);
          if (blockView.getLayoutParams() != null) {
            ((LinearLayout.LayoutParams) blockView.getLayoutParams()).setMargins(0, -26, 0, 0);
            ((LinearLayout.LayoutParams) blockView.getLayoutParams()).width =
                LinearLayout.LayoutParams.WRAP_CONTENT;
          }
        }

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
    for (int i = 0; i < e.getBlocks().size(); ++i) {
      if (e.getBlocks().get(i) instanceof Block) {
        if (e.getBlocks().get(i).getBlockType() == Block.BlockType.defaultBlock) {
          BlockDefaultView blockView = new BlockDefaultView(this);
          blockView.setBlock(e.getBlocks().get(i));
          binding.blockListEditorArea.addView(blockView, i + 1);
          if (blockView.getLayoutParams() != null) {
            ((LinearLayout.LayoutParams) blockView.getLayoutParams()).setMargins(0, -26, 0, 0);
            ((LinearLayout.LayoutParams) blockView.getLayoutParams()).width =
                LinearLayout.LayoutParams.WRAP_CONTENT;
          }
        }
      }
    }
  }

  public void saveFileList() {
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          try {
            FileOutputStream fos =
                new FileOutputStream(new File(new File(projectPath), "Files.txt"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(fileList);
            fos.close();
            oos.close();
            finish();
          } catch (Exception e) {
            // Toast.makeText(this, e.getMessage(), 0).show();
          }
        });
  }

  @Override
  @MainThread
  public void onBackPressed() {
    if (isLoaded) {
      updateBlocks();
      saveFileList();
    }
  }

  @Override
  protected void onPause() {
    if (isLoaded) {
      updateBlocks();
      saveFileList();
    }
    super.onPause();
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
      updateBlocks();
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
            new ShowSourceCodeDialog(this, event.getCode(), language);
        showSourceCodeDialog.show();
      }
    }
    Toast.makeText(this, String.valueOf(event.getBlocks().size()), 0).show();
    return super.onOptionsItemSelected(arg0);
  }

  public void updateBlocks() {
    if (isLoaded) {
      ArrayList<Block> arr = new ArrayList<Block>();
      for (int i = 0; i < binding.blockListEditorArea.getChildCount(); ++i) {
        if (binding.blockListEditorArea.getChildAt(i) instanceof BlockDefaultView) {
          arr.add(((BlockDefaultView) binding.blockListEditorArea.getChildAt(i)).getBlock());
        }
      }
      event.setBlocks(arr);
    }
  }
}
