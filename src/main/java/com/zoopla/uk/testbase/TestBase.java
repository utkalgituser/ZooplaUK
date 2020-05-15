package com.zoopla.uk.testbase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.zoopla.uk.drivers.DriverManager;
import com.zoopla.uk.drivers.InitializeDriver;
import com.zoopla.uk.reports.ExtentReport;

/**
 * @author UTKAL This abstract base file for driver creation and is going to
 *         extended to other files.
 */
public abstract class TestBase {
	private static final Logger log = LogManager.getLogger(TestBase.class);

	@BeforeSuite
	public void beforeSuite() {
		log.info("Going to initialize Extent Report");
		ExtentReport.initialize();
	}

	@BeforeMethod
	public void beforeMethod() {
		log.info("Going to initialize driver");
		InitializeDriver.intialize();
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		if (DriverManager.getDriver() != null) {
			log.warn("Going close driver instance " + DriverManager.getDriver());
			DriverManager.getDriver().close();
			// DriverManager.setDriver(null);
		}
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		// Appends the HTML file with all the ended tests. There must be at least 1
		// ended test for anything to be appended to the report.
		ExtentReport.report.flush();
		log.info("Extent Report creation completed");
		if (DriverManager.getDriver() != null) {
			try {
				log.warn("Going quit driver instance " + DriverManager.getDriver());
				DriverManager.getDriver().quit();
			} catch (Exception e) {
				log.error(e.getCause().toString());
			}
		}
	}

	/*
	 * public void baseSetup() { try { isrporterLogRequired =
	 * Boolean.valueOf(ConfigFileRead.readkey("rpoerterLogStatus"));
	 * logDebug("Reporterrr log status " + isrporterLogRequired); } catch (Exception
	 * e) { logError("Failed in TestBase constructor");
	 * logError(e.getCause().toString()); } }
	 * 
	 * public void initializeDriver() { log.
	 * info("Going to created webdriver instance and webevent listener for logging"
	 * ); listingHrefs = new ConcurrentHashMap<>(); try { if
	 * ((ConfigFileRead.readkey("browser")).equalsIgnoreCase("chrome")) { // This
	 * will reduce the Chrome driver error logs
	 * System.setProperty("webdriver.chrome.silentOutput", "true"); ChromeOptions
	 * chromeOptions=new ChromeOptions(); // this can be used to reduce log levels
	 * // chromeOptions.addArguments("--log-level=3");
	 * WebDriverManager.chromedriver().setup(); driver = new
	 * ChromeDriver(chromeOptions); logDebug("Driver is " + driver.toString());
	 * basicTimeoutSetup(driver); eventDriverSetup(driver);
	 * logDebug("Chrome driver creation completed"); } else if
	 * ((ConfigFileRead.readkey("browser")).equalsIgnoreCase("firefox")) { // Below
	 * 2 lines used to reduce Firefox log levels so that application log // remain
	 * clean. System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,
	 * "true"); System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
	 * System.getProperty("user.dir") + "\\driverLogs.log");
	 * WebDriverManager.firefoxdriver().setup(); driver = new FirefoxDriver();
	 * logDebug("Driver is " + driver.toString()); basicTimeoutSetup(driver);
	 * eventDriverSetup(driver); logDebug("firefox driver creation completed"); }
	 * else { WebDriverManager.edgedriver().setup(); driver = new EdgeDriver();
	 * logDebug("Driver is " + driver.toString()); basicTimeoutSetup(driver);
	 * eventDriverSetup(driver); logDebug("EdgeDriver creation completed"); }
	 * driver.get(ConfigFileRead.readkey("url")); logDebug("Navigating to URL " +
	 * ConfigFileRead.readkey("url")); } catch (Exception e) { LogStatus.fail(e);
	 * logError("Exception occured in driver init stage");
	 * logError(e.getStackTrace().toString()); } }
	 * 
	 *//**
		 * 
		 * @param driver
		 *            for WebDriver timeout setup
		 *//*
			 * public void basicTimeoutSetup(WebDriver driver) {
			 * driver.manage().window().maximize(); driver.manage().deleteAllCookies();
			 * driver.manage().timeouts().implicitlyWait(GlobalConstants.IMPLICIT_WAIT_20,
			 * TimeUnit.SECONDS);
			 * log.debug("Setting to implicitly Wait to "+GlobalConstants.IMPLICIT_WAIT_20);
			 * driver.manage().timeouts().pageLoadTimeout(GlobalConstants.PAGE_LOAD_TIMEOUT,
			 * TimeUnit.SECONDS);
			 * log.debug("Setting to implicitly Wait to "+GlobalConstants.PAGE_LOAD_TIMEOUT)
			 * ; log.
			 * info("Finished basic setup for maximize, delete all cookies, global timeouts."
			 * ); }
			 * 
			 * public void eventDriverSetup(WebDriver driver) { if (driver != null) {
			 * eventDriver = new EventFiringWebDriver(driver); eventListeners = new
			 * WebEventListeners(); eventDriver.register(eventListeners); driver =
			 * eventDriver; DriverManager.setDriver(driver); } else { try { throw new
			 * NullWebDriverException("Driver instance is null, please check"); } catch
			 * (NullWebDriverException e) { log.error(e.getCause().toString());; } }
			 * 
			 * }
			 */

	/*
	 * public void logDebug(String data) { log.debug(data); if
	 * (isrporterLogRequired) { Reporter.log(data, true); } }
	 * 
	 * public void logError(String data) { log.error(data); if
	 * (isrporterLogRequired) { Reporter.log(data, true); } }
	 */
}
