package com.zoopla.uk.reports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentReport {
	public static ExtentReports report = null;

	private static final Logger log = LogManager.getLogger(ExtentReport.class);

	// private constructor to limit initialization to one
	private ExtentReport() {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy_ hh_mm_ss");
			Date date = new Date();
			String currentDate = formatter.format(date);
			log.info("currentDate is " + currentDate);
			report = new ExtentReports(".\\ExtentReports\\Test_Automation_Report_" + currentDate + ".html");
			log.info("Report html file is " + report);
			report.loadConfig(new File(".\\src\\test\\resources\\extentreport.xml"));
		} catch (Exception e) {
			log.error(e.getCause().toString());
		}
	}

	// Initializes Extent Report
	public static void initialize() {
		ExtentReport report = new ExtentReport();
		log.info("Extent report object creation completed " + report);
	}
}
