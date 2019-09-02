/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2012. 7. 17. 오후 4:55:52
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 *
 * File  : CommandHandler.java 
 * 
 */
package open.eclipse.plugins.apps.command.commands;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.commands.IParameter;
import org.eclipse.core.commands.common.NotDefinedException;

import open.eclipse.plugins.apps.command.CommandManager;
import open.eclipse.plugins.apps.command.CommandManager.CommandInfo;

public abstract class AbstractCommandHandler implements IHandler {

	protected final Logger sLogger = Logger.getLogger(getClass());

	private static final String PROP_OS_NAME = "os.name";

	public AbstractCommandHandler() {
		super();
	}

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		if (sLogger.isTraceEnabled()) {
			sLogger.trace("addHandlerListener(handlerListener): "
			// + LogUtils.callStack(0, 5) //
			);
		}
	}

	@Override
	public void dispose() {
		if (sLogger.isTraceEnabled()) {
			sLogger.trace("dispose(): " //
			// + LogUtils.callStack(0, 5) //
			);
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (sLogger.isTraceEnabled()) {
			sLogger.trace("execute(event): " //
			// + LogUtils.callStack(0, 5) //
			);
		}

		String loc = HandlerUtils.getLocation(event);

		if (loc == null) {
			return null;
		}

		String osname = System.getProperty(PROP_OS_NAME);
		if (osname != null && osname.trim().length() > 0) {

			try {
				IParameter param = event.getCommand().getParameter("open.eclipse.plugins.apps.command.handlerType");

				// param.getValues()
				if (param != null) {
					executeCommand(CommandManager.getCommandInfo(param.getName() + "." + osname), loc);
				}
			} catch (NotDefinedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}

	protected abstract boolean executeCommand(final CommandInfo commandInfo, final String workDir);

	@Override
	public boolean isEnabled() {
		if (sLogger.isTraceEnabled()) {
			sLogger.trace("isEnabled(): " //
			// + LogUtils.callStack(0, 5)
			);
		}
		return true;
	}

	@Override
	public boolean isHandled() {
		if (sLogger.isTraceEnabled()) {
			sLogger.trace("isHandled(): " //
			// + LogUtils.callStack(0, 5) //
			);
		}
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		if (sLogger.isTraceEnabled()) {
			sLogger.trace("removeHandlerListener(handlerListener): " //
			// + LogUtils.callStack(0, 5) //
			);
		}
	}
}