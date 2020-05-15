package com.zoopla.uk.searchproperty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.zoopla.uk.drivers.DriverManager;
import com.zoopla.uk.drivers.InitializeDriver;
import com.zoopla.uk.pages.AgentsPage;
import com.zoopla.uk.pages.HomePage;
import com.zoopla.uk.pages.IndividualPropertyPage;
import com.zoopla.uk.pages.SearchResultPage;
import com.zoopla.uk.testbase.TestBase;
import com.zoopla.uk.utils.ConfigFileRead;
import com.zoopla.uk.utils.ExcelDataProviderLib;
import com.zoopla.uk.utils.TestUtils;

/**
 * @author UTKAL
 *
 */
public class SearchPropertyTest extends TestBase {

	HomePage homePage;
	TestUtils testUtils;
	SearchResultPage searchResultPage;
	IndividualPropertyPage individualPropertyPage;
	AgentsPage agentsPage;

	private static final Logger log = LogManager.getLogger(SearchPropertyTest.class);

	public void setUp() {
		homePage = new HomePage(DriverManager.getDriver());
		testUtils = new TestUtils(DriverManager.getDriver());
		log.info("Setup completed");
	}

	@Test(enabled = true, description = "This test is for properties search", dataProviderClass = ExcelDataProviderLib.class, dataProvider = "getDataFromExcel")
	public void searchPropertyTest(String areaName, String propertylistingnumber) {
		try {
			setUp();
			SoftAssert sa = new SoftAssert();
			sa.assertTrue(testUtils.verifyTitle(DriverManager.getDriver(), ConfigFileRead.readkey("homepage_title")));
			searchResultPage = homePage.enterSearchText(DriverManager.getDriver(), areaName);
			sa.assertTrue(searchResultPage.verifySearchResultHeaderText(DriverManager.getDriver(), areaName));
			sa.assertAll();			
		} catch (Exception e) {
			log.error(e.getCause().toString());
		}

	}

	@Test(enabled = true, description = "This test is to get all property price", dataProviderClass = ExcelDataProviderLib.class, dataProvider = "getDataFromExcel")
	public void searchPropertyAndPrintAllPricesTest(String areaName, String propertylistingnumber) {
		setUp();
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(testUtils.verifyTitle(DriverManager.getDriver(), ConfigFileRead.readkey("homepage_title")));
		searchResultPage = homePage.enterSearchText(DriverManager.getDriver(), areaName);
		sa.assertTrue(searchResultPage.verifySearchResultHeaderText(DriverManager.getDriver(), areaName));
		sa.assertTrue(searchResultPage.getAllPropPrices(DriverManager.getDriver()));
		sa.assertAll();
	}

	@Test(enabled = true, description = "This test is to validate agent details on property and viceversa", dataProviderClass = ExcelDataProviderLib.class, dataProvider = "getDataFromExcel")
	public void openAnyPropAndVerifyAgentDetailsTest(String areaName, String propertylistingnumber) {
		setUp();
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(testUtils.verifyTitle(DriverManager.getDriver(), ConfigFileRead.readkey("homepage_title")), "Title mismatch");
		searchResultPage = homePage.enterSearchText(DriverManager.getDriver(), areaName);
		sa.assertTrue(searchResultPage.verifySearchResultHeaderText(DriverManager.getDriver(), areaName));
		individualPropertyPage = searchResultPage.clickOnPropPriceforPropDetails(DriverManager.getDriver(), propertylistingnumber,
				InitializeDriver.listingHrefs);
		sa.assertTrue(individualPropertyPage.verifyProperty(DriverManager.getDriver(), InitializeDriver.listingHrefs), "Assertion Failed");
		agentsPage = individualPropertyPage.clickOnAgentIconAndVerify(DriverManager.getDriver());
		sa.assertTrue(agentsPage.verifyLandingPage(DriverManager.getDriver()),
				"Assertion failed in verifying agent landing page is same as that of property listing");
		sa.assertTrue(
				agentsPage.matchPropertyAvailableOnAgentsPage(DriverManager.getDriver(), ConfigFileRead.readkey("dropDownValue")),
				"Assertion failed as unable to find property listing on agent page");
		sa.assertAll();
	}

	public void logDebug(String data) {
		log.info(data);		Reporter.log(data, true);
	}
}
