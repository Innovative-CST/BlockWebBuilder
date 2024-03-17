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

package com.block.web.builder.ui.dialogs.blockeditor;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import com.block.web.builder.R;
import com.block.web.builder.databinding.LayoutAddTextInBlockBinding;
import com.block.web.builder.listeners.ValueListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AddTextInBlockDialog extends MaterialAlertDialogBuilder {
  private Activity activity;
  private LayoutAddTextInBlockBinding binding;
  private boolean containError = false;
  private String errorMessage = "";

  public AddTextInBlockDialog(Activity activity, ValueListener listener) {
    super(activity);
    this.activity = activity;
    binding = LayoutAddTextInBlockBinding.inflate(activity.getLayoutInflater());
    setTitle(R.string.enter_value);
    setView(binding.getRoot());
    if (binding.valueEditor.getText().toString().length() == 0) {
      errorMessage = activity.getString(R.string.value_should_not_be_empty);
      containError = true;
    } else {
      errorMessage = "";
      containError = false;
    }
    binding.TextInputLayout1.setError(errorMessage);
    binding.TextInputLayout1.setErrorEnabled(containError);
    binding.valueEditor.addTextChangedListener(
        new TextWatcher() {

          @Override
          public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

          @Override
          public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            if (binding.valueEditor.getText().toString().length() == 0) {
              errorMessage = "";
              containError = false;
              binding.TextInputLayout1.setErrorEnabled(true);
            } else {
              errorMessage = "";
              containError = false;
            }
            binding.TextInputLayout1.setError(errorMessage);
            binding.TextInputLayout1.setErrorEnabled(containError);
          }

          @Override
          public void afterTextChanged(Editable arg0) {}
        });
    setPositiveButton(
        R.string.done,
        (param1, param2) -> {
          if (containError) {
            listener.onError(errorMessage);
          } else {
            listener.onSubmitted(binding.valueEditor.getText().toString());
          }
        });
    setNegativeButton(R.string.cancel, (param1, param2) -> {});
  }
}
