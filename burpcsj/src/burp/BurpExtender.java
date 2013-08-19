package burp;
import malerisch.*;

public class BurpExtender implements IBurpExtender {


  @Override
  public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {

    callbacks.registerContextMenuFactory(new ExtMenu(callbacks));
  }
}