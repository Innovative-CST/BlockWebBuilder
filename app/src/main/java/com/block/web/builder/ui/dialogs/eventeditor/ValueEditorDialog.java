package com.block.web.builder.ui.dialogs.eventeditor;

import android.app.Activity;
import android.widget.Toast;
import com.block.web.builder.R;
import com.block.web.builder.databinding.LayoutSouceCodeDialogBinding;
import com.block.web.builder.listeners.ValueListener;
import com.block.web.builder.utils.ColorUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import editor.tsd.editors.ace.AceEditorColors;
import editor.tsd.editors.sora.lang.textmate.provider.TextMateProvider;
import editor.tsd.tools.EditorListeners;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver;

public class ValueEditorDialog extends MaterialAlertDialogBuilder {
  public ValueEditorDialog(
      Activity activity,
      String value,
      String language,
      String theme,
      boolean useSoraEditor,
      ValueListener listener) {
    super(activity);
    setTitle(R.string.value_editor);
    LayoutSouceCodeDialogBinding binding =
        LayoutSouceCodeDialogBinding.inflate(activity.getLayoutInflater());
    if (useSoraEditor) {
      FileProviderRegistry.getInstance()
          .addFileProvider(new AssetsFileResolver(activity.getAssets()));
      try {
        TextMateProvider.loadGrammars();
      } catch (Exception e) {
        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
      }

      binding.editor.setEditor(CodeEditorLayout.SoraCodeEditor);
      binding.editor.setTheme(Themes.SoraEditorTheme.Light.Default);
    } else {
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
      binding.editor.setTheme(Themes.AceEditorTheme.Light.Default);
    }

    binding.editor.setCode(value);
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
