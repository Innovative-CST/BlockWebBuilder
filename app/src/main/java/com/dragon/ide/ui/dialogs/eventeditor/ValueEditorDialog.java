package com.dragon.ide.ui.dialogs.eventeditor;

import android.app.Activity;
import android.graphics.Color;
import android.widget.LinearLayout;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutSouceCodeDialogBinding;
import com.dragon.ide.listeners.ValueListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import editor.tsd.tools.EditorListeners;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

public class ValueEditorDialog extends MaterialAlertDialogBuilder {
  public ValueEditorDialog(
      Activity activity, String value, String language, ValueListener listener) {
    super(activity);
    setTitle(R.string.value_editor);
    LayoutSouceCodeDialogBinding binding =
        LayoutSouceCodeDialogBinding.inflate(activity.getLayoutInflater());
    binding.editor.setEditor(CodeEditorLayout.SoraCodeEditor);
    binding.editor.setCode(value);
    binding.editor.setTheme(Themes.SoraEditorTheme.Light.Quietlight);
    binding.editor.setLanguageMode(language);
    binding.editor.getSoraEditor().getCodeEditor().setEditable(true);
    binding
        .editor
        .getSoraCodeEditor()
        .getColorScheme()
        .setColor(EditorColorScheme.CURRENT_LINE, Color.parseColor("#11000000"));
    if (binding.editor.getSoraEditor().getCodeEditor().getLayoutParams() != null) {
      ((LinearLayout.LayoutParams) binding.editor.getLayoutParams()).width =
          LinearLayout.LayoutParams.MATCH_PARENT;
    }
    binding.editor.setFocusable(true);
    binding.editor.setClickable(true);
    binding.editor.setFocusableInTouchMode(true);
    binding.editor.getSoraEditor().getCodeEditor().setFocusable(true);
    binding.editor.getSoraEditor().getCodeEditor().setClickable(true);
    binding.editor.getSoraEditor().getCodeEditor().setFocusableInTouchMode(true);
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
