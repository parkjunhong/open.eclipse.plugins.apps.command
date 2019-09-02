/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2012. 8. 28. 오전 11:12:36
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 *
 * File  : PreferenceDataManager.java 
 * 
 */

package open.eclipse.plugins.apps.command.preferences;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.IPreferenceStore;

import open.eclipse.plugins.apps.command.PlugInLogger;
import open.eclipse.plugins.apps.command.utils.Utils;

/**
 * @author Park Jun-Hong (jhpark@ymtech.kr)
 * 
 */
public class PreferenceDataManager implements IPreferenceDataManager {

	private static final Logger sLogger = Logger.getLogger(PreferenceDataManager.class);

	private static final Map<Object, IPreferenceDataManager> registry = new HashMap<Object, IPreferenceDataManager>();

	/**
	 * temporarily data.<br>
	 * When a user pushes the apply or ok button, this data is copied to {@link #latestCfg}.<br>
	 * And pushes the cancel button, this data is out done.
	 */
	protected Map<String, Object> concurrentCfg = new ConcurrentSkipListMap<String, Object>();

	/** latest saved data */
	protected Map<String, Object> latestCfg = new ConcurrentSkipListMap<String, Object>();

	/** default data */
	protected Map<String, Object> defaultCfg = new ConcurrentSkipListMap<String, Object>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.re.etri.mesto.ide.preferences.IOpenCLPreferenceDataManager#addCurrentBuildSetting(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void addCurrentBuildSetting(String key, Object value) throws NullPointerException {
		Assert.isNotNull(key);

		if (sLogger.isTraceEnabled()) {
			sLogger.trace("addCurrentBuildSetting(key, value): " + key + "=" + value);
		}

		concurrentCfg.put(key, value.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.re.etri.mesto.ide.preferences.IOpenCLPreferenceDataManager#addDefaultBuildSetting(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void addDefaultBuildSetting(String key, Object value) throws NullPointerException {
		Assert.isNotNull(key);

		if (sLogger.isTraceEnabled()) {
			sLogger.trace("addDefaultBuildSetting(key, value): " + key + "=" + value);
		}

		defaultCfg.put(key, value.toString());
	}

	protected void addLatestBuildSetting(String key, Object value) throws NullPointerException {
		Assert.isNotNull(key);

		if (sLogger.isTraceEnabled()) {
			sLogger.trace("addLatestBuildSetting(key, value): " + key + "=" + value);
		}

		latestCfg.put(key, value.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.re.etri.mesto.ide.preferences.IOpenCLPreferenceDataManager#currentBuildSetting()
	 */
	@Override
	public Map<String, Object> currentBuildSetting() {
		return Collections.unmodifiableMap(concurrentCfg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.re.etri.mesto.ide.preferences.IOpenCLPreferenceDataManager#defaultBuildSetting()
	 */
	@Override
	public Map<String, Object> defaultBuildSetting() {
		return Collections.unmodifiableMap(defaultCfg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.re.etri.mesto.ide.preferences.IOpenCLPreferenceDataManager#getCurrentValue(java.lang.String)
	 */
	@Override
	public String getCurrentValue(String key) throws NullPointerException {
		Assert.isNotNull(key);

		Object obj = concurrentCfg.get(key);

		return obj != null ? (String) obj : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.re.etri.mesto.ide.preferences.IOpenCLPreferenceDataManager#getDefaultValue(java.lang.String)
	 */
	@Override
	public String getDefaultValue(String key) throws NullPointerException {
		Assert.isNotNull(key);

		Object obj = defaultCfg.get(key);

		return obj != null ? (String) obj : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.re.etri.mesto.ide.preferences.IOpenCLPreferenceDataManager#latestBuildSetting()
	 */
	@Override
	public Map<String, Object> latestBuildSetting() {
		return Collections.unmodifiableMap(latestCfg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.re.etri.mesto.ide.preferences.IOpenCLPreferenceDataManager#loadPreferences(org.eclipse.jface.preference.
	 * IPreferenceStore, kr.re.etri.mesto.ide.preferences.OpenCLPreferenceDataManager.IOpenCLPreferenceLoader)
	 */
	@Override
	public void loadPreferences(IPreferenceStore prefStore, IPreferenceLoader prefLoader) {
		prefLoader.loadPreference(prefStore, defaultCfg, concurrentCfg, latestCfg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.re.etri.mesto.ide.preferences.IOpenCLPreferenceDataManager#restoreDefault()
	 */
	@Override
	public void restoreDefault() {
		concurrentCfg.clear();

		concurrentCfg.putAll(defaultCfg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.re.etri.mesto.ide.preferences.IOpenCLPreferenceDataManager#saveCurrent(org.eclipse.jface.preference.
	 * IPreferenceStore)
	 */
	@Override
	public boolean saveCurrent(IPreferenceStore prefStore) {
		Assert.isNotNull(prefStore);

		if (Utils.eqauls(latestCfg, concurrentCfg)) {
			return false;
		}

		// initialize the IPreferenceStore with a default value which is matched to a key
		for (Entry<String, Object> entry : latestCfg.entrySet()) {
			prefStore.setValue(entry.getKey(), prefStore.getDefaultString(entry.getKey()));

			if (sLogger.isTraceEnabled()) {
				sLogger.trace("saveCurrent(prefStore): [RESET latest properteis] key=" + entry.getKey() + ", value="
						+ prefStore.getDefaultString(entry.getKey()));
			}
		}

		latestCfg.clear();

		// copy real-time data to latest saved data.
		latestCfg.putAll(concurrentCfg);

		// saved a real-time data to IPreferenceStore.
		for (Entry<String, Object> entry : concurrentCfg.entrySet()) {
			prefStore.setValue(entry.getKey(), (String) entry.getValue());

			if (sLogger.isTraceEnabled()) {
				sLogger.trace("saveCurrent(prefStore): [ADD updated properties] key=" + entry.getKey() + ", value="
						+ entry.getValue());
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return "defaultSettings: " + defaultCfg.size() + ", latestSettings: " + latestCfg.size()
				+ ", realtimeSetting: " + concurrentCfg.size();
	}

	/**
	 * 
	 * @param key
	 *            a key of a {@link PreferenceDataManager} instance.
	 * @return an instance of {@link IPreferenceDataManager} or if key is <code>null</code>, returns <code>null</code>.
	 * @since 2012. 06. 19.
	 * @author Park Jun-Hong (jhpark_at_ymtech_dot_co_dot_kr)
	 */
	public static final IPreferenceDataManager getInstance(Object key) {
		if (key != null) {
			IPreferenceDataManager dataManager = registry.get(key);

			if (dataManager == null) {
				dataManager = new PreferenceDataManager();
				registry.put(key, dataManager);

				if (sLogger.isTraceEnabled()) {
					sLogger.trace("getInstance(key): registered a new " + PreferenceDataManager.class.getSimpleName()
							+ " | key: " + key + ", value: " + dataManager);
				}
			}

			return dataManager;
		} else {
			PlugInLogger.info("fail to get " + IPreferenceDataManager.class.getName() + ". key: " + key);

			return null;
		}
	}
}