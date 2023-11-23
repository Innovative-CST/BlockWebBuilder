package com.dragon.ide.ui.dialogs.eventList;

import android.app.Activity;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutSouceCodeDialogBinding;
import com.dragon.ide.utils.ColorUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import editor.tsd.editors.ace.AceEditorColors;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;

public class ShowSourceCodeDialog extends MaterialAlertDialogBuilder {
  public ShowSourceCodeDialog(Activity activity, String code, String language) {
    super(activity);
    LayoutSouceCodeDialogBinding binding =
        LayoutSouceCodeDialogBinding.inflate(activity.getLayoutInflater());
    setTitle(R.string.source_code);
    AceEditorColors aceEditorColors = new AceEditorColors();
    aceEditorColors.setEditorBackground("#00000000");
    aceEditorColors.setActiveLineColor("#0000002d");
    aceEditorColors.setGutterActiveLineColor("#0000002d");
    aceEditorColors.setGutterBackground("#00000000");
    aceEditorColors.setGutterTextColor(
        String.format(
            "#%06X",
            (0xFFFFFF
                & ColorUtils.getColor(
                    activity, com.google.android.material.R.attr.colorOnSurfaceVariant))));
    aceEditorColors.apply(activity);
    binding.editor.setEditor(CodeEditorLayout.AceCodeEditor);
    binding.editor.setTheme(Themes.AceEditorTheme.Light.Chrome);
    binding.editor.setLanguageMode(language);
    binding.editor.setCode(code);
    setView(binding.getRoot());
    setPositiveButton(R.string.cancel, (param1, param2) -> {});
  }
}
