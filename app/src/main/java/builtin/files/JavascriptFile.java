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
