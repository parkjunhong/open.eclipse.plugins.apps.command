package open.eclipse.plugins.apps.command.commands;

import java.io.File;
import java.io.InputStream;

import open.commons.log.LogUtils;
import open.eclipse.plugins.apps.command.CommandManager;
import open.eclipse.plugins.apps.command.CommandManager.CommandInfo;

public class TerminalHandler extends AbstractCommandHandler {

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
						ProcessBuilder builder = new ProcessBuilder(CommandManager.toCommandArray(commandInfo.command));
						builder.directory(new File(workDir));
						Process proc = builder.start();

						InputStream is = proc.getInputStream();

						while (is.read() != commandInfo.exitValue)
							;

						is.close();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		return true;
	}
}
