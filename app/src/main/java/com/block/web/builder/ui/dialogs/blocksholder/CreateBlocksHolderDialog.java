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
