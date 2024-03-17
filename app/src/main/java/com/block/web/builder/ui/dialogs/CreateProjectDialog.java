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

package com.block.web.builder.ui.dialogs;

import static com.block.web.builder.utils.Environments.PROJECTS;

import android.app.Activity;
import android.code.editor.common.utils.FileUtils;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import com.block.web.builder.R;
import com.block.web.builder.databinding.LayoutBottomsheetProjectPhotoBinding;
import com.block.web.builder.databinding.LayoutCreateProjectBinding;
import com.block.web.builder.listeners.ProjectCreationListener;
import com.block.web.builder.objects.Project;
import com.block.web.builder.ui.activities.MainActivity;
import com.block.web.builder.utils.ProjectNameValidator;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateProjectDialog {

  private Activity activity;
  public LayoutCreateProjectBinding dialogBinding;
  public LayoutBottomsheetProjectPhotoBinding layoutBottomsheetProjectPhotoBinding;
  public BottomSheetDialog mBottomSheetDialog;
  public boolean isAddedProjectPhoto;

  public CreateProjectDialog(
      Activity activity,
      ArrayList<HashMap<String, Object>> projectList,
      ProjectCreationListener listener) {

    this.activity = activity;

    MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);

    dialog.setTitle(activity.getString(R.string.create_new_project));

    LayoutCreateProjectBinding binding =
        LayoutCreateProjectBinding.inflate(activity.getLayoutInflater());

    dialogBinding = binding;

    dialog.setView(binding.getRoot());
    dialog.setNegativeButton(activity.getString(R.string.cancel), null);
    dialog.setPositiveButton(
        activity.getString(R.string.create),
        (param1, param2) -> {
          if (ProjectNameValidator.isValidProjectName(binding.projectName.getText().toString())) {
            new File(PROJECTS, binding.projectName.getText().toString()).mkdirs();
            try {
              FileOutputStream fos =
                  new FileOutputStream(
                      new File(
                          new File(PROJECTS, binding.projectName.getText().toString()),
                          "Project.txt"));
              ObjectOutputStream oos = new ObjectOutputStream(fos);

              if (isAddedProjectPhoto) {

                com.blankj.utilcode.util.FileUtils.copy(
                    ((MainActivity) activity).croppedImagePath,
                    new File(
                            new File(PROJECTS, binding.projectName.getText().toString()),
                            FileUtils.getLatSegmentOfFilePath(
                                ((MainActivity) activity).croppedImagePath))
                        .getAbsolutePath());
              }

              Project project = new Project();
              project.setProjectName(binding.projectName.getText().toString().trim());
              if (isAddedProjectPhoto) {
                project.setProjectPhotoPath(
                    FileUtils.getLatSegmentOfFilePath(((MainActivity) activity).croppedImagePath));
              }
              oos.writeObject(project);
              oos.close();
              fos.close();
              listener.onProjectCreated(project.getProjectName());
            } catch (Exception err) {
            }
          } else {
            if (!(ProjectNameValidator.isValidProjectName(
                binding.projectName.getText().toString()))) {
              Toast.makeText(
                      activity,
                      activity.getString(R.string.invalid_project_name),
                      Toast.LENGTH_LONG)
                  .show();
            } else if (isNameAlreadyInUse(projectList, binding.projectName.getText().toString())) {
              Toast.makeText(
                      activity, activity.getString(R.string.already_in_use), Toast.LENGTH_LONG)
                  .show();
            }
          }
        });
    binding.projectName.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

          @Override
          public void onTextChanged(CharSequence arg0, int start, int before, int count) {
            binding.TextInputLayout1.setError(activity.getString(R.string.invalid_project_name));
            if (ProjectNameValidator.isValidProjectName(arg0.toString())) {
              binding.TextInputLayout1.setErrorEnabled(false);
              if (isNameAlreadyInUse(projectList, binding.projectName.getText().toString())) {
                binding.TextInputLayout1.setErrorEnabled(true);
                binding.TextInputLayout1.setError(activity.getString(R.string.already_in_use));
              }
            } else {
              binding.TextInputLayout1.setErrorEnabled(true);
            }
          }

          @Override
          public void afterTextChanged(Editable arg0) {}
        });
    binding.websiteIcon.setOnClickListener(
        (v) -> {
          showProjectIconChooser();
        });
    dialog.create().show();
  }

  public boolean isNameAlreadyInUse(ArrayList<HashMap<String, Object>> projectList, String name) {
    boolean isUsed = false;
    for (int i = 0; i < projectList.size(); ++i) {
      if (projectList.get(i).containsKey("Path")) {
        if (((File) projectList.get(i).get("Path"))
            .getAbsolutePath()
            .toLowerCase()
            .equals(new File(PROJECTS, name).getAbsolutePath().toLowerCase())) {
          isUsed = true;
        }
      }
      if (projectList.get(i).containsKey("Project")) {
        if (((Project) projectList.get(i).get("Project"))
            .getProjectName()
            .toLowerCase()
            .equals(name.toLowerCase())) {
          isUsed = true;
        }
      }
    }
    return isUsed;
  }

  public void showProjectIconChooser() {
    mBottomSheetDialog = new BottomSheetDialog(activity);
    mBottomSheetDialog.setTitle(R.string.project_photo);
    layoutBottomsheetProjectPhotoBinding =
        LayoutBottomsheetProjectPhotoBinding.inflate(mBottomSheetDialog.getLayoutInflater());
    mBottomSheetDialog.setContentView(layoutBottomsheetProjectPhotoBinding.getRoot());
    layoutBottomsheetProjectPhotoBinding.photoPicker.setOnClickListener(
        (v) -> {
          Intent intent = new Intent();
          intent.setType("image/*");
          intent.setAction(Intent.ACTION_GET_CONTENT);
          activity.startActivityForResult(
              Intent.createChooser(intent, "Select Image"), MainActivity.PICK_IMAGE_REQUEST);
        });

    mBottomSheetDialog.show();
  }
}
