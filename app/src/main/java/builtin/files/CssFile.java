/*
 * This file is part of BlockWeb Builder [https://github.com/TS-Code-Editor/BlockWebBuilder].
 *
 * License Agreement
 * This software is licensed under the terms and conditions outlined below. By accessing, copying, modifying, or using this software in any way, you agree to abide by these terms.
 *
 * 1. **  Copy and Modification Restrictions  **
 *    - You are not permitted to copy or modify the source code of this software without the permission of the owner, which may be granted publicly on GitHub Discussions or on Discord.
 *    - If permission is granted by the owner, you may copy the software under the terms specified in this license agreement.
 *    - You are not allowed to permit others to copy the source code that you were allowed to copy by the owner.
 *    - Modified or copied code must not be further copied.
 * 2. **  Contributor Attribution  **
 *    - You must attribute the contributors by creating a visible list within the application, showing who originally wrote the source code.
 *    - If you copy or modify this software under owner permission, you must provide links to the profiles of all contributors who contributed to this software.
 * 3. **  Modification Documentation  **
 *    - All modifications made to the software must be documented and listed.
 *    - the owner may incorporate the modifications made by you to enhance this software.
 * 4. **  Consistent Licensing  **
 *    - All copied or modified files must contain the same license text at the top of the files.
 * 5. **  Permission Reversal  **
 *    - If you are granted permission by the owner to copy this software, it can be revoked by the owner at any time. You will be notified at least one week in advance of any such reversal.
 *    - In case of Permission Reversal, if you fail to acknowledge the notification sent by us, it will not be our responsibility.
 * 6. **  License Updates  **
 *    - The license may be updated at any time. Users are required to accept and comply with any changes to the license.
 *    - In such circumstances, you will be given 7 days to ensure that your software complies with the updated license.
 *    - We will not notify you about license changes; you need to monitor the GitHub repository yourself (You can enable notifications or watch the repository to stay informed about such changes).
 * By using this software, you acknowledge and agree to the terms and conditions outlined in this license agreement. If you do not agree with these terms, you are not permitted to use, copy, modify, or distribute this software.
 *
 * Copyright © 2024 Dev Kumar
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
