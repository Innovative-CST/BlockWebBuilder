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
