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
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.internal.core.Openable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeleteFilesHandler implements IHandler, ILocationCopy {

    protected final Logger sLogger = Logger.getLogger(getClass());

    @Override
    public void addHandlerListener(IHandlerListener handlerListener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    @SuppressWarnings("restriction")
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        try {
            TreeSelection selection = (TreeSelection) HandlerUtil.getCurrentSelection(event);

            Object obj = selection.getFirstElement();

            if (obj == null) {
                return null;
            }

            IResource resource = null;
            String loc = null;

            // Project Explorer, Navigator
            if (obj instanceof IResource) {
                resource = (IResource) obj;
                IPath path = resource.getLocation();
                File file = path.toFile();

                loc = file.getAbsolutePath();

                if (sLogger.isInfoEnabled()) {
                    sLogger.info("execute(event): - obj: " + obj + ", location: " + loc);
                }
            }
            // Package Explorer
            // - org.eclipse.jdt.internal.core.ClassFile
            // - org.eclipse.jdt.internal.core.CompilationUnit
            // - org.eclipse.jdt.internal.core.JavaModel
            // - org.eclipse.jdt.internal.core.JavaProject
            // - org.eclipse.jdt.internal.core.PackageFragment
            // - org.eclipse.jdt.internal.core.PackageFragmentRoot
            else if (obj instanceof Openable) {
                Openable openable = (Openable) obj;

                resource = openable.getResource();

                if (resource != null) {
                    loc = resource.getLocation().toOSString();
                } else {
                    loc = openable.getPath().toOSString();
                }

                if (sLogger.isInfoEnabled()) {
                    sLogger.info("execute(event): - obj: " + obj + ", location: " + loc);
                }
            }

            if (loc != null) {

                MessageBox box = new MessageBox(Display.getCurrent().getActiveShell());
                box.setMessage("message");
                box.setText("text");

                if (box.open() == SWT.OK) {
                    loc = copy(loc);
                    Clipboard cb = new Clipboard(Display.getCurrent());
                    TextTransfer transfer = TextTransfer.getInstance();
                    cb.setContents(new Object[] { loc }, new Transfer[] { transfer });

                    MessageDialog.openInformation(null, "Copy Location to Clipboard!", "location:\n\n" + loc);

                }

                // refresh a selected location infinite-depth
                resource.refreshLocal(Project.DEPTH_INFINITE, new NullProgressMonitor());

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
