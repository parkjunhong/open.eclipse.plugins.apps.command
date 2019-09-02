package open.eclipse.plugins.apps.command.shell;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import open.commons.utils.ComparableUtils;

class SPSContentProvider implements IStructuredContentProvider {

    public Object[] getElements(Object inputElement) {
        if (inputElement instanceof Map) {
            Map props = (Map) inputElement;

            Set entrySet = props.entrySet();

            Object[] elements = new Object[entrySet.size()];

            int i = 0;
            for (Object object : entrySet) {
                Entry entry = (Entry) object;
                elements[i++] = new PropertyElement((String) entry.getKey(), (String) entry.getValue());
            }

            return elements;
        }

        return new Object[0];
    }

    public void dispose() {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    static class PropertyElement implements Comparable<PropertyElement> {
        /** The Class Simple Name */
        private static final String CSN = PropertyElement.class.getSimpleName();

        public final String key;
        public final String value;

        /** true: key -> value, false: value -> key **/
        private boolean compareOrder = true;

        public PropertyElement(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Set the compare-order and return the latest order;
         * 
         * @param order
         *            new order
         * @return latest order;
         */
        public boolean setCompareOrder(boolean order) {
            boolean latestOrder = this.compareOrder;
            this.compareOrder = order;
            return latestOrder;
        }

        public String[] valuesToCompare() {
            if (compareOrder) {
                return new String[] { key, value };
            } else {
                return new String[] { value, key };
            }
        }

        @Override
        public int compareTo(PropertyElement o) {
            return ComparableUtils.comparable(valuesToCompare(), o.valuesToCompare());
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            PropertyElement other = (PropertyElement) obj;
            if (key == null) {
                if (other.key != null)
                    return false;
            } else if (!key.equals(other.key))
                return false;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return CSN + "[key=" + key + ", value=" + value + ", compareOrder=" + compareOrder + "]";
        }
    }
}