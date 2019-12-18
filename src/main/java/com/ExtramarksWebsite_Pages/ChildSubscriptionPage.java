package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ChildSubscriptionPage extends BasePage
{
	@FindBy(xpath="//button[contains(text(),'Program Subscription')]")
	WebElement ProgramSubscribtion;
	
	@FindBy(xpath="//ul[@class='dropdown-menu pull-right subscription-select-child']")
	List<WebElement> SelectChild;
	
	@FindBy(xpath="//div[@class='row clearfix']//div[@style='padding-left: 15px;']")
	WebElement ProgramSubMessage;
	
	public ChildSubscriptionPage(WebDriver driver, ExtentTest test)
	{
		super(driver, test);
		PageFactory.initElements(driver, this);
	}
	LoginPage lp= new LoginPage(driver, test);
	public void openProgSubs() throws InterruptedException
	{
		/*
		 *  Buy Package not complete, needs to work on it  
		 * 
		 */
		
		
		ProgramSubscribtion.click();
		SelectChild.get(0).click();
		Thread.sleep(3000);
		String Message = ProgramSubMessage.getText();
		lp.takeScreenShot();
		test.log(LogStatus.INFO,"This message "+Message+ "is displaying");
		
		
		
	}
	
}
