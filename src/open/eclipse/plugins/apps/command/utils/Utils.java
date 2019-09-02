package open.eclipse.plugins.apps.command.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import open.commons.Result;
import open.eclipse.plugins.apps.command.PlugInLogger;

public class Utils {

    private static final Logger sLogger = Logger.getLogger(Utils.class);

    /* can't create an instance. */
    private Utils() {
    }

    /**
     * 여러 개의 {@link Collection} 개체를 하나로 통합한다.
     * 
     * @param cols
     * @return
     */
    public static <T> Collection<T> addAll(Collection<T>... cols) {
        Collection<T> mergedCol = new Vector<T>();
        if (cols != null) {
            for (int i = 0; i < cols.length; i++) {
                if (cols[i] != null) {
                    mergedCol.addAll(cols[i]);
                }
            }
        }

        return mergedCol;
    }

    public static void asserts(Control control) {
        if (control == null || control.isDisposed()) {
            throw new SWTException(SWT.ERROR_DEVICE_DISPOSED);
        }
    }

    /**
     * Convert a class to urlpath which doesn't contain the class name.
     * 
     * @param clazz
     * @return
     */
    public static String classpathToUrlpath(Class<?> clazz) {
        String classpath = clazz.getName();
        String classname = clazz.getSimpleName();

        return classpathToUrlpath(classpath.replace(classname, ""));
    }

    /**
     * only path of the specified class. not contains the class name.
     * 
     * @param classpath
     * @return
     */
    public static String classpathToUrlpath(String classpath) {
        return "/" + classpath.replace(".", "/");
    }

    /**
     * Return a url-string of a properties resource which is in a location which the specified class exists. The resource's name is equal to the
     * specified class'name.
     * 
     * @param clazz
     * @return
     */
    public static String classToUrlResource(Class<?> clazz) {
        return classToUrlResource(clazz, clazz.getSimpleName(), "properties");
    }

    /**
     * Return a url-string of a resource which is in a location which the specified class exists.
     * 
     * @param clazz
     * @param resourceName
     *            a name of a resource
     * @param resourceExt
     *            a extension of a resource
     * @return
     */
    public static String classToUrlResource(Class<?> clazz, String resourceName, String resourceExt) {
        String classpath = clazz.getName();
        String classname = clazz.getSimpleName();

        return classpathToUrlpath(classpath.replace(classname, "")) + resourceName + "." + resourceExt;
    }

    public static void clearText(Text... texts) {
        for (Text text : texts) {
            try {
                asserts(text);

                text.setText("");
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }

        }
    }

    /**
     * 주어진 {@link Combo}안에 찾고자 하는 문자열(<code>value</code>이 존재하는지 여부를 반환한다.<br>
     * 
     * @param combo
     *            {@link Combo} 객체
     * @param value
     *            찾고자 하는 문자열
     * @param ignoreCase
     *            대/소문자 비교 여부
     * @return
     * @since 2012. 05. 30.
     * @author Park Jun-Hong (jhpark@ymtech.kr)
     */
    public static final boolean contains(Combo combo, String value, boolean ignoreCase) {
        try {
            asserts(combo);

            if (ignoreCase) {
                for (String item : combo.getItems()) {
                    if (item.equalsIgnoreCase(value)) {
                        return true;
                    }
                }

                return false;
            } else {
                for (String item : combo.getItems()) {
                    if (item.equals(value)) {
                        return true;
                    }
                }
                return false;
            }

        } catch (Exception ignored) {
            ignored.printStackTrace();

            PlugInLogger.error(ignored.getMessage(), ignored);

            return false;
        }

    }

    /**
     * 
     * @param combo
     *            a {@link Combo} object.
     * @param itemValue
     *            a string-value of a item which is contained in a {@link Combo}.
     * @param ignoreCase
     *            whether check CASE or not.
     * @return
     * @since 2012. 06. 19.
     * @author Park Jun-Hong (jhpark_at_ymtech_dot_co_dot_kr)
     */
    public static final Result<String> containsAndGetValue(Combo combo, String itemValue, boolean ignoreCase) {
        try {
            asserts(combo);

            if (ignoreCase) {
                for (String item : combo.getItems()) {
                    if (item.equalsIgnoreCase(itemValue)) {
                        return new Result<String>(item).andTrue();
                    }
                }

                return new Result<String>(null).andFalse();
            } else {
                for (String item : combo.getItems()) {
                    if (item.equals(itemValue)) {
                        return new Result<String>(item).andTrue();
                    }
                }
                return new Result<String>(null).andFalse();
            }

        } catch (Exception ignored) {
            ignored.printStackTrace();

            sLogger.error(ignored.getMessage(), ignored);
            return null;
        }
    }

