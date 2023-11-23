package com.dragon.ide.ui.dialogs.eventeditor;

import android.app.Activity;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutSouceCodeDialogBinding;
import com.dragon.ide.listeners.ValueListener;
import com.dragon.ide.utils.ColorUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import editor.tsd.editors.ace.AceEditorColors;
import editor.tsd.tools.EditorListeners;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;

public class ValueEditorDialog extends MaterialAlertDialogBuilder {
  public ValueEditorDialog(
      Activity activity, String value, String language, ValueListener listener) {
    super(activity);
    setTitle(R.string.value_editor);
    LayoutSouceCodeDialogBinding binding =
        LayoutSouceCodeDialogBinding.inflate(activity.getLayoutInflater());
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
    binding.editor.setCode(value);
    binding.editor.setTheme(Themes.AceEditorTheme.Light.Default);
    binding.editor.setLanguageMode(language);
    setView(binding.getRoot());
    setPositiveButton(
        R.string.done,
        (param1, param2) -> {
          binding.editor.getCode(
              new EditorListeners() {

                @Override
                public void onReceviedCode(String code) {
                  listener.onSubmitted(code);
                }
              });
        });
    setNegativeButton(R.string.cancel, (param1, param2) -> {});
  }
}
