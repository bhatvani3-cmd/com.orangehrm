package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.LoggerManager;

public class BaseClass {

	protected static Properties prop;
	//protected static WebDriver driver;
	//protected static ActionDriver actionDriver;
	
	private static final ThreadLocal<WebDriver> driver=new ThreadLocal<>();
	private static final ThreadLocal<ActionDriver> actionDriver=new ThreadLocal<>();
	
	public static final Logger logger= LoggerManager.getLoggerManager(BaseClass.class);
	

	@BeforeSuite
	public void loadconfig() throws IOException {
		// Load Config file
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
		logger.info("config.properties file loaded");
	}

	@BeforeMethod
	public synchronized void setup() throws IOException, InterruptedException {
		System.out.println("Setting up webdriver for the test case "+ this.getClass().getSimpleName());
		launchbrowser();
		congifurebrowser();
		logger.info("Webdriver initialized and Browser maximized");
		logger.warn("This is a warn message");
		logger.fatal("This is a fatal message");
		logger.error("This is a error message");
		logger.trace("This is a trace message");
		logger.debug("This is a debug message");

		
		
		/*if(actionDriver == null) {
			actionDriver =new ActionDriver(driver);
			logger.info("Intitialize the actiondriver instance");
		}*/
		//Initialize actiondriver for this thread
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("Action driver initialized for "+Thread.currentThread().getId()+
	            " | Driver hash: " + driver.hashCode());
		
		
	}

	private synchronized void launchbrowser() {
		String Browser = prop.getProperty("browser");

		if (Browser.equalsIgnoreCase("chrome")) {
			//driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			logger.info("Chrome instance created");
		} else if (Browser.equalsIgnoreCase("firefox")) {
			//driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
			logger.info("Firefox instance created");
		} else if (Browser.equalsIgnoreCase("edge")) {
			//driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			logger.info("Edge instance created");

		} else {
			throw new IllegalArgumentException("The browser type does not match");
		}
	}
	
	private void congifurebrowser() {
		// Wait
		int implicitwait = Integer.parseInt(prop.getProperty("ImplicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitwait));

		// Maximize the window
		getDriver().manage().window().maximize();
		
		try {
			// Navigate to url
			String url = prop.getProperty("url");
			getDriver().get(url);
		} catch (Exception e) {
			System.out.println("The Webpage load fail"+e.getMessage());
			e.printStackTrace();
		}

	}

	@AfterMethod
	public synchronized void teardown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				System.out.println("Could not quit driver "+e.getMessage());
			}
		}
		logger.info("Webdriver and actionDriver instance closed");
		//driver = null;
		//actionDriver=null;
		driver.remove();
		actionDriver.remove();
		
	}
	
	//Driver getter method
	public static WebDriver getDriver() {
		if(driver.get()==null) {
			System.out.println("Driver instance is not initialized");
			throw new IllegalStateException("Driver instance is not initialized");
		}
		return driver.get();
	}
	
	//actiondriver getter method
	public static ActionDriver getactionDriver() {
		if(actionDriver.get()==null) {
			System.out.println("actionDriver instance is not initialized");
			throw new IllegalStateException("actionDriver instance is not initialized");
		}
		return actionDriver.get();
	}

	//Setter method
	/*public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver = driver;
	}*/
	
	//getter method for prop
	public static Properties getprop() {
		return prop;
	}
}