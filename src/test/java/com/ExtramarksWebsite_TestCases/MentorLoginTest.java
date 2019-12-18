package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.hpsf.MarkUnsupportedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.error.MarkedYAMLException;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.Mentor_Assessment;
import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.Mentor_SchedulePage;
import com.ExtramarksWebsite_Pages.Mentor_StudentPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.ReportPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.relevantcodes.extentreports.LogStatus;

public class MentorLoginTest extends BaseTest
{


	@BeforeMethod
	public void init()
	{
		 rep = ExtentManager.getInstance();
		 test=rep.startTest("MentorLoginTest");
	}

	@DataProvider
	public Object[][] getData()
	{
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "MentorLoginTest");
		return data;
	}
	
	
	@Test(dataProvider="getData")
	public void doLogin(Hashtable<String,String> data)throws InterruptedException
	{
		
		String expectedResult="LOGIN_PASS";
		String actualResult="";
		
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		if(!DataUtil.isTestRunnable(xls, "MentorLoginTest")  ||  data.get("Runmode").equals("N"))
		{
		
			test.log(LogStatus.SKIP, "Skipping the test");
			
			throw new SkipException("skipping the test");
		}
		
		openBrowser(browser);
		test.log(LogStatus.INFO, "Browser Opened");
		LaunchPage launch=new LaunchPage(driver, test);
		
		LoginPage lp=launch.goToHomePage();
		String title= driver.getTitle();
		System.out.println(title);
		test.log(LogStatus.INFO, "Extramarks Website Home Page");
		lp.takeScreenShot();
		
		WebElement signin = driver.findElement(By.xpath("//*[@class='signin']"));
		signin.click();
		test.log(LogStatus.INFO, "Trying to Login");
		Object resultPage=lp.doLogin(data.get("Username"),data.get("Password"));
		
		//driver.switchTo().alert().accept();
		Thread.sleep(2000);
		
		String url = driver.getCurrentUrl();
		String actual_url="http://testautomation-www.extramarks.com/user/teacher-dashboard/3";
		String errorMEssage="Please login by registered identity";
		String Login_Error="Please enter valid email/username and password";  
		String loginFailed="Please enter valid email/username and password"; 
				
		if(!url.equals(actual_url))
		{
			if(errorMEssage.equals(lp.Error_Username.getText()))
			{
			test.log(LogStatus.INFO, "Not able to login");
			test.log(LogStatus.INFO, lp.Error_Username.getText()+ " message is displaying");		
			Assert.assertEquals(lp.Error_Username.getText(), errorMEssage);
			actualResult="Login_Fail";
			}
			else if(Login_Error.equals(lp.LoginFailed.getText()))
			{
				test.log(LogStatus.INFO, "Not able to login");
				test.log(LogStatus.INFO, lp.LoginFailed.getText()+ " message is displaying");		
				Assert.assertEquals(lp.LoginFailed.getText(), Login_Error);
				actualResult="Login_Fail";
			}
		}
		else
		{
			test.log(LogStatus.INFO, "Logged In");
			actualResult="LOGIN_PASS";
		}
				
		if(!expectedResult.equals(actualResult))
		{
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Got actual result as "+actualResult);
			Assert.fail("Got actual result as "+actualResult);
			
		}
		
		Thread.sleep(5000);
		test.log(LogStatus.PASS, "LOGIN PASS");
	}
	
	@AfterMethod
		public void LogOut() throws InterruptedException
		{
			ProfilePage pg= new ProfilePage(driver, test);
			DashBoardPage dp= new DashBoardPage(driver, test);
			Thread.sleep(5000);
			pg.SettingsIcon.click();
			dp.LogOut.click();
			driver.quit();
		}
		

}
