/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "open.eclipse.plugins.apps.command".
 *
 * Date  : 2012. 10. 6. 오후 10:57:50
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 * 
 */
package open.eclipse.plugins.apps.command.commands;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.widgets.Display;

import open.eclipse.plugins.apps.command.shell.SystemPropertiesShell;

public class SystemPropertiesHandler implements IHandler {

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

        if (sLogger.isTraceEnabled()) {
            sLogger.trace("execute(event): " //
            // + LogUtils.callStack(0, 5) //
            );
        }

        Display.getCurrent().asyncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    SystemPropertiesShell window = new SystemPropertiesShell();
                    window.open();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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

}
