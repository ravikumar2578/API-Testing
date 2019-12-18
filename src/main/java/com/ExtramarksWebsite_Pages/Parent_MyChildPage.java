package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Parent_MyChildPage extends BasePage {
	
	
	@FindBy(id = "childEmail")
	WebElement ChildEmail;

	@FindBy(id = "addChildButton")
	WebElement AddChildButton;

	@FindBy(xpath = "//a[@class='text-lightblue']")
	WebElement AddChildOption;

	@FindBy(xpath = "//input[@id='child_name']")
	WebElement ChildName;

	@FindBy(xpath = "//input[@id='child_uid']")
	WebElement ChildUserId;

	@FindBy(xpath = "//input[@id='child_password']")
	WebElement Password;
	@FindBy(id = "date")
	WebElement DobBtn;

	@FindBy(xpath = "//div[@class='datetimepicker-days']//th[@class='switch']")
	WebElement SwitchDays;

	@FindBy(xpath = "//div[@class='datetimepicker-months']//th[@class='switch']")
	WebElement SwitchYear;

	@FindBy(xpath = "//div[@class='datetimepicker-years']//span[@class='year']")
	List<WebElement> Years;

	@FindBy(xpath = "//div[@class='datetimepicker-months']//span[@class='month']")
	List<WebElement> Months;

	@FindBy(xpath = "//div[@class='datetimepicker-days']//td[@class='day']")
	List<WebElement> Days;

	@FindBy(id = "gender")
	WebElement Gender;

	@FindBy(xpath = "//input[@id='school_name']")
	WebElement SchoolName;

	@FindBy(xpath = "//select[@id='board']")
	WebElement Board;

	@FindBy(xpath = "//select[@id='classname']")
	WebElement Class;

	@FindBy(xpath = "//a[text()='Save']")
	WebElement SaveButton;

	@FindBy(partialLinkText = "Child Subscription")
	public WebElement ChildSubs;
	@FindBy(xpath = "//img[@class='img-responsive img-center']")
	WebElement RegisterChild;

	public Parent_MyChildPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	public void AddChild(String childEmail) throws InterruptedException {

		RegisterChild.click();
		Thread.sleep(2000);
		lp.takeScreenShot();
		test.log(LogStatus.INFO, "Registering a child");
		ChildEmail.sendKeys(childEmail);
		lp.takeScreenShot();
		AddChildButton.click();
		Thread.sleep(3000);
		lp.takeScreenShot();
		test.log(LogStatus.INFO, "Child Added via email only");
	}

	public void AddChildNew(String child_Name, String userId, String pwd, String school_Name)
			throws InterruptedException {

		Thread.sleep(3000);
		AddChildOption.click();
		lp.takeFullScreenshot();
		test.log(LogStatus.INFO, "Adding new child");
		ChildName.sendKeys(child_Name);
		ChildUserId.sendKeys(userId);
		Password.sendKeys(pwd);
		DobBtn.click();

		SwitchDays.click();
		SwitchYear.click();
		Years.get(0).click();
		Months.get(0).click();
		Days.get(3).click();

		Select gender = new Select(Gender);
		gender.selectByVisibleText("Female");
		SchoolName.sendKeys(school_Name);

		Select board = new Select(Board);
		board.selectByVisibleText("ICSE");

		Select cls = new Select(Class);
		cls.selectByVisibleText("V");

		Thread.sleep(4000);
		lp.takeFullScreenshot();
		SaveButton.click();
		Thread.sleep(2000);
		/*
		 * driver.switchTo().alert().accept(); test.log(LogStatus.INFO,
		 * "New Child Added");
		 */ }

}
