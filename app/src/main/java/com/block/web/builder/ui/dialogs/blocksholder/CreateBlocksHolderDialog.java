package com.block.web.builder.ui.dialogs.blocksholder;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import com.block.web.builder.R;
import com.block.web.builder.databinding.LayoutNewBlocksHolderDialogBinding;
import com.block.web.builder.listeners.BlocksHolderListener;
import com.block.web.builder.core.BlocksHolder;
import com.block.web.builder.utils.HexColorValidator;
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
              errorMessage1 = activity.getString(R.string.holder_name_empty_error);
              binding.TextInputLayout1.setError(
                  activity.getString(R.string.holder_name_empty_error));
            } else {
              binding.TextInputLayout1.setError(null);
              if (isBlocksHolderNameAlreadyInUse(
                  blocksHolderList, binding.holderName.getText().toString())) {
                errorMessage1 = activity.getString(R.string.already_in_use);
                binding.TextInputLayout1.setError(activity.getString(R.string.already_in_use));
              }
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
              errorMessage2 = activity.getString(R.string.invalid_color);
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
          } else if (isBlocksHolderNameAlreadyInUse(
              blocksHolderList, binding.holderName.getText().toString())) {
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

  public static boolean isBlocksHolderNameAlreadyInUse(
      ArrayList<BlocksHolder> blocksHolderList, String name) {
    boolean isBlocksHolderNameAlreadyInUse = false;
    for (int i = 0; i < blocksHolderList.size(); ++i) {
      if (blocksHolderList.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
        isBlocksHolderNameAlreadyInUse = true;
      }
    }
    return isBlocksHolderNameAlreadyInUse;
  }
}
