package com.zoopla.uk.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.zoopla.uk.base.TestBase;

public class ScreenShotLib extends TestBase {

	static TestUtils testUtils;
	private static final Logger log = Logger.getLogger(ScreenShotLib.class.getName());
	public static String screenshotFolder;

	static {
		try {
			// getProperty --> Gets the system property indicated by the
			// specified key.
			// getAbsoluteFile --> Returns the absolute form of this abstract
			// pathname. Equivalent to new File(this.getAbsolutePath())
			screenshotFolder = new File(System.getProperty("user.dir")).getAbsoluteFile()
					+ "/src/main/java/com/aricent/seachange/screenshot/";
			// log("screen shot folder is " + screenshotFolder);
		} catch (SecurityException e) {
			e.printStackTrace();
			log.error("Error is creating screen shot directory");
		}
	}

	public static void takeElementScreenShot(WebDriver driver, WebElement ele, String name) {
		if (name == "") {
			name = "blank";
		}
		try {
			testUtils = new TestUtils(driver);

			// create destination file
			File destFile = new File(screenshotFolder + "/failure_screenshot/" + name + "_"
					+ testUtils.getDateformatter().format(testUtils.getCalendar()) + ".png");
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
			log.error("error in taking screen shot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("error writing the image to disk");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("error while taking element ScreenShot");
			e.printStackTrace();
		}
	}

	public static String takeScreenShot(WebDriver driver, String name) {
		if (name == "") {
			name = "blank";
		}
		File destFile = null;
		try {
			destFile = new File(screenshotFolder + "/failure_screenshot/" + name + "_"
					+ testUtils.getDateformatter().format(testUtils.getCalendar()) + ".png");
			TakesScreenshot ts = (TakesScreenshot) driver;
			File srcFile = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, destFile);
			// this will helps to link the screen shot to the report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'><img src='" + destFile.getAbsolutePath()
					+ "'height='100''width='100'/></a>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("error in taking screen shot");
			e.printStackTrace();
		}
		return destFile.toString();
	}
}
