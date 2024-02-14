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

package builtin.files;

import com.block.web.builder.core.Event;
import com.block.web.builder.core.WebFile;
import com.block.web.builder.core.utils.CodeReplacer;
import java.util.ArrayList;

public class CssFile extends WebFile {
  public CssFile() {
    super();
    setFileType(WebFile.SupportedFileType.CSS);

    StringBuilder sb = new StringBuilder();

    sb.append("/* Default styles for all devices */\n\n");
    sb.append(CodeReplacer.getReplacer("defaultStyle"));
    sb.append("\n\n");

    sb.append("@media only screen and (max-width: 767px) {\n");
    sb.append("\t/* CSS code for mobile devices */\n\n");
    sb.append("\t");
    sb.append(CodeReplacer.getReplacer("mobileDeviceCSSEventCode"));
    sb.append("\n");
    sb.append("}\n\n");

    sb.append("@media only screen and (min-width: 768px) and (max-width: 991px) {\n");
    sb.append("\t/* CSS code for tablet devices */\n\n");
    sb.append("\t");
    sb.append(CodeReplacer.getReplacer("tabletDeviceCSSEventCode"));
    sb.append("\n");
    sb.append("}\n\n");

    sb.append("@media only screen and (min-width: 992px) and (max-width: 1199px) {\n");
    sb.append("\t/* CSS code for laptop devices */\n\n");
    sb.append("\t");
    sb.append(CodeReplacer.getReplacer("laptopDeviceCSSEventCode"));
    sb.append("\n");
    sb.append("}\n\n");

    sb.append("@media only screen and (min-width: 1200px) {\n");
    sb.append("\t/* CSS code for computer devices */\n\n");
    sb.append("\t");
    sb.append(CodeReplacer.getReplacer("computerDeviceCSSEventCode"));
    sb.append("\n");
    sb.append("}");

    setRawCode(new String(sb.toString()));

    ArrayList<Event> events = new ArrayList<Event>();

    Event defaultStyle = new Event();
    defaultStyle.setName("DefaultStyle");
    defaultStyle.setDesc("Default style of page(Applies to all)");
    defaultStyle.setReplacer("eventCode");
    defaultStyle.setRawCode(CodeReplacer.getReplacer("eventCode"));
    defaultStyle.setEventReplacer("defaultStyle");

    events.add(defaultStyle);

    Event mobileDevice = new Event();
    mobileDevice.setName("MobileStyle");
    mobileDevice.setDesc("Stylesheet for mobile devices(Overrides main)");
    mobileDevice.setReplacer("eventCode");
    mobileDevice.setRawCode(CodeReplacer.getReplacer("eventCode"));
    mobileDevice.setEventReplacer("mobileDeviceCSSEventCode");

    events.add(mobileDevice);

    Event tabletDevice = new Event();
    tabletDevice.setName("TabletStyle");
    tabletDevice.setDesc("Stylesheet for tablet devices(Overrides main)");
    tabletDevice.setReplacer("eventCode");
    tabletDevice.setRawCode(CodeReplacer.getReplacer("eventCode"));
    tabletDevice.setEventReplacer("tabletDeviceCSSEventCode");

    events.add(tabletDevice);

    setEvents(events);
  }
}
