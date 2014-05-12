package malerisch;

import burp.IBurpExtenderCallbacks;
import burp.ICookie;
import burp.IExtensionHelpers;
import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.browser.EmbeddedBrowser.BrowserType;
import com.crawljax.browser.WebDriverBackedEmbeddedBrowser;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.core.configuration.ProxyConfiguration;
import com.crawljax.plugins.crawloverview.CrawlOverview;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Provider;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.openqa.selenium.Cookie;
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

public class SetupCrawljax {
    
    private CrawlPanel crawlpanel;
    private final IExtensionHelpers helpers;
    private final IBurpExtenderCallbacks callbacks;
    private final CrawlTab tab;
    private final String url;
    
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
        
        @Override
        public EmbeddedBrowser get() {
            EmbeddedBrowser browser2 = null;
            if (null != CrawlPanel.Browser.getSelectedItem().toString()) {
                switch (CrawlPanel.Browser.getSelectedItem().toString()) {
                    case "Firefox":
                        browser2 = newFirefoxBrowser();
                        break;
                    case "IE":
                        browser2 = newIEBrowser();
                        break;
                    case "Chrome":
                        browser2 = newChromeBrowser();
                        break;
                    case "PhantomJS":
                        browser2 = newPhantomBrowser();
                        break;
                    case "Remote":
                        browser2 = newRemoteBrowser();
                        break;
                }
            }
            return browser2;
        }
        
        private EmbeddedBrowser newFirefoxBrowser() {
            FirefoxProfile profile = new FirefoxProfile();
            if (CrawlPanel.manualproxy.isSelected()) {
                String host = CrawlPanel.HostProxy.getText();
                Integer port = Integer.parseInt(CrawlPanel.PortProxy.getText());
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
            
            if (CrawlPanel.burpcookie.isSelected()) {
                setCookies(drivertest, "FirefoxBrowser");
            }
            
            return WebDriverBackedEmbeddedBrowser.withDriver(drivertest);
        }
        
        private EmbeddedBrowser newIEBrowser() {
            String iepath = CrawlPanel.ielocation.getText();
            File file = new File(iepath);
            System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
            DesiredCapabilities capability = new DesiredCapabilities();
            capability.setCapability("initialBrowserUrl", url);
            capability.setCapability("ignoreZoomSetting", true);
            capability.setCapability("ignoreProtectedModeSettings", true);
            
            if (CrawlPanel.manualproxy.isSelected()) {
                String host = CrawlPanel.HostProxy.getText();
                Integer port = Integer.parseInt(CrawlPanel.PortProxy.getText());
                String PROXY = host + ":" + port;
                org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
                proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
                capability.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
                capability.setCapability(CapabilityType.PROXY, proxy);
            }
            
            WebDriver drivertest = null;
            
            try {
                drivertest = new InternetExplorerDriver(capability);
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, "IE Location not specified");
                CrawlPanel.Browser.setSelectedItem("Firefox");
            }
            
            if (CrawlPanel.burpcookie.isSelected()) {
                setCookies(drivertest, "IEBrowser");
            }
            
            return WebDriverBackedEmbeddedBrowser.withDriver(drivertest);
        }
        
        private EmbeddedBrowser newChromeBrowser() {
            String chromepath = CrawlPanel.chromelocation.getText();
            File file = new File(chromepath);
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            DesiredCapabilities capability = new DesiredCapabilities();
            if (CrawlPanel.manualproxy.isSelected()) {
                String host = CrawlPanel.HostProxy.getText();
                Integer port = Integer.parseInt(CrawlPanel.PortProxy.getText());
                String PROXY = host + ":" + port;
                org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
                proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
                capability.setCapability(CapabilityType.PROXY, proxy);
            }
            
            WebDriver drivertest = null;
            
            try {
                drivertest = new ChromeDriver(capability);
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, "Chrome Location not specified");
                CrawlPanel.Browser.setSelectedItem("Firefox");
            }
            
            if (CrawlPanel.burpcookie.isSelected()) {
                setCookies(drivertest, "ChromeBrowser");
            }
            
