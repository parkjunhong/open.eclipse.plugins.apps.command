/*
 * COPYLEFT by 'Open Commons' & Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2014. 1. 29. 오후 4:02:51
 *
 * Author: Park_Jun_Hong(fafanmama_at_naver_dot_com)
 * 
 */
package open.eclipse.plugins.apps.command.pydev;

public class CheckedModule implements Comparable<CheckedModule> {
	private boolean selected;
	private String module;

	public CheckedModule() {

	}

	public CheckedModule(String module, boolean selected) {
		this.module = module;
		this.selected = selected;
	}

	@Override
	public int compareTo(CheckedModule o) {
		int c = selected ^ o.selected ? (selected ? -1 : 1) : 0;

		return c != 0 ? c : module.compareTo(o.module);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckedModule other = (CheckedModule) obj;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		return true;
	}

	public String getModule() {
		return module;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((module == null) ? 0 : module.hashCode());
		result = prime * result + (selected ? 1231 : 1237);
		return result;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
