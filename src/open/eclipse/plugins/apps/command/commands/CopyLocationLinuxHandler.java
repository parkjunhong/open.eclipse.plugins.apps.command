/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2016. 10. 13. 오후 9:33:09
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 *
 * File  : CopyLocationLinuxHandler.java 
 * 
 */
package open.eclipse.plugins.apps.command.commands;

import java.io.File;

import open.eclipse.plugins.apps.command.CommandManager;

/**
 * 
 * 
 * @since 2016. 10. 13.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class CopyLocationLinuxHandler extends CopyLocationHandler {

    private static final String LINUX_PATH_SEPARATOR = CommandManager.getCommandInfo("path.separator.linux").command;

    @Override
    public String copy(String loc) {
        if (loc == null) {
            return null;
        }

        // Drive 구분 문자열 제거
        String[] s = loc.split(":");

        // 나누어지면 Drive 제거하고 경로값으로 변경
        if (s.length > 1) {
            loc = s[1];
        }

        return loc.replace(File.separator, LINUX_PATH_SEPARATOR);
    }
}
