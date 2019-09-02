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
package open.eclipse.plugins.apps.command.commands;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

public class CopyLocationHandler implements IHandler, ILocationCopy {

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
			String loc = null;
			IResource resource = HandlerUtils.getResource(event);

			if (resource != null) {
				IPath path = resource.getLocation();
				File file = path.toFile();
				loc = file.getAbsolutePath();
			}

//			// Project Explorer, Navigator
//			if (obj instanceof IResource) {
//				IResource res = (IResource) obj;
//				IPath path = res.getLocation();
//				File file = path.toFile();
//
//				loc = file.getAbsolutePath();
//
//				if (sLogger.isInfoEnabled()) {
//					sLogger.info("execute(event): - obj: " + obj + ", location: " + loc);
//				}
//			}
//			// Package Explorer
//			// - org.eclipse.jdt.internal.core.ClassFile
//			// - org.eclipse.jdt.internal.core.CompilationUnit
//			// - org.eclipse.jdt.internal.core.JavaModel
//			// - org.eclipse.jdt.internal.core.JavaProject
//			// - org.eclipse.jdt.internal.core.PackageFragment
//			// - org.eclipse.jdt.internal.core.PackageFragmentRoot
//			else if (obj instanceof Openable) {
//				Openable openable = (Openable) obj;
//				
//				IResource resource = openable.getResource();
//				
//				if( resource != null) { 
//					loc = resource.getLocation().toOSString();
//				}else { 
//					loc = openable.getPath().toOSString();
//				}
//				
//				if (sLogger.isInfoEnabled()) {
//					sLogger.info("execute(event): - obj: " + obj + ", location: " + loc);
//				}
//			} 

			if (loc != null) {
				loc = copy(loc);
				Clipboard cb = new Clipboard(Display.getCurrent());
				TextTransfer transfer = TextTransfer.getInstance();
				cb.setContents(new Object[] { loc }, new Transfer[] { transfer });
				
				MessageDialog.openInformation(null, "Copy Location to Clipboard!", "location:\n\n" + loc);
			}

		} catch (Exception ignored) {
			ignored.printStackTrace();
		}

		return null;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public String copy(String loc) {
		return loc;
	}
}
