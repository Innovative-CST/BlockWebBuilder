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

package com.block.web.builder.ui.dialogs.filemanager;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import builtin.files.CssFile;
import builtin.files.HtmlFile;
import builtin.files.JavascriptFile;
import com.block.web.builder.R;
import com.block.web.builder.databinding.LayoutNewFileDialogBinding;
import com.block.web.builder.listeners.FileCreationListener;
import com.block.web.builder.core.WebFile;
import com.block.web.builder.ui.activities.FileManagerActivity;
import com.block.web.builder.ui.adapters.FileListAdapterItem;
import com.block.web.builder.utils.FileNameValidator;
import com.block.web.builder.utils.ProjectFileUtils;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.io.File;
import java.util.ArrayList;

public class CreateFileDialog extends MaterialAlertDialogBuilder {
  private AppCompatActivity activity;
  private LayoutNewFileDialogBinding binding;

  public CreateFileDialog(
      AppCompatActivity activity,
      ArrayList<String> paths,
      ArrayList<WebFile> fileList,
      String projectName,
      String projectPath) {
    super(activity);
    this.activity = activity;

    FileCreationListener listener =
        new FileCreationListener() {
          @Override
          public void onFileCreated(WebFile webFile) {
            fileList.add(webFile);
            paths.add(
                ProjectFileUtils.getProjectWebFile(
                        new File(
                            ProjectFileUtils.getProjectFilesDirectory(new File(projectPath)),
                            webFile
                                .getFilePath()
                                .concat(WebFile.getSupportedFileSuffix(webFile.getFileType()))))
                    .getAbsolutePath());
            if (activity instanceof FileManagerActivity) {
              ((FileManagerActivity) activity)
                  .getFileListRecyclerView()
                  .setAdapter(
                      new FileListAdapterItem(fileList, paths, activity, projectName, projectPath));
              ((FileManagerActivity) activity)
                  .getFileListRecyclerView()
                  .setLayoutManager(new LinearLayoutManager(activity));
              ((FileManagerActivity) activity).showSection(4);
            }
          }

          @Override
          public void onFileCreationFailed(String error) {
            Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
          }
        };
    setTitle(activity.getString(R.string.create_new_file));
    binding = LayoutNewFileDialogBinding.inflate(activity.getLayoutInflater());
    setView(binding.getRoot());
    binding.fileName.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

          @Override
          public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            binding.TextInputLayout1.setError(activity.getString(R.string.invalid_file_name));
            if (FileNameValidator.isValidFileName(arg0.toString())) {
              binding.TextInputLayout1.setErrorEnabled(false);
              boolean isNameInUse = false;
              for (int i = 0; i < fileList.size(); ++i) {
                if (fileList
                    .get(i)
                    .getFilePath()
                    .toLowerCase()
                    .equals(binding.fileName.getText().toString().toLowerCase())) {
                  int fileType = fileList.get(i).getFileType();
                  int fileCreatingButton = binding.fileTypeChooser.getCheckedButtonId();
                  if (fileCreatingButton == R.id.folder) {
                    if (fileType == WebFile.SupportedFileType.FOLDER) {
                      isNameInUse = true;
                    }
                  }
                  if (fileCreatingButton == R.id.html) {
                    if (fileType == WebFile.SupportedFileType.HTML) {
                      isNameInUse = true;
                    }
                  }
                  if (fileCreatingButton == R.id.css) {
                    if (fileType == WebFile.SupportedFileType.CSS) {
                      isNameInUse = true;
                    }
                  }
                  if (fileCreatingButton == R.id.js) {
                    if (fileType == WebFile.SupportedFileType.JS) {
                      isNameInUse = true;
                    }
                  }
                }
              }
              if (isNameInUse) {
                binding.TextInputLayout1.setError(activity.getString(R.string.file_already_exists));
                binding.TextInputLayout1.setErrorEnabled(true);
              }
            } else {
              binding.TextInputLayout1.setErrorEnabled(true);
            }
          }

