package com.dragon.ide.ui.activities;

import static com.dragon.ide.utils.Environments.PROJECTS;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.MainThread;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.dragon.ide.R;
import com.dragon.ide.databinding.ActivityEventEditorBinding;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.BlocksHolder;
import com.dragon.ide.objects.ComplexBlock;
import com.dragon.ide.objects.Event;
import com.dragon.ide.objects.WebFile;
import com.dragon.ide.ui.adapters.BlocksHolderEventEditorListItem;
import com.dragon.ide.ui.dialogs.eventList.ShowSourceCodeDialog;
import com.dragon.ide.ui.utils.BlocksLoader;
import com.dragon.ide.ui.view.BlockDefaultView;
import com.dragon.ide.ui.view.ComplexBlockView;
import com.dragon.ide.utils.BlocksHandler;
import com.dragon.ide.utils.Utils;
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
  private String language;
  private LinearLayout blockShadow;

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

    binding.relativeBlockListEditorArea.setOnDragListener(this);

    /*
     * Load block shadow
     */

    blockShadow = new LinearLayout(this);
    blockShadow.setTag("shadow");
    blockShadow.setBackgroundResource(R.drawable.block_default);

    Drawable backgroundDrawable = blockShadow.getBackground();
    backgroundDrawable.setTint(Color.BLACK);
    backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
    blockShadow.setBackground(backgroundDrawable);

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
                    if (file == null) {
                      runOnUiThread(
                          () -> {
                            showSection(2);
                            binding.tvInfo.setText("File not not found");
                            return;
                          });
                    }
                  } else {
                    runOnUiThread(
                        () -> {
                          showSection(2);
                          binding.tvInfo.setText("Not an instance of WebFile");
                        });
                  }

                  for (int i2 = 0; i2 < file.getEvents().size(); ++i2) {
                    Event loopEvent = file.getEvents().get(i2);
                    if (eventName.toLowerCase().equals(loopEvent.getName().toLowerCase())) {
                      event = file.getEvents().get(i2);
                      isLoaded = true;
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
                      runOnUiThread(
                          () -> {
                            loadBlocks(loopEvent);
                            showSection(3);
                          });
                    }
                  }

                  if (!isLoaded) {
                    runOnUiThread(
                        () -> {
                          showSection(2);
                          binding.tvInfo.setText("Event not found");
                        });
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

  public void handleShadowRemove(ViewGroup v) {
    if (v.getTag() != null) {
      if (v.getTag() instanceof String) {
        if (((String) v.getTag()).equals("blockDroppingArea")) {
          if (blockShadow.getParent() != null) {
            if (((ViewGroup) blockShadow.getParent()).getChildCount() > 1) {
              if (((ViewGroup) blockShadow.getParent()).getChildAt(0).getTag() != null) {
                if (((ViewGroup) blockShadow.getParent()).getChildAt(0).getTag()
                    instanceof String) {
                  if (((ViewGroup) blockShadow.getParent())
                      .getChildAt(0)
                      .getTag()
                      .equals("shadow")) {
                    if (((ViewGroup) blockShadow.getParent()).getId()
                        != R.id.relativeBlockListEditorArea) {
                      if (((ViewGroup) blockShadow.getParent()).getChildAt(1).getLayoutParams()
                          != null) {
                        ((LinearLayout.LayoutParams)
                                ((ViewGroup) blockShadow.getParent())
                                    .getChildAt(1)
                                    .getLayoutParams())
                            .setMargins(0, 0, 0, 0);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      } else if (v.getTag() instanceof String[]) {
        for (String str : (String[]) v.getTag()) {
          if (str.equals("boolean")) {
            if (v.getChildCount() > 1) {
              v.getChildAt(1).setVisibility(View.VISIBLE);
            }
          }
        }
      }
    }
    if (blockShadow.getParent() != null) {
      ((ViewGroup) blockShadow.getParent()).removeView(blockShadow);
    }
  }

  @Override
  public boolean onDrag(View v, DragEvent dragEvent) {
    final int action = dragEvent.getAction();
    View dragView = (View) dragEvent.getLocalState();
    int index = 0;
    float dropX = dragEvent.getX();
    float dropY = dragEvent.getY();

    if (v instanceof LinearLayout) {
      handleShadowRemove((LinearLayout) v);
    }

    for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
      View child = ((ViewGroup) v).getChildAt(i);
      if (dropY > child.getY() + child.getHeight() / 2) {
        index = i + 1;
      } else {
        break;
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
          handleShadowOnLocation(v, index);
        }
        return true;
      case DragEvent.ACTION_DRAG_EXITED:
        return true;
      case DragEvent.ACTION_DROP:
        if (v.getTag() != null) {
          if (v.getTag() instanceof String) {
            if (((String) v.getTag()).equals("blockDroppingArea")) {
              if ((dragView instanceof BlockDefaultView)) {
                if (((BlockDefaultView) dragView).getBlock().getBlockType()
                    == Block.BlockType.defaultBlock) {

                  BlockDefaultView blockView = null;
                  if (!(((BlockDefaultView) dragView).getEnableEdit())) {
                    blockView = new BlockDefaultView(this);
                    blockView.setLanguage(language);
                    blockView.setEnableEdit(true);
                    try {
                      Block block = ((BlockDefaultView) dragView).getBlock().clone();
                      blockView.setBlock(block);
                    } catch (CloneNotSupportedException e) {
                      blockView.setBlock(new Block());
                    }
                  } else {
                    blockView = (BlockDefaultView) dragView;
                    if (dragView.getParent() != null) {
                      int index2 = ((ViewGroup) dragView.getParent()).indexOfChild(dragView);
                      ((ViewGroup) blockView.getParent()).removeView(blockView);
                      if (index2 < index) {
                        index = index - 1;
                      }
                    }
                  }
                  ((LinearLayout) v).addView(blockView, index);
                  if (blockView.getLayoutParams() != null) {
                    ((LinearLayout.LayoutParams) blockView.getLayoutParams())
                        .setMargins(0, -26, 0, 0);
                    ((LinearLayout.LayoutParams) blockView.getLayoutParams()).width =
                        LinearLayout.LayoutParams.WRAP_CONTENT;
                  }
                  if (v.getId() != R.id.relativeBlockListEditorArea
                      || v.getId() != R.id.blockListEditorArea) {
                    if (index == 0) {
                      if (((LinearLayout.LayoutParams) blockView.getLayoutParams()) != null) {
                        ((LinearLayout.LayoutParams) blockView.getLayoutParams())
                            .setMargins(0, 0, 0, 0);
                        if (((LinearLayout) v).getChildCount() > 1) {
                          if (((LinearLayout) v).getChildAt(1).getLayoutParams() != null) {
                            ((LinearLayout.LayoutParams)
                                    ((LinearLayout) v).getChildAt(1).getLayoutParams())
                                .setMargins(0, -26, 0, 0);
                          }
                        }
                      }
                    }
                  }
                }
              }

              if ((dragView instanceof ComplexBlockView)) {
                if (((ComplexBlockView) dragView).getComplexBlock().getBlockType()
                    == Block.BlockType.complexBlock) {
                  ComplexBlockView blockView = null;
                  if (!(((ComplexBlockView) dragView).getEnableEdit())) {
                    blockView = new ComplexBlockView(this);
                    blockView.setLanguage(language);
                    blockView.setEnableEdit(true);
                    try {
                      ComplexBlock complexBlock =
                          ((ComplexBlockView) dragView).getComplexBlock().clone();
                      blockView.setComplexBlock(complexBlock);
                    } catch (CloneNotSupportedException e) {
                      blockView.setComplexBlock(new ComplexBlock());
                    }
                  } else {
                    blockView = (ComplexBlockView) dragView;
                    if (dragView.getParent() != null) {
                      int index2 = ((ViewGroup) dragView.getParent()).indexOfChild(dragView);
                      ((ViewGroup) blockView.getParent()).removeView(blockView);
                      if (index2 < index) {
                        index = index - 1;
                      }
                    }
                  }
                  ((LinearLayout) v).addView(blockView, index);
                  if (blockView.getLayoutParams() != null) {
                    ((LinearLayout.LayoutParams) blockView.getLayoutParams())
                        .setMargins(0, -26, 0, 0);
                    ((LinearLayout.LayoutParams) blockView.getLayoutParams()).width =
                        LinearLayout.LayoutParams.WRAP_CONTENT;
                  }

                  if (v.getId() != R.id.relativeBlockListEditorArea
                      || v.getId() != R.id.blockListEditorArea) {
                    if (index == 0) {
                      if (((LinearLayout.LayoutParams) blockView.getLayoutParams()) != null) {
                        ((LinearLayout.LayoutParams) blockView.getLayoutParams())
                            .setMargins(0, 0, 0, 0);
                        if (((LinearLayout) v).getChildCount() > 1) {
                          if (((LinearLayout) v).getChildAt(1).getLayoutParams() != null) {
                            ((LinearLayout.LayoutParams)
                                    ((LinearLayout) v).getChildAt(1).getLayoutParams())
                                .setMargins(0, -26, 0, 0);
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          } else if (v.getTag() instanceof String[]) {
            for (String str : (String[]) v.getTag()) {
              if (str.equals("boolean")) {
                BlockDefaultView blockView = new BlockDefaultView(this);
                blockView.setLanguage(language);
                blockView.setEnableEdit(true);
                try {
                  Block block = ((BlockDefaultView) dragView).getBlock().clone();
                  blockView.setBlock(block);
                } catch (CloneNotSupportedException e) {
                  blockView.setBlock(new Block());
                }
                if (((ViewGroup) v).getChildCount() != 0) {
                  View view = ((ViewGroup) v).getChildAt(0);
                  if (((ViewGroup) view).getParent() != null) {
                    ((ViewGroup) ((ViewGroup) view).getParent()).removeView(view);
                  }
                  binding.relativeBlockListEditorArea.addView(view);
                  FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
                  lp.setMargins(
                      (int) dropX + binding.relativeBlockListEditorArea.getScrollX(),
                      (int) dropY + binding.relativeBlockListEditorArea.getScrollY(),
                      0,
                      0);
                  view.setLayoutParams(lp);
                }

                ((ViewGroup) v).addView(blockView);
                if (blockView.getLayoutParams() != null) {
                  ((LinearLayout.LayoutParams) blockView.getLayoutParams()).width =
                      LinearLayout.LayoutParams.WRAP_CONTENT;
                }
              }
            }
          }
        } else if (v.getId() == R.id.relativeBlockListEditorArea) {
          if ((dragView instanceof BlockDefaultView)) {
            BlockDefaultView blockView = null;
            if (!(((BlockDefaultView) dragView).getEnableEdit())) {
              blockView = new BlockDefaultView(this);
              blockView.setLanguage(language);
              blockView.setEnableEdit(true);
              try {
                Block block = ((BlockDefaultView) dragView).getBlock().clone();
                blockView.setBlock(block);
              } catch (CloneNotSupportedException e) {
                blockView.setBlock(new Block());
              }
            } else {
              blockView = (BlockDefaultView) dragView;
              if (blockView.getParent() != null) {
                ((ViewGroup) blockView.getParent()).removeView(blockView);
              }
            }
            ((FrameLayout) v).addView(blockView);
            if (blockView.getLayoutParams() != null) {
              blockView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
              blockView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
              blockView.requestLayout();
              ((FrameLayout.LayoutParams) blockView.getLayoutParams())
                  .setMargins(
                      (int) dropX
                          + binding.relativeBlockListEditorArea.getScrollX()
                          - ((8
                              * (blockView.getWidth()
                                  + blockView.getPaddingLeft()
                                  + blockView.getPaddingRight()))),
                      (int) dropY
                          + binding.relativeBlockListEditorArea.getScrollY()
                          - ((2
                              * (blockView.getHeight()
                                  + blockView.getPaddingTop()
                                  + blockView.getPaddingBottom()))),
                      0,
                      0);
            }
          }

          if ((dragView instanceof ComplexBlockView)) {
            if (((ComplexBlockView) dragView).getComplexBlock().getBlockType()
                == Block.BlockType.complexBlock) {
              ComplexBlockView blockView = null;
              if (!(((ComplexBlockView) dragView).getEnableEdit())) {
                blockView = new ComplexBlockView(this);
                blockView.setLanguage(language);
                blockView.setEnableEdit(true);
                try {
                  ComplexBlock complexBlock =
                      ((ComplexBlockView) dragView).getComplexBlock().clone();
                  blockView.setComplexBlock(complexBlock);
                } catch (CloneNotSupportedException e) {
                  blockView.setComplexBlock(new ComplexBlock());
                }
              } else {
                blockView = (ComplexBlockView) dragView;
                if (blockView.getParent() != null) {
                  ((ViewGroup) blockView.getParent()).removeView(blockView);
                }
              }
              ((FrameLayout) v).addView(blockView);
              if (blockView.getLayoutParams() != null) {
                blockView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                blockView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                blockView.requestLayout();
                ((FrameLayout.LayoutParams) blockView.getLayoutParams())
                    .setMargins(
                        (int) dropX
                            + binding.relativeBlockListEditorArea.getScrollX()
                            - ((8
                                * (blockView.getWidth()
                                    + blockView.getPaddingLeft()
                                    + blockView.getPaddingRight()))),
                        (int) dropY
                            + binding.relativeBlockListEditorArea.getScrollY()
                            - ((2
                                * (blockView.getHeight()
                                    + blockView.getPaddingTop()
                                    + blockView.getPaddingBottom()))),
                        0,
                        0);
              }
            }
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

  private void handleShadowOnLocation(final View v, final int index) {
    if (v.getTag() != null) {
      if (v.getTag() instanceof String) {
        if (((String) v.getTag()).equals("blockDroppingArea")) {
          blockShadow.setBackgroundResource(R.drawable.block_default);

          Drawable backgroundDrawable = blockShadow.getBackground();
          backgroundDrawable.setTint(Color.BLACK);
          backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
          blockShadow.setBackground(backgroundDrawable);
          ((ViewGroup) v).addView(blockShadow, index);
          if (((ViewGroup) blockShadow).getLayoutParams() != null) {
            ((LinearLayout.LayoutParams) blockShadow.getLayoutParams()).setMargins(0, -26, 0, 0);
          }
          if (v.getId() != R.id.blockListEditorArea
              || v.getId() != R.id.relativeBlockListEditorArea) {
            if (index == 0) {
              if (((ViewGroup) blockShadow).getLayoutParams() != null) {
                ((LinearLayout.LayoutParams) blockShadow.getLayoutParams()).setMargins(0, 0, 0, 0);
                if (((LinearLayout) v).getChildCount() > 1) {
                  if (((LinearLayout) v).getChildAt(1).getLayoutParams() != null) {
                    ((LinearLayout.LayoutParams) ((LinearLayout) v).getChildAt(1).getLayoutParams())
                        .setMargins(0, -26, 0, 0);
                  }
                }
              }
            }
          }
        }
      } else if (v.getTag() instanceof String[]) {
        for (String str : (String[]) v.getTag()) {
          ((ViewGroup) v).addView(blockShadow, 0);
          if (str.equals("boolean")) {
            blockShadow.setBackgroundResource(R.drawable.block_boolean);

            Drawable backgroundDrawable = blockShadow.getBackground();
            backgroundDrawable.setTint(Color.BLACK);
            backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
            blockShadow.setBackground(backgroundDrawable);
            if (((ViewGroup) v).getChildCount() != 0) {
              ((ViewGroup) v).getChildAt(0).setVisibility(View.GONE);
            }
            if (v.getParent() != null) {
              ((ViewGroup) blockShadow.getParent()).removeView(blockShadow);
            }
            ((ViewGroup) v).addView(blockShadow, index);
          }
        }
      }
      if (((LinearLayout.LayoutParams) blockShadow.getLayoutParams()) != null) {
        ((LinearLayout.LayoutParams) blockShadow.getLayoutParams()).width =
            LinearLayout.LayoutParams.WRAP_CONTENT;
      }
    }
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
            FileOutputStream fos =
                new FileOutputStream(new File(new File(projectPath), "Files.txt"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(fileList);
            fos.close();
            oos.close();
            if (exitAfterSave) {
              finish();
            }
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
    getMenuInflater().inflate(R.menu.activity_event_list_menu, arg0);
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
        ShowSourceCodeDialog showSourceCodeDialog =
            new ShowSourceCodeDialog(this, event.getCode(), language);
        showSourceCodeDialog.show();
      }
    }
    return super.onOptionsItemSelected(arg0);
  }

  public void updateBlocks(ViewGroup view) {
    if (isLoaded) {
      event.setBlocks(BlocksHandler.loadBlocksIntoObject(view));
    }
  }
}
