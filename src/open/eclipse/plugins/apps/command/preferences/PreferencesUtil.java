/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2012. 8. 28. 오전 11:15:36
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 *
 * File  : PreferencesUtil.java 
 * 
 */

package open.eclipse.plugins.apps.command.preferences;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Text;

import open.eclipse.plugins.apps.command.PlugInLogger;
import open.eclipse.plugins.apps.command.utils.Utils;

/**
 * @author Park Jun-Hong (jhpark@ymtech.kr)
 * 
 */
public class PreferencesUtil {

	/**
	 * {@link IPreferenceStore} 객체에서 주어진 키(<code>key</code>)에 해당하는 값을 반환한다.<br>
	 * 
	 * @param prefStore
	 *            {@link IPreferenceStore} 객체
	 * @param name
	 *            Preferece's key
	 * @return 주어진 키(<code>key</code>)에 해당하는 값. 오류나 예외상황이 발생하면 <code>null</code>.
	 * @since 2012. 05. 30.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public static String getString(IPreferenceStore prefStore, String name) {

		try {
			Assert.isNotNull(prefStore);
			Assert.isNotNull(name);

			return prefStore.getString(name);
		} catch (Exception ignored) {
			ignored.printStackTrace();

			PlugInLogger.error(ignored.getMessage(), ignored);

			return "";
		}
	}

	/**
	 * 주어진 <code>key</code>에 해당하는 값을 기본값으로 변경한다.
	 * 
	 * @param prefStore
	 * @param name
	 * @since 2012. 05. 31.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public static void reset(IPreferenceStore prefStore, String name) {
		try {
			Assert.isNotNull(prefStore);
			Assert.isNotNull(name);

			prefStore.setValue(name, prefStore.getDefaultString(name));

		} catch (Exception ignored) {
			ignored.printStackTrace();

			PlugInLogger.error(ignored.getMessage(), ignored);
		}
	}

	/**
	 * {@link IPreferenceStore}가 {@link IPersistentPreferenceStore}인 경우에 저장을 하고 결과를 반환한다.
	 * 
	 * @param prefStore
	 * @param ignoreException
	 * @return
	 * @since 2012. 05. 30.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public static boolean savePreference(IPreferenceStore prefStore, boolean ignoreException) {
		if (prefStore instanceof IPersistentPreferenceStore) {
			try {
				((IPersistentPreferenceStore) prefStore).save();

				return true;
			} catch (IOException e) {
				if (ignoreException) {
					e.printStackTrace();

					PlugInLogger.error(e.getMessage(), e);

					return false;
				} else {
					RuntimeException ex = new RuntimeException("fail to save 'perference'", e);

					PlugInLogger.error(ex.getMessage(), ex);

					throw ex;
				}
			}
		} else {
			return false;
		}
	}

	public static void setDefaultString(IPreferenceStore prefStore, IPreferenceDataManager dataManager,
			Entry<String, String> data) {
		try {
			Assert.isNotNull(prefStore);
			Assert.isNotNull(dataManager);
			Assert.isNotNull(data);

			String key = data.getKey();
			String value = data.getValue();

			prefStore.setDefault(key, value);
			dataManager.addDefaultBuildSetting(key, value);

		} catch (Exception ignored) {
			ignored.printStackTrace();

			PlugInLogger.error(ignored.getMessage(), ignored);
		}
	}

	public static void setDefaultString(IPreferenceStore prefStore, IPreferenceDataManager dataManager,
			Map<String, String> data) {
		try {
			Assert.isNotNull(prefStore);
			Assert.isNotNull(dataManager);
			Assert.isNotNull(data);

			String key = null;
			String value = null;
			for (Entry<String, String> entry : data.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();

				prefStore.setDefault(key, value);
				dataManager.addDefaultBuildSetting(key, value);
			}

		} catch (Exception ignored) {
			ignored.printStackTrace();

			PlugInLogger.error(ignored.getMessage(), ignored);
		}
	}

	public static void setDefaultString(IPreferenceStore prefStore, IPreferenceDataManager dataManager, String name,
			String value) {

		try {
			Assert.isNotNull(prefStore);
			Assert.isNotNull(dataManager);
			Assert.isNotNull(name);

			prefStore.setDefault(name, value);
			dataManager.addDefaultBuildSetting(name, value);
		} catch (Exception ignored) {
			ignored.printStackTrace();

			PlugInLogger.error(ignored.getMessage(), ignored);
		}
	}

	/**
	 * 
	 * @param prefStore
	 * @param dataManager
	 * @param dataArray
	 *            Two dimension String-array. The length of each element is 2 and each element must not be null.
	 * @since 2012. 07. 01.
	 * @author Park Jun-Hong (jhpark_at_ymtech_dot_co_dot_kr)
	 */
	public static void setDefaultString(IPreferenceStore prefStore, IPreferenceDataManager dataManager,
			String[][] dataArray) {
		try {
			Assert.isNotNull(prefStore);
			Assert.isNotNull(dataManager);
			Assert.isNotNull(dataArray);

			for (String[] data : dataArray) {
				prefStore.setDefault(data[0], data[1]);
				dataManager.addDefaultBuildSetting(data[0], data[1]);
			}

		} catch (Exception ignored) {
			ignored.printStackTrace();

			PlugInLogger.error(ignored.getMessage(), ignored);
		}
	}

