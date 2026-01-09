package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

public class LoginPageTest extends BaseClass{
	
	private HomePage homepage;
	private LoginPage loginpage;
	
	@BeforeMethod
	public void setuppage() {
		loginpage= new LoginPage(getDriver());
		homepage= new HomePage(getDriver());
	}
	
	@Test
	public void verifyvalidlogin() {
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		System.out.println("print this");
		loginpage.userLogin(username, password);
		Assert.assertTrue(homepage.isAdminVisible(), "Admin tab should be visible after successful login");
		homepage.logoutOperation();
	}
	
	@Test
	public void invalidLogin() {
		loginpage.userLogin("Admin", "Password");
		Assert.assertTrue(loginpage.isErrorMessageDisplayed());
		Assert.assertTrue(loginpage.checkErrorMessage("Invalid credentials"),
				"Test failed : Invalid login credentials ");		
	}
}
