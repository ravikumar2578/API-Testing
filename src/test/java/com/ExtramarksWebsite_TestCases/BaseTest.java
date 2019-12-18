package com.ExtramarksWebsite_TestCases;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.ExtramarksWebsite_Pages.*;

import com.ExtramarksWebsite_Utils.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {

	/*
	 * @BeforeMethod public void init() { rep = ExtentManager.getInstance(); test =
	 * rep.startTest("StudentTest"); }
	 * 
	 * @AfterMethod public void quit() { rep.endTest(test); rep.flush(); }
	 */

	public ExtentReports rep;
	public static ExtentTest test;
	public static WebDriver driver;
	public static Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);

	public static void openBrowser(String browser) {

		// normal machine
		if (browser.equals("Mozilla")) {
			driver = new FirefoxDriver();
		} else if (browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", Constants.CHROME_PATH);
			// System.setProperty("webdriver.chrome.driver",
			// "D:\\Drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-web-security");

			//options.addArguments("headless");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
		} else if (browser.equals("ie")) {
			// System.setProperty("webdriver.ie.driver", Constants.IEDRIVER_PATH);
			driver = new InternetExplorerDriver();

		}
		// driver.manage().window().maximize(); -------This is not working when
		// executing with Jenkins

		Dimension d = new Dimension(1382, 744);// --------------working with jenkins
		// Resize the current window to the given dimension
		//driver.manage().window().setSize(d);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		 test.log(LogStatus.INFO, "Opened the browser");
	}

	/*
	 * public static DashBoardPage defaultLogin(){
	 * test.log(LogStatus.INFO,"Doing Default Login "); LaunchPage lp = new
	 * LaunchPage(driver,test); PageFactory.initElements(driver, lp);
	 * test.log(LogStatus.INFO, "Trying to login"); LoginPage loginPage =
	 * lp.goToHomePage(); //hp.verifyTitle(Constants.homepage_title);
	 * 
	 * 
	 * //loginPage.verifyTitle("Home tile"); Object
	 * page=loginPage.doLogin(Constants.USERNAME,Constants.PASSWORD); DashBoardPage
	 * lPage = (DashBoardPage)page; return lPage;
	 * 
	 * 
	 * }
	 */

	public void defaultLogin(Hashtable<String, String> data) throws InterruptedException {
		String browser = data.get("Browser");
		/*
		 * if(!DataUtil.isTestRunnable(xls, "StudentAssessmentTest") ||
		 * data.get("Runmode").equals("N")) { test.log(LogStatus.SKIP,
		 * "Skipping the test"); throw new SkipException("skipping the test"); }
		 */
		openBrowser(browser);
		LaunchPage launch = new LaunchPage(driver, test);
		LoginPage lp = launch.goToHomePage();
		WebElement signin = driver.findElement(By.xpath("//ul[@id='navigation-top']//a[@class='signin']"));
		signin.click();
		Object resultPage = lp.doLogin(data.get("Username"), data.get("Password"));
		Thread.sleep(5000);
		lp.takeScreenShot();
		// test.log(LogStatus.PASS, "LOGIN PASS");
	}

	/*
	 * public void defaultLogin(Hashtable<String, String> data) { String browser =
	 * data.get("Browser"); openBrowser(browser); driver.get(Constants.URL);
	 * WebElement signin = driver.findElement(By.xpath("//*[@class='signin']"));
	 * signin.click(); LoginPage lp= new LoginPage(driver, test);
	 * lp.usernameField.sendKeys("9089089089"); lp.passwordField.sendKeys("123456");
	 * lp.Submit_button.click();
	 * 
	 * }
	 * 
	 */
	/*
	 * public static DashBoardPage defaultLogin(Hashtable<String, String> data)
	 * throws InterruptedException{
	 * 
	 * String browser = data.get("Browser"); openBrowser(browser);
	 * 
	 * test.log(LogStatus.INFO,"Doing Default Login ");
	 * 
	 * LaunchPage lp = new LaunchPage(driver,test);
	 * 
	 * //PageFactory.initElements(driver, lp);
	 * 
	 * test.log(LogStatus.INFO, "Trying to login");
	 * 
	 * LoginPage loginPage = lp.goToHomePage(); WebElement signin =
	 * driver.findElement(By.xpath("//*[@class='signin']")); signin.click();
	 * 
	 * //hp.verifyTitle(Constants.homepage_title);
	 * 
	 * //loginPage.verifyTitle("Home tile");
	 * 
	 * Object page=loginPage.doLogin("",""); DashBoardPage dp= new
	 * DashBoardPage(driver, test);
	 * 
	 * return dp; }
	 */
}
