package com.orangehrm.test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class LoginPageNegativeTest extends BaseClass{

	//private HomePage homepage;
	private LoginPage loginpage;
	
	@BeforeMethod
	public void setuppage() {
		loginpage= new LoginPage(getDriver());
		//= new HomePage(getDriver());
	}
	
	@Test(dataProvider = "invalidLogindata", dataProviderClass = DataProviders.class)
	public void invalidLogin(String username, String password) {
		//ExtentManager.startTest("Invalid Login Test");
		ExtentManager.logStep("Entering invalid user name passowrd");
		loginpage.userLogin(username, password);
		Assert.assertTrue(loginpage.isErrorMessageDisplayed());
		Assert.assertTrue(loginpage.checkErrorMessage("Invalid credentials"),
				"Test failed : Invalid login credentials ");
		//ExtentManager.logStep("Validation successful");
		staticWait(2);
		
	}
}