    /**
     * @param combo
     *            a {@link Combo} object
     * @param dataKey
     *            a key of data in {@link Combo}.<br>
     *            see {@link Combo#setData(String, Object)}.
     * @param dataValue
     *            a string value wanted.
     * @param ignoreCase
     *            whether check CASE or not.
     * @return
     * @since 2012. 05. 30.
     * @author Park Jun-Hong (jhpark@ymtech.kr)
     */
    public static final Result<String> containsAndGetValue(Combo combo, String dataKey, String dataValue,
            boolean ignoreCase) {
        try {
            asserts(combo);

            // Get a data
            String data = (String) combo.getData(dataKey);

            // Check whether equals to. (contains null-equal)
            if (data == dataValue) {
                return new Result<String>(data).andTrue();
            }

            if (data == null || dataValue == null) {
                return new Result<String>(data).andFalse();
            }

            if (ignoreCase) {
                return new Result<String>(data, data.equalsIgnoreCase(dataValue));

            } else {
                return new Result<String>(data, data.equals(dataValue));
            }

        } catch (Exception ignored) {
            ignored.printStackTrace();

            PlugInLogger.error(ignored.getMessage(), ignored);

            return null;
        }
    }

    /**
     * 2개의 맵이 서로 동일한지 여부를 반환한다.
     * 
     * @param map1
     * @param map2
     * @return
     * @since 2012. 06. 04.
     * @author Park Jun-Hong (jhpark@ymtech.kr)
     */
    public static boolean eqauls(Map<String, Object> map1, Map<String, Object> map2) {

        if (equals(map1, map2)) {
            return true;
        }

        try {
            Assert.isNotNull(map1);
            Assert.isNotNull(map2);
        } catch (Exception ignored) {
            ignored.printStackTrace();

            PlugInLogger.error(ignored.getMessage(), ignored);

            return false;
        }

        int m1Length = map1.size();
        int m2Length = map2.size();

        if (m1Length != m2Length) {
            return false;
        }

        Map<String, Object> m1Clone = null;
        Map<String, Object> m2Clone = null;

        synchronized (map1) {
            m1Clone = Collections.unmodifiableMap(map1);
        }

        synchronized (map2) {
            m2Clone = Collections.unmodifiableMap(map2);
        }

        String key = null;
        Object value = null;
        for (Entry<String, Object> m1Entry : m1Clone.entrySet()) {
            key = m1Entry.getKey();
            value = m1Entry.getValue();

            if (!equals(value, m2Clone.get(key))) {
                return false;
            }
        }

        return true;
    }

    public static boolean equals(Object o1, Object o2) {
        // contains case of o1 and o2 are all null.
        if (o1 == o2) {
            return true;
        }

        if (o1 != null && o2 != null) {
            return o1.equals(o2);
        } else {
            return false;
        }
    }

    /**
     * 선택한 시스템 경로를 반환한다.
     * 
     * @param parent
     * @return
     * @since 2012. 05. 30.
     * @author Park Jun-Hong (jhpark@ymtech.kr)
     */
    public static final String getPath(Shell parent) {

        try {
            DirectoryDialog dlg = new DirectoryDialog(parent);

            return dlg.open();
        } catch (Exception ignored) {

            sLogger.error(ignored.getMessage(), ignored);

            return null;
        }
    }

    /**
     * 선택한 시스템 경로를 반환한다.
     * 
     * @param parent
     * @return
     * @since 2012. 05. 30.
     * @author Park Jun-Hong (jhpark@ymtech.kr)
     */
    public static final String getPath(Shell parent, String defaultPath) {

        try {
            DirectoryDialog dlg = new DirectoryDialog(parent);
            dlg.setFilterPath(defaultPath);

            return dlg.open();
        } catch (Exception ignored) {

            sLogger.error(ignored.getMessage(), ignored);

            return null;
        }
    }

