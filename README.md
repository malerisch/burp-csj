What is burpcsj?
================

BurpCSJ is an extension written in Java for Burp Pro web proxy. 
BurpCSJ integrates Crawljax, Selenium and JUnit together.
The intent of this extension is to aid web application security testing, increase web application crawling capability and speed-up complex test-cases.

Quick Start
===========

- Download the BurpCJS extension jar: burpcsj.jar 
- Load BurpCSJ extension jar via the Extender tab;
- Choose the URL item from any Burp tab (e.g. target, proxy history, repeater); 
- Right click on the URL item;
- Choose menu item "Send URL to Crawljax";
- Crawljax will automatically start crawling the URL that you choose.

Minimum Requirements
====================

- Burp Pro v1.5.01 or higher;
- A browser installed (e.g. Firefox);
- If you intend to use JUnit, then you would need JDK to compile classes and Selenium IDE to export JUnit test cases

Extra
=====

If you are planning to use Chrome, IE or PhantomJS browsers you would need the following drivers/executables:

- Chrome driver: https://code.google.com/p/chromedriver/downloads/list
- IE Driver: https://code.google.com/p/selenium/downloads/list
- PhantomJS download: http://phantomjs.org/download.html


Tutorial
========

For a simple tutorial: http://blog.malerisch.net/burpcsj-tutorial

Known Issues
============

If you intend to use BurpCSJ when crawling over HTTPS, then it is recommended to use Firefox or Chrome browsers.
There are issues with Remote WebDriver and browsers such as PhantomJS or IE.

When a crawling session is started and you need to interrupt for any reason, the browser driver might not close properly, so you might decide to kill it via Task Manager.