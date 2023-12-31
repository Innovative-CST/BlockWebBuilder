package builtin.files;

import com.dragon.ide.objects.Event;
import com.dragon.ide.objects.WebFile;
import com.dragon.ide.utils.CodeReplacer;
import java.util.ArrayList;

public class JavascriptFile extends WebFile {
  public JavascriptFile() {
    super();
    setFileType(WebFile.SupportedFileType.JS);

    StringBuilder sb = new StringBuilder();

    sb.append(CodeReplacer.getReplacer("mainJavaScriptEvent"));
    sb.append("\n");

    setRawCode(new String(sb.toString()));

    ArrayList<Event> events = new ArrayList<Event>();

    Event mainEntry = new Event();
    mainEntry.setName("MainEntry");
    mainEntry.setDesc("Main Entry of javascript file");
    mainEntry.setReplacer("eventCode");
    mainEntry.setRawCode(CodeReplacer.getReplacer("eventCode"));
    mainEntry.setEventReplacer("mainJavaScriptEvent");

    events.add(mainEntry);
    setEvents(events);
  }
}
