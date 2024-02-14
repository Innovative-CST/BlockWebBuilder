/*
 *  This file is part of BlockWeb Builder.
 *
 *  BlockWeb Builder is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BlockWeb Builder is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with BlockWeb Builder.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.block.web.builder.ui.dialogs.eventList;

import android.app.Activity;
import android.widget.Toast;
import com.block.web.builder.R;
import com.block.web.builder.databinding.LayoutSourceCodeDialogBinding;
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
    LayoutSourceCodeDialogBinding binding =
        LayoutSourceCodeDialogBinding.inflate(activity.getLayoutInflater());
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
