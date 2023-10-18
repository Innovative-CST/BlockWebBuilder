package com.dragon.ide.ui.dialogs.filemanager;

import android.app.Activity;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutNewFileDialogBinding;
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
    setPositiveButton(R.string.create, (param1, param2) -> {});
    setNegativeButton(R.string.cancel, (param1, param2) -> {});
  }
}
