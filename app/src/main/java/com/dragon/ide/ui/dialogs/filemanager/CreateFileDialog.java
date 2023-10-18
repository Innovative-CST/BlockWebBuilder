package com.dragon.ide.ui.dialogs.filemanager;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutNewFileDialogBinding;
import com.dragon.ide.utils.FileNameValidator;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class CreateFileDialog extends MaterialAlertDialogBuilder {
  private Activity activity;
  private LayoutNewFileDialogBinding binding;

  public CreateFileDialog(Activity activity) {
    super(activity);
    this.activity = activity;
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

          } else {
            Toast.makeText(
                    getContext(),
                    getContext().getString(R.string.invalid_file_name),
                    Toast.LENGTH_SHORT)
                .show();
          }
        });
    setNegativeButton(R.string.cancel, (param1, param2) -> {});
  }
}
