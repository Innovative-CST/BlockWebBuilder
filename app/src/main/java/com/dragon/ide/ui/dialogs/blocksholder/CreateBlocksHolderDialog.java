package com.dragon.ide.ui.dialogs.blocksholder;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutNewBlocksHolderDialogBinding;
import com.dragon.ide.listeners.BlocksHolderListener;
import com.dragon.ide.objects.BlocksHolder;
import com.dragon.ide.utils.HexColorValidator;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.ArrayList;

public class CreateBlocksHolderDialog extends MaterialAlertDialogBuilder {

  private String errorMessage1 = "";
  private String errorMessage2 = "";

  public CreateBlocksHolderDialog(
      Activity activity, ArrayList<BlocksHolder> blocksHolderList, BlocksHolderListener listener) {
    super(activity);
    setTitle(getContext().getString(R.string.create_new_blocks_holder));
    LayoutNewBlocksHolderDialogBinding binding =
        LayoutNewBlocksHolderDialogBinding.inflate(activity.getLayoutInflater());
    setView(binding.getRoot());
    setNegativeButton(R.string.cancel, (param1, param2) -> {});
    binding.holderName.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

          @Override
          public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            if (binding.holderName.getText().toString().length() == 0) {
              binding.TextInputLayout1.setError(
                  activity.getString(R.string.holder_name_empty_error));
            } else {
              binding.TextInputLayout1.setError(null);
            }
          }

          @Override
          public void afterTextChanged(Editable arg0) {}
        });
    binding.color.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

          @Override
          public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            if (HexColorValidator.isValidateHexColor(binding.color.getText().toString())) {
              binding.TextInputLayout2.setError(null);
            } else {
              binding.TextInputLayout2.setError(activity.getString(R.string.invalid_color));
            }
          }

          @Override
          public void afterTextChanged(Editable arg0) {}
        });
    if (binding.holderName.getText().toString().length() == 0) {
      errorMessage1 = activity.getString(R.string.holder_name_empty_error);
      binding.TextInputLayout1.setError(activity.getString(R.string.holder_name_empty_error));
      binding.TextInputLayout1.setErrorEnabled(true);
    } else {
      binding.TextInputLayout1.setErrorEnabled(false);
    }
    if (HexColorValidator.isValidateHexColor(binding.color.getText().toString())) {
      binding.TextInputLayout1.setErrorEnabled(false);
    } else {
      errorMessage2 = activity.getString(R.string.invalid_color);
      binding.TextInputLayout2.setError(activity.getString(R.string.invalid_color));
      binding.TextInputLayout1.setErrorEnabled(true);
    }
    setPositiveButton(
        R.string.create,
        (param1, param2) -> {
          boolean containsError1 = false;
          boolean containsError2 = false;
          if (binding.holderName.getText().toString().length() == 0) {
            containsError1 = true;
          }
          if (!HexColorValidator.isValidateHexColor(binding.color.getText().toString())) {
            containsError2 = true;
          }
          if (containsError1) {
            listener.onBlocksHolderFailedToCreate(errorMessage1);
          } else {
            if (containsError2) {
              listener.onBlocksHolderFailedToCreate(errorMessage2);
            } else {
              BlocksHolder holder = new BlocksHolder();
              holder.setName(binding.holderName.getText().toString());
              holder.setColor(binding.color.getText().toString());
              blocksHolderList.add(holder);
              listener.onBlockHolderCreate(holder);
            }
          }
        });
  }
}
