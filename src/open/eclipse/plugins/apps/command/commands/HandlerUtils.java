/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2012. 10. 6. 오후 11:12:14
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 * 
 */
package open.eclipse.plugins.apps.command.commands;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.internal.core.ClassFile;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.core.Openable;
import org.eclipse.jdt.internal.core.SourceRefElement;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import open.eclipse.plugins.apps.command.PlugInLogger;

@SuppressWarnings("restriction")
public class HandlerUtils {

    private static final Logger sLogger = Logger.getLogger(HandlerUtils.class);

    public static String getLocation(ExecutionEvent event) {
        String loc = null;
        Class<?> objClass = null;

        try {
            // Object obj = getSelectedElement(event);
            //
            // if (obj == null) {
            // return null;
            // }
            //
            // objClass = obj.getClass();

            IResource resource = getResource(event);

            if (resource != null) {
                loc = getLocation(resource);
            }
        } catch (Exception e) {
            PlugInLogger.error("Oops... Unresolved Resource..... resouce: " + objClass);
        }

        return loc;
    }

    private static String getLocation(IResource resource) {
        String location = null;

        IPath path = resource.getLocation();
        File file = path.toFile();
        // project, folder
        if (file.isDirectory()) {
            location = file.getAbsolutePath();
        }
        // file
        else {
            location = file.getParent();
        }

        if (sLogger.isInfoEnabled()) {
            sLogger.info("execute(event): - resource: " + resource + ", location: " + location);
        }

        return location;
    }

    public static IResource getResource(Object obj) {

        IResource resource = null;
        Class<?> objClass = null;

        try {

            if (obj == null) {
                return null;
            }

            objClass = obj.getClass();

            // Project Explorer, Navigator
            if (IResource.class.isAssignableFrom(objClass)) {

                resource = (IResource) obj;
            }
            // Package Explorer
            // - org.eclipse.jdt.internal.core.ClassFile
            // - org.eclipse.jdt.internal.core.CompilationUnit
            // - org.eclipse.jdt.internal.core.JavaModel
            // - org.eclipse.jdt.internal.core.JavaProject
            // - org.eclipse.jdt.internal.core.PackageFragment
            // - org.eclipse.jdt.internal.core.PackageFragmentRoot
            else if (Openable.class.isAssignableFrom(objClass)) {
                Openable openable = (Openable) obj;

                if (obj instanceof ClassFile || obj instanceof CompilationUnit) {
                    resource = openable.getParent().getResource();
                } else {
                    resource = openable.getResource();
                }

            } else if (SourceRefElement.class.isAssignableFrom(objClass)) {
                sLogger.info("Oops... None of doing.... sorry...");
            } else {

                try {
                    // start - just for 'detecting methods of new CHALLENGERs : 2014. 1. 17. 오후 1:47:29
                    Method[] methods = objClass.getMethods();

                    for (Method m_ : methods) {
                        PlugInLogger.info(m_.toGenericString());
                    }
                    // end - just for 'detecting methods of new CHALLENGERs : 2014. 1. 17. 오후 1:47:29

                    PlugInLogger.warn("Oops... Unresolved Resource..... resouce: " + objClass.getName());

                } catch (Exception e) {
                    PlugInLogger.error("Oops... Unresolved Resource..... resouce: " + objClass.getName());
                }
            }
        } catch (Exception e) {
            PlugInLogger.error("Oops... Unresolved Resource..... resouce: " + objClass);
        }

        return resource;
    }

    public static IResource getResource(ExecutionEvent event) {

        IResource resource = null;
        Class<?> objClass = null;

        try {
            Object obj = getSelectedElement(event);

            if (obj == null) {
                return null;
            }

            objClass = obj.getClass();

            // Project Explorer, Navigator
            if (IResource.class.isAssignableFrom(objClass)) {

                resource = (IResource) obj;
            }
            // Package Explorer
            // - org.eclipse.jdt.internal.core.ClassFile
            // - org.eclipse.jdt.internal.core.CompilationUnit
            // - org.eclipse.jdt.internal.core.JavaModel
            // - org.eclipse.jdt.internal.core.JavaProject
            // - org.eclipse.jdt.internal.core.PackageFragment
            // - org.eclipse.jdt.internal.core.PackageFragmentRoot
            // - org.eclipse.jdt.internal.core.JarPackageFragmentRoot
            else if (Openable.class.isAssignableFrom(objClass)) {
                Openable openable = (Openable) obj;

                resource = openable.getResource();

                // if (obj instanceof ClassFile || obj instanceof CompilationUnit) {
                // resource = openable.getParent().getResource();
                // } else {
                // resource = openable.getResource();
                // }

            } else if (SourceRefElement.class.isAssignableFrom(objClass)) {
                sLogger.info("Oops... None of doing.... sorry...");
            } else {

                try {
                    // start - just for 'detecting methods of new CHALLENGERs : 2014. 1. 17. 오후 1:47:29
                    Method[] methods = objClass.getMethods();

                    for (Method m_ : methods) {
                        PlugInLogger.info(m_.toGenericString());
                    }
                    // end - just for 'detecting methods of new CHALLENGERs : 2014. 1. 17. 오후 1:47:29

                    PlugInLogger.warn("Oops... Unresolved Resource..... resouce: " + objClass.getName());

                } catch (Exception e) {
                    PlugInLogger.error("Oops... Unresolved Resource..... resouce: " + objClass.getName());
                }
            }
        } catch (Exception e) {
            PlugInLogger.error("Oops... Unresolved Resource..... resouce: " + objClass);
        }

        return resource;
    }

    /**
     * 이벤트에 포함된 객체를 반환한다.
     * 
     * @param event
     * @return
     */
    public static Object getSelectedElement(ExecutionEvent event) {
        Object obj = null;

        try {
            TreeSelection selection = (TreeSelection) HandlerUtil.getCurrentSelection(event);
            obj = selection.getFirstElement();
        } catch (Exception ignored) {
        }

        return obj;
    }

    /**
     * {@link IResource} 객체가 파일인지 여부를 반환한다.
     * 
     * @param resource
     * @return
     */
    public static boolean isFile(IResource resource) {
        IPath path = resource.getLocation();
        File file = path.toFile();

        return file.isFile();
    }

}
