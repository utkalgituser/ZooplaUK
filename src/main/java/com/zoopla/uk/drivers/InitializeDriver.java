package com.zoopla.uk.drivers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;

import com.zoopla.uk.listeners.WebEventListeners;
import com.zoopla.uk.reports.LogStatus;
import com.zoopla.uk.utils.ConfigFileRead;
import com.zoopla.uk.utils.GlobalConstants;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author UTKAL
 *
 */
public class InitializeDriver {
	public WebDriver driver = null;
	public WebEventListeners eventListeners;
	public EventFiringWebDriver eventDriver;
	public static boolean isreporterLogRequired;
	public static boolean imageLoadingOption;
	public static Map<String, String> listingHrefs;
	public DesiredCapabilities desiredCapabilities;
	private static String OS = System.getProperty("os.name").toLowerCase();

	private static Logger log = LogManager.getLogger(InitializeDriver.class);

	private InitializeDriver() {
		log.info("Inside InitializeDriver method");
		isreporterLogRequired = Boolean.valueOf(ConfigFileRead.readkey("reporterLogStatus"));
		imageLoadingOption = Boolean.valueOf(ConfigFileRead.readkey("isImageLoadingDisabled"));
		logDebug("Reporter log status " + isreporterLogRequired);
		logDebug("Image Loading Disabled status " + imageLoadingOption);
		if (ConfigFileRead.readkey("runtype").equalsIgnoreCase("local")) {
			log.info("Runtype local is configured");
			setUpForLocal();
		} else if (ConfigFileRead.readkey("runtype").equalsIgnoreCase("remote")) {
			log.info("Runtype remote is configured");
			setUpForRemote(driver);
		} else {
			try {
				throw new Exception(
						"Please check runtype in config.properties file, in case these is mismatch or missing");
			} catch (Exception e) {
				log.fatal("Webdriver initialization failed.");
			}
		}
		eventDriverSetup();
		basicTimeoutSetup();
		logDebug("Chrome driver creation completed");
		driver.get(ConfigFileRead.readkey("url"));
		logDebug("Navigating to URL " + ConfigFileRead.readkey("url"));
	}

	/**
	 * 
	 */
	private void setUpForRemote(WebDriver driver) {
		String browserRemote = ConfigFileRead.readkey("browser");

		switch (browserRemote) {
		case "firefox":
			desiredCapabilities = DesiredCapabilities.firefox();
			desiredCapabilities.setBrowserName("FIREFOX");
			desiredCapabilities.setPlatform(Platform.ANY);
			FirefoxOptions firefoxOptions1 = new FirefoxOptions();
			disableFirefoxImages(ConfigFileRead.readkey("browser"), firefoxOptions1);
			try {
				driver = new RemoteWebDriver(new URL(ConfigFileRead.readkey("RemoteURL")), desiredCapabilities);
			} catch (MalformedURLException e) {
				log.info("Please check remote URL");
				logError(e.getStackTrace().toString());
			}
			break;
		case "chrome":
			desiredCapabilities = DesiredCapabilities.chrome();
			desiredCapabilities.setBrowserName("CHROMEs");
			desiredCapabilities.setPlatform(Platform.ANY);
			ChromeOptions chromeOptions = new ChromeOptions();
			disableChromeImages(ConfigFileRead.readkey("browser"), chromeOptions);
			try {
				driver = new RemoteWebDriver(new URL(ConfigFileRead.readkey("RemoteURL")), desiredCapabilities);
			} catch (MalformedURLException e) {
				log.info("Please check remote URL");
				logError(e.getStackTrace().toString());
			}
			break;
		case "edge":
			desiredCapabilities = DesiredCapabilities.edge();
			desiredCapabilities.setBrowserName("edge");
			desiredCapabilities.setPlatform(Platform.ANY);
			try {
				driver = new RemoteWebDriver(new URL(ConfigFileRead.readkey("RemoteURL")), desiredCapabilities);
			} catch (MalformedURLException e) {
				log.info("Please check remote URL");
				logError(e.getStackTrace().toString());
			}
			break;
		default:
			desiredCapabilities = DesiredCapabilities.firefox();
			desiredCapabilities.setBrowserName("firefox");
			desiredCapabilities.setPlatform(Platform.ANY);
			FirefoxOptions firefoxOptions2 = new FirefoxOptions();
			disableFirefoxImages(ConfigFileRead.readkey("browser"), firefoxOptions2);
			try {
				driver = new RemoteWebDriver(new URL(ConfigFileRead.readkey("RemoteURL")), desiredCapabilities);
			} catch (MalformedURLException e) {
				log.info("Please check remote URL");
				logError(e.getStackTrace().toString());
			}
			break;
		}
	}

