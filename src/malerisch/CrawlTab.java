package malerisch;

import burp.IBurpExtenderCallbacks;
import burp.ITab;
import java.awt.Component;

public class CrawlTab implements ITab {

    CrawlPanel tab;
    private final IBurpExtenderCallbacks callbacks;

    public CrawlTab(final IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        tab = new CrawlPanel(callbacks);
        callbacks.customizeUiComponent(tab);
        callbacks.addSuiteTab(CrawlTab.this);
    }

    @Override
    public String getTabCaption() {
        return "Crawljax";
    }

    @Override
    public Component getUiComponent() {
        return tab;
    }
}
