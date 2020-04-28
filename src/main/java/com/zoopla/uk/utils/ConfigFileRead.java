package com.zoopla.uk.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigFileRead {
	/**
	 * 
	 * This is used for searching property file based on key returns a value of the
	 * particular key in string format. it's a class with static methods.
	 * 
	 */

	private static final File configFile;
	private static Properties prop;
	private static final Logger log = Logger.getLogger(ConfigFileRead.class.getName());

	static {
		// Get the value from properties file
		configFile = new File(System.getProperty("user.dir") + "/src/test/resources/" + "config.properties");
		log.info("Config file path is " + configFile);
	}

	public static String readConfigFile(String key) {
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

}

class EmptyPropretyException extends Exception {
	/**
	 * 
	 * This class is to generate empty property exception. Accepts 2 arguments, key
	 * which is being searched in property file and filename as string format.
	 * 
	 */
	private static final Logger log = Logger.getLogger(EmptyPropretyException.class.getName());

	private static final long serialVersionUID = 1L;

	EmptyPropretyException(String key, String configFile) {
		super(key);
		log.error("Error is fetching " + key + " ,please verify in " + configFile + " file");
	}

}
