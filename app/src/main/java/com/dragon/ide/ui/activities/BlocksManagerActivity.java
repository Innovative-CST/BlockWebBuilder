package com.dragon.ide.ui.activities;

import com.dragon.ide.objects.BlocksHolder;
import static com.dragon.ide.utils.Environments.BLOCKS;

import android.os.Bundle;
import android.view.View;
import com.dragon.ide.R;
import com.dragon.ide.databinding.ActivityBlockManagerBinding;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlocksManagerActivity extends BaseActivity {

  private ActivityBlockManagerBinding binding;
  private ArrayList<BlocksHolder> blocksHolderList;

  @Override
  protected void onCreate(Bundle arg0) {
    super.onCreate(arg0);

    // Inflate and get instance of binding.
    binding = ActivityBlockManagerBinding.inflate(getLayoutInflater());

    // set content view to binding's root.
    setContentView(binding.getRoot());

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

    // Initialize blocksHolderList to avoid null errors
    blocksHolderList = new ArrayList<BlocksHolder>();

    /*
     * Ask for storage permission if not granted.
     * Load blocks holder list if storage permission is granted.
     */
    if (MainActivity.isStoagePermissionGranted(this)) {
      loadBlocksHolderList();
    } else {
      showSection(2);
      MainActivity.showStoragePermissionDialog(this);
    }
  }

  private void loadBlocksHolderList() {
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
              } else {
                runOnUiThread(
                    () -> {
                      showSection(2);
                      binding.tvInfo.setText(getString(R.string.an_error_occured_while_parsing_blocks_holder_list));
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
                  binding.tvInfo.setText(getString(R.string.no_blocks_holder_yet));
                });
          }
        });
  }

  public void showSection(int section) {
    binding.loading.setVisibility(View.GONE);
    binding.info.setVisibility(View.GONE);
    binding.blocksHolder.setVisibility(View.GONE);
    switch (section) {
      case 1:
        binding.loading.setVisibility(View.VISIBLE);
        break;
      case 2:
        binding.info.setVisibility(View.VISIBLE);
        break;
      case 3:
        binding.blocksHolder.setVisibility(View.VISIBLE);
        break;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
  }
}
