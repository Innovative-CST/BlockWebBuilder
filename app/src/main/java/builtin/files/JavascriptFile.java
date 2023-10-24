package builtin.files;

import com.dragon.ide.objects.WebFile;

public class JavascriptFile extends WebFile {
  public JavascriptFile() {
    super();
    setFileType(WebFile.SupportedFileType.JS);

    StringBuilder sb = new StringBuilder();

    sb.append("%%%% DragonIDE mainJavaScriptEvent %%%%");
    sb.append("\n");

    setRawCode(new String(sb.toString()));
  }
}
