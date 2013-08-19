package malerisch;

import java.net.*;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Provider;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.browser.EmbeddedBrowser.BrowserType;
import com.crawljax.browser.WebDriverBackedEmbeddedBrowser;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.ProxyConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.plugins.crawloverview.CrawlOverview;

import burp.IBurpExtenderCallbacks;
import burp.ICookie;
import burp.IExtensionHelpers;

public class SetupCrawljax {
	
	private CrawlPanel crawlpanel;
	
	private IExtensionHelpers helpers;
	  private IBurpExtenderCallbacks callbacks;
	  private CrawlTab tab;
	  private String url;
	  
	  
	  public SetupCrawljax(IBurpExtenderCallbacks callbacks, IExtensionHelpers helpers, CrawlTab tab, String url) {
		  
		  this.helpers = helpers;
		    this.callbacks = callbacks;
		    this.tab = tab;
		    this.url = url;
		    
	  }
	  
	
	public CrawljaxConfigurationBuilder setupBuilder(String url) {
		
        CrawljaxConfigurationBuilder builder = CrawljaxConfiguration.builderFor(url);
    	
    	return builder;
		
		
	}
	
	
	
	
	
class MyCustomBrowser implements Provider<EmbeddedBrowser> {
    	
    	//this class is needed to be passed to crawljax
	
	
    	
		@Override
		public EmbeddedBrowser get() {
			EmbeddedBrowser browser2 = null;
			
			if (tab.tab.Browser.getSelectedItem().toString() == "Firefox") {
			
			
			browser2 = newFireFoxBrowser();
			
			}
			
			if (tab.tab.Browser.getSelectedItem().toString() == "IE") {
				
				
				browser2 = newIEBrowser();
				
				}
			
if (tab.tab.Browser.getSelectedItem().toString() == "Chrome") {
				
				
				browser2 = newChromeBrowser();
				
				}


if (tab.tab.Browser.getSelectedItem().toString() == "PhantomJS") {
	
	
	browser2 = newPhantomBrowser();
	
	}
		if(tab.tab.Browser.getSelectedItem().toString() == "Remote") {
			
			browser2= newRemoteBrowser();
			
		}	
			return browser2;
		}
		
		
		
		
		
		
 
