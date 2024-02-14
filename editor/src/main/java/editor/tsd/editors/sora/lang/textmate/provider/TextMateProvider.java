/*
 *  This file is part of Android Code Editor.
 *
 *  Android Code Editor is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Android Code Editor is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with Android Code Editor.  If not, see <https://www.gnu.org/licenses/>.
 */

package editor.tsd.editors.sora.lang.textmate.provider;

import android.code.editor.common.utils.FileUtils;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry;
import java.util.Map;

public class TextMateProvider {
  public static void loadGrammars() {
    GrammarRegistry.getInstance().loadGrammars("Editor/SoraEditor/languages.json");
  }

  public static String getLanguageScope(Context context, String fileExt) {
    Map<String, String> scopes;
    var type = new TypeToken<Map<String, String>>() {}.getType();
    scopes =
        new Gson()
            .fromJson(
                FileUtils.readFileFromAssets(context.getAssets(), "Editor/SoraEditor/language_scopes.json"),
                type);
    return scopes.get(fileExt);
  }
}
