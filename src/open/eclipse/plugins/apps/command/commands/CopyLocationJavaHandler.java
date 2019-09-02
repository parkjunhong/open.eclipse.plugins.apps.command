/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2012. 8. 22. 오후 9:33:09
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 *
 * File  : CopyLocationJavaHandler.java 
 * 
 */
package open.eclipse.plugins.apps.command.commands;

import java.io.File;

public class CopyLocationJavaHandler extends CopyLocationHandler {

	@Override
	public String copy(String loc) {
		return loc != null ? loc.replace(File.separator, File.separator + File.separator) : loc;
	}
}
