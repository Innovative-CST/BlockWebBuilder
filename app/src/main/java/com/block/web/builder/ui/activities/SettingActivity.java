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

  public static Object getPreferencesValue(
      ArrayList<BasePreference> preferences, String preferenceKey, Object defaultValue) {
    for (int position = 0; position < preferences.size(); ++position) {
      if (preferences.get(position).getPreferenceKey().equals(preferenceKey)) {
        if (preferences
            .get(position)
            .getPreferenceType()
            .equals(BasePreference.PeferenceType.BooleanPreference)) {
          return ((BooleanPreference) preferences.get(position)).getPreferenceValue();
        }
      }
    }

    return defaultValue;
  }
}
