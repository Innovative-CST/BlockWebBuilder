package com.dragon.ide.ui.dialogs.eventList;

import android.app.Activity;
import android.graphics.Color;
import com.dragon.ide.R;
import com.dragon.ide.databinding.LayoutSouceCodeDialogBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

public class ShowSourceCodeDialog extends MaterialAlertDialogBuilder {
  public ShowSourceCodeDialog(Activity activity, String code, String language) {
    super(activity);
    LayoutSouceCodeDialogBinding binding =
        LayoutSouceCodeDialogBinding.inflate(activity.getLayoutInflater());
    setTitle(R.string.source_code);
    binding.editor.setEditor(CodeEditorLayout.SoraCodeEditor);
    binding.editor.setCode(code);
    binding.editor.setTheme(Themes.SoraEditorTheme.Light.Quietlight);
    binding.editor.setLanguageMode(language);
    binding
        .editor
        .getSoraCodeEditor()
        .getColorScheme()
        .setColor(EditorColorScheme.CURRENT_LINE, Color.parseColor("#11000000"));
    setView(binding.getRoot());
    setPositiveButton(R.string.cancel, (param1, param2) -> {});
  }
}
