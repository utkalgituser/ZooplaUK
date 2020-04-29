/**
 * 
 */
package com.zoopla.uk.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zoopla.uk.utils.TestUtils;

/**
 * @author UTKAL
 *
 */
public class IndividualPropertyPage {
	private static final Logger log = Logger.getLogger(HomePage.class.getName());
	public TestUtils testUtils;
	
	@FindBy(xpath = "//form//h2")
	private WebElement cookieTitleHeaderTxt;

	public IndividualPropertyPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		testUtils = new TestUtils(driver);
	}

	public WebElement getAcceptCookiesBtn() {
		return cookieTitleHeaderTxt;
	}
	
	
}
