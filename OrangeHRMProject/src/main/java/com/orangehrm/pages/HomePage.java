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
	
	private By pim=By.xpath("//span[text()='PIM']");
	private By employeeSearch = By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div/div/div/input");
	private By searchButton=By.xpath("//button[@type='submit']");
	private By employeeFirstNameandMiddleName=By.xpath("//div[@class='oxd-table-card']/div/div[3]");
	private By employeeLastName=By.xpath("//div[@class='oxd-table-card']/div/div[4]");
	
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
	
	public void clickonPIMtab() {
		actionDriver.clickelement(pim);
		
	}
	
	public void searchEmployee(String value) {
		actionDriver.entertext(employeeSearch, value);
		actionDriver.clickelement(searchButton);
		actionDriver.scrollToElement(employeeFirstNameandMiddleName);
	}
	
	public boolean verifyEmpFirstandmiddleName(String empFirstandmiddleNamefronDB) {
		return actionDriver.compareText(employeeFirstNameandMiddleName, empFirstandmiddleNamefronDB);
		
	}
	
	public boolean verifyEmpLastName(String empLastNamefronDB) {
		return actionDriver.compareText(employeeLastName, empLastNamefronDB);
	}
	
	
}
