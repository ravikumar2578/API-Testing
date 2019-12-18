package com.ExtramarksWebsite_TestCases;

import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.helpers.SyslogWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;



	public class LoginTest extends BaseTest {
		
		
		@BeforeMethod
		public void init(){
			 rep = ExtentManager.getInstance();
			 test=rep.startTest("LoginTest");
		
			// test = rep.startTest("Login Test");
	}
		
		public void quit(){
			rep.endTest(test);
			rep.flush();
			
		/*	if(driver !=null)
				driver.quit();*/
		}
		
		@Test(dataProvider="getData", priority=1)
		public void doLogin(Hashtable<String,String> data)throws InterruptedException{
			String expectedResult="Login_PASS";
			String actualResult="";
			String browser = data.get("Browser");
			test.log(LogStatus.INFO, "Login test started");
			if(!DataUtil.isTestRunnable(xls, "LoginTest")  ||  data.get("Runmode").equals("N")){
			
				test.log(LogStatus.SKIP, "Skipping the test");
				
				throw new SkipException("skipping the test");
			
				//throw new SkipException("Skipping the test");
			}
			
			//System.out.println("test skip code");
			
			openBrowser(browser);
			test.log(LogStatus.INFO, "Browser Opened");
			LaunchPage launch=new LaunchPage(driver, test);
			//HomePage hp = lp.goToHomePage();
			LoginPage lp=launch.goToHomePage();
			String title= driver.getTitle();
			System.out.println(title);
			test.log(LogStatus.INFO, "Extramarks Website Home Page");
			lp.takeScreenShot();
			 WebElement signin = driver.findElement(By.xpath("//*[@class='signin']"));
			 signin.click();
			test.log(LogStatus.INFO, "Trying to Login");
			Object resultPage=lp.doLogin(data.get("Username"),data.get("Password"));
			
			driver.switchTo().alert().accept();
			
			if(resultPage instanceof LoginPage){
				test.log(LogStatus.INFO, "Not able to Login");
				actualResult="Login_FAIL";
				System.out.println("Not logged in");
			}
			else if(resultPage instanceof DashBoardPage){
				test.log(LogStatus.INFO, "Able to Login");
				actualResult="Login_PASS";
				System.out.println("Logged in");
			}
			
			if(!expectedResult.equals(actualResult)){
				//take screenshot
				lp.takeScreenShot();
				test.log(LogStatus.FAIL, "Got actual result as "+actualResult);
				Assert.fail("Got actual result as "+actualResult);
			}
			
			test.log(LogStatus.PASS, "Login Test Passed");
			
		}
		
	

	@DataProvider
	public Object[][] getData(){
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "LoginTest");
		return data;
	}
	
	@Test(testName="ABC",priority=2)
	public static void study() throws InterruptedException{
		test.log(LogStatus.INFO, "open study tab");
		DashBoardPage dp = new DashBoardPage(driver, test); 
		//dp.getStudy();
		dp.openstudytab();
		Thread.sleep(3000);
		dp.takeScreenShot();
		//System.out.println("test complete");
		 WebElement eng = driver.findElement(By.xpath("//a[@href='#/chapterDetails/165/subject']"));
         eng.click();
         Thread.sleep(3000);
     	dp.takeScreenShot();
     	test.log(LogStatus.INFO, "open subject");
         WebElement eng_Communicative = driver.findElement(By.xpath("//*[text()='English Communicative : Grammatical Skills']"));
         eng_Communicative.click();
     	dp.takeScreenShot();
     	test.log(LogStatus.INFO, "open subsubject");
         WebElement activepassive = driver.findElement(By.xpath("//a[@href='#/chapterDetails/1743/chapter']"));
         activepassive.click();
     	dp.takeScreenShot();
     	test.log(LogStatus.INFO, "open chapter");
         WebElement Test = driver.findElement(By.xpath("//a[@id='test-panel']"));
         Test.click();

     	dp.takeScreenShot();
     	 //---------------------------------MCQ----------------------
	test.log(LogStatus.INFO, "MCQ Test Start");
        WebElement mcq = driver.findElement(By.xpath("//i[@class='mcq']"));
        mcq.click();
       	dp.takeScreenShot();

        List<WebElement> level_test = driver.findElements(
                                        By.xpath("//div[@id='pl-table-cont13']//div[@class='media-body media-middle text-left']"));
        int no_of_leveltest = level_test.size();
        System.out.println("no of level count in MCQ: " + no_of_leveltest);
        test.log(LogStatus.INFO, "No of level count in MCQ"+ no_of_leveltest);
        WebElement choosetest = driver.findElement(By.xpath("//*[@class='btn practise-btn orange ng-scope']"));
        Thread.sleep(3000);
        choosetest.click();
        Thread.sleep(3000);
       	dp.takeScreenShot();

        WebElement popmenu = driver.findElement(By.xpath("//div[@class='modal fade ng-scope in']//*[@class='close']"));
        popmenu.click();
        Thread.sleep(3000);
       	dp.takeScreenShot();

        List<WebElement> questions = driver
                                        .findElements(By.xpath("//div[@class='row']//ul[@class='testq-counter ng-scope']//li//span"));
        System.out.println("number of questions in MCQ: " + questions.size());
        test.log(LogStatus.INFO, "number of questions in MCQ:"+ questions.size());
        for (int i = 0; i < questions.size(); i++) {

                        if (i < 4) 
                        {
                                        Thread.sleep(2000);
                                        driver.findElement(By.xpath(" //div[@class='col-sm-12 chapter-list-name']//form//div[1]")).click();
                                        Thread.sleep(3000);
                                        dp.takeScreenShot();
                        }
                        else if (i > 4) 
                        {
                                        Thread.sleep(2000);
                                        driver.findElement(By.xpath(" //div[@class='col-sm-12 chapter-list-name']//form//div[2]")).click();
                                        Thread.sleep(3000);
                                        dp.takeScreenShot();
                        }

                        }
        Thread.sleep(5000);
        
        WebElement finish = driver.findElement(By.xpath("//*[@class='btn practise-btn orange ml10 font12 ng-scope']"));
        finish.click();
        Thread.sleep(2000);
        
       	dp.takeScreenShot();
       	test.log(LogStatus.PASS, "MCQ Test Pass");
        
        //--------------------------------Addaptive test-----------------------
        driver.navigate().back();
        WebElement Test_1 = driver.findElement(By.xpath("//a[@id='test-panel']"));
        Test_1.click();
       	dp.takeScreenShot();

        WebElement Test_Adaptive = driver.findElement(By.xpath("//i[@class='adaptive-test']"));
        Test_Adaptive.click();
       	dp.takeScreenShot();

        WebElement Start_Test = driver.findElement(By.xpath("//a[@class='btn jawab-btn']"));
        Start_Test.click();
        Thread.sleep(3000);
       	dp.takeScreenShot();
        
        //WebElement closeIcon=driver.findElement(By.xpath("//div[@class='modal fade ng-scope in']//*[@class='close']"));
        
        WebDriverWait wait= new WebDriverWait(driver, 10);
        WebElement closeIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='modal fade ng-scope in']//*[@class='close']")));
        closeIcon.click();

       	dp.takeScreenShot();
        
        
        List<WebElement> questionsinAdaptive = driver.findElements(By.xpath("//div[@class='col-sm-12 chapter-list-name']"));
        
        System.out.println("number of questions in MCQ: " + questionsinAdaptive.size());
        for (int b = 0; b< questionsinAdaptive.size(); b++) 
        {
                        if (b<4)
                        {
                                        Thread.sleep(2000);
                                        driver.findElement(By.xpath(" //div[@class='col-sm-12 chapter-list-name']//form//div[2]")).click();
                                       	dp.takeScreenShot();
                       }
                        else if (b > 4) 
                        {
                                        Thread.sleep(2000);
                                        driver.findElement(By.xpath(" //div[@class='col-sm-12 chapter-list-name']//form//div[4]")).click();
                                       	dp.takeScreenShot();
                        }

        }

        
        
        //--------------------------------Peroidic test----------------------
