package com.dragon.ide.ui.dialogs.filemanager;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import builtin.files.CssFile;
import builtin.files.HtmlFile;
import builtin.files.JavascriptFile;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutNewFileDialogBinding;
import com.dragon.ide.listeners.FileCreationListener;
import com.dragon.ide.objects.WebFile;
import com.dragon.ide.ui.activities.FileManagerActivity;
import com.dragon.ide.ui.adapters.FileListAdapterItem;
import com.dragon.ide.utils.FileNameValidator;
import com.dragon.ide.utils.ProjectFileUtils;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.io.File;
import java.util.ArrayList;

public class CreateFileDialog extends MaterialAlertDialogBuilder {
  private Activity activity;
  private LayoutNewFileDialogBinding binding;

  public CreateFileDialog(
      Activity activity,
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
                            .concat(WebFile.getSupportedFileSuffix(webFile.getFileType())))).getAbsolutePath());
            if (activity instanceof FileManagerActivity) {
              ((FileManagerActivity) activity).getFileListRecyclerView();
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
