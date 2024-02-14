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

import static com.block.web.builder.utils.Environments.BLOCKS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.block.web.builder.R;
import com.block.web.builder.databinding.ActivityBlockManagerBinding;
import com.block.web.builder.core.Block;
import com.block.web.builder.core.BlocksHolder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlockManagerActivity extends BaseActivity {

  private ActivityBlockManagerBinding binding;
  private ArrayList<BlocksHolder> blocksHolderList;
  private ArrayList<Block> blocks;
  private String blocksHolderSelectedName;

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

    // Initialize blocksHolderList and blocks to avoid null errors
    blocks = new ArrayList<Block>();
    blocksHolderList = new ArrayList<BlocksHolder>();

    // Get selected Blocks holder
    if (getIntent().hasExtra("BlocksHolder")) {
      blocksHolderSelectedName = getIntent().getStringExtra("BlocksHolder");
    } else {
      showSection(2);
      binding.tvInfo.setText(getString(R.string.error));
      return;
    }

    /*
     * Ask for storage permission if not granted.
     * Load blocks list if storage permission is granted.
     */
    if (MainActivity.isStoagePermissionGranted(this)) {
      loadBlocksList();
      binding.fab.setOnClickListener(
          (view) -> {
            Intent blockEditor = new Intent();
            blockEditor.putExtra("BlocksHolder", blocksHolderSelectedName);
            blockEditor.putExtra("createNewBlock", true);
            blockEditor.setClass(BlockManagerActivity.this, BlockEditorActivity.class);
            startActivity(blockEditor);
          });
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
                      showSection(1);
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
    binding.loading.setVisibility(View.GONE);
    binding.info.setVisibility(View.GONE);
    binding.blocksList.setVisibility(View.GONE);
    switch (section) {
      case 1:
        binding.loading.setVisibility(View.VISIBLE);
        break;
      case 2:
        binding.info.setVisibility(View.VISIBLE);
        break;
      case 3:
        binding.blocksList.setVisibility(View.VISIBLE);
        break;
    }
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
                  Toast.makeText(BlockManagerActivity.this, e.getMessage(), Toast.LENGTH_SHORT)
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