Thread.sleep(3000);
        driver.navigate().back();
        Thread.sleep(3000);
        WebElement Test_2 = driver.findElement(By.xpath("//a[@id='test-panel']"));
        Test_2.click();
       	dp.takeScreenShot();

        WebElement Test_perodic = driver.findElement(By.xpath("//i[@class='periodic-test']"));
        Test_perodic.click();
       	dp.takeScreenShot();

        List<WebElement> Servicetype = driver
                                        .findElements(By.xpath("//ul[@class='table-list ng-scope']//a[@id='serviceType.serviceId']"));

        int Servicecount = Servicetype.size();

        System.out.println("total number of service under Periodic Test ---" + Servicecount);

        for (int j = 0; j < Servicecount; j++) {
                        Servicetype.get(j).click();
                       	dp.takeScreenShot();

                        List<WebElement> ques = driver.findElements(By.xpath("//*[contains(text(),'View Answer')]"));

                        int quescount = ques.size();
                        System.out.println("no of questions = " + quescount);

        }

        
        
        
        
        
        //---------------------------------UniformSA----------------------
        driver.navigate().back();

        WebElement Test2 = driver.findElement(By.xpath("//a[@id='test-panel']"));
        Test2.click();
       	dp.takeScreenShot();

        WebElement UniformSA = driver.findElement(By.xpath("//i[@class='uniform-sa']"));
        Thread.sleep(3000);
        UniformSA.click();
       	dp.takeScreenShot();

        List<WebElement> Service_uniformSA = driver.findElements(By.xpath("//a[@id='serviceType.serviceId']"));

        int count1 = Service_uniformSA.size();

        System.out.println("total number of service under Periodic Test ---" + count1);
        Thread.sleep(3000);

        for (int k = 0; k < count1; k++) {
                        Thread.sleep(3000);

                        Service_uniformSA.get(k).click();
                       	dp.takeScreenShot();
        }

        List<WebElement> Question = driver.findElements(By.xpath("//*[contains(text(),'View Answer')]"));

        int Questioncount = Question.size();
        System.out.println("no of questions = " + Questioncount);

        Thread.sleep(2000);
        

        
        //----------------------Solved Board Papers----------------------

