package com.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.apache.commons.io.FileUtils;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Long, WebDriver> driverMap = new HashMap<Long, WebDriver>();

	// Initialize extent report
	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			String reportPath = System.getProperty("user.dir") + "/src/test/resources/ExtentReport/ExtentReport.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setDocumentTitle("OrangeHRM Project");
			spark.config().setReportName("Automation test report ");
			spark.config().setTheme(Theme.DARK);

			extent = new ExtentReports();
			extent.attachReporter(spark);
			extent.setSystemInfo("Operating system", System.getProperty("os.name"));
			extent.setSystemInfo("Java version", System.getProperty("java.version"));
			extent.setSystemInfo("userName", System.getProperty("user.name"));
		}
		return extent;
	}

	// Start a test
	public synchronized  static ExtentTest startTest(String testName) {
		ExtentTest extentTest = getReporter().createTest(testName);
		test.set(extentTest);
		return extentTest;
	}

	public synchronized static void endTest() {
		getReporter().flush();
	}

	// get the current thread test
	public synchronized static ExtentTest getTest() {
		return test.get();
	}

	// get the current thread name
	public static String getTestName() {
		ExtentTest currentTest = getTest();
		if (currentTest != null) {
			return currentTest.getModel().getFullName();
		} else {
			return "No Test is currently being run in the thread";
		}
	}

	// log a step
	public static void logStep(String logMessage) {
		getTest().info(logMessage);
	}

	// log a stepwith screenshot
	public static void logStepwithScreenshot(WebDriver driver, String logMessage, String screenSHotMessage) {
		getTest().pass(logMessage);
		// attach screenshot
		attachScreenShot(driver, screenSHotMessage);
	}

	public static void logfailure(WebDriver driver, String logMessage, String screenSHotMessage) {
		String colorMessage = String.format("<span style='color:red;'>%s</span>", logMessage);
		getTest().fail(colorMessage);
		// attach screenshot
		attachScreenShot(driver, screenSHotMessage);

	}

	public static void logSkipstep(String logMessage) {
		String colorMessage = String.format("<span style='orange;'>%s</span>", logMessage);
		getTest().skip(colorMessage);
	}

	public synchronized  static String takeScreenShot(WebDriver driver, String screenShotName) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String screenShotPath = System.getProperty("user.dir") + "/src/test/resources/screenshots/" + screenShotName
				+ timestamp + ".png";

		File dest = new File(screenShotPath);
		try {
			FileUtils.copyFile(src, dest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String base64Format = convertToBase64(src);
		return base64Format;

	}

	public static String convertToBase64(File screenshotFile) {
		String base64Format = "";
		try {
			byte[] fileContent = FileUtils.readFileToByteArray(screenshotFile);
			base64Format = Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64Format;
	}

	public synchronized static void attachScreenShot(WebDriver driver, String message) {
		try {
			String screenShot =takeScreenShot(driver, getTestName());
			getTest().info(message, MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot).build());
		} catch (Exception e) {
			getTest().fail("Failed to attach a screenshot"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void registerDriver(WebDriver driver) {
		driverMap.put(Thread.currentThread().getId() , driver);
	}
}