          @Override
          public void afterTextChanged(Editable arg0) {}
        });
    setPositiveButton(
        R.string.create,
        (param1, param2) -> {
          if (FileNameValidator.isValidFileName(binding.fileName.getText().toString())) {
            boolean isNameInUse = false;
            for (int i = 0; i < fileList.size(); ++i) {
              if (fileList
                  .get(i)
                  .getFilePath()
                  .toLowerCase()
                  .equals(binding.fileName.getText().toString().toLowerCase())) {
                int fileType = fileList.get(i).getFileType();
                int fileCreatingButton = binding.fileTypeChooser.getCheckedButtonId();
                if (fileCreatingButton == R.id.folder) {
                  if (fileType == WebFile.SupportedFileType.FOLDER) {
                    isNameInUse = true;
                  }
                }
                if (fileCreatingButton == R.id.html) {
                  if (fileType == WebFile.SupportedFileType.HTML) {
                    isNameInUse = true;
                  }
                }
                if (fileCreatingButton == R.id.css) {
                  if (fileType == WebFile.SupportedFileType.CSS) {
                    isNameInUse = true;
                  }
                }
                if (fileCreatingButton == R.id.js) {
                  if (fileType == WebFile.SupportedFileType.JS) {
                    isNameInUse = true;
                  }
                }
              }
            }
            if (isNameInUse) {
              listener.onFileCreationFailed(activity.getString(R.string.file_already_exists));
            } else {
              WebFile webFile = new WebFile();
              int fileCreatingButton = binding.fileTypeChooser.getCheckedButtonId();
              int fileType = 0;
              if (fileCreatingButton == R.id.folder) {
                webFile = new WebFile();
                webFile.setFileType(WebFile.SupportedFileType.FOLDER);
                fileType = WebFile.SupportedFileType.FOLDER;
              }
              if (fileCreatingButton == R.id.html) {
                webFile = new HtmlFile();
                fileType = WebFile.SupportedFileType.HTML;
              }
              if (fileCreatingButton == R.id.css) {
                webFile = new CssFile();
                fileType = WebFile.SupportedFileType.CSS;
              }
              if (fileCreatingButton == R.id.js) {
                webFile = new JavascriptFile();
                fileType = WebFile.SupportedFileType.JS;
              }
              webFile.setFilePath(binding.fileName.getText().toString());
              webFile.setFileType(fileType);
              listener.onFileCreated(webFile);
            }
          } else {
            listener.onFileCreationFailed(activity.getString(R.string.invalid_file_name));
          }
        });
    binding.fileTypeChooser.addOnButtonCheckedListener(
        new MaterialButtonToggleGroup.OnButtonCheckedListener() {
          @Override
          public void onButtonChecked(MaterialButtonToggleGroup arg0, int arg1, boolean arg2) {
            binding.TextInputLayout1.setError(activity.getString(R.string.invalid_file_name));
            if (FileNameValidator.isValidFileName(binding.fileName.getText().toString())) {
              binding.TextInputLayout1.setErrorEnabled(false);
              boolean isNameInUse = false;
              for (int i = 0; i < fileList.size(); ++i) {
                if (fileList
                    .get(i)
                    .getFilePath()
                    .toLowerCase()
                    .equals(binding.fileName.getText().toString().toLowerCase())) {
                  int fileType = fileList.get(i).getFileType();
                  int fileCreatingButton = binding.fileTypeChooser.getCheckedButtonId();
                  if (fileCreatingButton == R.id.folder) {
                    if (fileType == WebFile.SupportedFileType.FOLDER) {
                      isNameInUse = true;
                    }
                  }
                  if (fileCreatingButton == R.id.html) {
                    if (fileType == WebFile.SupportedFileType.HTML) {
                      isNameInUse = true;
                    }
                  }
                  if (fileCreatingButton == R.id.css) {
                    if (fileType == WebFile.SupportedFileType.CSS) {
                      isNameInUse = true;
                    }
                  }
                  if (fileCreatingButton == R.id.js) {
                    if (fileType == WebFile.SupportedFileType.JS) {
                      isNameInUse = true;
                    }
                  }
                }
              }
              if (isNameInUse) {
                binding.TextInputLayout1.setError(activity.getString(R.string.file_already_exists));
                binding.TextInputLayout1.setErrorEnabled(true);
              }
            } else {
              binding.TextInputLayout1.setErrorEnabled(true);
            }
          }
        });

    setNegativeButton(R.string.cancel, (param1, param2) -> {});
  }
}
