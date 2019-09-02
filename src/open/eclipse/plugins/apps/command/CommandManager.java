/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.terminal".
 *
 * Date  : 2012. 7. 12. 오후 8:17:01
 *
 * Author: Park Jun-Hong (jhpark_at_ymtech_dot_co_dot_kr)
 *
 * File  : ConsoleCommandManager.java 
 * 
 */
package open.eclipse.plugins.apps.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import open.eclipse.plugins.apps.command.utils.Utils;

/**
 * 
 * @author Park Jun-Hong (jhpark_at_ymtech_dot_co_dot_kr)
 * 
 */
public class CommandManager {

	private static final String KEY_DELIMITER_ARGS = "args.delimiter";

	private static final String KEY_DELIMITER_EXITVALUE = "exit.delimiter";
	private static String DELIMITER_ARGS;

	private static String DELIMITER_EXITVALUE;
	private static final Map<String, String> COMMANDS = new HashMap<String, String>();

	static {
		try {
			Properties prop = new Properties();
			prop.load(CommandManager.class.getResourceAsStream(Utils.classToUrlResource(CommandManager.class)));

			for (Entry<Object, Object> entry : prop.entrySet()) {
				COMMANDS.put(((String) entry.getKey()).replace("command.", "").replace("$", " "),
						(String) entry.getValue());
			}

			DELIMITER_ARGS = COMMANDS.get(KEY_DELIMITER_ARGS);
			DELIMITER_EXITVALUE = COMMANDS.get(KEY_DELIMITER_EXITVALUE);

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private static String getCommand0(String osname) {
		String command = COMMANDS.get(osname);

		if (command == null) {
			String candidated = null;
			for (Entry<String, String> entry : COMMANDS.entrySet()) {
				candidated = entry.getKey();
				if (osname.equalsIgnoreCase(candidated) || osname.contains(candidated)) {
					command = entry.getValue();

					break;
				}
			}
		}

		return command;
	}

	/**
	 * Returns the console name of the specific OS(Operating System).
	 * 
	 * @param osname
	 * @return
	 */
	public static CommandInfo getCommandInfo(String osname) {
		if (osname != null) {
			String info = getCommand0(osname);
			if (info != null) {
				String[] infoArray = info.split("[" + DELIMITER_EXITVALUE + "]");
				try {
					if (infoArray.length == 1) {
						return new CommandInfo(infoArray[0], Integer.MIN_VALUE);
					} else if (infoArray.length == 2) {
						return new CommandInfo(infoArray[0], Integer.parseInt(infoArray[1]));
					} else {
						throw new IndexOutOfBoundsException();
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();

					System.out.println("ConsoleCommandManager.getCommandInfo(): check 'exit value'. current="
							+ infoArray[1]);
				}
			}
			return null;
		} else {
			return null;
		}
	}

	/**
	 * Returns a String array that contains a command and arguments.
	 * 
	 * @param command
	 *            a {@link String} array, or null if <b><code>a parameter is null</code></b>.
	 * @return
	 */
	public static String[] toCommandArray(String command) {
		if (command != null) {
			return command.split("[" + DELIMITER_ARGS + "]");
		} else {
			return null;
		}
	}

	public static class CommandInfo {
		public final String command;
		public final int exitValue;

		public CommandInfo(String c, int v) {
			command = c;
			exitValue = v;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "command: " + command + ", exitValue; " + exitValue;
		}
	}
}
