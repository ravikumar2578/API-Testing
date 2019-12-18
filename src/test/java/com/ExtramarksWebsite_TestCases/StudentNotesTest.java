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

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.NotesPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class StudentNotesTest extends BaseTest
{
	@BeforeMethod
	public void init()
	{
		 rep = ExtentManager.getInstance();
		 test=rep.startTest("StudentNotesTest");
	}
			@DataProvider
		public Object[][] getData()
		{
			Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
			Object[][] data = DataUtil.getData(xls, "StudentNotesTest");
			return data;
		}
			
		@Test(dataProvider="getData")
		public void Notes(Hashtable<String, String> data) throws IOException, InterruptedException
		{
			defaultLogin(data);
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
