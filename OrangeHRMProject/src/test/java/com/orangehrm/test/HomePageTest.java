package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;

public class HomePageTest extends BaseClass {
	private HomePage homepage;
	private LoginPage loginpage;
	
	@BeforeMethod
	public void setuppage() {
		loginpage= new LoginPage(getDriver());
		homepage= new HomePage(getDriver());
	}
	@Test
	public void verifyOrangeHRMLogo() {
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		loginpage.userLogin(username, password);
		Assert.assertTrue(homepage.logoisDisplayed(),"Logo is not visible");
		homepage.logoutOperation();
	}
}
