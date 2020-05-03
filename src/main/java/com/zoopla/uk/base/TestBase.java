package com.zoopla.uk.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.varia.NullAppender;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;

import com.zoopla.uk.utils.ConfigFileRead;
import com.zoopla.uk.utils.TestUtils;
import com.zoopla.uk.utils.WebEventListeners;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author UTKAL This abstract base file for driver creation and is going to
 *         extended to other files.
 */
public abstract class TestBase {
	public WebDriver driver;
	public WebEventListeners eventListeners;
	public EventFiringWebDriver eventDriver;
	public static boolean isrpoerterLogRequired;
	public static Map<String, String> listingHrefs;
	private static final Logger log = Logger.getLogger(TestBase.class.getName());
	private static String OS = System.getProperty("os.name").toLowerCase();

	public void baseSetup() {
		try {
			// Log4j and reporter log setup
			log.info("In TestBase Constructor and going to initialize log file");
			BasicConfigurator.configure(new NullAppender());
			PropertyConfigurator
					.configure(System.getProperty("user.dir") + "\\src\\main\\resouces\\Log4j.properties");
			isrpoerterLogRequired = Boolean.valueOf(ConfigFileRead.readConfigFile("rpoerterLogStatus"));
			logDebug("Reporterrr log status " + isrpoerterLogRequired);
		} catch (Exception e) {
			log.error("Failed in TestBase constructor");
			e.printStackTrace();
		}
	}

	public void initializeDriver() {
		log.info("Going to created webdriver instance and webevent lister for logging");
		listingHrefs=new ConcurrentHashMap<>();
		try {
			if ((ConfigFileRead.readConfigFile("browser")).equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.debug("Driver is " + driver.toString());
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(TestUtils.IMPLICIT_WAIT_20, TimeUnit.SECONDS);
				// driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT,
				// TimeUnit.SECONDS);
				driver.manage().deleteAllCookies();
				eventDriver = new EventFiringWebDriver(driver);
				eventListeners = new WebEventListeners();
				eventDriver.register(eventListeners);
				driver = eventDriver;
				log.debug("Chrome driver creation completed");
			} else if ((ConfigFileRead.readConfigFile("browser")).equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(TestUtils.IMPLICIT_WAIT_20, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
				driver.manage().deleteAllCookies();
				eventDriver = new EventFiringWebDriver(driver);
				eventListeners = new WebEventListeners();
				eventDriver.register(eventListeners);
				driver = eventDriver;
				log.debug("firefox driver creation completed");
			} else {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(TestUtils.IMPLICIT_WAIT_20, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
				driver.manage().deleteAllCookies();
				eventDriver = new EventFiringWebDriver(driver);
				eventListeners = new WebEventListeners();
				eventDriver.register(eventListeners);
				driver = eventDriver;
				log.debug("EdgeDriver creation completed");
			}
			driver.get(ConfigFileRead.readConfigFile("url"));
			log.debug("Navigating to URL " + ConfigFileRead.readConfigFile("url"));
			log.debug("Driver is " + driver.toString());
		} catch (Exception e) {
			log.error("Exception occured in driver init stage");
			e.printStackTrace();
		}
	}

	public void logDebug(String data) {
		log.debug(data);
		if (isrpoerterLogRequired) {
			Reporter.log(data, true);
		}
	}
}
