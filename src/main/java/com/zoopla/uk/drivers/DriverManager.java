package com.zoopla.uk.drivers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * @author UTKAL ThreadLocal provides safer means to run parallel execution, by
 *         providing variables which can be accessed by the same thread. Though
 *         the same code is run by multiple threads and the code has access to
 *         the same ThreadLocal variable, the threads can't view each other's
 *         ThreadLocal variable. This provides a simple way to make code thread
 *         safe.
 */
public class DriverManager {
	private static final Logger log = LogManager.getLogger(DriverManager.class);
	private static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();

	public static WebDriver getDriver() {
		return threadLocal.get();
	}

	public static void setDriver(WebDriver driver) {
		try {
			log.info("Going to set driver reference for threadlocal " + driver);
			threadLocal.set(driver);
		} catch (Exception e) {
			log.error(e.getCause().toString());
		}
	}
}
