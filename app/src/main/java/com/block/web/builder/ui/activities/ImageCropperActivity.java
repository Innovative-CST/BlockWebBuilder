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

import static com.block.web.builder.utils.Environments.CROPED_IMAGE_CACHE;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import com.block.web.builder.R;
import com.block.web.builder.databinding.ActivityImageCropperBinding;
import com.canhub.cropper.CropImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.concurrent.Executors;

public class ImageCropperActivity extends BaseActivity {

  private ActivityImageCropperBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityImageCropperBinding.inflate(getLayoutInflater());

    setContentView(binding.getRoot());

    binding.toolbar.setTitle(R.string.app_name);
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    binding.toolbar.setNavigationOnClickListener(
        (view) -> {
          onBackPressed();
        });

    if (getIntent() != null) {
      if (getIntent().getExtras() != null) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
          Uri pickedImageUri = (Uri) extras.get("pickedImageUri");
          binding.cropImageView.setImageUriAsync(pickedImageUri);
          binding.cropImageView.setAspectRatio(1, 1);
          binding.cropImageView.setCropShape(CropImageView.CropShape.RECTANGLE);

          binding.done.setOnClickListener(
              (view) -> {
                Executors.newSingleThreadExecutor()
                    .execute(
                        () -> {
                          Bitmap croppedImage = binding.cropImageView.getCroppedImage(500, 500);

                          try {
                            String path =
                                String.valueOf(Calendar.getInstance().getTimeInMillis() + ".png");

                            if (!CROPED_IMAGE_CACHE.exists()) {
                              CROPED_IMAGE_CACHE.mkdirs();
                            }

                            FileOutputStream fos =
                                new FileOutputStream(new File(CROPED_IMAGE_CACHE, path));

                            croppedImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

                            fos.close();
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(
                                "path", new File(CROPED_IMAGE_CACHE, path).getAbsolutePath());
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                          } catch (Exception e) {
                            e.printStackTrace();
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("error", e.getMessage());
                            setResult(Activity.RESULT_CANCELED, resultIntent);
                            finish();
                          }
                        });
              });
        }
      }
    }
    binding.cancel.setOnClickListener(
        (view) -> {
          Intent resultIntent = new Intent();
          setResult(Activity.RESULT_CANCELED, resultIntent);
          finish();
        });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
  }
}
