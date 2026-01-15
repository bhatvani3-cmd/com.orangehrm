package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {
	private ActionDriver actionDriver;
	
	//define locators using By class
	private By admin= By.xpath("//span[text()='Admin']");
	private By userNameButton = By.xpath("//span[@class='oxd-userdropdown-tab']");
	private By logout= By.xpath("//a[text()='Logout']");
	private By orangeHRMLogo = By.xpath("//img[@alt='client brand banner']");
	//private By orangeHRMLogo = By.xpath("//h6[normalize-space()='Dashboard']");
	 
	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getactionDriver();
	}
	//verify orangeHRMlogo
	public boolean logoisDisplayed() {
		return actionDriver.isDisplayed(orangeHRMLogo);
	}
	
	//logout operation
	public void logoutOperation() {
		try {
			actionDriver.clickelement(userNameButton);
			actionDriver.clickelement(logout);
		} catch (Exception e) {
			System.out.println("Could not perform logout operation "+e.getMessage());
		}
	}
	
	//Verify admin tab
	public boolean isAdminVisible() {
		return actionDriver.isDisplayed(admin);
	}
	
}
