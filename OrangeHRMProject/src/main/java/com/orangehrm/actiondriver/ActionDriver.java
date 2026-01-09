package com.orangehrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseClass;

public class ActionDriver {

	protected WebDriver driver;
	protected WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int explicitwait = Integer.parseInt(BaseClass.getprop().getProperty("explicitwait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitwait));
		logger.info("Webdriver instance created");
	}

	// method to click an element
	public void clickelement(By by) {
		String elementdescription = getElementDescription(by);
		
		try {
			elementtobeclickable(by);
			driver.findElement(by).click();
			logger.info("Clicked an element "+ elementdescription);
		} catch (Exception e) {
			System.out.println("Unable to click element " + elementdescription +" "+e.getMessage());
			logger.error("unable Clicked an element");

		}
	}

	// method to send text to an input field
	public void entertext(By by, String value) {
		try {
			elementtobevisible(by);
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(value);
			logger.info("Text entered at"+getElementDescription(by));

		} catch (Exception e) {
			logger.error("Unable to enter text " + getElementDescription(by) +" "+e.getMessage());
		}
	}

	// method to get text from an input field
	public String getText(By by) {
		try {
			elementtobevisible(by);
			return driver.findElement(by).getText();

		} catch (Exception e) {
			System.out.println("could not get text from " +getElementDescription(by) + e.getMessage());
			logger.error("unable to get text");
			return "";
		}

	}

// check if an element is displayed
	public boolean isDisplayed(By by) {
		try {
			elementtobevisible(by);
			logger.info("Element is displayed" +getElementDescription(by));
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			logger.error("Error displaying the element " +getElementDescription(by)+" "+ e.getMessage());
			return false;
		}

	}

	// Scroll to an element
	public void scrollToElement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			logger.info("Scrolled to the Element");

		} catch (Exception e) {
			logger.error("Error scrolling to the element" + e.getMessage());
		}
	}

	public void waitForPageToLoad(int timeOutInSec) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(WebDriver -> (JavascriptExecutor) WebDriver)
					.executeScript("return document.readyState").equals("complete");
		} catch (Exception e) {
			logger.error("Error loading page " + e.getMessage());
		}

	}

	// method to compare text
	public boolean compareText(By by, String expectedText) {
		try {
			elementtobevisible(by);
			String actualText = driver.findElement(by).getText();
			if (actualText.equals(expectedText)) {
				System.out.println("Text are matching " + actualText + " equals " + expectedText);
				logger.info("compared text match");
				return true;
			} else {
				logger.error("Text are not matching " + actualText + " not equals " + expectedText);
				return false;
			}
		} catch (Exception e) {
			logger.error("Unable to compare Text at " +getElementDescription(by)+" "+ e.getMessage());
			return false;
		}
	}

	// wait for element to be clickable
	private void elementtobeclickable(By by) {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("Element is not clickable" + by.toString() + e.getMessage());
		}
	}

	// wait for the element to be visible
	private void elementtobevisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("Element is not visible" + by.toString() +e.getMessage());
		}
	}
	
	
	public String getElementDescription(By by) {
		
		if(driver ==null) {
			return "Driver is not initialized";
		}
		if(by == null) {
			return "locator is null";
		}
		try {
			//Find the element using the lcoator 
			WebElement element = driver.findElement(by);
			
			//get element attributes
			String name = element.getDomAttribute("name");
			String id = element.getDomAttribute("id");
			String clazz = element.getDomAttribute("class");
			String placeholder = element.getDomAttribute("placeholder");
			String text = element.getText();

			
			if(isNotEmpty(name)) {
				return "Element with name "+name;
			}else if(isNotEmpty(id)) {
				return "Element with id "+id;
			}else if(isNotEmpty(clazz)) {
				return "Element with class "+clazz;
			}else if(isNotEmpty(placeholder)) {
				return "Element with placeholder "+placeholder;
			}else if(isNotEmpty(text)) {
				return "Element with Text "+trunkateString(text,30);	
			}
		} catch (Exception e) {
			logger.error("Unable to get Description "+e.getMessage());
	        return "Element located by: " + by.toString();
		}
		return null;
	}
	
	//method to check if a string is not null or empty
	private boolean isNotEmpty(String value) {
		return value!= null && !value.isEmpty();
	}		
	
	//Truncate long string
	private String trunkateString(String value, int maxLength) {
		if(value ==null || value.length() < maxLength) {
			return value;
			
		}
		
		return value.substring(0, maxLength)+"...";
	}
	
	
	
	
	
}
