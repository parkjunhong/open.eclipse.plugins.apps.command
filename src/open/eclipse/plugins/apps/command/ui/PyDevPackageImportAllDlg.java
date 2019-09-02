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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import open.commons.collection.FIFOMap;
import open.eclipse.plugins.apps.command.pydev.CheckedModule;

public class PyDevPackageImportAllDlg extends TitleAreaDialog {

	private static final String SELECT_ALL = "Select All";

	private FIFOMap<String, CheckedModule> modules = new FIFOMap<String, CheckedModule>();
	private Composite moduleContainer;
	private Button btnCheckedAllTrigger;
	private Label lblSelectAllTrigger;

	private SelectionListener moduleSelectionListener = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent e) {
			selectTrigger(isSelectAllModule());
		}
	};

	private void selectTrigger(boolean selection) {
		btnCheckedAllTrigger.setSelection(selection);
	}

	private boolean isSelectAllModule() {

		for (CheckedModuleUI ui : getModuleUI()) {
			if (!ui.isSelected()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 * @param modules
	 */
	public PyDevPackageImportAllDlg(Shell parentShell, Set<CheckedModule> modules) {
		super(parentShell);

		if (modules != null) {
			for (CheckedModule module : modules) {
				this.modules.put(module.getModule(), module);
			}
		}
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 * 
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);

		boolean selectAll = true;
		CheckedModuleUI moduleUi = null;
		for (CheckedModule module : this.modules.values()) {
			moduleUi = new CheckedModuleUI(moduleContainer, SWT.NONE);
			moduleUi.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			moduleUi.setModuleName(module.getModule());
			moduleUi.select(module.isSelected());
			moduleUi.setSelectionListener(moduleSelectionListener);

			selectAll &= module.isSelected();
		}

		((ScrolledComposite) moduleContainer.getParent()).setMinSize(moduleContainer.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));

		selectTrigger(selectAll);

		return control;
	}

	private void selectAllModule(boolean selection) {
		for (CheckedModuleUI ui : getModuleUI()) {
			ui.select(selection);
		}
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Select Import Files");
		setMessage("Select python files to import in following files.");

		Composite area = (Composite) super.createDialogArea(parent);
		area.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(area, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnCheckedAllTrigger = new Button(composite, SWT.CHECK);
		btnCheckedAllTrigger.setBounds(0, 0, 109, 22);
		btnCheckedAllTrigger.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				selectAllModule(btnCheckedAllTrigger.getSelection());
			}
		});

		lblSelectAllTrigger = new Label(composite, SWT.NONE);
		lblSelectAllTrigger.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblSelectAllTrigger.setText(SELECT_ALL);

		ScrolledComposite scrolledComposite = new ScrolledComposite(area, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		moduleContainer = new Composite(scrolledComposite, SWT.NONE);
		moduleContainer.setLayout(new GridLayout(1, false));
		scrolledComposite.setContent(moduleContainer);
		scrolledComposite.setMinSize(moduleContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		return area;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(576, 504);
	}

	public Set<CheckedModule> getModules() {
		Set<CheckedModule> result = new ConcurrentSkipListSet<CheckedModule>();

		for (CheckedModule module : this.modules.values()) {
			if (module.isSelected()) {
				result.add(module);
			}
		}

		return result;
	}

	private List<CheckedModuleUI> getModuleUI() {
		List<CheckedModuleUI> modules = new ArrayList<CheckedModuleUI>();

		for (Control control : moduleContainer.getChildren()) {
			if (control instanceof CheckedModuleUI) {
				modules.add((CheckedModuleUI) control);
			}
		}

		return modules;
	}

	@Override
	protected void okPressed() {

		CheckedModule cm = null;

		for (CheckedModuleUI cmUI : getModuleUI()) {
			cm = modules.get(cmUI.getModuleName());
			cm.setSelected(cmUI.isSelected());
		}

		super.okPressed();
	}
}
