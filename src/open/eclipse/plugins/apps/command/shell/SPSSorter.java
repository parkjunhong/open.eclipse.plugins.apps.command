package open.eclipse.plugins.apps.command.shell;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

import open.eclipse.plugins.apps.command.shell.SPSContentProvider.PropertyElement;

class SPSSorter extends ViewerSorter {

    private int target = 0;
    private boolean direction;

    public SPSSorter(int target, boolean direction) {
        this.target = target;
        this.direction = direction;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {

        if (e1 == null || e2 == null || !e1.getClass().equals(e2.getClass())) {
            throw new SWTException(SWT.ERROR_INVALID_ARGUMENT, "e1=" + e1 + ", e2=" + e2);
        }

        PropertyElement elem1 = (PropertyElement) e1;
        PropertyElement elem2 = (PropertyElement) e2;

        switch (target) {
            case SPSTableLabelProvider.INDEX_KEY:
                elem1.setCompareOrder(true);
                elem2.setCompareOrder(true);
                break;
            case SPSTableLabelProvider.INDEX_VALUE:
                elem1.setCompareOrder(false);
                elem2.setCompareOrder(false);
                break;
            default:
                throw new SWTException(SWT.ERROR_INVALID_ARGUMENT, "Sort.target=" + target);
        }

        int r = elem1.compareTo(elem2);

        return direction ? r : -r;
    }
}