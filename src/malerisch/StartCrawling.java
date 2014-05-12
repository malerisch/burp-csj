package malerisch;

import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import burp.IBurpExtenderCallbacks;
import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import burp.IRequestInfo;

public class StartCrawling {

    private final IExtensionHelpers helpers;
    private final IBurpExtenderCallbacks callbacks;
    private final CrawlTab tab;

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

        private final String url;

        public ThreadCrawljax(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            System.out.println("Crawljax Thread started");
            SetupCrawljax MySetup = new SetupCrawljax(callbacks, helpers, tab, url);
            CrawljaxConfigurationBuilder builder = MySetup.setupBuilder(url);
            MySetup.ChangeBrowserConfig(builder, url);
            MySetup.Start(builder);
        }
    }
}
