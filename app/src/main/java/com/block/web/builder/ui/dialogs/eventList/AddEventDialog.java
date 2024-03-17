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

package com.block.web.builder.ui.dialogs.eventList;

import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.block.web.builder.R;
import com.block.web.builder.databinding.LayoutAddEventDialogBinding;
import com.block.web.builder.listeners.EventAddListener;
import com.block.web.builder.core.Event;
import com.block.web.builder.core.WebFile;
import com.block.web.builder.ui.activities.EventListActivity;
import com.block.web.builder.ui.adapters.EventAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.ArrayList;

public class AddEventDialog extends MaterialAlertDialogBuilder {
  private EventListActivity mEventListActivity;
  private WebFile file;
  private EventAddListener listener;
  private EventAdapter adapter;

  public AddEventDialog(
      EventListActivity mEventListActivity, WebFile file, EventAddListener listener) {
    super(mEventListActivity);
    this.mEventListActivity = mEventListActivity;
    this.file = file;
    this.listener = listener;

    LayoutAddEventDialogBinding binding =
        LayoutAddEventDialogBinding.inflate(mEventListActivity.getLayoutInflater());
    setTitle(R.string.add_events);
    setCancelable(false);
    setView(binding.getRoot());
    AlertDialog mAlertDialog = create();
    mAlertDialog.show();
    adapter = new EventAdapter(file.getEvents(), mEventListActivity);
    binding.list.setAdapter(adapter);
    binding.list.setLayoutManager(new LinearLayoutManager(mEventListActivity));
    if (file.getEvents().size() > 0) {
      binding.info.setVisibility(View.GONE);
      binding.addEventSection.setVisibility(View.VISIBLE);
    }
    binding.cancel.setOnClickListener(
        (view) -> {
          mAlertDialog.dismiss();
        });
    binding.add.setOnClickListener(
        (view) -> {
          ArrayList<Event> selectedEvents = new ArrayList<Event>();
          for (int i = 0; i < adapter.selectedCheckboxes.length; ++i) {
            if (adapter.selectedCheckboxes[i]) {
              selectedEvents.add(adapter._data.get(i));
            }
          }
          listener.onAdd(selectedEvents);
          mAlertDialog.dismiss();
        });
  }
}
