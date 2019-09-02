/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2012. 7. 31. 오후 4:27:16
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 *
 * File  : CopyLocation.java 
 * 
 */
package open.eclipse.plugins.apps.command.pydev;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.python.pydev.navigator.elements.PythonFile;

import open.commons.collection.FIFOSet;
import open.eclipse.plugins.apps.command.commands.HandlerUtils;
import open.eclipse.plugins.apps.command.ui.PyDevPackageImportAllDlg;

public class PyDevDefineImportAllHandler implements IHandler {

	private static final String __all__ = "__all__";

	private static final String __INIT__FILE = "__init__.py";
	private static final String __init__ = "__init__";

	private static final String PYTHON_EXTENSION = ".py";

	protected final Logger sLogger = Logger.getLogger(getClass());

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		try {

			Object obj = HandlerUtils.getSelectedElement(event);

			// 선택된 개체가 PyDevFile 인지 확인 - [Park Jun-Hong]: 2014. 1. 29.
			if (!PythonFile.class.isAssignableFrom(obj.getClass())) {
				return null;
			}

			PythonFile pdfile = (PythonFile) obj;
			IFile resource = pdfile.getActualObject();

			if (resource != null) {
				IPath path = resource.getLocation();
				File file = path.toFile();

				if (file.isFile() && __INIT__FILE.equals(file.getName())) {
					refinePythonImportAll(resource.getParent(), file);
				}
			}

		} catch (Exception ignored) {
			ignored.printStackTrace();
		}

		return null;
	}

	private void fillWithModules(Set<CheckedModule> moduleContainer, boolean selected, Collection<String> modules) {
		CheckedModule cm = null;
		for (String module : modules) {
			if( module.endsWith(PYTHON_EXTENSION)) {
				module = module.substring(0, module.length() - PYTHON_EXTENSION.length());
			}
			
			cm = new CheckedModule(module, selected);			
			if( !moduleContainer.contains(cm) ){
				moduleContainer.add(cm);
			}
		}
	}

	private void refinePythonImportAll(IContainer parent, File file) {

		try {
			System.out.println(parent);
			System.out.println(file);

			FIFOSet<CheckedModule> modules = new FIFOSet<CheckedModule>();

			// #1. __init__.py 파일에 기술된 모듈 내용 읽어 오기 - [Park Jun-Hong]: 2014. 1. 29.
			Properties prop = new Properties();
			prop.load(new FileInputStream(file));

			String moduleString = prop.getProperty(__all__, null);
			
			final List<String> reservedModules = new ArrayList<String>();
			if (moduleString != null) {
				String[] reserved = moduleString.trim().replaceAll("[\\[\\]]", "").split("[,]");
				for(String m : reserved) {
					if( !m.trim().isEmpty()) {
						reservedModules.add(m.trim().replaceAll("\"", "'").replaceAll("'", ""));
					}
				}

				fillWithModules(modules, true, reservedModules);
			}

			// #2. 패키지에 있는 모듈 내용 읽어오기 - [Park Jun-Hong]: 2014. 1. 29.
			File pkg = file.getParentFile();
			String[] moduleFiles = pkg.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					boolean accepted = false;
					
					if( name.endsWith(PYTHON_EXTENSION)) {
						name = name.substring(0, name.length() - PYTHON_EXTENSION.length());
						accepted = !reservedModules.contains(name) && !__init__.equals(name);
					}
					
					return accepted;
				}
			});
			
			Set<String> remainedModules = new ConcurrentSkipListSet<String>();
			remainedModules.addAll(Arrays.asList(moduleFiles));

			fillWithModules(modules, false, remainedModules);

			PyDevPackageImportAllDlg dlg = new PyDevPackageImportAllDlg(Display.getCurrent().getActiveShell(), modules);

			if (dlg.open() == IDialogConstants.OK_ID) {
				// 3. 대화창에서 설정한 모듈 정보 추가하기 - [Park Jun-Hong]: 2014. 1. 29.
				Set<CheckedModule> refinedModules = dlg.getModules();
				Iterator<CheckedModule> itr = refinedModules.iterator();

				if (itr.hasNext()) {
					StringBuffer moduleList = new StringBuffer();
					moduleList.append('[');

					moduleList.append('\'');
					moduleList.append(itr.next().getModule());
					moduleList.append('\'');

					while (itr.hasNext()) {
						moduleList.append(", '");
						moduleList.append(itr.next().getModule());
						moduleList.append('\'');
					}

					moduleList.append(']');

					prop.setProperty(__all__, moduleList.toString());

				} else {
					prop.remove(__all__);
				}
			}

			// 4. 갱신된 모듈 이름 저장하기 - [Park Jun-Hong]: 2014. 1. 29.
			FileWriter fileWriter = new FileWriter(file);

			StringBuffer writeString = new StringBuffer();
			for (Entry<Object, Object> entry : prop.entrySet()) {
				writeString.append(entry.getKey().toString());
				writeString.append(" = ");
				writeString.append(entry.getValue().toString());
				writeString.append('\n');

				fileWriter.write(writeString.toString());
				fileWriter.flush();

				writeString.setLength(0);
			}

			fileWriter.close();

			// 5. 디렉토리 갱신 - [Park Jun-Hong]: 2014. 1. 29.
			parent.refreshLocal(1, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#removeHandlerListener(org.eclipse.core.commands.IHandlerListener)
	 */
	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}
}
