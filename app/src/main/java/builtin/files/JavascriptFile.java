package builtin.files;

import com.dragon.ide.objects.Event;
import com.dragon.ide.objects.WebFile;
import java.util.ArrayList;

public class JavascriptFile extends WebFile {
  public JavascriptFile() {
    super();
    setFileType(WebFile.SupportedFileType.JS);

    StringBuilder sb = new StringBuilder();

    sb.append("DevKumar DragonIDE mainJavaScriptEvent DevKumar");
    sb.append("\n");

    setRawCode(new String(sb.toString()));

    ArrayList<Event> events = new ArrayList<Event>();

    Event mainEntry = new Event();
    mainEntry.setName("MainEntry");
    mainEntry.setDesc("Main Entry of javascript file");
    mainEntry.setReplacer("eventCode");
    mainEntry.setRawCode("DevKumar DragonIDE eventCode DevKumar");
    mainEntry.setEventReplacer("mainJavaScriptEvent");

    events.add(mainEntry);
    setEvents(events);
  }
}
