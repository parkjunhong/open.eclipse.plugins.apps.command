/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2012. 10. 7. 오후 5:17:00
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 * 
 */
package open.eclipse.plugins.apps.command.shell;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

public class STSColumnLabelProvider extends ColumnLabelProvider {

    private boolean direction;

    public STSColumnLabelProvider() {
        this(true);
    }

    public STSColumnLabelProvider(boolean direction) {
        this.direction = direction;
    }

    @Override
    public Image getImage(Object element) {
        if (direction) {
            return SWTResourceManager.getImage("/icons/dir_ascending.gif");
        } else {
            return SWTResourceManager.getImage("/icons/dir_descending.gif");
        }
    }

    @Override
    public String getText(Object element) {
        return super.getText(element);
    }
}