    /**
     * 주어진 {@link Combo} 에서 선택된 값을 반환한다.<br>
     * 없는 경우 <code>null</code>을 반환한다.
     * 
     * @param combo
     * @return
     * @since 2012. 05. 30.
     * @author Park Jun-Hong (jhpark@ymtech.kr)
     */
    public static final String getSelectedItem(Combo combo) {

        try {
            asserts(combo);
            if (combo.getItemCount() > 0) {
                int sIndex = combo.getSelectionIndex();
                if (sIndex > -1) {
                    return combo.getItem(sIndex);
                }
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();

            PlugInLogger.error(ignored.getMessage(), ignored);
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(classpathToUrlpath(Utils.class));
    }

    /**
     * 프로퍼티에서 key에 해당하는 값이 존재하는 경우, 해당값을 대상 해쉬테이블에 추가한다.<br>
     * 존재하지 않거나 null 인 경우, 기본값(<code>defaultValue</code>)을 추가한다.
     * 
     * @param properties
     *            source map
     * @param key
     *            key of value
     * @param defaultValue
     *            <code>null</code>인 경우 설정될 기본 값
     * @param tmap
     *            target map
     * @since 2012. 06. 01.
     * @author Park Jun-Hong (jhpark@ymtech.kr)
     */
    public static void put(Properties properties, String key, String defaultValue, Map<String, String> tmap) {
        try {
            Assert.isNotNull(tmap);
            Assert.isNotNull(properties);
            Assert.isNotNull(key);

            String value = (String) properties.get(key);

            tmap.put(key, value != null ? value : defaultValue);

        } catch (Exception ignored) {
            ignored.printStackTrace();

            PlugInLogger.error(ignored.getMessage(), ignored);
        }
    }

    /**
     * 주어진 컬렉션에 새로운 데이타를 추가한다.<br>
     * 
     * @param col
     * @param elem
     */
    public static <T> void putElement(Collection<T> col, T elem) {
        if (col != null && elem != null) {
            col.add(elem);
        }
    }

    /**
     * 주어진 {@link Combo} 객체의 아이템을 <code>items</code>으로 변경한다.
     * 
     * @param combo
     * @param items
     * @return
     * @since 2012. 05. 30.
     * @author Park Jun-Hong (jhpark@ymtech.kr)
     */
    public static boolean resetComboItemsAndSelect(Combo combo, String[] items, int defaultIndex) {
        try {
            asserts(combo);
            Assert.isNotNull(items);

            // reset 'items'.
            combo.setItems(items);

            combo.select(defaultIndex);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            PlugInLogger.error(e.getMessage(), e);

            return false;
        }
    }

    /**
     * 주어진 {@link Combo} 객체의 아이템을 <code>items</code>으로 변경한다.
     * 
     * @param combo
     * @param items
     * @param defaultValue
     * @return
     * @since 2012. 05. 30.
     * @author Park Jun-Hong (jhpark@ymtech.kr)
     */
    public static boolean resetComboItemsAndSelect(Combo combo, String[] items, String defaultValue) {
        try {
            asserts(combo);
            Assert.isNotNull(items);
            Assert.isNotNull(defaultValue);

            // reset 'items'.
            combo.setItems(items);

            int index = -1;

            for (int i = 0; index == -1 && i < items.length; i++) {
                if (defaultValue.equals(items[i])) {
                    index = i;
                }
            }

            // select the specific item.
            combo.select(index);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            PlugInLogger.error(e.getMessage(), e);

            return false;
        }
    }

    /**
     * 주어진 배열을 정렬해서 반환한다.<br>
     * 정렬시 오류나 예외가 발생하는 경우 주어진 배열을 그대로 반환한다.
     * 
     * @param elems
     * @return
     * @since 2012. 05. 30.
     * @author Park Jun-Hong (jhpark@ymtech.kr)
     */
    @SuppressWarnings("unchecked")
    public static final <T extends Comparable<T>> T[] toOrderedArray(T... elems) {
        try {
            NavigableSet<T> ordering = new ConcurrentSkipListSet<T>();

            for (T elem : elems) {
                ordering.add(elem);
            }

            return ordering.toArray((T[]) Array.newInstance(elems.getClass().getComponentType(), elems.length));
        } catch (NegativeArraySizeException ignored) {
            ignored.printStackTrace();

            PlugInLogger.error(ignored.getMessage(), ignored);

            return elems;
        }
    }

    /**
     * 
     * @param string
     * @return
     * @throws NullPointerException
     *             a parameter is <code>null</code>.
     */
    public static final boolean copyToClipboard(String string) throws NullPointerException {
        try {
            Clipboard cb = new Clipboard(Display.getCurrent());
            TextTransfer transfer = TextTransfer.getInstance();
            cb.setContents(new Object[] { string }, new Transfer[] { transfer });

            MessageDialog.openInformation(null, "Copy to Clipboard!", string);

            return true;
        } catch (Exception e) {
            PlugInLogger.error("fail to copy data to clipboard. data: " + string, e);

            return false;
        }
    }
}