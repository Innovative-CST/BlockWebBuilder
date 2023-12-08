package com.dragon.ide.ui.dialogs.eventList;

import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutAddEventDialogBinding;
import com.dragon.ide.listeners.EventAddListener;
import com.dragon.ide.objects.WebFile;
import com.dragon.ide.ui.activities.EventListActivity;
import com.dragon.ide.ui.adapters.EventAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AddEventDialog extends MaterialAlertDialogBuilder {
  private EventListActivity mEventListActivity;
  private WebFile file;
  private EventAddListener listener;

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
    binding.cancel.setOnClickListener(
        (view) -> {
          mAlertDialog.dismiss();
        });
    binding.list.setAdapter(new EventAdapter(file.getEvents(), mEventListActivity));
    binding.list.setLayoutManager(new LinearLayoutManager(mEventListActivity));
    if (file.getEvents().size() > 0) {
      binding.info.setVisibility(View.GONE);
      binding.addEventSection.setVisibility(View.VISIBLE);
    }
  }
}
