package malerisch;

import burp.IBurpExtenderCallbacks;
import burp.IContextMenuFactory;
import burp.IContextMenuInvocation;
import burp.IExtensionHelpers;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;

public class ExtMenu implements IContextMenuFactory {

    private final IBurpExtenderCallbacks callbacks;
    private final IExtensionHelpers helpers;
    private final CrawlTab tab;
    private final JUnitTab tab2;

    public ExtMenu(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        helpers = callbacks.getHelpers();
        tab = new CrawlTab(callbacks);
        tab2 = new JUnitTab(callbacks);
    }

    @Override
    public List<JMenuItem> createMenuItems(
            final IContextMenuInvocation invocation) {
        List<JMenuItem> list;
        list = new ArrayList<>();
        JMenuItem item = new JMenuItem("Send URL to Crawljax");

        item.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                StartCrawling crawler = new StartCrawling(callbacks, helpers, tab);
                crawler.launchCrawljax(invocation.getSelectedMessages()[0]);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        list.add(item);

        return list;
    }
}
