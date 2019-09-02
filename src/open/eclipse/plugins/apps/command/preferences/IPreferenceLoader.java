/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2012. 8. 28. 오전 11:12:07
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 *
 * File  : IPreferenceLoader.java 
 * 
 */

package open.eclipse.plugins.apps.command.preferences;

import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * This class provided a mechanism for loading default values in an {@link IPreferenceStore} instance.
 * 
 * @author Park Jun-Hong (jhpark_at_ymtech_dot_co_dot_kr)
 * 
 */
public interface IPreferenceLoader {
	/**
	 * 
	 * @param prefStore
	 *            an {@link IPreferenceStore} object.
	 * @param defaultCfg
	 *            a map for default values.
	 * @param concurrentCfg
	 *            a map for concurrent values.
	 * @param latestCfg
	 *            a map for latest values are saved.
	 * @since 2012. 06. 19.
	 * @author Park Jun-Hong (jhpark_at_ymtech_dot_co_dot_kr)
	 */
	public void loadPreference(IPreferenceStore prefStore, Map<String, Object> defaultCfg,
			Map<String, Object> concurrentCfg, Map<String, Object> latestCfg);
}