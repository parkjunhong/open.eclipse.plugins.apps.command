/*
 * COPYLEFT by 'Open Commons' &  Park Jun-Hong All Rights Reserved.
 * 
 * This file is generated under this project, "SystemPropertiesView".
 *
 * Date  : 2012. 10. 2. 오후 6:04:19
 *
 * Author: Park_Jun_Hong_(jhpark_at_ymtech_dot_co_dot_kr)
 * 
 */
package open.eclipse.plugins.apps.command.shell;

import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentSkipListMap;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;

import open.eclipse.plugins.apps.command.utils.Utils;

public class SystemPropertiesShell {
    protected Shell shell;
    private Text textFilter;
    private Table tableProperties;
    private TableViewer tblvwProperties;
    private TableColumn tblclmnKey;
    private TableViewerColumn tblclmnvwKey;
    private TableColumn tblclmnValue;
    private TableViewerColumn tblclmvwValue;

    private ConcurrentSkipListMap<String, String> properties = new ConcurrentSkipListMap<String, String>();

    /** true: ascending order, false: descending order **/
    private boolean orderDirectionKey = true;
    /** true: ascending order, false: descending order **/
    private boolean orderDirectionValue = true;

    private SPSSorter sorter;

    private Button btnClearFilter;

    public SystemPropertiesShell() {
        sorter = new SPSSorter(SPSTableLabelProvider.INDEX_KEY, true);
    }

    private void sorting(int target, boolean direction) {
        sorter.setTarget(target);
        sorter.setDirection(direction);

        loadProperties();
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell();
        shell.setSize(640, 548);
        shell.setText("System Properteis");
        shell.setLayout(new GridLayout(3, false));

        Label lblFilter = new Label(shell, SWT.NONE);
        lblFilter.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblFilter.setText("Key &Filter: ");

        textFilter = new Text(shell, SWT.BORDER);
        textFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                loadProperties();
            }
        });
        textFilter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        btnClearFilter = new Button(shell, SWT.NONE);
        btnClearFilter.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                textFilter.setText("");
                loadProperties();
            }
        });
        btnClearFilter.setText("&Clear");

        tblvwProperties = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
        tableProperties = tblvwProperties.getTable();
        tableProperties.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                ViewerCell cell = tblvwProperties.getCell(new Point(e.x, e.y));
                if (cell != null) {
                    String text = cell.getText();
                    if (text != null && !"".equals(text)) {
                        Utils.copyToClipboard(text);
                    }
                }
            }
        });
        tableProperties.setLinesVisible(true);
        tableProperties.setHeaderVisible(true);
        tableProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

        // start - TableColumn about Key : 2012. 10. 7. 오후 5:31:37
        tblclmnvwKey = new TableViewerColumn(tblvwProperties, SWT.NONE);
        tblclmnvwKey.setLabelProvider(new STSColumnLabelProvider());

        tblclmnKey = tblclmnvwKey.getColumn();
        tblclmnKey.setText("Key");
        tblclmnKey.setImage(ResourceManager.getPluginImage("open.eclipse.plugins.apps.command", "icons/dir_no.gif"));
        tblclmnKey.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                orderDirectionKey = !orderDirectionKey;

                sorting(SPSTableLabelProvider.INDEX_KEY, orderDirectionKey);

                Image image = null;
                if (orderDirectionKey) {
                    image = ResourceManager.getPluginImage("open.eclipse.plugins.apps.command",
                            "icons/dir_ascending.gif");
                } else {
                    image = ResourceManager.getPluginImage("open.eclipse.plugins.apps.command",
                            "icons/dir_descending.gif");
                }

                tblclmnKey.setImage(image);

                tblclmnValue.setImage(ResourceManager.getPluginImage("open.eclipse.plugins.apps.command",
                        "icons/dir_no.gif"));
            }
        });
        tblclmnKey.setWidth(234);
        // end - TableColumn about Key : 2012. 10. 7. 오후 5:31:37

        // start - TableColumn about Value : 2012. 10. 7. 오후 5:31:24
        tblclmvwValue = new TableViewerColumn(tblvwProperties, SWT.NONE);

        tblclmnValue = tblclmvwValue.getColumn();
        tblclmnValue.setText("Value");
        tblclmnValue.setImage(ResourceManager.getPluginImage("open.eclipse.plugins.apps.command", "icons/dir_no.gif"));
        tblclmnValue.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                orderDirectionValue = !orderDirectionValue;

                sorting(SPSTableLabelProvider.INDEX_VALUE, orderDirectionValue);

                Image image = null;
                if (orderDirectionValue) {
                    image = ResourceManager.getPluginImage("open.eclipse.plugins.apps.command",
                            "icons/dir_ascending.gif");
                } else {
                    image = ResourceManager.getPluginImage("open.eclipse.plugins.apps.command",
                            "icons/dir_descending.gif");
                }

                tblclmnValue.setImage(image);

                tblclmnKey.setImage(ResourceManager.getPluginImage("open.eclipse.plugins.apps.command",
                        "icons/dir_no.gif"));
            }
        });
        tblclmnValue.setWidth(435);
        // end - TableColumn about Value : 2012. 10. 7. 오후 5:31:24

        // start - set provider : 2012. 10. 7. 오후 5:26:14
        tblvwProperties.setSorter(sorter);
        tblvwProperties.setLabelProvider(new SPSTableLabelProvider());
        tblvwProperties.setContentProvider(new SPSContentProvider());
    }

    private void loadProperties() {

        String filter = textFilter.getText().trim();

        properties.clear();

        Properties props = System.getProperties();
        String key = null;
        String value = null;
        for (Entry<Object, Object> entry : props.entrySet()) {
            key = (String) entry.getKey();
            value = (String) entry.getValue();

            if (key.contains(filter)) {
                this.properties.put(key, value);
            }
        }

        tblclmnKey.setImage(ResourceManager.getPluginImage("open.eclipse.plugins.apps.command", "icons/dir_no.gif"));
        tblclmnValue.setImage(ResourceManager.getPluginImage("open.eclipse.plugins.apps.command", "icons/dir_no.gif"));

        tblvwProperties.setInput(properties);

        StringBuffer sb = new StringBuffer();
        for (Entry<String, String> entry : properties.entrySet()) {
            sb.append(String.format("   %-20s = %s\n", entry.getKey(), entry.getValue()));
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();

        loadProperties();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Launch the application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            SystemPropertiesShell window = new SystemPropertiesShell();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
