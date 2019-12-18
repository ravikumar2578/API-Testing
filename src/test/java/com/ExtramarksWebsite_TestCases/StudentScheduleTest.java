package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.SchedulePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class StudentScheduleTest extends BaseTest
{
	@BeforeMethod
	public void init()
	{
		 rep = ExtentManager.getInstance();
		 test=rep.startTest("StudentScheduleTest");
	}
	
	/*
	@AfterMethod
	public void quit()
	{
		rep.endTest(test);
		rep.flush();
	}*/
	
	
	@DataProvider
		public Object[][] getData()
		{
			Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
			Object[][] data = DataUtil.getData(xls, "StudentScheduleTest");
			return data;
		}
	
	@Test(dataProvider="getData")
	public void AddSchedule(Hashtable<String, String>data) throws IOException, InterruptedException
	{
		defaultLogin(data);
		LoginPage lp= new LoginPage(driver, test);
		String expectedResult="PASS";
		String actualResult="";
		DashBoardPage dp= new DashBoardPage(driver, test);
		SchedulePage sp= new SchedulePage(driver, test);
		
		Object resultPage = dp.openSchedule();
		sp.clickAddSchedule(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"));
		Thread.sleep(3000);
		sp.clickMySchedule(data.get("EditSubject"), data.get("EditChapter"));
		sp.ExamSchedule();
		
		if( resultPage instanceof SchedulePage)
		{
			test.log(LogStatus.INFO, "Schedule Page Validated");
			actualResult="PASS";
			System.out.println("SchedulePage opens");
		}
		else
		{
			actualResult="FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "Schedule Page not open");
		}
		if(!expectedResult.equals(actualResult))
		{
			//take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Got actual result as "+actualResult);
			Assert.fail("Got actual result as "+actualResult);
		}
		test.log(LogStatus.PASS, "Schedule Test passed");
		dp.Dashboard.click();
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
