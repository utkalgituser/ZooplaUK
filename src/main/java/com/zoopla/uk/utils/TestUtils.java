package com.zoopla.uk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestUtils {
	private static final Logger log = Logger.getLogger(TestUtils.class.getName());

	// Static variable for page load and implicit wait
	public static long PAGE_LOAD_TIMEOUT = 300;
	public static long IMPLICIT_WAIT_0 = 0;
	public static long IMPLICIT_WAIT_20 = 300;
	
	public TestUtils(WebDriver driver) {
		
	}
	
	/**
	 * 
	 * @param driver
	 *            for WebDriver timeout setup
	 */
	public void basicTimeoutSetup(WebDriver driver) {
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		log.info("finished basic setup");
	}
	
	public Date getCalendar() {
		return new Date();
	}
	
	/**
	 * 
	 * @param driver
	 *            accepts WebDriver instance as argument.
	 * @param xpath
	 */
	public void explicitlyWaitXpath(WebDriver driver, By element) {
		try {
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_0, TimeUnit.SECONDS);
			new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(element)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param driver accepts webDriver instance as argument 
	 * @return	returns a boolean value based on visibility condition
	 * @param titleToMatch	matched with actual title from web page
	 */
	public boolean verifyTitle(WebDriver driver, String titleToMatch) {
		boolean isDisplayed = false;
		if (driver.getTitle().equalsIgnoreCase(titleToMatch)) {
			isDisplayed = true;
			log.debug("Title match is success and changing status to "+isDisplayed);
		}
		return isDisplayed;
	}
	
	/**
	 * 
	 * @param driver
	 *            accepts WebDriver instance as argument.
	 * @param element
	 *            accepts Web element instance as argument
	 */
	public void waitForVisibility(WebDriver driver, WebElement element) {
		try {
			log.info("Waiting for element visibilty "+element);
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_0, TimeUnit.SECONDS);
			log.info("Setting to implicitly Wait to "+IMPLICIT_WAIT_0);
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(element));
			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_20, TimeUnit.SECONDS);
			log.info("Setting to implicitly Wait to "+IMPLICIT_WAIT_20);
		}catch (Exception e) {
			log.error(e.getCause().toString());
		}

	}

	public void waitForLinkToAppear(WebDriver driver, String linkText) {
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_0, TimeUnit.SECONDS);
		new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
	}

	public void elementToBeclickable(WebDriver driver, WebElement element) {
		if (driver != null) {
			try {
				driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_0, TimeUnit.SECONDS);
				new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(element));
				driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_20, TimeUnit.SECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new NullWebDriverException("Driver instance is null");
			} catch (NullWebDriverException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * @return simple date format in the form of "dd_mm_yyyy_hh_mm_ss.S".
	 */
	public SimpleDateFormat getDateformatter() {
		return new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss.S");
	}

	// ************************* JAVA SCRIPT *************************
	/**
	 * 
	 * @param driver
	 *            accepts WebDriver instance as argument. This method is to play
	 *            video available in UI.
	 */
	public void playVideo(WebDriver driver) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementById(\"video\").play()");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * @param driver
	 *            accepts WebDriver instance as argument.
	 * @param element
	 *            accepts Web element instance as argument.
	 * 
	 */
	public void scrollToLocationByJavaScriptAndClick(WebDriver driver, WebElement element) {
		if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("window.scrollTo(0," + element.getLocation().y + ")");
			jsExecutor.executeScript("window.scrollTo(0," + element.getLocation().x + ")");
			element.click();
		}
	}

	/**
	 * 
	 * @param driver
	 *            accepts WebDriver instance as argument.
	 * @param element
	 *            accepts Web element instance as argument
	 */
	public void javaScriptExecutorClick(WebDriver driver, WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", element);		
		log.info("Completed javaScriptExecutor click operation");
	}

	/**
	 * 
	 * @param driver
	 *            accepts WebDriver instance as argument. this method can be used to
	 *            scroll to bottom of page
	 */
	public static void scrollToBottomOfPage(WebDriver driver) {
		try {
			long Height = Long.parseLong(
					((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight").toString());

			while (true) {
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
				Thread.sleep(500);

				long newHeight = Long.parseLong(
						((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight").toString());
				if (newHeight == Height) {
					break;
				}
				Height = newHeight;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param driver
	 *            accepts WebDriver instance as argument. this method can be used to
	 *            scroll to top of the page
	 */
	public void scrollToTopOfPage(WebDriver driver) {

		((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,0);");

	}

	/**
	 * 
	 * @param driver
	 *            accepts WebDriver instance as argument.
	 * @param element
	 *            accepts Web element instance as argument. this method can be used
	 *            to scroll to top of the page
	 */
	public void javaScriptScrollIntoViewElement(WebDriver driver, WebElement element) {

		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			log.info("scrolled to the target element " + element);
		} catch (Exception e) {
			log.error("Failed to scrolled to the element using Java script.");
			log.error(e.getCause().toString());
		}

	}

	/**
	 * 
	 * @param driver
	 *            accepts WebDriver instance as argument.
	 * @param textBox
	 *            accepts Web element instance as argument
	 * @param input
	 *            accepts character sequence as input
	 * 
	 */
	public void sendTextForTextBox(WebDriver driver, WebElement textBox, String inputString) {
		waitForVisibility(driver, textBox);
		if (textBox.isEnabled()) {
			textBox.sendKeys(inputString);
		}
	}

	public int generateRandomNumber(int howmany, int range) {
		Random ran = new Random();
		int randomNumbers = 0;
		for (int i = 0; i < howmany; i++) {
			randomNumbers = ran.nextInt(range);
		}
		System.out.println("random number is " + randomNumbers);
		log.info("random number is  " + randomNumbers);
		return randomNumbers;
	}

	// FindBy over loaded method
	public WebElement findByVisibleText(WebDriver driver, String linkText) {
		return driver.findElement(By.linkText(linkText));
	}

	public WebElement findByXpath(WebDriver driver, String linkText) {

		return driver.findElement(By.xpath(linkText));
	}

	public int findNumberofElementsXpath(WebDriver driver, String xpath) {
		log.info("Going to find number of elements");
		return driver.findElements(By.xpath(xpath)).size();
	}

	public boolean verifyTextByXpath(WebDriver driver, String xpath, String expectedText) {
		boolean result = false;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		String actText = driver.findElement(By.xpath(xpath)).getText();
		System.out.println("expectedText is " + expectedText + " actText is " + actText);
		if (actText.equals(expectedText)) {
			System.out.println("matching text");
			result = true;
		} else {
			System.out.println("Text is not matching");
			return result = false;
		}
		return result;
	}

	public boolean verifyTextByWebelemt(WebDriver driver, String elementText, String expectedText) {
		boolean result = false;
		try {
			// WebDriverWait wait = new WebDriverWait(driver, 20);
			// wait.until(ExpectedConditions.presenceOfElementLocated((By)
			// element));
			// String actText = element.getText();
			System.out.println("expectedText is " + expectedText + " actText is " + elementText);
			if (elementText.equals(expectedText)) {
				System.out.println("matching text");
				result = true;
			} else {
				System.out.println("No text match");
				return result = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return result = false;
		}
		return result;
	}

	public void select(WebDriver driver, String selBoxXpath, int index) {
		WebElement selWb = driver.findElement(By.xpath(selBoxXpath));
		Select sel = new Select(selWb);
		sel.selectByIndex(index);
	}

	public void select(WebDriver driver, String selBoxXpath, String visibleText) {
		WebElement selWb = driver.findElement(By.xpath(selBoxXpath));
		Select sel = new Select(selWb);
		sel.selectByVisibleText(visibleText);
	}

	public void select(WebDriver driver, WebElement selBoxWb, String byValue) {
		Select sel = new Select(selBoxWb);
		sel.selectByVisibleText(byValue);
	}

	public void acceptAlert(WebDriver driver) {
		Alert alt = driver.switchTo().alert();
		System.out.println(alt.getText());
		alt.accept();
	}

	public void rejectAlert(WebDriver driver) {
		Alert alt = driver.switchTo().alert();
		System.out.println(alt.getText());
		alt.dismiss();
	}

	// Scroll down the web page by 200 pixels
	public void scrollDown(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,200)");
	}

	public void scrollRight(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(200,0)");
	}

	public void moveToElement(WebDriver driver, WebElement ele) {
		if (driver != null) {
			Actions act = new Actions(driver);
			act.moveToElement(ele).perform();
		} else {
			try {
				throw new NullWebDriverException("Driver instance is null");
			} catch (NullWebDriverException e) {
				e.printStackTrace();
			}
		}
	}

	public void actionsEscape(WebDriver driver) {
		if (driver != null) {
			Actions act = new Actions(driver);
			act.sendKeys(Keys.ESCAPE).perform();
		} else {
			try {
				throw new NullWebDriverException("Driver instanace is null");
			} catch (NullWebDriverException e) {
				e.printStackTrace();
			}
		}
	}

	public void switchToDefaultContent(WebDriver driver, String xpath) {
		driver.switchTo().defaultContent();
	}

	public void switchToFrame(WebDriver driver, String xpath) {
		driver.switchTo().frame(xpath);
	}

	public void verifyText(WebDriver driver, String text) {

	}

	/**
	 * 
	 * @param driver
	 *            provide driver instance
	 * @return the iterator instance to worked upon
	 */
	public Iterator<String> getAllWindowhandles(WebDriver driver) {
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> itr = handles.iterator();
		return itr;
	}

	/**
	 * 
	 * @param xpath
	 *            that will be worked upon
	 * @param dataToReplace
	 *            This value depends what you want replace with real time
	 * @return this return of of By, to use in findElement()
	 */
	public By createDynamicXpath(String xpath, String dataToReplace) {
		String rawXpath = xpath.replaceAll("$replace_with$", dataToReplace);
		return By.xpath(rawXpath);
	}

	/**
	 * 
	 * @param options
	 *            this method accepts ChromeOptions as argument and used to disable
	 *            image loading on Chrome browser
	 */
	public void disableImg(ChromeOptions options) {
		HashMap<String, Object> images = new HashMap<String, Object>();
		images.put("images", 2);
		HashMap<String, Object> pref = new HashMap<String, Object>();
		pref.put("profile.default_content_setting_values", images);
		options.setExperimentalOption("prefs", pref);
	}

	/**
	 * 
	 * @param options
	 *            this method accepts ChromeOptions as argument and used to disable
	 *            image loading on firefox browser
	 */
	public void disableImg(FirefoxOptions options) {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("permissions.default.image", 2);
		options.setProfile(profile);
		options.setCapability(FirefoxDriver.PROFILE, profile);
	}
	
	/**
	 * 
	 * @param url accepts URL in String format 
	 * @param whichPartOfArray	After splitting the Url with "?", which part of String[] is required for further processing 
	 * @return return a string. 
	 */
	public String splitAndExtractID(String url, int whichPartOfArray) {
		String finalID=null;
		try {
			String[] part=url.split("\\?");
			finalID=part[whichPartOfArray].replaceAll("\\D","");
			log.debug("Final ID is "+finalID);
		}catch (Exception e) {
			log.error(e.getCause().toString());
		}
		return finalID;
	}
	
}
