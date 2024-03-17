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

import static com.block.web.builder.utils.Environments.PREFERENCES;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import com.block.web.builder.R;
import com.block.web.builder.databinding.ActivitySettingBinding;
import com.block.web.builder.databinding.LayoutSettingItemBinding;
import com.block.web.builder.listeners.TaskListener;
import com.block.web.builder.utils.DeserializationException;
import com.block.web.builder.utils.DeserializerUtils;
import com.block.web.builder.utils.preferences.BasePreference;
import com.block.web.builder.utils.preferences.BooleanPreference;
import com.google.android.material.elevation.SurfaceColors;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class SettingActivity extends BaseActivity {

  private ActivitySettingBinding binding;

  private ArrayList<BasePreference> preferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivitySettingBinding.inflate(getLayoutInflater());

    setContentView(binding.getRoot());

    // Setup toolbar.
    binding.toolbar.setTitle(R.string.settings);
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

    binding.collapsingToolbar.setStatusBarScrimColor(SurfaceColors.SURFACE_2.getColor(this));
    binding.collapsingToolbar.setContentScrimColor(SurfaceColors.SURFACE_2.getColor(this));

    tryLoadingPreferences();

    addPreferenceIfNotExists(
        "Canva Manual Lock", BasePreference.PeferenceType.BooleanPreference, false);

    addPreferenceIfNotExists(
        "Use Sora Editor", BasePreference.PeferenceType.BooleanPreference, false);

    addSettingsUI();
  }

  public void addSettingsUI() {
    addPreferences(
        "Canva lock",
        "If enabled a canva manual lock will be enabled if you need to lock canva manually",
        "Canva Manual Lock");

    addPreferences(
        "Sora Editor",
        "If enabled Sora Editor will be used instead of Ace Editor",
        "Use Sora Editor");
  }

  public void addPreferenceIfNotExists(
      String preferenceKey, BasePreference.PeferenceType preferenceType, Object value) {
    boolean isKeyPreferenceExists = false;
    for (int position = 0; position < preferences.size(); ++position) {
      if (preferences.get(position).getPreferenceKey().equals(preferenceKey)) {
        isKeyPreferenceExists = true;
      }
    }

    if (!isKeyPreferenceExists) {
      BasePreference preference = null;
      if (preferenceType.equals(BasePreference.PeferenceType.BooleanPreference)) {
        BooleanPreference booleanPreference = new BooleanPreference();
        booleanPreference.setPreferenceValue((Boolean) value);
        preference = booleanPreference;
      }
      preference.setPreferenceKey(preferenceKey);
      preference.setPreferenceType(preferenceType);
      if (preference != null) {
        preferences.add(preference);
      }
    }
  }

  public void tryLoadingPreferences() {
    try {
      DeserializerUtils.deserializePreferences(
          PREFERENCES,
          new TaskListener() {
            @Override
            public void onSuccess(Object result) {
              preferences = (ArrayList<BasePreference>) result;
            }
          });
    } catch (DeserializationException e) {
      preferences = new ArrayList<BasePreference>();
    }
  }

  @Override
  @Deprecated
  @MainThread
  @CallSuper
  public void onBackPressed() {
    super.onBackPressed();
    saveSetting(true);
  }

  public void saveSetting(boolean exitAfterSave) {
    Executors.newSingleThreadExecutor()
        .execute(
            () -> {
              try {
                FileOutputStream fos = new FileOutputStream(PREFERENCES);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(preferences);
                fos.close();
                oos.close();
                if (exitAfterSave) {
                  finish();
                }
              } catch (Exception e) {
              }
            });
  }

  public void addPreferences(String title, String secondaryText, String preferenceKey) {
    LayoutSettingItemBinding preferenceLayout =
        LayoutSettingItemBinding.inflate(getLayoutInflater());
    preferenceLayout.primaryText.setText(title);
    preferenceLayout.secondaryText.setText(secondaryText);
    binding.contents.addView(preferenceLayout.getRoot());

    if (preferences != null) {

      boolean isKeyPreferenceExists = false;

      for (int position = 0; position < preferences.size(); ++position) {
        if (preferences.get(position).getPreferenceKey().equals(preferenceKey)) {
          if (preferences
              .get(position)
              .getPreferenceType()
              .equals(BasePreference.PeferenceType.BooleanPreference)) {

            isKeyPreferenceExists = true;

            preferenceLayout.check.setChecked(
                ((BooleanPreference) preferences.get(position)).getPreferenceValue());

            preferenceLayout
                .getRoot()
                .setOnClickListener(
                    (view) -> {
                      preferenceLayout.check.setChecked(!preferenceLayout.check.isChecked());
                    });

            final int i = position;
            preferenceLayout.check.setOnCheckedChangeListener(
                (switchView, isChecked) -> {
                  ((BooleanPreference) preferences.get(i)).setPreferenceValue(isChecked);
                });

          } else {
            preferenceLayout.check.setVisibility(View.GONE);
          }
          return;
        }
      }

      if (!isKeyPreferenceExists) {
        preferenceLayout.check.setVisibility(View.GONE);
      }
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
  }
}
