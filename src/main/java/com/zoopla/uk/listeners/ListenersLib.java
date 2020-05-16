package com.zoopla.uk.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.zoopla.uk.drivers.DriverManager;
import com.zoopla.uk.drivers.InitializeDriver;
import com.zoopla.uk.reports.ExtentReport;
import com.zoopla.uk.reports.ExtentReportManager;
import com.zoopla.uk.reports.LogStatus;
import com.zoopla.uk.testbase.TestBase;
//  Listener class which is implementing ITestListener and hence we can use this to dynamically create reports, write logs.
import com.zoopla.uk.utils.ScreenShotLib;
import com.zoopla.uk.utils.TestUtils;

public class ListenersLib extends TestBase implements ITestListener {

	private static String testCaseName;
	TestUtils testUtils;
	private static final Logger log = LogManager.getLogger(ListenersLib.class);

	public void setTestCaseName(String testCaseName) {
		ListenersLib.testCaseName = testCaseName;
	}
	
	public static String getTestCaseName() {
		return testCaseName;
	}
	
	public void onTestStart(ITestResult result) {
		logDebug("************** Test case " + result.getName() + " started ************** \n");
		testCaseName = result.getMethod().getDescription();
		logDebug("Test Case Description is " + testCaseName);
		setTestCaseName(testCaseName);
		ExtentReportManager.setExtentTest(ExtentReport.report.startTest(testCaseName));
		LogStatus.pass(testCaseName + " started now");
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
		logDebug(result.getMethod().getDescription() + " passed successfully");
		LogStatus.pass(result.getMethod().getDescription() + " passed successfully");
		ExtentReport.report.endTest(ExtentReportManager.getExtentTest());
	}

	public void onTestFailure(ITestResult result) {
		logError(result.getMethod().getDescription() + " is failed");
		LogStatus.fail(result.getMethod().getDescription() + " is failed");
		LogStatus.fail(result.getThrowable().toString());
		LogStatus.fail("Failed ",
				ScreenShotLib.takeScreenShot(DriverManager.getDriver(), result.getMethod().getDescription()));
		ExtentReport.report.endTest(ExtentReportManager.getExtentTest());
		/*
		 * try { if (DriverManager.getDriver() != null) { testUtils = new
		 * TestUtils(DriverManager.getDriver()); // returns method name String
		 * methodName = result.getName(); if (!result.isSuccess()) { // File with
		 * current date and time File destFile = new File(ScreenShotLib.screenshotFolder
		 * + "/failure_screenshot/" + methodName + "_" +
		 * testUtils.getDateformatter().format(testUtils.getCalendar()) + ".png");
		 * TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver(); File
		 * srcFile = ts.getScreenshotAs(OutputType.FILE); try {
		 * FileUtils.copyFile(srcFile, destFile); } catch (IOException e) {
		 * log.error(e.getStackTrace().toString()); } // this will helps to link the
		 * screen shot to the report Reporter.log("<a href='" +
		 * destFile.getAbsolutePath() + "'><img src='" + destFile.getAbsolutePath() +
		 * "'height='100''width='100'/></a>");
		 * logDebug("Screen shot taken during failue " + destFile);
		 * logDebug("Screen shot taken during failue " + destFile);
		 * logDebug("test failed " + result.getMethod().getMethodName()); } else { throw
		 * new NullWebDriverException("Driver instance is null"); } } } catch (Exception
		 * e) { log.error("Failed to take screenshot."); e.printStackTrace(); }
		 */ }

	public void onTestSkipped(ITestResult result) {
		logDebug(result.getMethod().getDescription()+" Test skipped");
		LogStatus.skip(result.getMethod().getDescription() + " is skipped");
		ExtentReport.report.endTest(ExtentReportManager.getExtentTest());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		ExtentReport.report.endTest(ExtentReportManager.getExtentTest());
	}

	public void onStart(ITestContext context) {
		logDebug("<<<<<<<<<<<<<<<< TEST Execution Started " + context.getStartDate() + " >>>>>>>>>>>>>>>>> \n");
	}

	public void onFinish(ITestContext context) {
		logDebug("<<<<<<<<<<<<<<<< TEST EXECUTION FINISHED " + context.getEndDate() + " >>>>>>>>>>>>>>>>> ");
	}

	public void logInfo(String data) {
		log.info(data);
		if (InitializeDriver.isreporterLogRequired) {
			Reporter.log(data);
		}
	}

	public static void logDebug(String data) {
		if (log.isDebugEnabled()) {
			log.debug(data);
			if (InitializeDriver.isreporterLogRequired) {
				Reporter.log(data, true);
			}
		}
	}

	public static void logError(String data) {
		log.error(data);
		if (InitializeDriver.isreporterLogRequired) {
			Reporter.log(data, true);
		}
	}
}
