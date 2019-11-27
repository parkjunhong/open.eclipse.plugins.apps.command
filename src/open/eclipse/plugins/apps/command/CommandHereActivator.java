package open.eclipse.plugins.apps.command;

import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.xml.DOMConfigurator;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class CommandHereActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "open.eclipse.plugins.apps.command"; //$NON-NLS-1$

	// The shared instance
	private static CommandHereActivator plugin;

	static {
		try {
			DOMConfigurator.configure(CommandHereActivator.class.getResource("/resources/log4j.xml")); //$NON-NLS-1$

		} catch (FactoryConfigurationError ignored) {
			ignored.printStackTrace();
		}
	}

	/**
	 * The constructor
	 */
	public CommandHereActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static CommandHereActivator getDefault() {
		return plugin;
	}

}
