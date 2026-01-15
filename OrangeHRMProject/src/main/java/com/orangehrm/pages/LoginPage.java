package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {
	
	private ActionDriver actionDriver;
	
	//Define locators using By class
	
	private By username =By.name("username");
	private By password = By.name("password");
	private By login=By.xpath("//button[normalize-space()='Login']");
	private By errorMessage=By.xpath("//p[text()='Invalid credentials']");
	
	
	public LoginPage(WebDriver driver) {
		this.actionDriver = BaseClass.getactionDriver();
	}
	
	//Method to perform login
	public void userLogin(String inputusername, String inputpassword) {
		actionDriver.entertext(username, inputusername);
		actionDriver.entertext(password, inputpassword);
		actionDriver.clickelement(login);
	}
	
	//Method to check if error message is displayed
	public boolean isErrorMessageDisplayed() {
		return actionDriver.isDisplayed(errorMessage);
	}
	
	//Method to check the text of the error message
	public String getErrorMessage() {
		return actionDriver.getText(errorMessage);
	}
	
	//method to check if the error message is displayed correctly 
	public boolean checkErrorMessage(String expectedErrorMessage) {
		return actionDriver.compareText(errorMessage, expectedErrorMessage);
	}
	
}
