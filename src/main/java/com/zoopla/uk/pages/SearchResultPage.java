package com.zoopla.uk.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author UTKAL This POM class is for property search results in a particular
 *         area.
 */
public class SearchResultPage {
	private static final Logger log = Logger.getLogger(SearchResultPage.class.getName());

	private static final String propPricesXpath = "//div[@class='listing-results-right clearfix']/a[@class='listing-results-price text-price']";

	@FindBy(css = "div#content>div>h1")
	private WebElement searchResultHeaderTxt;

	@FindBy(css = "a.listing-results-right clearfix a.listing-results-price text-price")
	private WebElement propPriceTxt;

	public SearchResultPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public WebElement getSearchResultHeaderTxt() {
		return searchResultHeaderTxt;
	}

	public WebElement getPropPriceTxt() {
		return propPriceTxt;
	}

	/**
	 * 
	 * @param driver   accepts webDriver instance.
	 * @param areaName accepts area name which needs to be searched.
	 * @return returns boolean value based on if text found.
	 */
	public boolean verifySearchResultHeaderText(WebDriver driver, String areaName) {
		boolean isFound = false;
		areaName = areaName.substring(0, 1).toUpperCase() + areaName.substring(1, areaName.length());
		System.out.println("New areaName " + areaName);
		if (getSearchResultHeaderTxt().getText().contains(areaName)) {
			isFound = true;
			log.info("Area name found in header text, so setting the boolean value to true.");
		} else {
			log.error("Area name MISSING in header text, so CHECK FAILED.");
		}
		return isFound;
	}

	/**
	 * 
	 * @param driver accepts driver instance.
	 * @return boolean value in case able to get all the price list
	 */
	public boolean getAllPropPrices(WebDriver driver) {
		boolean isSame = false;
		List<WebElement> prices = driver.findElements(By.xpath(propPricesXpath));
		log.debug("Price list size is " + prices.size());
		List<Integer> priceList = new ArrayList<Integer>();
		for (WebElement element : prices) {
			String priceWithCurrency = element.getText();
			String priceWithoutCurrency = priceWithCurrency.replaceAll("\\D", "");
			log.debug("priceWithoutCurrency is " + priceWithoutCurrency);
			if (priceWithoutCurrency != null && !priceWithoutCurrency.equalsIgnoreCase("")) {
				priceList.add(Integer.parseInt(priceWithoutCurrency));
			}
			// To sort a list in ascending order using sort method available in Collections
			// class
			Collections.sort(priceList);
			isSame = true;
		}
		log.debug("Price list after sort is " + priceList);
		return isSame;
	}

	public boolean clickOnPropPriceAndVerifyAgent(WebDriver driver, String propertylistingnumber) {
		log.debug("Property to look is on " + propertylistingnumber + ", list.");
		boolean isSame = false;
		try {
			List<WebElement> prices = driver.findElements(By.xpath(propPricesXpath));
			log.debug("Price list size is " + prices.size());
			WebElement priceLink = prices.get(Integer.parseInt(propertylistingnumber.toString()));
			String listingHref = priceLink.getAttribute("href");
			log.debug("listingHref is " + listingHref);
			priceLink.click();
			
		} catch (Exception e) {
			log.error(e.getCause().toString());
		}
		return isSame;
	}
}
