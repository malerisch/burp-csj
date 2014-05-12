package burp;

import malerisch.ExtMenu;

public class BurpExtender implements IBurpExtender {

    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {
        callbacks.registerContextMenuFactory(new ExtMenu(callbacks));
    }
}
