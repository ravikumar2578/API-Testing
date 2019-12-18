package com.ExtramarksWebsite_TestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.SignupPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class SignUpTest extends BaseTest
{
	@BeforeMethod
		public void init()
	{
		rep = ExtentManager.getInstance();
		 test=rep.startTest("SignUpTest");
	}
	
	@DataProvider
	public Object[][] getData()
	{
		Xls_Reader xls= new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data= DataUtil.getData(xls, "SignUpTest");
		return data;
	}
/*	
	@DataProvider
	public Object[][] setData()
	{
		Xls_Reader xls= new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data1= DataUtil.setData(xls, "SignUpTest");
		return data1;
	}*/
	
	@Test(dataProvider="getData", priority=1)
	
	public void signUp(Hashtable<String,String> data)throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		String expectedResult="Signup_PASS";
		String actualResult="";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "SignUp test started");
		if(!DataUtil.isTestRunnable(xls, "SignUpTest")  ||  data.get("Runmode").equals("N"))
		{
		
			test.log(LogStatus.SKIP, "Skipping the test");
			
			throw new SkipException("Skipping the test");
		
			//throw new SkipException("Skipping the test");
		}
		//System.out.println("test skip code");
		
		openBrowser(browser);
		test.log(LogStatus.INFO, "Browser Opened");
		LaunchPage launch=new LaunchPage(driver, test);
		//HomePage hp = lp.goToHomePage();
		SignupPage sp=launch.goToDashboardPage();
		String title= driver.getTitle();
		System.out.println(title);
		test.log(LogStatus.INFO, "Extramarks Website Home Page");
		sp.takeScreenShot();
		
		test.log(LogStatus.INFO, "Trying to Signup");
		Object resultPage=sp.signup(data.get("Name"),data.get("Mobile"),data.get("City"));
		
		 String dbUrl = "jdbc:mysql://10.1.17.6/phpauto/";
		 String username1 = "auto_test";
		 String password1 = "Abc!@#456";
		
		 String query = "SELECT * FROM t_sms_log where mobile_number='+91-9929939941'";
		 
		  Class.forName("com.mysql.jdbc.Driver");
		  Connection con = DriverManager.getConnection(dbUrl, username1, password1);
		  Statement stmt = con.createStatement();
		  
		 /* PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
		
		  preparedStmt.setString(1, a);
          preparedStmt.executeUpdate();
          */
		  
        
			ResultSet rs = stmt.executeQuery(query);
			rs.absolute(-1);// for last row in Table
			int count = rs.getMetaData().getColumnCount();
			System.out.println("columncount >"+count);
			for(int i=1;i<=count;i++)
			{
			String ColumnNAME=rs.getMetaData().getColumnName(i);
			String ColName=i+" "+"ColName >" +ColumnNAME;
			System.out.println(ColName);
			/*String Columnvalue=rs.getString(i);
			System.out.println(ColumnNAME + ":"+" "+Columnvalue);*/
			}
			
			String OTPMessage = rs.getString(5);
			String MobileNumber = rs.getString(3);
			System.out.println("mobile no. > " + MobileNumber);
			System.out.println("SMS Text >" + OTPMessage);
			String OTP = OTPMessage.substring(17, 23);
			//long OTP = Long.parseLong(OTPMessage.replaceAll("\\D", ""));
			
			System.out.println("Required OTP for Registration > " + OTP);
			con.close();
			
			/*FileInputStream fis = new FileInputStream(Constants.XLS_FILE_PATH);
			FileOutputStream fos = new FileOutputStream(Constants.XLS_FILE_PATH);
    		XSSFWorkbook workbook = new XSSFWorkbook(fis);
    		XSSFSheet sheet = workbook.getSheet("Data");
    		Row row = sheet.createRow(59);
    		Cell cell = row.createCell(6);
    		cell.setCellValue(OTP);
    		
    		workbook.write(fos);
	*/}
	
	
	
	
	
	
}	
