package open.eclipse.plugins.apps.command.shell;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import open.eclipse.plugins.apps.command.shell.SPSContentProvider.PropertyElement;

class SPSTableLabelProvider extends LabelProvider implements ITableLabelProvider {

    public static final int INDEX_KEY = 0;
    public static final int INDEX_VALUE = 1;

    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    public String getColumnText(Object element, int columnIndex) {

        if (element instanceof PropertyElement) {
            PropertyElement elem = (PropertyElement) element;
            switch (columnIndex) {
                case INDEX_KEY:
                    return elem.key;
                case INDEX_VALUE:
                    return elem.value;
                default:
                    return "";
            }
        }

        return "";
    }
}