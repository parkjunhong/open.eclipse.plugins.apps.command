/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2012. 8. 28. 오전 11:11:33
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 *
 * File  : IPreferenceDataManager.java 
 * 
 */

package open.eclipse.plugins.apps.command.preferences;

import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;

/**
 * 
 * @author Park Jun-Hong (jhpark_at_ymtech_dot_co_dot_kr)
 * 
 */
public interface IPreferenceDataManager {

	/**
	 * 
	 * @param key
	 * @param value
	 * 
	 * @since 2012. 06. 01.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 * 
	 * @see Map#put(Object, Object)
	 */
	public abstract void addCurrentBuildSetting(String key, Object value) throws NullPointerException;

	/**
	 * 
	 * @param key
	 * @param value
	 * 
	 * @since 2012. 06. 01.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 * 
	 * @see Map#put(Object, Object)
	 */
	public abstract void addDefaultBuildSetting(String key, Object value) throws NullPointerException;

	/**
	 * Returns the current data which a user updates.
	 * 
	 * @return
	 * @since 2012. 06. 01.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public abstract Map<String, Object> currentBuildSetting();

	/**
	 * Returns the default data.
	 * 
	 * @return
	 * @since 2012. 06. 01.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public abstract Map<String, Object> defaultBuildSetting();

	/**
	 * Return the data which key is equal to a parameter.
	 * 
	 * @param key
	 * @return
	 * @throws NullPointerException
	 * @since 2012. 06. 05.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public abstract String getCurrentValue(String key) throws NullPointerException;

	/**
	 * Return the default which key is equal to a parameter.
	 * 
	 * @param key
	 * @return
	 * @throws NullPointerException
	 * @since 2012. 06. 05.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public abstract String getDefaultValue(String key) throws NullPointerException;

	/**
	 * Returns the data which is saved latest.
	 * 
	 * @return
	 * @since 2012. 06. 01.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public abstract Map<String, Object> latestBuildSetting();

	/**
	 * 
	 * @param prefStore
	 * @param prefLoader
	 * @since 2012. 06. 01.
	 * @author Park Jun-Hong (jhpark_at_ymtech_dot_co_dot_kr)
	 */
	public abstract void loadPreferences(IPreferenceStore prefStore, IPreferenceLoader prefLoader);

	/**
	 * 
	 * 
	 * @since 2012. 06. 01.
	 * @author Park Jun-Hong (jhpark_at_ymtech_dot_co_dot_kr)
	 */
	public abstract void restoreDefault();

	/**
	 * @param prefStore
	 * 
	 * @return If {@link #latestCfg} is not equal to {@link #concurrentCfg}, returns <code>true</code>. Otherwise
	 *         returns <code>false</code>.
	 * 
	 * @since 2012. 06. 01.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public abstract boolean saveCurrent(IPreferenceStore prefStore);

}
