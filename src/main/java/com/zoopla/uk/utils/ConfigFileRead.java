package com.zoopla.uk.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;

import com.zoopla.uk.drivers.InitializeDriver;

public class ConfigFileRead {
	/**
	 * 
	 * This is used for searching property file based on key returns a value of the
	 * particular key in string format. it's a class with static methods.
	 * 
	 */

	private static final File configFile;
	private static Properties prop;
	private static final Logger log = LogManager.getLogger(ConfigFileRead.class);

	static {
		// Get the value from properties file
		configFile = new File(System.getProperty("user.dir") + "/src/test/resources/" + "config.properties");
		log.info("Config file path is " + configFile);
	}

	public static String readkey(String key) {
		String value = "";
		try {
			prop = new Properties();
			prop.load(new FileReader(configFile));
			// log.debug("key is " + key);
			value = prop.getProperty(key).toString();
			if (value == null) {
				throw new EmptyPropretyException(key, configFile.getAbsolutePath().toString());
			} else {
				log.debug("Value is " + value + " ,for key " + key);
			}

		} catch (IOException e) {
			log.error("Something wrong, config file setup failed");
			e.printStackTrace();
		} catch (EmptyPropretyException e) {
			log.error("Check property file, it might be empty.");
			e.printStackTrace();
		}
		return value;
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

class EmptyPropretyException extends Exception {
	// This class is to generate custom empty property exception.

	private static final Logger log = LogManager.getLogger(EmptyPropretyException.class);

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param key
	 *            accepts key which needs to be searched in property file
	 * @param configFile
	 *            config file with absolute path
	 * 
	 */
	EmptyPropretyException(String key, String configFile) {
		super(key);
		log.error("Error is fetching " + key + " ,please verify in " + configFile + " file");
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
