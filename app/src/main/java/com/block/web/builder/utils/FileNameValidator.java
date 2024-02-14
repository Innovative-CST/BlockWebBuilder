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

package com.block.web.builder.utils;

import java.util.regex.Pattern;

public class FileNameValidator {
  private static final String FILE_NAME_PATTERN = "^[a-zA-Z0-9-_\\.]+$";
  private static final Pattern pattern = Pattern.compile(FILE_NAME_PATTERN);

  public static boolean isValidFileName(String fileName) {
    return pattern.matcher(fileName).matches();
  }
}
