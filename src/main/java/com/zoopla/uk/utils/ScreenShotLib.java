package com.zoopla.uk.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.zoopla.uk.drivers.InitializeDriver;
import com.zoopla.uk.testbase.TestBase;

public class ScreenShotLib extends TestBase {

	static TestUtils testUtils;
	private static final Logger log = LogManager.getLogger(ScreenShotLib.class);
	public static String screenshotFolder;

	static {
		try {
			// getProperty --> Gets the system property indicated by the specified key.
			// getAbsoluteFile --> Returns the absolute form of this abstract
			// pathname. Equivalent to new File(this.getAbsolutePath())
			screenshotFolder = new File(System.getProperty("user.dir")).getAbsoluteFile()
					+ "\\screenshot\\";
			logDebug("Screenshot folder creation " + screenshotFolder+" is successful");
		} catch (SecurityException e) {
			logError(e.getStackTrace().toString());;
			logError("Error is creating screenshot directory");
		}
	}

	public static void takeElementScreenShot(WebDriver driver, WebElement ele, String TCname) {
		logDebug("TCname is -->> "+TCname);
		if (TCname == "" || TCname==null) {
			TCname = "blank";
		}
		try {
			testUtils = new TestUtils(driver);

			// create destination file
			File destFile = new File(screenshotFolder + "\\failure_screenshot\\" + TCname + "_"
					+ testUtils.getDateformatter().format(testUtils.getCalendar()) + ".png");
			logDebug("destFile is "+destFile);
			// Indicates a driver that can capture a screenshot and store it in
			// different ways. create screenshot variable
			// Capture the screenshot and store it in the specified location.
			TakesScreenshot ts = (TakesScreenshot) driver;
			// create screen shot file type
			File srcFile = ts.getScreenshotAs(OutputType.FILE);

			// Returns a BufferedImage as the result of decoding a supplied File
			// with an ImageReader chosen automatically from among those
			// currently registered. The File is wrapped in an ImageInputStream.
			// If no registered ImageReader claims to be able to read the
			// resulting stream, null is returned.
			BufferedImage img = ImageIO.read(srcFile);

			// get the element location
			Point p = ele.getLocation();
			BufferedImage subImg = img.getSubimage(p.getX(), p.getY(), ele.getSize().getWidth(),
					ele.getSize().getHeight());
			// Writes an image using an arbitrary ImageWriter that supports the given format
			// to a File. If there is already a File present, its contents are discarded.
			ImageIO.write(subImg, "png", srcFile);
			FileUtils.copyFile(srcFile, destFile);
			// this will helps to link the screen shot to the report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'><img src='" + destFile.getAbsolutePath()
					+ "'height='100''width='100'/></a>");
		} catch (WebDriverException e) {
			e.printStackTrace();
			logError("error in taking screen shot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logError("error writing the image to disk");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logError("error while taking element ScreenShot");
			logError(e.getCause().toString());
		}
	}

	public static String takeScreenShot(WebDriver driver, String TCname) {
		logDebug("TCname is -->> "+TCname);
		if (TCname == "" || TCname==null) {
			TCname = "blank";
		}
		File destFile = null;
		try {
			destFile = new File(screenshotFolder + "\\failure_screenshot\\" + TCname + "_"
					+ testUtils.getDateformatter().format(testUtils.getCalendar()) + ".png");
			logDebug("destFile is "+destFile);
			TakesScreenshot ts = (TakesScreenshot) driver;
			File srcFile = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, destFile);
			// this will helps to link the screen shot to the report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'><img src='" + destFile.getAbsolutePath()
					+ "'height='100''width='100'/></a>");
		} catch (Exception e) {
			logError("Error in taking screen shot, please check");
			logError(e.getCause().toString());
		}
		return destFile.toString();
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
