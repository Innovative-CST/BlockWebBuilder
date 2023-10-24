package com.dragon.ide.ui.dialogs.eventList;

import android.app.Activity;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutSouceCodeDialogBinding;
import com.dragon.ide.objects.WebFile;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import editor.tsd.tools.Themes;

public class ShowSourceCodeDialog extends MaterialAlertDialogBuilder {
  public ShowSourceCodeDialog(Activity activity, String code, String language) {
    super(activity);
    LayoutSouceCodeDialogBinding binding =
        LayoutSouceCodeDialogBinding.inflate(activity.getLayoutInflater());
    setTitle(R.string.source_code);
    setView(binding.getRoot());
    binding.editor.setCode(code);
    binding.editor.setTheme(Themes.SoraEditorTheme.Light.Quietlight);
    binding.editor.setLanguageMode(language);
    setPositiveButton(R.string.cancel, (param1, param2) -> {});
  }
}
