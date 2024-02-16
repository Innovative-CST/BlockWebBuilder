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

package com.block.web.builder.ui.dialogs.filemanager;

import androidx.appcompat.app.AppCompatActivity;
import com.block.web.builder.core.WebFile;
import com.block.web.builder.listeners.TaskListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DeleteFileDialog extends MaterialAlertDialogBuilder {
  public DeleteFileDialog(AppCompatActivity activity, WebFile file, TaskListener listener) {
    super(activity);
    setTitle("Delete File?");
    setMessage(
        "You are about to delete "
            + file.getFilePath()
            + WebFile.getSupportedFileSuffix(file.getFileType()));
    setPositiveButton(
        "Confirm Delete",
        (param1, param2) -> {
          listener.onSuccess(0);
        });
    setNegativeButton(
        "Cancel",
        (param1, param2) -> {
          listener.onCancelled();
        });
  }
}
