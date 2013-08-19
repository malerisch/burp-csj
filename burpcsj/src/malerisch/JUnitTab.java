package malerisch;

import java.awt.*;

import javax.swing.*;

import burp.IBurpExtenderCallbacks;
import burp.ITab;



public class JUnitTab implements ITab {


	private IBurpExtenderCallbacks callbacks;
	JUnitPanel tab;

	public JUnitTab(IBurpExtenderCallbacks callbacks) {
		
		this.callbacks = callbacks;
	    

	    tab = new JUnitPanel(callbacks);

	    callbacks.customizeUiComponent(tab);

	    callbacks.addSuiteTab(JUnitTab.this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTabCaption() {
		// TODO Auto-generated method stub
		return "JUnit (beta)";
	}

	@Override
	public Component getUiComponent() {
		// TODO Auto-generated method stub
		return tab;
	}
	
	
	

}
