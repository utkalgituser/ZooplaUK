package com.zoopla.uk.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author UTKAL
 *
 */
public class SearchResultPage {
	private static final Logger log=Logger.getLogger(SearchResultPage.class.getName());
	
	@FindBy(id="search-input-location")
	private WebElement searchBox;
	
	public SearchResultPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
}
