package open.eclipse.plugins.apps.command.commands;

import open.commons.log.LogUtils;
import open.commons.utils.ArrayUtils;
import open.eclipse.plugins.apps.command.CommandManager;
import open.eclipse.plugins.apps.command.CommandManager.CommandInfo;

public class ExplorerHandler extends AbstractCommandHandler {

	protected boolean executeCommand(final CommandInfo commandInfo, final String workDir) {
		if (sLogger.isTraceEnabled()) {
			sLogger.trace("executeCommand(commandInfo, workDir):" //
					+ commandInfo + ", workDir: " + workDir //
					+ LogUtils.callStack(0, 10) //
			);
		}
		if (commandInfo != null) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Runtime runtime = Runtime.getRuntime();
						Process proc = runtime.exec(ArrayUtils.add(CommandManager.toCommandArray(commandInfo.command),
								workDir));
						proc.getInputStream().close();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		return true;
	}
}
