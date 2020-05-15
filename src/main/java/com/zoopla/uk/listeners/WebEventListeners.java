package com.zoopla.uk.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.Reporter;

import com.zoopla.uk.drivers.InitializeDriver;
import com.zoopla.uk.testbase.TestBase;

/**
 * @author UTKAL
 * This class is used for generating logs using WebDriverEventListener interface and extends TestBase class. 
 */

public class WebEventListeners extends TestBase implements WebDriverEventListener{

	private static final Logger log = LogManager.getLogger(WebEventListeners.class);

	public void beforeNavigateTo(String url, WebDriver driver) {
		logInfo("Before navigating to: '" + url + "'");
	}

	public void afterNavigateTo(String url, WebDriver driver) {
		logInfo("Navigated to:'" + url + "'");
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		logInfo("Value of the:" + element.toString() + " before any changes made");

	}

	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		logInfo("Element value changed to: " + element.toString());
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		logInfo("Trying to click on: " + element.toString());
	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		logInfo("Clicked on: " + element.toString());
	}

	public void beforeNavigateBack(WebDriver driver) {
		logInfo("Navigating back to previous page");
	}

	public void afterNavigateBack(WebDriver driver) {
		logInfo("Navigated back to previous page");
	}

	public void beforeNavigateForward(WebDriver driver) {
		logInfo("Navigating forward to next page");
	}

	public void afterNavigateForward(WebDriver driver) {
		logInfo("Navigated forward to next page");
	}

	public void onException(Throwable error, WebDriver driver) {
		// logInfo("Exception occured " + arg0, false);
		log.error("Exception occured " + error);
		try {
			//ScreenShotLib.takeScreenShot(driver, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		logInfo("Trying to find Element By : " + by.toString());
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		logInfo("Found Element By : " + by.toString());
	}

	/*
	 * non overridden methods of WebListener class
	 */
	public void beforeScript(String script, WebDriver driver) {
	}

	public void afterScript(String script, WebDriver driver) {
	}

	public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub

	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub

	}

	public <X> void afterGetScreenshotAs(OutputType<X> arg0, X arg1) {
		// TODO Auto-generated method stub

	}

	public void afterGetText(WebElement arg0, WebDriver arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	public void afterSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub

	}

	public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
		// TODO Auto-generated method stub

	}

	public void beforeGetText(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	public void logInfo(String data) {
		log.info(data);
		if (InitializeDriver.isrporterLogRequired) {
			Reporter.log(data);
		}
	}
	
	public void logDebug(String data) {
		log.info(data);
		if (InitializeDriver.isrporterLogRequired) {
			Reporter.log(data);
		}
	}
}