	/**
	 * 
	 * @param prefStore
	 * @param name
	 * @param text
	 * @since 2012. 05. 31.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public static void setDefaultString(IPreferenceStore prefStore, String name, Text text) {
		try {
			Utils.asserts(text);
			Assert.isNotNull(prefStore);
			Assert.isNotNull(name);

			prefStore.setDefault(name, text.getText().trim());

		} catch (Exception ignored) {
			ignored.printStackTrace();

			PlugInLogger.error(ignored.getMessage(), ignored);
		}
	}

	public static void setString(IPreferenceStore prefStore, IPreferenceDataManager dataManager, String name,
			String value) {

		try {
			Assert.isNotNull(prefStore);
			Assert.isNotNull(dataManager);
			Assert.isNotNull(name);

			prefStore.setDefault(name, value);
			dataManager.addDefaultBuildSetting(name, value);
		} catch (Exception ignored) {
			ignored.printStackTrace();

			PlugInLogger.error(ignored.getMessage(), ignored);
		}
	}

	/**
	 * 
	 * @param prefStore
	 * @param name
	 * @param text
	 * @since 2012. 05. 31.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public static void setString(IPreferenceStore prefStore, IPreferenceDataManager dataManager, String name, Text text) {
		try {
			Utils.asserts(text);
			Assert.isNotNull(prefStore);
			Assert.isNotNull(name);

			prefStore.setValue(name, text.getText().trim());
			dataManager.addCurrentBuildSetting(name, text.getText().trim());

		} catch (Exception ignored) {
			ignored.printStackTrace();

			PlugInLogger.error(ignored.getMessage(), ignored);
		}

	}

	/**
	 * 
	 * @param prefStore
	 * @param name
	 * @param text
	 * @since 2012. 05. 31.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public static void setString(IPreferenceStore prefStore, String name, String value) {
		try {
			Assert.isNotNull(prefStore);
			Assert.isNotNull(name);
			Assert.isNotNull(value);

			prefStore.setValue(name, value);

		} catch (Exception ignored) {
			ignored.printStackTrace();

			PlugInLogger.error(ignored.getMessage(), ignored);
		}
	}

	/**
	 * 
	 * @param prefStore
	 * @param name
	 * @param text
	 * @since 2012. 05. 31.
	 * @author Park Jun-Hong (jhpark@ymtech.kr)
	 */
	public static void setString(IPreferenceStore prefStore, String name, Text text) {
		try {
			Utils.asserts(text);
			Assert.isNotNull(prefStore);
			Assert.isNotNull(name);

			prefStore.setValue(name, text.getText().trim());

		} catch (Exception ignored) {
			ignored.printStackTrace();

			PlugInLogger.error(ignored.getMessage(), ignored);
		}

	}

}
