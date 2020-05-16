package com.zoopla.uk.pages;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zoopla.uk.testbase.TestBase;
import com.zoopla.uk.utils.TestUtils;

/**
 * @author UTKAL
 *
 */
public class IndividualPropertyPage extends TestBase {

	private static final Logger log = LogManager.getLogger(IndividualPropertyPage.class);

	TestUtils testUtils;

	private String currentUrl;
	public static String agentName;
	public String agentTelePhone;
	public static String listingIDFromURL;
	public static String listingID;

	@FindBy(css = "a.ui-agent__details>div.ui-agent__text>h4")
	private WebElement agentNameTxt;

	@FindBy(css = "div.ui-agent>p>a.ui-link")
	private WebElement agentTelehoneTxt;

	@FindBy(css = "div.ui-agent__logo>img")
	private WebElement agentContactLnk;

	public IndividualPropertyPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		testUtils = new TestUtils(driver);
	}

	public WebElement getAgentNameTxt() {
		return agentNameTxt;
	}

	public WebElement getAgentTelehoneTxt() {
		return agentTelehoneTxt;
	}

	public WebElement getAgentContactLnk() {
		return agentContactLnk;
	}

	/**
	 * 
	 * @param driver
	 *            accepts driver instance as argument.
	 * @param listingHrefs
	 *            accepts a ConcurrentHashMap as argument which is map between
	 *            listingID and Href URL.
	 * @return boolean value if listing IDs of property is same.
	 */
	public boolean verifyProperty(WebDriver driver, Map<String, String> listingHrefs) {
		boolean isSame = false;
		try {
			testUtils.waitForVisibility(driver, agentNameTxt);
			log.info("Current page title is " + driver.getTitle());
			currentUrl = driver.getCurrentUrl();
			log.debug("Current URL is " + currentUrl);
			listingID = testUtils.splitAndExtractID(currentUrl, 0);
			log.debug("listingID from current URL is " + listingID);
			String hrefFromSearchPage = listingHrefs.get(listingID);
			if (hrefFromSearchPage.contains(listingID)) {
				log.info("listingID Matched");
				log.debug("Property title is ---> " + driver.getTitle());
				isSame = true;
			} else {
				log.error("Listing ID from current URL and search page href is mismatch. Please check again.");
			}
		} catch (Exception e) {
			log.error(e.getCause().toString());
		}
		return isSame;
	}

	public AgentsPage clickOnAgentIconAndVerify(WebDriver driver) {
		try {
			agentName = getAgentNameTxt().getText();
			agentTelePhone = getAgentTelehoneTxt().getText();
			log.debug(
					"agentName and agentTelePhone from property page are --->>> " + agentName + " , " + agentTelePhone);
			getAgentContactLnk().click();
		} catch (Exception e) {
			log.error(e.getCause().toString());
		}
		return new AgentsPage(driver);
	}
}
