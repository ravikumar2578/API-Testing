package com.ExtramarksWebsite_TestCases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
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
import com.ExtramarksWebsite_Pages.Mentor_StudentPage;
import com.ExtramarksWebsite_Pages.Parent_MyChildPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;
import com.thoughtworks.selenium.webdriven.commands.IsAlertPresent;

public class ParentMyChildTest extends BaseTest
{

	@BeforeMethod
	public void init()
	{
		 rep = ExtentManager.getInstance();
		 test=rep.startTest("ParentMyChildTest");
	}
	@DataProvider
	public Object[][] getData()
	{
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "ParentMyChildTest");
		return data;
	}
	
	@Test(dataProvider="getData")
	public void MyChild(Hashtable<String,String> data) throws InterruptedException
	{
		
		
		Mentor_StudentPage msp= new Mentor_StudentPage(driver, test);
	
		defaultLogin(data);
		Thread.sleep(5000);
		LoginPage lp= new LoginPage(driver, test);
		
		String expectedResult = "PASS";
		String actualResult="";
	
		
		String url = driver.getCurrentUrl();
		String actual_url="http://testautomation-www.extramarks.com/user/teacher-dashboard/3";
		
		DashBoardPage dp=new DashBoardPage(driver, test);
		if(url.equals(actual_url) && dp.ParentTab()==0)
		{
			System.out.println("Logged in but not on parent page");
			test.log(LogStatus.INFO, "Logged in but not on parent page");
			actualResult="Login_Fail";
			/*SoftAssert sa = new SoftAssert();
			sa.assertEquals(actualResult, expectedResult);
			sa.assertAll();*/
			test.log(LogStatus.FAIL, "Got actual result as "+actualResult);
			Assert.fail("got actual result as= "+actualResult);
		}
		
		
		

		//LoginPage lp= new LoginPage(driver, test);
		/*	int TotalChild = msp.StudentList.size();
		System.out.println("Total Child ="+TotalChild);
		*/
		WebDriverWait wait= new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(dp.MyChild));
		Object resultPage = dp.openMyChild();
		Parent_MyChildPage pmc= new Parent_MyChildPage(driver, test);
		pmc.AddChild(data.get("ChildEmail"));
		pmc.AddChildNew(data.get("ChildName"),data.get("ChildUserId"),data.get("Password"),data.get("SchoolName"));
		
		if( resultPage instanceof Parent_MyChildPage)
		{
			test.log(LogStatus.INFO, "MyChildPage opens");
			actualResult="PASS";
			System.out.println("MyChildPage opens");
		}
		else
		{
			actualResult="FAIL";
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "MyChildPage not open");
			System.out.println("MyChildPage not opens");
		}
		 try  
		 {
		    Alert alert = driver.switchTo().alert();
		    System.out.println(alert.getText()+" Alert is Displayed"); 
	    }
	    catch(NoAlertPresentException ex)
		{
	    	actualResult="FAIL";
		    System.out.println("Save alert is not displayed");
		    test.log(LogStatus.INFO, "Unable to Save the Child Details");
		    
	    }
			    
		if(!expectedResult.equals(actualResult))
		{
			//take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Got actual result as "+actualResult);
			Assert.fail("Got actual result as "+actualResult);
		}
		else
		{
		test.log(LogStatus.PASS, "Parents MyChildTest passed");
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