		private EmbeddedBrowser newFireFoxBrowser() {
			
			


			FirefoxProfile profile = new FirefoxProfile();
			
			
			
			
			if (tab.tab.manualproxy.isSelected()) {
				
				String host = tab.tab.HostProxy.getText();
				Integer port = Integer.parseInt(tab.tab.PortProxy.getText());
				
				// set something on the profile...
            profile.setPreference("network.proxy.type", 1);
           profile.setPreference("network.proxy.http", host);
            profile.setPreference("network.proxy.http_port", port);
            profile.setPreference("network.proxy.ssl", host);
            profile.setPreference("network.proxy.ssl_port", port);
            profile.setPreference("network.proxy.socks", host);
           profile.setPreference("network.proxy.socks_port", port);
            profile.setPreference("network.proxy.ftp", host);
            profile.setPreference("network.proxy.ftp_port", port);
            				
			}
			
			WebDriver drivertest = new FirefoxDriver(profile);
			
			if (tab.tab.burpcookie.isSelected()) {
				
				// here we need to add what we have to the driver
				
				URL Url = null;
				
				
			
		      
		      System.out.println(drivertest.getCurrentUrl());
		      
		      // we need to fetch cookies from the cookie jar BEFORE first GET request
		      List<ICookie> cookies = callbacks.getCookieJarContents();
		      
		      for(ICookie o: cookies) {
					
					//System.out.println("GetHost(): "+Url.getHost().toString());	
					System.out.println("BEFORE");
		    	  System.out.println("o.getDomain(): "+o.getDomain());
					System.out.println("o.getName():" +o.getName());
					System.out.println("o.getValue():"+o.getValue());
					
		      }
		      
		      
		      try {
		    	  drivertest.get(url);
		    	  System.out.println("First GET request done");
		    	  
		    	  
			      drivertest.manage().deleteAllCookies();
			      System.out.println("Cookie should be destroyed - is that true?");
			      
			      
			    
			      
			      
			      try {
						Url = new URL(url);
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
			      
			        
					for(ICookie o: cookies) {
					
						
						
						System.out.println("LATER");
					System.out.println("GetHost(): "+Url.getHost().toString());	
					System.out.println("o.getDomain(): "+o.getDomain());
					System.out.println("o.getName():" +o.getName());
					System.out.println("o.getValue():"+o.getValue());
					
					String fullhost = Url.getHost().toString();
					
					 String[] arr = fullhost.split("\\.");
					    //should check the size of arr here
					    System.out.println(arr[arr.length-2] + '.' + arr[arr.length-1]);
					
					    String topdomain = arr[arr.length-2] + '.' + arr[arr.length-1];
					
					 System.out.println("topdomain: " +topdomain);   
					 System.out.println("o.getDomain()" + o.getDomain().toString());
				
					 
					 
					try {
						drivertest.manage().addCookie(new Cookie(o.getName(), o.getValue(), fullhost, "/", null));
						drivertest.manage().addCookie(new Cookie(o.getName(), o.getValue(), o.getDomain(), "/", null));
					}
					
					catch (Throwable e) {
						
						System.out.println("Error: "+e);
						
					}
											
						
					}
			      
			      
			      
			      
			      
			      }
			      
			      catch (Throwable e) {
			      
			      System.out.println(e);  
			      }
				
			}
			
			
			
      
			return WebDriverBackedEmbeddedBrowser.withDriver(drivertest);
		}
		
		
		
private EmbeddedBrowser newIEBrowser() {
			
	String iepath = tab.tab.ielocation.getText();	
	
	


	File file = new File(iepath);
	System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
	
	
	DesiredCapabilities capability = new DesiredCapabilities();
	
	capability.setCapability("initialBrowserUrl",url);
	capability.setCapability("ignoreZoomSetting", true);
	capability.setCapability("ignoreProtectedModeSettings", true);
			
			
			if (tab.tab.manualproxy.isSelected()) {
				
				
				
				String host = tab.tab.HostProxy.getText();
				Integer port = Integer.parseInt(tab.tab.PortProxy.getText());
				
				String PROXY = host+":"+port;

				org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
				proxy.setHttpProxy(PROXY)
				     .setFtpProxy(PROXY)
				     .setSslProxy(PROXY);
				
			     
			    capability.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
			    capability.setCapability(CapabilityType.PROXY, proxy); 
			    
            				
			}
			
			WebDriver drivertest = null;
			
			try {
			
			drivertest = new InternetExplorerDriver(capability);
			
			}
			
			catch(Throwable e) {
				
				JOptionPane.showMessageDialog(null, "IE Location not specified");
				// we should default to FF then...
				
				tab.tab.Browser.setSelectedItem("Firefox");
				
				
			}
			
			
			if (tab.tab.burpcookie.isSelected()) {
				
				
		        
		     
		      
				System.out.println(drivertest.getCurrentUrl());
			      
			      // we need to fetch cookies from the cookie jar BEFORE first GET request
			      List<ICookie> cookies = callbacks.getCookieJarContents();
			      
			      for(ICookie o: cookies) {
						
						//System.out.println("GetHost(): "+Url.getHost().toString());	
						System.out.println("BEFORE");
			    	  System.out.println("o.getDomain(): "+o.getDomain());
						System.out.println("o.getName():" +o.getName());
						System.out.println("o.getValue():"+o.getValue());
						
			      }
			      
			      
			
		      try {
		    	  drivertest.get(url);
		    	  System.out.println("First GET request done");
			      drivertest.manage().deleteAllCookies();
			      System.out.println("Cookie should be destroyed - is that true?");
			      
			      
			      URL Url = null;
			      
			      try {
						Url = new URL(url);
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					for(ICookie o: cookies) {
					
					System.out.println(Url.getHost().toString());	
						
					String fullhost = Url.getHost().toString();
					
					try {
						
						drivertest.manage().addCookie(new Cookie(o.getName(), o.getValue(), fullhost, "/", null));
						drivertest.manage().addCookie(new Cookie(o.getName(), o.getValue(), o.getDomain(), "/", null));
					}
					
					catch (Throwable e) {
						
						System.out.println("Error: "+e);
						
					}
						
						
					}
		      
		      }
		      
		      catch (Throwable e) {
		      
		      System.out.println(e);  
		      }
				
				
			}
			
			
	
			return WebDriverBackedEmbeddedBrowser.withDriver(drivertest);
		}
		


private EmbeddedBrowser newChromeBrowser() {
	
	
String chromepath = tab.tab.chromelocation.getText();

	





File file = new File(chromepath);
System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());






DesiredCapabilities capability = new DesiredCapabilities();


	
	if (tab.tab.manualproxy.isSelected()) {
		
		String host = tab.tab.HostProxy.getText();
		Integer port = Integer.parseInt(tab.tab.PortProxy.getText());
		
		// set something on the profile...
		String PROXY = host+":"+port;

		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		proxy.setHttpProxy(PROXY)
		     .setFtpProxy(PROXY)
		     .setSslProxy(PROXY);
	     
	    
	    capability.setCapability(CapabilityType.PROXY, proxy); 
	    
    				
	}
	
	WebDriver drivertest = null;
	
	try {
	drivertest = new ChromeDriver(capability);
	}
	
	catch (Throwable e) {
		
		JOptionPane.showMessageDialog(null, "Chrome Location not specified");
		// we should default to FF then...
		
		tab.tab.Browser.setSelectedItem("Firefox");
		
	}
	
	
	if (tab.tab.burpcookie.isSelected()) {
		
		
        
   
      
      System.out.println(drivertest.getCurrentUrl());
      
      List<ICookie> cookies = callbacks.getCookieJarContents();
      
      for(ICookie o: cookies) {
			
				
			System.out.println("BEFORE");
    	  System.out.println("o.getDomain(): "+o.getDomain());
			System.out.println("o.getName():" +o.getName());
			System.out.println("o.getValue():"+o.getValue());
			
      }
	
      try {
    	  drivertest.get(url);
    	  System.out.println("First GET request done");
    	
	      drivertest.manage().deleteAllCookies();
	      System.out.println("Cookie should be destroyed - is that true?");
	      
	      URL Url = null;
	      
	      try {
				Url = new URL(url);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
	        
			for(ICookie o: cookies) {
				
			System.out.println(Url.getHost().toString());	
			
			String fullhost = Url.getHost().toString();
			
			try {
				drivertest.manage().addCookie(new Cookie(o.getName(), o.getValue(), fullhost, "/", null));
				drivertest.manage().addCookie(new Cookie(o.getName(), o.getValue(), o.getDomain(), "/", null));
			}
			
			catch (Throwable e) {
				
				System.out.println("Error: "+e);
				
			}
				
				
			}
	      
      
      }
      
      catch (Throwable e) {
      
      System.out.println(e);  
      }
		
		
	}
	
	

	return WebDriverBackedEmbeddedBrowser.withDriver(drivertest);
}

private EmbeddedBrowser newPhantomBrowser() {
	
	
	String phantompath = tab.tab.phatomjslocation.getText();
	
File file = new File(phantompath);




DesiredCapabilities capability = new DesiredCapabilities();

capability.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
        file.getAbsolutePath());   

	
	if (tab.tab.manualproxy.isSelected()) {
		
		String host = tab.tab.HostProxy.getText();
		Integer port = Integer.parseInt(tab.tab.PortProxy.getText());
		// set something on the profile...
		String PROXY = host+":"+port;

		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		proxy.setHttpProxy(PROXY)
		     .setFtpProxy(PROXY)
		     .setSslProxy(PROXY);
			     
			    
			    capability.setCapability(CapabilityType.PROXY, proxy); 
    				
	}
	 capability.setCapability("takesScreenshot", false); 
	WebDriver drivertest = null;
	try{
	drivertest = new PhantomJSDriver(capability);
	}
	
	catch( Throwable e) {
		JOptionPane.showMessageDialog(null, "PhantomJS Location not specified");
		// we should default to FF then...
		
		tab.tab.Browser.setSelectedItem("Firefox");
		
	}
	
	if (tab.tab.burpcookie.isSelected()) {
		
	
      
		 System.out.println(drivertest.getCurrentUrl());
	      
	      // we need to fetch cookies from the cookie jar BEFORE first GET request
	      List<ICookie> cookies = callbacks.getCookieJarContents();
	      
	      for(ICookie o: cookies) {
				
				//System.out.println("GetHost(): "+Url.getHost().toString());	
				System.out.println("BEFORE");
	    	  System.out.println("o.getDomain(): "+o.getDomain());
				System.out.println("o.getName():" +o.getName());
				System.out.println("o.getValue():"+o.getValue());
				
	      }
	
      try {
	      drivertest.get(url);
	      drivertest.manage().deleteAllCookies();

URL Url = null;
	      
	      try {
				Url = new URL(url);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
	        
			for(ICookie o: cookies) {
			
			System.out.println(Url.getHost().toString());	
				
			String fullhost = Url.getHost().toString();
			
			try {
				drivertest.manage().addCookie(new Cookie(o.getName(), o.getValue(), fullhost, "/", null));
				drivertest.manage().addCookie(new Cookie(o.getName(), o.getValue(), o.getDomain(), "/", null));
			}
			
			catch (Throwable e) {
				
				System.out.println("Error: "+e);
				
			}
				
				
			}
	      
	      }
	      
	      catch (Throwable e) {
	      
	      System.out.println(e);  
	      }
		
	}
	
	
	

	return WebDriverBackedEmbeddedBrowser.withDriver(drivertest);
}

private EmbeddedBrowser newRemoteBrowser() {
	
	String remoteUrl = tab.tab.remoteurl.getText();	
	
	DesiredCapabilities capability = new DesiredCapabilities();
	
	
			if (tab.tab.manualproxy.isSelected()) {
				
				
				
				String host = tab.tab.HostProxy.getText();
				Integer port = Integer.parseInt(tab.tab.PortProxy.getText());
				
				String PROXY = host+":"+port;

				org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
				proxy.setHttpProxy(PROXY)
				     .setFtpProxy(PROXY)
				     .setSslProxy(PROXY);
				
				capability.setCapability(CapabilityType.PROXY, proxy); 
			    
            				
			}
			 capability.setCapability("takesScreenshot", false); 
			 
			 
			WebDriver drivertest = null;
			
			try {
			
				drivertest = new RemoteWebDriver(
			            new URL(remoteUrl), 
			            capability);
			
			}
			
			catch(Throwable e) {
				
				JOptionPane.showMessageDialog(null, "Remote Driver url cannot be found");
				// we should default to FF then...
				
				tab.tab.Browser.setSelectedItem("Firefox");
				
				
			}
			
			
			if (tab.tab.burpcookie.isSelected()) {
				
			
		      
				 System.out.println(drivertest.getCurrentUrl());
			      
			      // we need to fetch cookies from the cookie jar BEFORE first GET request
			      List<ICookie> cookies = callbacks.getCookieJarContents();
			      
			      for(ICookie o: cookies) {
						
						
						System.out.println("BEFORE");
			    	  System.out.println("o.getDomain(): "+o.getDomain());
						System.out.println("o.getName():" +o.getName());
						System.out.println("o.getValue():"+o.getValue());
						
			      }
			
		      try {
		    	  drivertest.get(url);
			      drivertest.manage().deleteAllCookies();
			      
			      URL Url = null;
			      
			      try {
						Url = new URL(url);
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					 
					for(ICookie o: cookies) {
					
					System.out.println(Url.getHost().toString());	
						
					String fullhost = Url.getHost().toString();
					
					
					try {
						
						drivertest.manage().addCookie(new Cookie(o.getName(), o.getValue(), fullhost, "/", null));
						drivertest.manage().addCookie(new Cookie(o.getName(), o.getValue(), o.getDomain(), "/", null));
					}
					
					catch (Throwable e) {
						
						System.out.println("Error: "+e);
						
					}
						
						
					}
		      
		      }
		      
		      catch (Throwable e) {
		      
		      System.out.println(e);  
		      }
				
				
			}
			
			
	
			return WebDriverBackedEmbeddedBrowser.withDriver(drivertest);
		}
		
 
	}
	
public CrawljaxRunner Start(CrawljaxConfigurationBuilder builder) {
    	
    	CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
     
        
    	callbacks.issueAlert("Crawljax Started: "+url);
    	
        crawljax.call();
        
        callbacks.issueAlert("Crawljax Finished: "+url +" - Reason: "+ crawljax.getReason());
      
       
    	 
    	return crawljax;
    	
    }



public CrawljaxConfigurationBuilder ChangeBrowserConfig(CrawljaxConfigurationBuilder builder, String url) {
	
    
    
    System.out.println(CrawlPanel.Browser.getSelectedItem().toString());
    

	
	
    Integer instances = Integer.parseInt(tab.tab.Instance.getSelectedItem().toString());
    
    BrowserConfiguration browserConfg =
	        new BrowserConfiguration(BrowserType.INTERNET_EXPLORER, instances, new MyCustomBrowser());
  
    if (tab.tab.systemproxy.isSelected()) {
  
    builder.setProxyConfig(ProxyConfiguration.systemDefault());
    
    }
    
    if (tab.tab.manualproxy.isSelected()) {
    	String host = tab.tab.HostProxy.getText();
		Integer port = Integer.parseInt(tab.tab.PortProxy.getText());
    	builder.setProxyConfig(ProxyConfiguration.manualProxyOn(host, port));
    	
    }
    
    if (tab.tab.insertrandom.isSelected()) {
		
		 builder.crawlRules().insertRandomDataInInputForms(true);
		
	} else {
		
		builder.crawlRules().insertRandomDataInInputForms(false);
		
		
	}
	
	if (tab.tab.clickonce.isSelected()) {
		
		 builder.crawlRules().clickOnce(true);
		
	} else {
		
	 builder.crawlRules().clickOnce(false);
		
	}
	
	if (tab.tab.crawlframes.isSelected()) {
		
		builder.crawlRules().crawlFrames(true);
		    		
	} else {
		
		builder.crawlRules().crawlFrames(false);
		
	}
	
	if (tab.tab.crawlhiddenanchors.isSelected()) {
		
		builder.crawlRules().crawlHiddenAnchors(true);
	} else {
		
		builder.crawlRules().crawlHiddenAnchors(false);
		
	}
	
	Integer maxr;
	
	maxr = Integer.parseInt(tab.tab.maxruntime.getText());
	
	builder.setMaximumRunTime(maxr, TimeUnit.MINUTES);
	
	Integer depth;
	
	depth = Integer.parseInt(tab.tab.maxdepth.getText());
	
	builder.setMaximumDepth(depth);
	
	Integer States;
	
	States = Integer.parseInt(tab.tab.maxstates.getText());
	
	Integer maxurl;
	
	maxurl = Integer.parseInt(tab.tab.waitafterreloadurl.getText());
	
	builder.crawlRules().waitAfterReloadUrl(maxurl, TimeUnit.MILLISECONDS);
	
Integer maxevent;
	
	maxevent = Integer.parseInt(tab.tab.waitafterevent.getText());
	
	builder.crawlRules().waitAfterEvent(maxevent, TimeUnit.MILLISECONDS);
	
	if (tab.tab.elA.isSelected())
		
	{
		
		builder.crawlRules().click("A");
		
	}
	
	if (tab.tab.elBUTTON.isSelected()) {
		
		builder.crawlRules().click("BUTTON");
	}
	
if (tab.tab.elDIV.isSelected()) {
		
		builder.crawlRules().click("DIV");
	}

if (tab.tab.elFORM.isSelected()) {

builder.crawlRules().click("FORM");
}

if (tab.tab.elSPAN.isSelected()) {

builder.crawlRules().click("SPAN");
}
    
if (tab.tab.elTR.isSelected()) {

builder.crawlRules().click("TR");
}

if (tab.tab.elOL.isSelected()) {

builder.crawlRules().click("OL");
}

if (tab.tab.elLI.isSelected()) {

builder.crawlRules().click("LI");
}

if (tab.tab.elRADIO.isSelected()) {

builder.crawlRules().click("RADIO");
}

if (tab.tab.elNON.isSelected()) {

builder.crawlRules().click("NON");
}

if (tab.tab.elMETA.isSelected()) {

builder.crawlRules().click("META");
}

if (tab.tab.elREFRESH.isSelected()) {

builder.crawlRules().click("REFRESH");
}

if (tab.tab.elXHR.isSelected()) {

builder.crawlRules().click("XHR");
}

if (tab.tab.elRELATIVE.isSelected()) {

builder.crawlRules().click("RELATIVE");
}

if (tab.tab.elLINK.isSelected()) {

builder.crawlRules().click("LINK");
}

if (tab.tab.elSELF.isSelected()) {

builder.crawlRules().click("SELF");
}

if (tab.tab.elSELECT.isSelected()) {

builder.crawlRules().click("SELECT");
}

if (tab.tab.elINPUT.isSelected()) {

builder.crawlRules().click("INPUT");
}

if (tab.tab.elOPTION.isSelected()) {

builder.crawlRules().click("OPTION");
}

if (tab.tab.elIMG.isSelected()) {

builder.crawlRules().click("IMG");
}

if (tab.tab.elP.isSelected()) {

builder.crawlRules().click("P");
}


// logout exceptions - read from eTable
DefaultTableModel model = (DefaultTableModel) tab.tab.eTable.getModel();

String exclusion;

for(int i=0; i<model.getRowCount(); i++) {
	
	exclusion = model.getValueAt(i, 0).toString();
	
	System.out.println("Setting exclusion for builder "+exclusion);
	
	builder.crawlRules().dontClick("A").underXPath("//*[contains(concat(' ', @href, ' '), '"+exclusion+"')]");
	builder.crawlRules().dontClick("A").withText(exclusion);
	builder.crawlRules().dontClick("A").withAttribute("href", exclusion);
}


if(tab.tab.overviewplugin.isSelected()) {
	
	String outp = tab.tab.outputpath.getText();
	
	File outpath = new File(outp);
	CrawlOverview crov = new CrawlOverview(); 
	builder.setOutputDirectory(outpath);
	builder.addPlugin(crov);
	
}




  builder.setBrowserConfig(browserConfg);
	
	return builder;
	
	
}



}


