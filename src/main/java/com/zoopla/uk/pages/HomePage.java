package com.zoopla.uk.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	private static final Logger log = Logger.getLogger(HomePage.class.getName());

	@FindBy(id = "search-input-location")
	private WebElement searchBox;

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public WebElement getSearchBox() {
		return searchBox;
	}

	public boolean enterSearchText(WebDriver driver) {
		getSearchBox().click();
		getSearchBox().sendKeys("London");
		log.debug("Completed search operation");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
