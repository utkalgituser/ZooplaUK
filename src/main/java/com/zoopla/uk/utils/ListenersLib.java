package com.zoopla.uk.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.zoopla.uk.base.TestBase;

public class ListenersLib extends TestBase implements ITestListener {

	TestUtils testUtils;
	private static final Logger log = Logger.getLogger(ListenersLib.class.getName());

	public void onTestStart(ITestResult result) {
		logDebug("");
		logDebug("************** Test case " + result.getName() + " started ************** ");
	}

	public void onTestSuccess(ITestResult result) {
		/*
		 * try { String methodName = result.getName(); if (result.isSuccess()) { // File
		 * with current date and time File destFile = new
		 * File(ScreenShotLib.screenshotFolder + "/success_screenshot/" + methodName +
		 * "_" + formater.format(cal.getTime()) + ".png"); TakesScreenshot ts =
		 * (TakesScreenshot) driver; File srcFile = ts.getScreenshotAs(OutputType.FILE);
		 * FileUtils.copyFile(srcFile, destFile); // this will helps to link the screen
		 * shot to the report Reporter.log("<a href='" + destFile.getAbsolutePath() +
		 * "'><img src='" + destFile.getAbsolutePath() +
		 * "'height='100''width='100'/></a>");
		 * log.info("Screen shot taken during success");
		 * log("Screen shot taken during success " + destFile); log("test passed " +
		 * result.getMethod().getMethodName()); } } catch (Exception e) {
		 * e.printStackTrace(); log.error("screen shot failed"); }
		 */
	}

	public void onTestFailure(ITestResult result) {
		try {
			if (driver != null) {
				testUtils = new TestUtils(driver);
				// returns method name
				String methodName = result.getName();
				if (!result.isSuccess()) {
					// File with current date and time
					File destFile = new File(ScreenShotLib.screenshotFolder + "/failure_screenshot/" + methodName + "_"
							+ testUtils.getDateformatter().format(testUtils.getCalendar()) + ".png");
					TakesScreenshot ts = (TakesScreenshot) driver;
					File srcFile = ts.getScreenshotAs(OutputType.FILE);
					try {
						FileUtils.copyFile(srcFile, destFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// this will helps to link the screen shot to the report
					Reporter.log("<a href='" + destFile.getAbsolutePath() + "'><img src='" + destFile.getAbsolutePath()
							+ "'height='100''width='100'/></a>");
					logDebug("Screen shot taken during failue " + destFile);
					logDebug("Screen shot taken during failue " + destFile);
					logDebug("test failed " + result.getMethod().getMethodName());
				} else {
					throw new NullWebDriverException("Driver instance is null");
				}
			}
		} catch (Exception e) {
			log.error("Failed to take screenshot.");
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		logDebug("Test skipped " + result.getMethod().getMethodName());
		try {
			// driver.get(ConfigFIleRead.urls);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onStart(ITestContext context) {
		logDebug("<<<<<<<<<<<<<<<< TEST Execution Started " + context.getStartDate() + " >>>>>>>>>>>>>>>>> ");
	}

	public void onFinish(ITestContext context) {
		logDebug("<<<<<<<<<<<<<<<< TEST EXECUTION FINISHED " + context.getEndDate() + " >>>>>>>>>>>>>>>>> ");
	}

	public void logDebug(String data) {
		log.debug(data);
		if (isrpoerterLogRequired) {
			Reporter.log(data, true);
		}
	}
}
