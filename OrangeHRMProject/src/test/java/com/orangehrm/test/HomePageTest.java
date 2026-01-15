package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass {
	private HomePage homepage;
	private LoginPage loginpage;
	
	@BeforeMethod
	public void setuppage() {
		loginpage= new LoginPage(getDriver());
		homepage= new HomePage(getDriver());
	}
	
	@Test(dataProvider = "validLogindata", dataProviderClass = DataProviders.class)
	public void verifyOrangeHRMLogo(String username, String password) {
		//ExtentManager.startTest("Verify orange HRM logo");
		System.out.println("Running test on Thread"+Thread.currentThread().getId());
		ExtentManager.logStep("Entering username and password");
		//String username = prop.getProperty("username");
		//String password = prop.getProperty("password");
		loginpage.userLogin(username, password);
		Assert.assertTrue(homepage.logoisDisplayed(),"Logo is not visible");
		staticWait(2);
		//ExtentManager.logStep("Validation successful");

		//homepage.logoutOperation();
	}
}
