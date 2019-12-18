package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.Mentor_SchedulePage;
import com.ExtramarksWebsite_Pages.Mentor_StudentPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.ReportPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class MentorDashboardTest extends BaseTest
{
	@BeforeMethod
	public void init()
	{
		 rep = ExtentManager.getInstance();
		 test=rep.startTest("MentorDashboardTest");
	}
	@DataProvider
	public Object[][] getData()
	{
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "MentorDashboardTest");
		return data;
	}
	
	
	@Test(dataProvider="getData")
	public void MentorDashboard(Hashtable<String, String> data) throws InterruptedException, IOException
	{
		defaultLogin(data);
		LoginPage lp=new LoginPage(driver, test);
		DashBoardPage dp= new DashBoardPage(driver, test);
		Mentor_StudentPage msp= new Mentor_StudentPage(driver, test);
		Mentor_SchedulePage msd= new Mentor_SchedulePage(driver, test);
		ReportPage rp= new ReportPage(driver, test);
		
		try 
		{
		dp.ViewActivities();
		Thread.sleep(3000);
		}
		catch(Exception e)
		{
		dp.Mentor_AddSchedule.click();
		int TotalStudents=msp.StudentList.size();
		test.log(LogStatus.INFO, "Total Students= "+TotalStudents);
		System.out.println("Total Students= "+TotalStudents);
		
		{
			int i=0;
			msp.StudentList.get(i).click();
			Thread.sleep(2000);
			//SHEDULE NOT WORKING IN TEST-AUTOMATION//
			
			msd.AddSchedule(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"));
				
			Thread.sleep(3000);
			
			msd.UploadFiles();
			Thread.sleep(3000);
			
				/*WebDriverWait wt= new WebDriverWait(driver, 20);
			wt.until(ExpectedConditions.elementToBeClickable(rp.Report));
			
			rp.viewReport();*/
			
			//driver.navigate().back();
			dp.openStudent();
			
		}
		dp.Dashboard.click();
	}
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
