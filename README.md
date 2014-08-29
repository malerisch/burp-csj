What is burpcsj?
================

BurpCSJ is an extension written in Java for [Burp Pro](http://portswigger.net/burp/) web proxy. 
BurpCSJ integrates [Crawljax](http://www.crawljax.com), [Selenium](http://docs.seleniumhq.org/) and [JUnit](http://junit.org/) together.
The intent of this extension is to aid web application security testing, increase web application crawling capability and speed-up complex test-cases execution.

Quick Start
===========

- [Download the BurpCJS extension jar](http://bit.ly/burpcsj0-2)
- Load BurpCSJ extension jar via the Extender tab;
- Choose the URL item from any Burp tab (e.g. target, proxy history, repeater); 
- Right click on the URL item;
- Choose menu item "Send URL to Crawljax";
- Crawljax will automatically start crawling the URL that you choose.

Minimum Requirements
====================

- Burp Suite Free or Professional;
- JRE or JDK 1.7;
- A browser installed (e.g. Firefox);
- If you intend to use JUnit, then you would need JDK to compile classes and Selenium IDE to export JUnit test cases
<<<<<<< HEAD
- Enough memory when starting Burp if you also want to use BurpCSJ (recommended to use a 64bit env)
=======
- Enough memory when starting Burp if you also want to use BurpCSJ (recommended to use a 64bits env)
>>>>>>> 6c97b8db99ba7cc462d317d3a02851e2386e6f1d

Extra
=====

If you are planning to use Chrome, IE or PhantomJS browsers you would need the following drivers/executables:

- Chrome driver: https://code.google.com/p/chromedriver/downloads/list
- IE Driver: https://code.google.com/p/selenium/downloads/list
- PhantomJS download: http://phantomjs.org/download.html


Tutorials
=========

- For a simple tutorial: http://blog.malerisch.net/2013/09/burpcsj-tutorial-using-crawljax.html
- Dealing with authentication: http://blog.malerisch.net/2014/08/burpcsj-dealing-with-authentication.html

Tested on:
==========

- Windows 7 with Java(TM) SE Runtime Environment (build 1.7.0_55-b13) and Burp Pro 1.6.05
- OS X Maverick with Java(TM) SE Runtime Environment (build 1.7.0_67-b01) and Burp Pro 1.6.05
- Kali Linux with OpenJDK Runtime Environment (IcedTea 2.5.1) (7u65-2.5.1-2~deb7u1) and Burp Suite Free 1.6


Known Issues
============

If you intend to use BurpCSJ when crawling over HTTPS, then it is recommended to use Firefox or Chrome browsers.
There are issues with Remote WebDriver and browsers such as PhantomJS or IE.

When a crawling session is started and you need to interrupt for any reason, the browser driver might not close properly, so you might decide to kill it.