	/**
	 * 
	 */
	private void setUpForLocal() {
		log.info("Going to created webdriver instance and webevent listener for logging");
		listingHrefs = new ConcurrentHashMap<>();
		try {
			if ((ConfigFileRead.readkey("browser")).equalsIgnoreCase("chrome")) {
				// This will reduce the Chrome driver error logs
				System.setProperty("webdriver.chrome.silentOutput", "true");
				ChromeOptions chromeOptions = new ChromeOptions();
				// disableChromeImages(ConfigFileRead.readkey("browser"), chromeOptions);
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(chromeOptions);
				logDebug("Driver is " + driver.toString());
			} else if ((ConfigFileRead.readkey("browser")).equalsIgnoreCase("firefox")) {
				// Below 2 lines used to reduce Firefox log levels so that application log
				// remain clean.
				System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
						System.getProperty("user.dir") + "\\driverLogs.log");
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				disableFirefoxImages(ConfigFileRead.readkey("browser"), firefoxOptions);
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				logDebug("Driver is " + driver.toString());
				logDebug("Firefox driver creation completed");
			} else if ((ConfigFileRead.readkey("browser")).equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				logDebug("Driver is " + driver.toString());
				logDebug("EdgeDriver creation completed");
			} else {
				// Below 2 lines used to reduce Firefox log levels so that application log
				// remain clean.
				System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
						System.getProperty("user.dir") + "\\driverLogs.log");
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				disableFirefoxImages(ConfigFileRead.readkey("browser"), firefoxOptions);
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				logDebug("Driver is " + driver.toString());
			}
		} catch (Exception e) {
			LogStatus.fail(e.getStackTrace().toString());
			logError("Exception occured in driver init stage");
			logError(e.getStackTrace().toString());
		}
	}

	/**
	 * 
	 * @param driver
	 *            for WebDriver timeout setup
	 */
	public void basicTimeoutSetup() {
		log.info("Inside basicTimeoutSetup");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(GlobalConstants.IMPLICIT_WAIT_20, TimeUnit.SECONDS);
		logDebug("Setting to implicitly Wait to " + GlobalConstants.IMPLICIT_WAIT_20);
		driver.manage().timeouts().pageLoadTimeout(GlobalConstants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		logDebug("Setting to implicitly Wait to " + GlobalConstants.PAGE_LOAD_TIMEOUT);
		log.info("Finished basic setup for maximize, delete all cookies, global timeouts.");
	}

	public void eventDriverSetup() {
		log.info("Inside eventDriverSetup");
		if (driver != null) {
			eventDriver = new EventFiringWebDriver(driver);
			eventListeners = new WebEventListeners();
			eventDriver.register(eventListeners);
			driver = eventDriver;
			DriverManager.setDriver(driver);
		} else {
			try {
				// throw new NullWebDriverExceptions("Driver instance is null, please check");
			} catch (Exception e) {
				logError(e.getCause().toString());
			}
		}

	}

	public static void intialize() {
		// if (DriverManager.getDriver() == null) {
		try {
			new InitializeDriver();
		} catch (Exception e) {
			logError("Failed in driver initialization");
			logError("%%%%%%%%%%% " + e.getCause().toString());
		}

		// }
	}

	public void disableChromeImages(String browserName, ChromeOptions chromeOptions) {
		if (imageLoadingOption == true) {
			try {
				HashMap<String, Object> imageSetting = new HashMap<String, Object>();
				imageSetting.put("images", 2);
				HashMap<String, Object> imageLoadingPreferences = new HashMap<String, Object>();
				imageLoadingPreferences.put("profile.default_content_setting_values", imageSetting);
				chromeOptions.setExperimentalOption("preferences", imageLoadingPreferences);
				logDebug("Images will be disabled from now");
			} catch (Exception e) {
				logError("Error in disabling image");
				logError(e.getStackTrace().toString());
			}
		}
	}

	public void disableFirefoxImages(String browserName, FirefoxOptions firefoxOptions) {
		if (imageLoadingOption == true) {
			try {
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				firefoxProfile.setPreference("permissions.default.image", 2);
				firefoxOptions.setProfile(firefoxProfile);
				firefoxOptions.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
				logDebug("Images will be disabled from now");
			} catch (Exception e) {
				logError("Error in setting disabling image");
				logError(e.getStackTrace().toString());
			}
		}
	}

	public static void logInfo(String data) {
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
