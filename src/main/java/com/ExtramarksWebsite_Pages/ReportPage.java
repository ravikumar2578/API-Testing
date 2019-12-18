package com.ExtramarksWebsite_Pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportPage extends BasePage
{
	

	@FindBy(id="subejctdropdown")
	WebElement SubjectDropDwn;
	
	@FindBy(id="chapterDropDown")
	WebElement ChapterDropDwn;
	
	@FindBy(xpath="//a[@id='view_comments']")
	List<WebElement> View;
	
	@FindBy(xpath="//a[@class='practise-panel']")
	WebElement PractiseTab;
	
	@FindBy(xpath="//select[@id='subejctdropdown']//option[@ng-repeat]")
	List<WebElement> Subjects;
	@FindBy(xpath="//select[@id='chapterDropDown']//option[@ng-repeat]")
	List<WebElement> Chapters;
	
	@FindBy(xpath="//a[@id='rapor']")
	public WebElement Report;
	
	public ReportPage(WebDriver driver, ExtentTest test)
	{
		super(driver, test);
		PageFactory.initElements(driver, this);
	}
	LoginPage lp= new LoginPage(driver, test);
	
	public void viewReport() throws InterruptedException
	{
		//Report.click();
		Thread.sleep(3000);
		int TotalSubjects=Subjects.size();
		test.log(LogStatus.INFO, "Total Subjects : "+TotalSubjects);
		System.out.println("Total Subjects: "+TotalSubjects);
		//for(int i=0; i<=TotalSubjects;i++)
		{
		int i=0;
		Select sel= new Select(SubjectDropDwn);
		sel.selectByIndex(i);
		
		int TotalChapters= Chapters.size();
		//for(int j=0; j<=TotalChapters;j++)
		{
			int j=0;
			Select sel1= new Select(ChapterDropDwn);
			Thread.sleep(2000);
			sel1.selectByIndex(j);
			Thread.sleep(2000);
			lp.takeFullScreenshot();
		}
		}
	}
}
