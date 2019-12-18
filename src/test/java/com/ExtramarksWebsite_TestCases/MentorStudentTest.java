package com.ExtramarksWebsite_TestCases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.Mentor_StudentPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class MentorStudentTest extends BaseTest
{
	
	@BeforeMethod
	public void init()
	{
		 rep = ExtentManager.getInstance();
		 test=rep.startTest("MentorStudentTest");
	}
	@DataProvider
	public Object[][] getData()
	{
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "MentorStudentTest");
		return data;
	}
	
	
	@Test(dataProvider="getData")
	public void MentorsStudent(Hashtable<String, String> data) throws InterruptedException
	{
		defaultLogin(data);
		String expectedResult="PASS";
		String actualResult="";
		LoginPage lp= new LoginPage(driver, test);
		DashBoardPage dp= new DashBoardPage(driver, test);
		Mentor_StudentPage msp= new Mentor_StudentPage(driver, test);
		Object resultPage = dp.openStudent();
		msp.AddStudent(data.get("Email"), data.get("Board"), data.get("Class"), data.get("Subject"));
		if(resultPage instanceof Mentor_StudentPage)
		{
			test.log(LogStatus.INFO, "StudentPage opens");
			actualResult="PASS";
			System.out.println("StudentPage opens");
		}
		
		else
		{
			actualResult="FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "StudentPage not open");
			System.out.println("StudentPage not opens");
		}
		if(!expectedResult.equals(actualResult))
		{
			//take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Got actual result as "+actualResult);
			Assert.fail("Got actual result as "+actualResult);
		}
		
		test.log(LogStatus.PASS, "Mentors StudentTest passed");
		
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
