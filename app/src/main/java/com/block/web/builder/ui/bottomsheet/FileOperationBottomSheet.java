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

package android.code.editor.ui.bottomsheet;

import android.code.editor.common.interfaces.FileDeleteListener;
import android.code.editor.common.utils.FileDeleteUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.block.web.builder.core.WebFile;
import com.block.web.builder.databinding.LayoutFileOperationBinding;
import com.block.web.builder.listeners.TaskListener;
import com.block.web.builder.ui.activities.FileManagerActivity;
import com.block.web.builder.ui.dialogs.filemanager.DeleteFileDialog;
import com.block.web.builder.utils.ProjectFileUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.io.File;

public class FileOperationBottomSheet extends BottomSheetDialogFragment {
  public int position;
  public FileManagerActivity mFileManagerActivity;

  public FileOperationBottomSheet(int position, FileManagerActivity mFileManagerActivity) {
    this.position = position;
    this.mFileManagerActivity = mFileManagerActivity;
  }

  @Override
  public View onCreateView(LayoutInflater inflator, ViewGroup layout, Bundle bundle) {
    LayoutFileOperationBinding binding = LayoutFileOperationBinding.inflate(inflator);
    binding.deleteFile.setOnClickListener(
        v -> {
          new DeleteFileDialog(
                  mFileManagerActivity,
                  mFileManagerActivity.getFileList().get(position),
                  new TaskListener() {

                    @Override
                    public void onSuccess(Object result) {
                      FileDeleteUtils.delete(
                          new File(
                              new File(mFileManagerActivity.getListPath()),
                              mFileManagerActivity
                                  .getFileList()
                                  .get(position)
                                  .getFilePath()
                                  .concat(
                                      WebFile.getSupportedFileSuffix(
                                          mFileManagerActivity
                                              .getFileList()
                                              .get(position)
                                              .getFileType()))),
                          new FileDeleteListener() {
                            @Override
                            public void onProgressUpdate(int deleteDone) {}

                            @Override
                            public void onTotalCount(int total) {}

                            @Override
                            public void onDeleting(File path) {}

                            @Override
                            public void onDeleteComplete(File path) {}

                            @Override
                            public void onTaskComplete() {
                              mFileManagerActivity.showFileList();
                            }
                          },
                          true,
                          mFileManagerActivity);
                    }
                  })
              .create()
              .show();
        });

    return binding.getRoot();
  }
}
