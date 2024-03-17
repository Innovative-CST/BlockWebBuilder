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

import static com.block.web.builder.utils.Environments.BLOCKS;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.block.web.builder.R;
import com.block.web.builder.databinding.ActivityBlockEditorBinding;
import com.block.web.builder.listeners.ValueListener;
import com.block.web.builder.core.Block;
import com.block.web.builder.core.BlocksHolder;
import com.block.web.builder.ui.dialogs.blockeditor.AddTextInBlockDialog;
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
