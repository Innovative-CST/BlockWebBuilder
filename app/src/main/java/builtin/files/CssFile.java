package builtin.files;

import com.dragon.ide.objects.Event;
import com.dragon.ide.objects.WebFile;
import java.util.ArrayList;

public class CssFile extends WebFile {
  public CssFile() {
    super();
    setFileType(WebFile.SupportedFileType.CSS);

    StringBuilder sb = new StringBuilder();

    sb.append("/* Default styles for all devices */\n\n");
    sb.append("%%%% DragonIDE defaultStyle %%%%\n\n");

    sb.append("@media only screen and (max-width: 767px) {\n");
    sb.append("\t/* CSS code for mobile devices */\n\n");
    sb.append("\t%%%% DragonIDE mobileDeviceCSSEventCode %%%%\n");
    sb.append("}\n\n");

    sb.append("@media only screen and (min-width: 768px) and (max-width: 991px) {\n");
    sb.append("\t/* CSS code for tablet devices */\n\n");
    sb.append("\t%%%% DragonIDE tabletDeviceCSSEventCode %%%%\n");
    sb.append("}\n\n");

    sb.append("@media only screen and (min-width: 992px) and (max-width: 1199px) {\n");
    sb.append("\t/* CSS code for laptop devices */\n\n");
    sb.append("\t%%%% DragonIDE laptopDeviceCSSEventCode %%%%\n");
    sb.append("}\n\n");

    sb.append("@media only screen and (min-width: 1200px) {\n");
    sb.append("\t/* CSS code for computer devices */\n\n");
    sb.append("\t%%%% DragonIDE computerDeviceCSSEventCode %%%%\n");
    sb.append("}");

    setRawCode(new String(sb.toString()));

    ArrayList<Event> events = new ArrayList<Event>();

    Event defaultStyle = new Event();
    defaultStyle.setName("DefaultStyle");
    defaultStyle.setDesc("Default style of page(Applies to all)");
    defaultStyle.setReplacer("eventCode");
    defaultStyle.setRawCode("%%%% DragonIDE eventCode %%%%");
    defaultStyle.setEventReplacer("defaultStyle");

    events.add(defaultStyle);

    Event mobileDevice = new Event();
    mobileDevice.setName("MobileStyle");
    mobileDevice.setDesc("Stylesheet for mobile devices(Overrides main)");
    mobileDevice.setReplacer("eventCode");
    mobileDevice.setRawCode("%%%% DragonIDE eventCode %%%%");
    mobileDevice.setEventReplacer("mobileDeviceCSSEventCode");

    events.add(mobileDevice);

    Event tabletDevice = new Event();
    tabletDevice.setName("TabletStyle");
    tabletDevice.setDesc("Stylesheet for tablet devices(Overrides main)");
    tabletDevice.setReplacer("eventCode");
    tabletDevice.setRawCode("%%%% DragonIDE eventCode %%%%");
    tabletDevice.setEventReplacer("tabletDeviceCSSEventCode");

    events.add(tabletDevice);

    setEvents(events);
  }
}
