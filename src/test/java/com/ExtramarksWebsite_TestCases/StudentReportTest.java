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
import com.ExtramarksWebsite_Pages.ReportPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class StudentReportTest extends BaseTest 
{

	@BeforeMethod
	public void init()
	{
		 rep = ExtentManager.getInstance();
		 test=rep.startTest("StudentReportTest");
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
			Object[][] data = DataUtil.getData(xls, "StudentReportTest");
			return data;
		}
	
	@Test(dataProvider="getData", priority=1)
	public void Report(Hashtable<String, String>data) throws InterruptedException, IOException
	{
		defaultLogin(data);
		String expectedResult="PASS";
		String actualResult="";
		LoginPage lp= new LoginPage(driver, test);
		DashBoardPage dp= new DashBoardPage(driver, test);
		ReportPage rp= new ReportPage(driver, test);
		Thread.sleep(3000);
		Object resultPage = dp.openReport();
		rp.viewReport();
		if( resultPage instanceof ReportPage)
		{
			test.log(LogStatus.INFO, "Opening ReportPage");
			actualResult="PASS";
			System.out.println("ReportPage opens");
		}
		else
		{
			actualResult="FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "ReportPage not open");
		}
		if(!expectedResult.equals(actualResult))
		{
			//take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Got actual result as "+actualResult);
			Assert.fail("Got actual result as "+actualResult);
		}
		
		test.log(LogStatus.PASS, "Report Test passed");
	
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
