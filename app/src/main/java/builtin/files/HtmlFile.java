package builtin.files;

import com.dragon.ide.objects.Event;
import com.dragon.ide.objects.WebFile;
import java.util.ArrayList;

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
    sb.append("\t\tDevKumar DragonIDE mainContent DevKumar");
    sb.append("\n");
    sb.append("\t</body>");
    sb.append("\n");
    sb.append("</html>\n");

    setRawCode(new String(sb.toString()));

    ArrayList<Event> events = new ArrayList<Event>();

    Event mainContent = new Event();
    mainContent.setName("MainPageView");
    mainContent.setDesc("Main contents of page");
    mainContent.setReplacer("eventMain");
    mainContent.setRawCode("DevKumar DragonIDE eventMain DevKumar");
    mainContent.setEventReplacer("mainContent");

    events.add(mainContent);
    setEvents(events);
  }
}
