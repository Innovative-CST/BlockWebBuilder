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
    sb.append("\t\t");
    sb.append(CodeReplacer.getReplacer("mainContent"));
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
    mainContent.setRawCode(CodeReplacer.getReplacer("eventMain"));
    mainContent.setEventReplacer("mainContent");

    events.add(mainContent);
    setEvents(events);
  }
}
