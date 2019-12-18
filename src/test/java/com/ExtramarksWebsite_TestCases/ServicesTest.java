package com.ExtramarksWebsite_TestCases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.ServicesPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class ServicesTest extends BaseTest
{
	@BeforeMethod
	public void init()
{
	rep = ExtentManager.getInstance();
	 test=rep.startTest("ServicesTest");
}

@DataProvider
public Object[][] getData()
{
	Xls_Reader xls= new Xls_Reader(Constants.XLS_FILE_PATH);
	Object[][] data= DataUtil.getData(xls, "ServicesTest");
	return data;
}

	@Test( dataProvider="getData",priority=1)
	public void LearningApp(Hashtable<String, String>data) throws InterruptedException
	{
		String expectedResult="PASS";
		String actualResult="";
		String browser = data.get("Browser");

		openBrowser(browser);
		LaunchPage lp= new LaunchPage(driver, test);
		lp.openpage();
		ServicesPage sp= new ServicesPage(driver, test);
		sp.LearningApp();
		Thread.sleep(2000);
		
	/*	String url="https://apps.apple.com/in/app/extramarks-the-learning-app/id1232323918";
		String currentURL = driver.getCurrentUrl();
		  if(url.equals(currentURL))
		  {
			  actualResult="PASS";
			  System.out.println("AppStore Page Opens");
			  test.log(LogStatus.INFO, "AppStore Page Opens");
		  }
		  else
		  {
			  actualResult="FAIL";
			  test.log(LogStatus.INFO, "AppStore Page doesn't open");
		  }
		  
		  if(!expectedResult.equals(actualResult))
		  {
			  Assert.fail("Got actual result as: "+actualResult);
			  test.log(LogStatus.FAIL, "FAILED");
		  }
		  else
		  {
			  test.log(LogStatus.PASS, "Passed");
		  }
		  
		  driver.close();
		  driver.switchTo().window(nameOrHandle);*/
		driver.quit();
	}
	
	@Test(dataProvider="getData",priority=2)
	public void KidsLearning(Hashtable<String, String>data) throws InterruptedException
	{
		String browser=data.get("Browser");
		openBrowser(browser);
		LaunchPage lp= new LaunchPage(driver, test);
		lp.openpage();
		
		ServicesPage sp= new ServicesPage(driver, test);
		sp.KidsLearning();
	}
	
	
	
	
	
	
}