driver.navigate().back();

        

        WebElement Test_3 = driver.findElement(By.xpath("//a[@id='test-panel']"));
        Test_3.click(); 


        WebElement SolvedBoardpapers = driver.findElement(By.xpath("//i[@class='solved-board-papers']"));
        Thread.sleep(3000);
        SolvedBoardpapers.click();
       	dp.takeScreenShot();
        List<WebElement> WholeServicetype = driver.findElements(By.xpath("//ul[@class='table-list ng-scope']"));
        int WholeServicetypecount = WholeServicetype.size();
        
        System.out.println("total number of solved paper ----" + WholeServicetypecount);

        /*
        * JavascriptExecutor js= (JavascriptExecutor)driver; ((JavascriptExecutor)
        * driver).executeScript("window.scrollBy(0,200)");
        */

                        for (int x = 0; x < WholeServicetypecount; x++){
                                        Thread.sleep(3000);
                                        WholeServicetype.get(x).click();
                                       	dp.takeScreenShot();

                                                        List<WebElement> question = 
                                                        driver.findElements(By.xpath("//div[@class='model-paper']//*[text()='View Answer']"));

                        int questioncount = question.size();
                        System.out.println("no of questions = " + questioncount);
                        for (int p = 0; p < questioncount; p++) {
                                        
                                        Thread.sleep(5000);
                                        question.get(p).click();
                                       	dp.takeScreenShot();

                                        Thread.sleep(3000);

                                        System.out.println("welcome in Test");
                                        Thread.sleep(5000);
                                        WebElement qs=driver.findElement(By.xpath("//*[@class='modal fade in']/div/div/div/button"));
                                        boolean qa=qs.isDisplayed();
                                        System.out.println(qa);
                                        qs.click();
                                       	dp.takeScreenShot();
                                        
                                        //WebElement closeAns = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='select-class']/div/div/button")));
                                        
                                        //closeAns.click();
                                        // driver.findElement(By.xpath("//div[@class='modal-content']//button[@class='close']")).click();
                                        //driver.findElement(By.xpath("(//div[@class='modal-content']//button[@class='close'])[1]")); // Need to discuss

                                        System.out.println(p);

                                        

                        } 
                        Thread.sleep(5000);
                      /* boolean endteststatus= driver.findElement(By.xpath("//a[@id='endTest']")).isDisplayed();
                        if (endteststatus= true)
                        
                        {
                        Thread.sleep(5000);
                        WebElement Endtest=driver.findElement(By.xpath("//a[@id='endTest']"));
                        Endtest.click();
                        driver.switchTo().alert().accept();
                        driver.close();
                        }*/
        }

}


         

	}



	
