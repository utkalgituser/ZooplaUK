package com.zoopla.uk.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.zoopla.uk.base.TestBase;
import com.zoopla.uk.utils.TestUtils;

public class HomePage extends TestBase {

	private static final Logger log = Logger.getLogger(HomePage.class.getName());
	public TestUtils testUtils;

	@FindBy(id = "search-input-location")
	private WebElement searchBox;

	@FindBy(xpath = "//form[@id='cookie-consent-form']//button[@class='ui-button-primary ui-cookie-accept-all-medium-large']")
	private WebElement acceptCookiesBtn;

	@FindBy(xpath = "//form//h2")
	private WebElement cookieTitleHeaderTxt;

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		testUtils = new TestUtils(driver);
	}

	public WebElement getAcceptCookiesBtn() {
		return acceptCookiesBtn;
	}

	public WebElement getCookieTitleHeaderTxt() {
		return cookieTitleHeaderTxt;
	}

	public WebElement getSearchBox() {
		return searchBox;
	}

	public SearchResultPage enterSearchText(WebDriver driver, String searchString) {
		log.info("Inside enterSearchText method");
		if (getCookieTitleHeaderTxt().getText().equalsIgnoreCase("Your cookie preferences")) {
			log.debug("Cookie preferences form visible");
			// getCookieTailoredAdvertisingBtn().click();
			if (getAcceptCookiesBtn().isEnabled()) {
				log.info("Element is enabled");
				testUtils.javaScriptExecutorClick(driver, getAcceptCookiesBtn());
			}
			testUtils.javaScriptExecutorClick(driver, getSearchBox());
			getSearchBox().sendKeys(searchString, Keys.ENTER);
			logDebug("Completed search operation");
		}
		return new SearchResultPage(driver);
	}

	public void logDebug(String data) {
		log.info(data);
		Reporter.log(data, true);
	}
}