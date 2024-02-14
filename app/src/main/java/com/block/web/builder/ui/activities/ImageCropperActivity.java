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
