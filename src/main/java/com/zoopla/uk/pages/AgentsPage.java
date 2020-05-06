package com.zoopla.uk.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zoopla.uk.base.TestBase;
import com.zoopla.uk.utils.TestUtils;

/**
 * @author UTKAL
 *
 */
public class AgentsPage extends TestBase {

	private static final Logger log = LogManager.getLogger(AgentsPage.class);
	
	TestUtils testUtils;
	private static final String allListings = "//ul[@class='listing-results clearfix js-gtm-list']/li[starts-with(@id, 'listing_')]";

	/*@FindBy(xpath = "//div[@class='clearfix agents-latest-listings']/a[0]")
	private WebElement seeAllPropertyLnk;*/

	@FindBy(css = "div#content>div>h1>b")
	private WebElement landingPageTxt;

	@FindBy(xpath = "//select[@class='js-redirects-to-option js-check js-touched']")
	private WebElement selectNumberOfPropertyDropdown;

	public AgentsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		testUtils = new TestUtils(driver);
	}

	/*public WebElement getSeeAllPropertyLnk() {
		return seeAllPropertyLnk;
	}*/

	public WebElement getLandingPageTxt() {
		return landingPageTxt;
	}

	public WebElement getSelectNumberOfPropertyDropdown() {
		return selectNumberOfPropertyDropdown;
	}

	public boolean verifyLandingPage(WebDriver driver) {
		boolean isSame = false;
		if (getLandingPageTxt().getText().equalsIgnoreCase(IndividualPropertyPage.agentName)) {
			log.debug("Agent name matched on both Property Page and Agent Page, so boolean value will be set to true");
			isSame = true;
		}
		return isSame;
	}

	public boolean matchPropertyAvailableOnAgentsPage(WebDriver driver, String dropDownValue) {
		boolean isSame = false;
		/*testUtils.javaScriptScrollIntoViewElement(driver, getSeeAllPropertyLnk());
		getSeeAllPropertyLnk().click();
		log.info("Clicked on see all properties link. Going to select number of property to be displayed is "
				+ dropDownValue);
		testUtils.select(driver, getSelectNumberOfPropertyDropdown(), dropDownValue);
		log.info("Selected " + dropDownValue + " from drop down.");*/
		testUtils.waitForVisibility(driver, getLandingPageTxt());
		List<WebElement> allListingElements = driver.findElements(By.xpath(allListings));
		log.debug("allListingElements size is " + allListingElements.size());
		for (WebElement ele : allListingElements) {
			if (ele.getAttribute("data-listing-id").equals(IndividualPropertyPage.listingID)) {
				log.info("Listing ID found the agent page, so setting the boolean value to true");
				isSame = true;
			}
		}
		return isSame;
	}
}
