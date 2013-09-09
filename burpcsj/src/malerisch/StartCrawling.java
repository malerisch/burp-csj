package malerisch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;

import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import burp.IRequestInfo;
import burp.BurpExtender.*;

public class StartCrawling {

  private IExtensionHelpers helpers;
  private IBurpExtenderCallbacks callbacks;
  private CrawlTab tab;

  public StartCrawling(IBurpExtenderCallbacks callbacks, IExtensionHelpers helpers, CrawlTab tab) {
    this.helpers = helpers;
    this.callbacks = callbacks;
    this.tab = tab;

  }


public void launchCrawljax(IHttpRequestResponse requestResponse) {
	
	IRequestInfo info = helpers.analyzeRequest(requestResponse);

	String url = info.getUrl().toString();


    System.out.println("URL requested: " + url);
    
    
    Runnable MyThread = new ThreadCrawljax(url);
    
    new Thread(MyThread).start();
    
    
      }



public class ThreadCrawljax implements Runnable {
    
	
	
    private String url;
    
    public ThreadCrawljax(String url) {
            
            this.url = url;
                    
            }
            


    @Override
public void run() {
    System.out.println("Crawljax Thread started");
    for (URL url :
        ((URLClassLoader) (Thread.currentThread()
            .getContextClassLoader())).getURLs()) {
    	//Thread.currentThread().setContextClassLoader(cl);
    	//System.out.println(url);
    	
    }
    SetupCrawljax MySetup = new SetupCrawljax(callbacks, helpers, tab, url);
    
    CrawljaxConfigurationBuilder builder = MySetup.setupBuilder(url);
    
    
    
    MySetup.ChangeBrowserConfig(builder, url);
    
    CrawljaxRunner myrunner = MySetup.Start(builder);

    
    }
    
}
    
 
}