package malerisch;

import java.awt.Component;
import burp.IBurpExtenderCallbacks;
import burp.ITab;

public class JUnitTab implements ITab {

    private final IBurpExtenderCallbacks callbacks;
    JUnitPanel tab;

    public JUnitTab(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        tab = new JUnitPanel(callbacks);
        callbacks.customizeUiComponent(tab);
        callbacks.addSuiteTab(JUnitTab.this);
    }

    @Override
    public String getTabCaption() {
        return "JUnit (beta)";
    }

    @Override
    public Component getUiComponent() {
        return tab;
    }
}
