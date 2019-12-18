package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Mentor_StudentPage extends BasePage
{
	

	@FindBy(xpath="//img[@alt='add-child']")
	WebElement AddChild;
	
	@FindBy(xpath="//input[@id='student_email']")
	WebElement EmailChild;
	
	@FindBy(xpath="//select[@id='student_board']")
	WebElement StudentBoard;
	
	@FindBy(xpath="//select[@id='student_class']")
	WebElement StudentClass;
	
	@FindBy(xpath="//select[@id='student_subject']")
	WebElement StudentSubject;
	
	@FindBy(xpath="//button[@id='popup_addmurid']")
	WebElement AddStudentButton;
	
	@FindBy(xpath="//div[@id='add-murid']//button[@class='close']")
	WebElement CloseButton;
	
	@FindBy(xpath="//img[@alt='user-guide']")
	public List<WebElement> StudentList;
	
	
	
	
	public Mentor_StudentPage(WebDriver driver, ExtentTest test)
	{
		super(driver, test);
		PageFactory.initElements(driver, this);
	}
	LoginPage lp= new LoginPage(driver, test);
	
	public void AddStudent(String email, String Board, String Class, String Subject) throws InterruptedException
	{
		AddChild.click();
		Thread.sleep(3000);
		test.log(LogStatus.INFO, "Adding new Student");
		lp.takeFullScreenshot();
		EmailChild.sendKeys(email);
		Select sel = new Select(StudentBoard);
		sel.selectByVisibleText(Board);
		
		Thread.sleep(2000);
		StudentClass.click();
		Select sel1= new Select(StudentClass);
		sel1.selectByVisibleText(Class);
		
		Select sel2= new Select(StudentSubject);
		sel2.selectByVisibleText(Subject);
		AddStudentButton.click();
		Thread.sleep(3000);
		lp.takeScreenShot();
		
		
		/*
		 * Add Student service is not working in test-automation.www.extramarks.com
		 * 
		*/
		
		
		
		
		//close button will not be clicked once Select subject start working.
		CloseButton.click();
		
	}
	
	
	
	
}
