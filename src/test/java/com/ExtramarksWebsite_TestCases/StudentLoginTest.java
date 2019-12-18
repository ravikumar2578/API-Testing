package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.AssessmentPage;
import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.NotesPage;
import com.ExtramarksWebsite_Pages.NotificationPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.ReportPage;
import com.ExtramarksWebsite_Pages.SchedulePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class StudentLoginTest extends BaseTest
{
	@BeforeMethod
	public void init()
	{
		 rep = ExtentManager.getInstance();
		 test=rep.startTest("StudentLoginTest");
	}
	
	/*@AfterMethod
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
		Object[][] data = DataUtil.getData(xls, "StudentLoginTest");
		return data;
	}
	
	@Test(dataProvider="getData")
	public static void doLogin(Hashtable<String,String> data)throws InterruptedException
	{
		String expectedResult="LOGIN_PASS";
		String actualResult="";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		if(!DataUtil.isTestRunnable(xls, "StudentLoginTest")  ||  data.get("Runmode").equals("N"))
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
		String actual_url="http://testautomation-www.extramarks.com/";
		String errorMEssage="Please login by registered identity";
		String Login_Error="Please enter valid email/username and password";  
		String loginFailed="Please enter valid email/username and password"; 
				
		if(url.equals(actual_url))
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
		
	/*@Test(priority=3)
	public void AddSchedule(Hashtable<String, String>data) throws InterruptedException
	{
		LoginPage lp= new LoginPage(driver, test);
		String expectedResult="PASS";
		String actualResult="";
		DashBoardPage dp= new DashBoardPage(driver, test);
		SchedulePage sp= new SchedulePage(driver, test);
		
		Object resultPage = dp.openSchedule();
		//sp.clickAddSchedule();
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
	
	@Test(dataProvider="getData",priority= 4)
	public void Notes(Hashtable<String, String> data) throws IOException, InterruptedException
	{
		String expectedResult="PASS";
		String actualResult="";
		LoginPage lp= new LoginPage(driver, test);
		DashBoardPage dp=new DashBoardPage(driver, test);
		NotesPage np= new NotesPage(driver, test);
		Object resultPage = dp.openNotes();
		
		np.AddNotes(data.get("Title"),data.get("Class"), data.get("Subject"), data.get("Chapter"), data.get("Description"));
		//np.DeleteNotes();
		np.EditNotes(data.get("EditSubject"), data.get("EditChapter"));
		if( resultPage instanceof NotesPage)
		{
			test.log(LogStatus.INFO, "Notes Page Validated");
			actualResult="PASS";
			System.out.println("NotesPage opens");
		}
		else
		{
			actualResult="FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "Notes Page not open");
		}
		if(!expectedResult.equals(actualResult))
		{
			//take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Got actual result as "+actualResult);
			Assert.fail("Got actual result as "+actualResult);
		}
		test.log(LogStatus.PASS, "Notes Test passed");
		
		
		
	}
	
	@Test(priority=5)
	public void Assessment() throws IOException, InterruptedException
	{
		String expectedResult="PASS";
		String actualResult="";
		LoginPage lp= new LoginPage(driver, test);
		DashBoardPage dp= new DashBoardPage(driver, test);
		AssessmentPage ap= new AssessmentPage(driver, test);
		Thread.sleep(3000);
		Object resultPage = dp.openAssessment();
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
		
		
		ap.AttemptAssessment();
		ap.SubmittedForEvaluation();
		ap.Evaluated();
		
		test.log(LogStatus.PASS, "Assessment test passed");
		
		
	}
	
	@Test(priority=6)
	public void Report() throws InterruptedException, IOException
	{
		String expectedResult="PASS";
		String actualResult="";
		LoginPage lp= new LoginPage(driver, test);
		DashBoardPage dp= new DashBoardPage(driver, test);
		ReportPage rp= new ReportPage(driver, test);
		Thread.sleep(3000);
		Object resultPage = dp.openReport();
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
		
	rp.viewReport();
	test.log(LogStatus.PASS, "Report Test passed");
	
	}*/
/*	
	@Test(priority=7)
	public void Profile() throws InterruptedException
	{
		
		ProfilePage PrPage= new ProfilePage(driver, test);
		PrPage.openProfile();
		
				
	}
	
	@Test(priority=8)
	public void Notification()
	{
		String expectedResult="PASS";
		String actualResult="";
		LoginPage lp= new LoginPage(driver, test);
		
		NotificationPage np= new NotificationPage(driver, test);
		np.openNotification();
	}*/
	
	@AfterMethod
	public void LogOut() throws InterruptedException
	{
		ProfilePage pg= new ProfilePage(driver, test);
		DashBoardPage dp= new DashBoardPage(driver, test);
		Thread.sleep(3000);
		pg.SettingsIcon.click();
		Thread.sleep(3000);
		dp.LogOut.click();
		driver.quit();
	}
	
	
	
}
