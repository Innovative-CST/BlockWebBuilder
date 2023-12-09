package com.dragon.ide.ui.dialogs.eventList;

import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutAddEventDialogBinding;
import com.dragon.ide.listeners.EventAddListener;
import com.dragon.ide.objects.Event;
import com.dragon.ide.objects.WebFile;
import com.dragon.ide.ui.activities.EventListActivity;
import com.dragon.ide.ui.adapters.EventAdapter;
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
