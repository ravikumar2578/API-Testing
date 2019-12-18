package com.ExtramarksWebsite_TestCases;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class StudentValidationTest extends BaseTest {
	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("StudentValidationTest");
	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();
	}

	@Test(dataProvider = "getData", priority = 1)
	public void doLogin(Hashtable<String, String> data) throws InterruptedException {
		String expectedResult = "LOGIN_PASS";
		String actualResult = "";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		if (!DataUtil.isTestRunnable(xls, "StudentValidationTest") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");

			throw new SkipException("skipping the test");
		}

		openBrowser(browser);
		test.log(LogStatus.INFO, "Browser Opened");
		LaunchPage launch = new LaunchPage(driver, test);

		LoginPage lp = launch.goToHomePage();
		String title = driver.getTitle();
		System.out.println(title);
		test.log(LogStatus.INFO, "Extramarks Website Home Page");
		lp.takeScreenShot();

		WebElement signin = driver.findElement(By.xpath("//*[@class='signin']"));
		signin.click();
		test.log(LogStatus.INFO, "Trying to Login");
		Object resultPage = lp.doLogin(data.get("Username"), data.get("Password"));
		String url = driver.getCurrentUrl();
		String actual_url = "http://testautomation-www.extramarks.com/";
		String errorMEssage = "Please login by registered identity";
		String Login_Error = "Please enter valid email/username and password";
		// String loginFailed="Please enter valid email/username and password";

		if (url.equals(actual_url)) {
			if (errorMEssage.equals(lp.Error_Username.getText())) {
				test.log(LogStatus.INFO, "Not able to login");
				test.log(LogStatus.INFO, lp.Error_Username.getText() + " message is displaying");
				Assert.assertEquals(lp.Error_Username.getText(), errorMEssage);
				actualResult = "Login_Fail";
			} else if (Login_Error.equals(lp.LoginFailed.getText())) {
				test.log(LogStatus.INFO, "Not able to login");
				test.log(LogStatus.INFO, lp.LoginFailed.getText() + " message is displaying");
				Assert.assertEquals(lp.LoginFailed.getText(), Login_Error);
				actualResult = "Login_Fail";
			}

			if (!expectedResult.equals(actualResult)) {
				lp.takeScreenShot();
				test.log(LogStatus.FAIL, "Got actual result as " + actualResult);
				Assert.fail("Got actual result as " + actualResult);

			}

			Thread.sleep(5000);
			test.log(LogStatus.PASS, "LOGIN PASS");
		}
	}

	@DataProvider
	public Object[][] getData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "StudentValidationTest");
		return data;
	}

}
