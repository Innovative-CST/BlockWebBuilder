package com.block.web.builder.ui.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import com.block.web.builder.listeners.ValueListener;
import com.block.web.builder.ui.dialogs.blockeditor.AddTextInBlockDialog;
import static com.block.web.builder.utils.Environments.BLOCKS;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.block.web.builder.R;
import com.block.web.builder.databinding.ActivityBlockEditorBinding;
import com.block.web.builder.objects.Block;
import com.block.web.builder.objects.BlocksHolder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlockEditorActivity extends BaseActivity {

  private ActivityBlockEditorBinding binding;
  private ArrayList<BlocksHolder> blocksHolderList;
  private BlocksHolder blocksHolder;
  private ArrayList<Block> blocks;
  private String blocksHolderSelectedName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Inflate and get instance of binding.
    binding = ActivityBlockEditorBinding.inflate(getLayoutInflater());

    // set content view to binding's root.
    setContentView(binding.getRoot());

    // Setup toolbar.
    binding.toolbar.setTitle(R.string.app_name);
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

    // Initialize to avoid null errors
    blocks = new ArrayList<Block>();
    blocksHolderList = new ArrayList<BlocksHolder>();
    blocksHolder = new BlocksHolder();

    // Get selected Blocks holder
    if (getIntent().hasExtra("BlocksHolder")) {
      blocksHolderSelectedName = getIntent().getStringExtra("BlocksHolder");
    } else {
      showSection(2);
      binding.tvInfo.setText(getString(R.string.error));
      return;
    }

    if (MainActivity.isStoagePermissionGranted(this)) {
      loadBlocksList();
    } else {
      showSection(2);
      MainActivity.showStoragePermissionDialog(this);
    }
  }

  private void loadBlocksList() {
    showSection(1);
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          if (BLOCKS.exists()) {
            try {
              FileInputStream fis = new FileInputStream(BLOCKS);
              ObjectInputStream ois = new ObjectInputStream(fis);
              Object obj = ois.readObject();
              if (obj instanceof ArrayList) {
                blocksHolderList = (ArrayList<BlocksHolder>) obj;
                runOnUiThread(
                    () -> {
                      showSection(3);
                      for (int i = 0; i < blocksHolderList.size(); ++i) {
                        if (blocksHolderList
                            .get(i)
                            .getName()
                            .toLowerCase()
                            .equals(blocksHolderSelectedName.toLowerCase())) {
                          blocksHolder = blocksHolderList.get(i);
                          Drawable backgroundDrawable = binding.block.getBackground();
                          backgroundDrawable.setTint(Color.parseColor(blocksHolder.getColor()));
                          backgroundDrawable.setTintMode(PorterDuff.Mode.SRC_IN);
                          binding.block.setBackground(backgroundDrawable);
                        }
                      }
                      binding.PropertyText.setOnClickListener(
                          (view) -> {
                            AddTextInBlockDialog addTextInBlockDialog =
                                new AddTextInBlockDialog(
                                    BlockEditorActivity.this,
                                    new ValueListener() {

                                      @Override
                                      public void onSubmitted(String value) {
                                        TextView tv = new TextView(BlockEditorActivity.this);
                                        tv.setText(value);
                                        tv.setTextSize(10);
                                        binding.blockContent.addView(
                                            tv, binding.blockContent.getChildCount());
                                      }

                                      @Override
                                      public void onError(String error) {
                                        Toast.makeText(
                                                BlockEditorActivity.this, error, Toast.LENGTH_SHORT)
                                            .show();
                                      }
                                    });
                            addTextInBlockDialog.show();
                          });
                    });
              } else {
                runOnUiThread(
                    () -> {
                      showSection(2);
                      binding.tvInfo.setText(getString(R.string.error));
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
                  binding.tvInfo.setText(getString(R.string.error));
                });
          }
        });
  }

  public void showSection(int section) {
    binding.loading.setVisibility(section == 1 ? View.VISIBLE : View.GONE);
    binding.info.setVisibility(section == 2 ? View.VISIBLE : View.GONE);
    binding.blocksList.setVisibility(section == 3 ? View.VISIBLE : View.GONE);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
  }

  private void saveBlocksHolderList() {
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          try {
            BLOCKS.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(BLOCKS);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(blocksHolderList);
            fos.close();
            oos.close();
            finish();
          } catch (Exception e) {
            runOnUiThread(
                () -> {
                  Toast.makeText(BlockEditorActivity.this, e.getMessage(), Toast.LENGTH_SHORT)
                      .show();
                });
          }
        });
  }

  @Override
  public void onBackPressed() {
    saveBlocksHolderList();
  }
}
