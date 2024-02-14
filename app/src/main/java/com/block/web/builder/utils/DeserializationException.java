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

public class DeserializationException extends Exception {
  public static final int FILE_NOT_FOUND_EXCEPTION = 0;
  public static final int FOLDER_FOUND_EXCEPTION = 1;
  public static final int DESERIALIZATION_EXCEPTION = 2;
  public static final int OBJECT_TYPE_EXCEPTION = 3;

  private int errorType;

  public DeserializationException(String message) {
    super(message);
  }

  public int getErrorType() {
    return errorType;
  }

  public void setErrorType(int errorType) {
    this.errorType = errorType;
  }
}
