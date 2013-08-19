package malerisch;

import java.awt.*;
import burp.IBurpExtenderCallbacks;
import burp.ITab;

public class CrawlTab implements ITab {
	
	CrawlPanel tab;
	private IBurpExtenderCallbacks callbacks;

	public CrawlTab(final IBurpExtenderCallbacks callbacks) {
	    this.callbacks = callbacks;
	  

	    tab = new CrawlPanel(callbacks);

	    callbacks.customizeUiComponent(tab);

	    callbacks.addSuiteTab(CrawlTab.this);
	    
	}


	@Override
	public String getTabCaption() {
		// TODO Auto-generated method stub
		return "Crawljax";
	}

	@Override
	public Component getUiComponent() {
		// TODO Auto-generated method stub
		return tab;
	}
	
	
	

}