package com.zoopla.uk.searchproperty;

import org.apache.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.zoopla.uk.base.TestBase;
import com.zoopla.uk.pages.HomePage;
import com.zoopla.uk.pages.SearchResultPage;
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

	private static final Logger log = Logger.getLogger(SearchPropertyTest.class.getName());

	@BeforeMethod
	public void setUp() {
		log.info("Inside HomePage setup");
		initialize();
		homePage = new HomePage(driver);
		testUtils = new TestUtils(driver);
		log.info("Setup completed");
	}

	@Test(enabled = false, description = "This test is for properties search", dataProviderClass = ExcelDataProviderLib.class, dataProvider = "getDataFromExcel")
	public void searchPropertyTest(String areaName, String propertylistingnumber) {
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(testUtils.verifyTitle(driver, ConfigFileRead.readConfigFile("homepage_title")));
		searchResultPage=homePage.enterSearchText(driver, areaName);
		sa.assertTrue(searchResultPage.verifySearchResultHeaderText(driver, areaName));
		sa.assertAll();
	}
	
	@Test(enabled = false, description = "This test is to get all property price", dataProviderClass = ExcelDataProviderLib.class, dataProvider = "getDataFromExcel")
	public void searchPropertyAndPrintAllPricesTest(String areaName, String propertylistingnumber) {
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(testUtils.verifyTitle(driver, ConfigFileRead.readConfigFile("homepage_title")));
		searchResultPage=homePage.enterSearchText(driver, areaName);
		sa.assertTrue(searchResultPage.verifySearchResultHeaderText(driver, areaName));
		sa.assertTrue(searchResultPage.getAllPropPrices(driver));
		sa.assertAll();
	}
	
	@Test(enabled = true, description = "This test is to get all property price", dataProviderClass = ExcelDataProviderLib.class, dataProvider = "getDataFromExcel")
	public void openAnyPropAndVerifyAgentDetailsTest(String areaName, String propertylistingnumber) {
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(testUtils.verifyTitle(driver, ConfigFileRead.readConfigFile("homepage_title")));
		searchResultPage=homePage.enterSearchText(driver, areaName);
		sa.assertTrue(searchResultPage.verifySearchResultHeaderText(driver, areaName));
		searchResultPage.clickOnPropPriceAndVerifyAgent(driver, propertylistingnumber);
		sa.assertAll();
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		try {
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logDebug(String data) {
		log.info(data);
		Reporter.log(data, true);
	}

}
