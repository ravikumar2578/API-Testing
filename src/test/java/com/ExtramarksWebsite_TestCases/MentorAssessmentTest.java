package com.ExtramarksWebsite_TestCases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.Mentor_Assessment;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class MentorAssessmentTest extends BaseTest 
{
	@BeforeMethod
	public void init()
	{
		 rep = ExtentManager.getInstance();
		 test=rep.startTest("MentorAssessmentTest");
	}
	@DataProvider
	public Object[][] getData()
	{
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "MentorAssessmentTest");
		return data;
	}
	
	
	
	
	@Test(dataProvider="getData")
	public void MentorAssessment(Hashtable<String,String> data) throws InterruptedException
	{
		defaultLogin(data); 
		String expectedResult="PASS";
		String actualResult="";
		LoginPage lp= new LoginPage(driver, test);
		DashBoardPage dp= new DashBoardPage(driver, test);
		Mentor_Assessment ma= new Mentor_Assessment(driver, test);
		dp.Assessment.click();
		test.log(LogStatus.INFO, "Open Assessment Page");
		ma.DraftAssessment();
		Thread.sleep(3000);
	
		
		ma.AssessmentDetails(data.get("Title"),data.get("Class"),data.get("Subject"),data.get("Chapter"), data.get("Hours_1"), data.get("Hours_2"),data.get("Minutes_1"),data.get("Minutes_2"),data.get("AssessmentInstruction"));
		
		Object result = ma.createAssessment(data.get("Ques_Area")); 
		
		ma.AssessmentList(data.get("Marks"));
		
		if(result instanceof Mentor_Assessment)
		{
			test.log(LogStatus.INFO, "AssessmentPage opens");
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
		
		test.log(LogStatus.PASS, "AssessmentTest passed");
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