            return WebDriverBackedEmbeddedBrowser.withDriver(drivertest);
        }
        
        private EmbeddedBrowser newPhantomBrowser() {
            String phantompath = CrawlPanel.phatomjslocation.getText();
            File file = new File(phantompath);
            DesiredCapabilities capability = new DesiredCapabilities();
            capability.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, file.getAbsolutePath());
            if (CrawlPanel.manualproxy.isSelected()) {
                String host = CrawlPanel.HostProxy.getText();
                Integer port = Integer.parseInt(CrawlPanel.PortProxy.getText());
                String PROXY = host + ":" + port;
                org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
                proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
                capability.setCapability(CapabilityType.PROXY, proxy);
            }
            capability.setCapability("takesScreenshot", false);
            String[] args = {"--ignore-ssl-errors=yes"};
            capability.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, args);
            capability.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 5.1; rv:22.0) Gecko/20100101 Firefox/22.0");
            //System.out.println("Capability:" +capability);
            WebDriver drivertest = null;
            try {
                drivertest = new PhantomJSDriver(capability);
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, "PhantomJS Location not specified");
                CrawlPanel.Browser.setSelectedItem("Firefox");
            }
            
            if (CrawlPanel.burpcookie.isSelected()) {
                setCookies(drivertest, "PhantomBrowser");
            }
            
            return WebDriverBackedEmbeddedBrowser.withDriver(drivertest);
        }
        
        private EmbeddedBrowser newRemoteBrowser() {
            String remoteUrl = CrawlPanel.remoteurl.getText();
            DesiredCapabilities capability = new DesiredCapabilities();
            if (CrawlPanel.manualproxy.isSelected()) {
                String host = CrawlPanel.HostProxy.getText();
                Integer port = Integer.parseInt(CrawlPanel.PortProxy.getText());
                String PROXY = host + ":" + port;
                org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
                proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
                capability.setCapability(CapabilityType.PROXY, proxy);
            }
            capability.setCapability("takesScreenshot", false);
            WebDriver drivertest = null;
            
            try {
                drivertest = new RemoteWebDriver(new URL(remoteUrl), capability);
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, "Remote Driver url cannot be found");
                CrawlPanel.Browser.setSelectedItem("Firefox");
            }
            
            if (CrawlPanel.burpcookie.isSelected()) {
                setCookies(drivertest, "RemoteBrowser");
            }
            
            return WebDriverBackedEmbeddedBrowser.withDriver(drivertest);
        }
        
        private void setCookies(WebDriver drivertest, String browserName) {
            List<ICookie> cookies = callbacks.getCookieJarContents();
            try {
                drivertest.get(url);
                if (browserName.equalsIgnoreCase("IEBrowser")) {
                    drivertest.navigate().to("javascript:document.getElementById('overridelink').click()");
                }
                drivertest.manage().deleteAllCookies();
                URL Url = new URL(url);
                for (ICookie o : cookies) {
                    String fullhost = Url.getHost();
                    if (o.getDomain().equalsIgnoreCase(fullhost)) {
                        drivertest.manage().addCookie(new Cookie(o.getName(), o.getValue(), o.getDomain(), "/", null));
                    }
                }
            } catch (MalformedURLException e) {
                System.out.println("WebDriver setCookies Error: " + e.getMessage());
            }
        }
    }
    
    public CrawljaxRunner Start(CrawljaxConfigurationBuilder builder) {
        CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
        callbacks.issueAlert("Crawljax Started: " + url);
        crawljax.call();
        callbacks.issueAlert("Crawljax Finished: " + url + " - Reason: " + crawljax.getReason());
        return crawljax;
    }
    
    public CrawljaxConfigurationBuilder ChangeBrowserConfig(CrawljaxConfigurationBuilder builder, String url) {
        Integer instances = Integer.parseInt(CrawlPanel.Instance.getSelectedItem().toString());
        BrowserConfiguration browserConfg = new BrowserConfiguration(BrowserType.INTERNET_EXPLORER, instances, new MyCustomBrowser());
        
        if (CrawlPanel.systemproxy.isSelected()) {
            builder.setProxyConfig(ProxyConfiguration.systemDefault());
        }
        
        if (CrawlPanel.manualproxy.isSelected()) {
            String host = CrawlPanel.HostProxy.getText();
            Integer port = Integer.parseInt(CrawlPanel.PortProxy.getText());
            builder.setProxyConfig(ProxyConfiguration.manualProxyOn(host, port));
        }
        
        if (CrawlPanel.insertrandom.isSelected()) {
            builder.crawlRules().insertRandomDataInInputForms(true);
        } else {
            builder.crawlRules().insertRandomDataInInputForms(false);
        }
        
        if (CrawlPanel.clickonce.isSelected()) {
            builder.crawlRules().clickOnce(true);
        } else {
            builder.crawlRules().clickOnce(false);
        }
        
        if (CrawlPanel.crawlframes.isSelected()) {
            builder.crawlRules().crawlFrames(true);
        } else {
            builder.crawlRules().crawlFrames(false);
        }
        
        if (CrawlPanel.crawlhiddenanchors.isSelected()) {
            builder.crawlRules().crawlHiddenAnchors(true);
        } else {
            builder.crawlRules().crawlHiddenAnchors(false);
        }
        
        Integer maxr = Integer.parseInt(CrawlPanel.maxruntime.getText());
        builder.setMaximumRunTime(maxr, TimeUnit.MINUTES);
        Integer depth = Integer.parseInt(CrawlPanel.maxdepth.getText());
        builder.setMaximumDepth(depth);
        Integer States = Integer.parseInt(CrawlPanel.maxstates.getText());
        Integer maxurl = Integer.parseInt(CrawlPanel.waitafterreloadurl.getText());
        builder.crawlRules().waitAfterReloadUrl(maxurl, TimeUnit.MILLISECONDS);
        Integer maxevent = Integer.parseInt(CrawlPanel.waitafterevent.getText());
        builder.crawlRules().waitAfterEvent(maxevent, TimeUnit.MILLISECONDS);
        
        if (CrawlPanel.elA.isSelected()) {
            builder.crawlRules().click("A");
        }
        
        if (CrawlPanel.elBUTTON.isSelected()) {
            builder.crawlRules().click("BUTTON");
        }
        
        if (CrawlPanel.elDIV.isSelected()) {
            builder.crawlRules().click("DIV");
        }
        
        if (CrawlPanel.elFORM.isSelected()) {
            builder.crawlRules().click("FORM");
        }
        
        if (CrawlPanel.elSPAN.isSelected()) {
            builder.crawlRules().click("SPAN");
        }
        
        if (CrawlPanel.elTR.isSelected()) {
            builder.crawlRules().click("TR");
        }
        
        if (CrawlPanel.elOL.isSelected()) {
            builder.crawlRules().click("OL");
        }
        
        if (CrawlPanel.elLI.isSelected()) {
            builder.crawlRules().click("LI");
        }
        
        if (CrawlPanel.elRADIO.isSelected()) {
            builder.crawlRules().click("RADIO");
        }
        
        if (CrawlPanel.elNON.isSelected()) {
            builder.crawlRules().click("NON");
        }
        
        if (CrawlPanel.elMETA.isSelected()) {
            builder.crawlRules().click("META");
        }
        
        if (CrawlPanel.elREFRESH.isSelected()) {
            builder.crawlRules().click("REFRESH");
        }
        
        if (CrawlPanel.elXHR.isSelected()) {
            builder.crawlRules().click("XHR");
        }
        
        if (CrawlPanel.elRELATIVE.isSelected()) {
            builder.crawlRules().click("RELATIVE");
        }
        
        if (CrawlPanel.elLINK.isSelected()) {
            builder.crawlRules().click("LINK");
        }
        
        if (CrawlPanel.elSELF.isSelected()) {
            builder.crawlRules().click("SELF");
        }
        
        if (CrawlPanel.elSELECT.isSelected()) {
            builder.crawlRules().click("SELECT");
        }
        
        if (CrawlPanel.elINPUT.isSelected()) {
            builder.crawlRules().click("INPUT");
        }
        
        if (CrawlPanel.elOPTION.isSelected()) {
            builder.crawlRules().click("OPTION");
        }
        
        if (CrawlPanel.elIMG.isSelected()) {
            builder.crawlRules().click("IMG");
        }
        
        if (CrawlPanel.elP.isSelected()) {
            builder.crawlRules().click("P");
        }
        
        DefaultTableModel model = (DefaultTableModel) CrawlPanel.eTable.getModel();
        
        String exclusion;
        for (int i = 0; i < model.getRowCount(); i++) {
            exclusion = model.getValueAt(i, 0).toString();
            System.out.println("Setting exclusion for builder " + exclusion);
            builder.crawlRules().dontClick("A").underXPath("//*[contains(concat(' ', @href, ' '), '" + exclusion + "')]");
            builder.crawlRules().dontClick("A").withText(exclusion);
            builder.crawlRules().dontClick("A").withAttribute("href", exclusion);
        }
        
        if (CrawlPanel.overviewplugin.isSelected()) {
            String outp = CrawlPanel.outputpath.getText();
            File outpath = new File(outp);
            CrawlOverview crov = new CrawlOverview();
            builder.setOutputDirectory(outpath);
            builder.addPlugin(crov);
        }
        builder.setBrowserConfig(browserConfg);
        return builder;
    }
}
