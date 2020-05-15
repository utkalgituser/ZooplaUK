package com.zoopla.uk.reports;

import com.zoopla.uk.utils.ConfigFileRead;
import com.zoopla.uk.utils.TestUtils;

public class LogStatus {
	// Methods to log test data into Extent Report
	// Private constructor to prevent unauthorized initialization of class

	private LogStatus() {

	}

	public static void pass(String message) {
		ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.PASS, message);

	}

	public static void fail(String message) {
		ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.FAIL, message);
	}

	public static void fail(Exception message) {
		ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.FAIL, message);
	}

	public static void fail(AssertionError a) {
		ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.FAIL, a);
	}

	public static void info(String message) {
		ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.INFO, message);
	}

	public static void error(String message) {
		ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.ERROR, message);
	}

	public static void fatal(String message) {
		ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.FATAL, message);
	}

	public static void skip(String message) {
		ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.SKIP, message);
	}

	public static void unknown(String message) {
		ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.UNKNOWN, message);
	}

	public static void warning(String message) {
		ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.WARNING, message);
	}

	public static void pass(String string, String screenShotLocation) {

		if (ConfigFileRead.readkey("isSuccessScreenShotRequired").equalsIgnoreCase("yes")) {
			ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.PASS, string,
					ExtentReportManager.getExtentTest().addBase64ScreenShot(
							"data:image/png;base64," + TestUtils.generateBase64Img(screenShotLocation)));
		}
	}

	public static void fail(String string, String screenShotLocation) {

		if (ConfigFileRead.readkey("isFailedScreenShotRequired").equalsIgnoreCase("yes")) {
			ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.FAIL, string,
					ExtentReportManager.getExtentTest().addBase64ScreenShot(
							"data:image/png;base64," + TestUtils.generateBase64Img(screenShotLocation)));
		}

	}

	public static void skip(String string, String screenShotLocation) {
		if (ConfigFileRead.readkey("isSkippedScreenShotRequired").equalsIgnoreCase("yes")) {
			ExtentReportManager.getExtentTest().log(com.relevantcodes.extentreports.LogStatus.SKIP, string,
					ExtentReportManager.getExtentTest().addBase64ScreenShot(
							"data:image/png;base64," + TestUtils.generateBase64Img(screenShotLocation)));
		}

	}
}
