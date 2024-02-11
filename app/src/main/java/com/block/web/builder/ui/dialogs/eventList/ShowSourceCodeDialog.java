package com.block.web.builder.ui.dialogs.eventList;

import android.app.Activity;
import android.widget.Toast;
import com.block.web.builder.R;
import com.block.web.builder.databinding.LayoutSouceCodeDialogBinding;
import com.block.web.builder.utils.ColorUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import editor.tsd.editors.ace.AceEditorColors;
import editor.tsd.editors.sora.lang.textmate.provider.TextMateProvider;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver;

public class ShowSourceCodeDialog extends MaterialAlertDialogBuilder {
  public ShowSourceCodeDialog(
      Activity activity, String code, String language, boolean useSoraEditor) {
    super(activity);
    LayoutSouceCodeDialogBinding binding =
        LayoutSouceCodeDialogBinding.inflate(activity.getLayoutInflater());
    setTitle(R.string.source_code);

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

    binding.editor.setLanguageMode(language);
    binding.editor.setCode(code);
    setView(binding.getRoot());
    setPositiveButton(R.string.cancel, (param1, param2) -> {});
  }
}
