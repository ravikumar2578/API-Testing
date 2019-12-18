package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class NotificationPage extends BasePage
{
	@FindBy(xpath="//li[@role='presentation' ]//a[@class='dropdown-toggle']")
	List<WebElement> NotificationIcon;
	
	@FindBy(xpath="//a[@class='view-all-notification-btn']")
	WebElement ViewNotification;
	
	@FindBy(xpath="//a[@class='subject-click']//img[@alt='Extramarks Logo']")
	List<WebElement> Notification;
	
	@FindBy(xpath="//a[contains(text(),'Back')]")
	List<WebElement> back;
	
	public NotificationPage(WebDriver driver, ExtentTest test)
	{
		super(driver,test);
		PageFactory.initElements(driver, this);
	}
	
	public void openNotification()
	{
		NotificationIcon.get(0).click();
		ViewNotification.click();
		int AllNotification = Notification.size();
		for(int i=0; i<AllNotification;i++)
		{
			Notification.get(i).click();
			int Back = back.size();
			if(Back==0)
			{
				driver.navigate().back();				
			}
				
		}
	}
	
	
}
