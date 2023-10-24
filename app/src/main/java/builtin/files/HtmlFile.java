package builtin.files;

import com.dragon.ide.objects.WebFile;

public class HtmlFile extends WebFile {
  public HtmlFile() {
    super();
    setFileType(WebFile.SupportedFileType.HTML);
    StringBuilder sb = new StringBuilder();
    sb.append("<!DOCTYPE html>\n");
    sb.append("<html>");
    sb.append("\n");
    sb.append("\t<head>\n");
    sb.append("\t\t<meta charset=\"UTF-8\">\n");
    sb.append("\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
    sb.append("\t\t<title></title>\n");
    sb.append("\t</head>");
    sb.append("\n");
    sb.append("\t<body>");
    sb.append("\n");
    sb.append("\t\t%%%% DragonIDE mainContent %%%%");
    sb.append("\n");
    sb.append("\t</body>");
    sb.append("\n");
    sb.append("</html>\n");

    setRawCode(new String(sb.toString()));
  }
}
