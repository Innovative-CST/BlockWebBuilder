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
