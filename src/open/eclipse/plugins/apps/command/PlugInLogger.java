/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2012. 8. 17. 오후 4:30:57
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 *
 * File  : PlugInLogger.java 
 * 
 */
package open.eclipse.plugins.apps.command;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * @author Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 * 
 */
public class PlugInLogger {
	private static ILog logger;

	private static boolean isReady = false;

	static {
		try {
			logger = CommandHereActivator.getDefault().getLog();

			isReady = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// prevent to create a new instance.
	private PlugInLogger() {
	}

	public static void error(String message) {
		IStatus status = newStatus(IStatus.ERROR, message);
		log(status);
	}

	public static void error(String message, Throwable t) {
		IStatus status = newStatus(IStatus.ERROR, IStatus.OK, message, t);
		log(status);
	}

	public static void error(Throwable t) {
		IStatus status = newStatus(IStatus.ERROR, IStatus.OK, t != null ? t.getMessage() : "", t);
		log(status);
	}

	public static void info(String message) {
		IStatus status = newStatus(IStatus.INFO, message);

		log(status);
	}

	public static void info(String message, Throwable t) {
		IStatus status = newStatus(IStatus.INFO, IStatus.OK, message, t);
		log(status);
	}

	public static void info(Throwable t) {
		IStatus status = newStatus(IStatus.INFO, IStatus.OK, t != null ? t.getMessage() : "", t);
		log(status);
	}

	private static void log(IStatus status) {
		if (isReady) {
			logger.log(status);
		}
	}

	public static IStatus newStatus(int severity, int code, String message, Throwable t) {
		return new Status(severity, CommandHereActivator.PLUGIN_ID, code, message, t);
	}

	public static IStatus newStatus(int severity, String message) {
		return new Status(severity, CommandHereActivator.PLUGIN_ID, message);
	}

	public static IStatus newStatus(int severity, String message, Throwable t) {
		return new Status(severity, CommandHereActivator.PLUGIN_ID, message, t);
	}

	public static void warn(String message) {
		IStatus status = newStatus(IStatus.WARNING, message);
		log(status);
	}

	public static void warn(String message, Throwable t) {
		IStatus status = newStatus(IStatus.WARNING, IStatus.OK, message, t);
		log(status);
	}

	public static void warn(Throwable t) {
		IStatus status = newStatus(IStatus.WARNING, IStatus.OK, t != null ? t.getMessage() : "", t);
		log(status);
	}
}
