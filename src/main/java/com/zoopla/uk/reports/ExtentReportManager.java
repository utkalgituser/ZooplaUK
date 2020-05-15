package com.zoopla.uk.reports;

import com.relevantcodes.extentreports.ExtentTest;

/**
 * @author UTKAL
 *
 */
public class ExtentReportManager {
	private static ThreadLocal<ExtentTest> extentTest=new ThreadLocal<ExtentTest>();
	
	public static ExtentTest getExtentTest() {
		return extentTest.get();
	}

	public static void setExtentTest(ExtentTest test) {
		extentTest.set(test);
	}
}
