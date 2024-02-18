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

package com.block.web.builder.utils.preferences;

import java.util.ArrayList;

public class PreferencesUtils {
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
