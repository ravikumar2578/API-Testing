package com.ExtramarksWebsite_TestCases;

import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.Parent_MyChildPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class ParentDashboardTest extends BaseTest {
	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("ParentDashboardTest");
	}

	@DataProvider
	public Object[][] getData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "ParentDashboardTest");
		return data;
	}

	/*
	 * @AfterMethod public void quit() { rep.endTest(test); rep.flush(); }
	 */

	@Test(dataProvider = "getData")
	public void DashBoard(Hashtable<String, String> data) throws InterruptedException {
		String expectedResult = "LOGIN_PASS";
		String actualResult = "";

		
		LoginPage lp = new LoginPage(driver, test);
	
		defaultLogin(data);
		Thread.sleep(5000);

		String url = driver.getCurrentUrl();
		String actual_url = "http://testautomation-www.extramarks.com/user/teacher-dashboard/3";
		DashBoardPage dp = new DashBoardPage(driver, test);
		if (url.equals(actual_url) && dp.ParentTab() == 0) {
			System.out.println("Logged in but not on parent page");
			test.log(LogStatus.INFO, "Logged in but not on parent page");
			actualResult = "Login_Fail";
			/*
			 * SoftAssert sa = new SoftAssert(); sa.assertEquals(actualResult,
			 * expectedResult); sa.assertAll();
			 */
			test.log(LogStatus.FAIL, "Got actual result as " + actualResult);
			Assert.fail("got actual result as= " + actualResult);

		}
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(dp.AddChild));
		dp.AddChild.click();
		Parent_MyChildPage pmc = new Parent_MyChildPage(driver, test);
		pmc.AddChild(data.get("ChildEmail"));
		pmc.AddChildNew(data.get("ChildName"), data.get("ChildUserId"), data.get("Password"), data.get("SchoolName"));

		/*
		 * Thread.sleep(5000); driver.switchTo().alert().accept(); Thread.sleep(3000);
		 */
		/*
		 * if(!expectedResult.equals(actualResult)) { lp.takeScreenShot();
		 * test.log(LogStatus.FAIL, "Got actual result as "+actualResult);
		 * Assert.fail("Got actual result as "+ actualResult); }
		 */

	}

	@AfterMethod
	public void LogOut() throws InterruptedException {
		ProfilePage pg = new ProfilePage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		Thread.sleep(3000);
		pg.SettingsIcon.click();
		dp.LogOut.click();
		driver.quit();
	}

}
