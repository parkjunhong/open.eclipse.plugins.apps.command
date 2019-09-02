/*
 * COPYLEFT by 'Open Commons' & Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2014. 1. 29. 오후 4:01:51
 *
 * Author: Park_Jun_Hong(fafanmama_at_naver_dot_com)
 * 
 */

package open.eclipse.plugins.apps.command.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class CheckedModuleUI extends Composite {

	private Text textModule;
	private Button btnSelected;
	private SelectionListener listener;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CheckedModuleUI(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 1;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);

		btnSelected = new Button(this, SWT.CHECK | SWT.CENTER);
		btnSelected.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1));

		textModule = new Text(this, SWT.BORDER);
		textModule.setEditable(false);
		textModule.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		parent.pack();
		
	}
	
	public void setSelectionListener(SelectionListener l) {
		if( this.listener != null) {
			btnSelected.removeSelectionListener(this.listener);
		}
		
		this.listener = l;
		
		btnSelected.addSelectionListener(this.listener);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}


	/**
	 * 모듈 네임을 반환한다.
	 * 
	 * 
	 * @return
	 */
	public String getModuleName() {
		return textModule.getText();
	}
	
	/**
	 * 선택 여부를 반환한다.
	 * 
	 * @return
	 */
	public boolean isSelected() {
		return btnSelected.getSelection();
	}
	
	public void select(boolean selected) {
		btnSelected.setSelection(selected);
	}

	public void setModuleName(String module) {
		textModule.setText(module);
	}
}
