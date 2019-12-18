package com.ExtramarksWebsite_Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;

public class ProfilePage extends BasePage
{
	@FindBy(xpath="//img[@src='//testautomation-www.extramarks.com/images/website_v7/profile-setting-icon.png']")
	public WebElement SettingsIcon;
	
	@FindBy(partialLinkText="PROFILE")
	WebElement Profile;
	@FindBy(xpath="//a[@class='btn btn-border-orange']")
	WebElement EditProfile;
	@FindBy(xpath="//input[@id='pincode']")
	WebElement Pincode;
	@FindBy(xpath="//a[@class='btn btn-border-orange orange text-white']")
	WebElement Save;
	@FindBy(id="schedule_date10")
	WebElement Date;
	@FindBy(xpath="//span[@id='dob-date']")
	WebElement DOB;
	@FindBy(xpath="//div[@class='datetimepicker-years']")
	WebElement CurrentYear;
	@FindBy(xpath="//div[@class='datetimepicker-months']")
	WebElement CurrentMonth;
	
	
	
	public ProfilePage(WebDriver driver, ExtentTest test)
	{
		super(driver, test);
		PageFactory.initElements(driver, this);
	}
	
	/*public void SelectDate(WebElement calendar, String year, String monthName, String day, WebDriver driver)
	{
		DOB.click();
		String currentYear = CurrentYear.getText();
		if(!currentYear.equals(year));
		{
		do
		{
			driver.findElement(By.xpath("//div[@style='display: block;']//th[@class='prev']")).click();
		}
		while(!CurrentYear.getText().equals(year));
		}
	
	String currentMonth=CurrentMonth.getText();
	if(!currentMonth.equals(monthName))
	{
		do 
		{
			driver.findElement(By.xpath("//div[@style='display: block;']//th[@class='prev']")).click();
		}
		while(!CurrentMonth.getText().equals(monthName));
	}
	
	
	}*/
	
	
	public void openProfile() throws InterruptedException
	{
		SettingsIcon.click();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		Profile.click();
		EditProfile.click();
		Thread.sleep(3000);
		DOB.click();
		
		//if(CurrentYear!=year)
		
		
		
		Date.sendKeys("2000-06-13");
		Thread.sleep(3000);
		Pincode.sendKeys("201301");
		Save.click();
	}
	
	
	

}
