package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.AssessmentPage;
import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class StudentAssessmentTest extends BaseTest
{
	@BeforeMethod
	public void init()
	{
		 rep = ExtentManager.getInstance();
		 test=rep.startTest("StudentAssessmentTest");
	}
	/*
	@AfterMethod
	public void quit()
	{
		rep.endTest(test);
		rep.flush();
	}
	*/
	
	@DataProvider
		public Object[][] getData()
		{
			Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
			Object[][] data = DataUtil.getData(xls, "StudentAssessmentTest");
			return data;
		}
	
	
	@Test(dataProvider="getData")
	public void Assessment(Hashtable<String,String> data) throws IOException, InterruptedException
	{
		defaultLogin(data);
		String expectedResult="PASS";
		String actualResult="";
		LoginPage lp= new LoginPage(driver, test);
		DashBoardPage dp= new DashBoardPage(driver, test);
		AssessmentPage ap= new AssessmentPage(driver, test);
		Thread.sleep(3000);
		Object resultPage = dp.openAssessment();
		ap.AttemptAssessment(data.get("Answer"));
		ap.SubmittedForEvaluation();
		ap.Evaluated();
		if( resultPage instanceof AssessmentPage)
		{
			test.log(LogStatus.INFO, "AssessmentPage validated");
			actualResult="PASS";
			System.out.println("AssessmentPage opens");
		}
		else
		{
			actualResult="FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "AssessmentPage not open");
			System.out.println("AssessmentPage not opens");
		}
		if(!expectedResult.equals(actualResult))
		{
			//take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Got actual result as "+actualResult);
			Assert.fail("Got actual result as "+actualResult);
		}
		
		
		
		
		test.log(LogStatus.PASS, "Assessment test passed");
		
	}
	
	@AfterMethod
	public void LogOut() throws InterruptedException
	{
		ProfilePage pg= new ProfilePage(driver, test);
		DashBoardPage dp= new DashBoardPage(driver, test);
		Thread.sleep(3000);
		pg.SettingsIcon.click();
		dp.LogOut.click();
		driver.quit();
	}
	

}
