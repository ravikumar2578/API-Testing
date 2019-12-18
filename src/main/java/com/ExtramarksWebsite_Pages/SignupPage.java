package com.ExtramarksWebsite_Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SignupPage extends BasePage {
	public SignupPage(WebDriver dr, ExtentTest t) 
	{
		super(dr, t);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='register']")
	WebElement SignUp;

	@FindBy(id = "y_1")
	WebElement CheckboxYes;

	@FindBy(id = "display_name")
	WebElement Name;

	@FindBy(id = "mobile")
	WebElement MobileNo;

	@FindBy(id = "city")
	WebElement City;
	
	@FindBy(id = "s2id_autogen1_search")
	WebElement CitySendkeys;

	@FindBy(xpath = "//select[@id='city']")
	WebElement CityDropDwn;

	@FindBy(xpath = "//button[@name='submit']")
	WebElement Submit;

	@FindBy(xpath = "//a[@id='reg_otp-verify']")
	WebElement VerifyBtn;
	
	@FindBy(xpath="//div[@class='pincode-input-container']")
	public WebElement otp;
	
	

	public Object signup(String name, String mobile, String city) {
		SignUp.click();

		CheckboxYes.click();
		test.log(LogStatus.INFO, "Entering name: " + name);
		Name.sendKeys(name);

		test.log(LogStatus.INFO, "Entering mobile no. " + mobile);
		MobileNo.sendKeys(mobile);

	City.click();
	City.sendKeys(city);
	City.sendKeys(Keys.ENTER);
   // Select citysel= new Select (City);
   // citysel.selectByValue(city);

		Submit.click();
		takeScreenShot();
		////
		System.out.println(driver.getTitle());
		String title = driver.getTitle();
		System.out.println(title);
		boolean signup = false;
		// if(title.equals("Extramarks India"))
		if (title.equals(
				"Online Learning - CBSE / ICSE / NCERT Study Material, Tutorials, Syllabus & Sample Papers - Extramarks"))
			signup = true;
		else
			signup = false;

		if (!signup) {
			// test.log(LogStatus.INFO, "signup successful");
			SignupPage signupPage = new SignupPage(driver, test);
			PageFactory.initElements(driver, signupPage);
			return signupPage;
		} else {
			test.log(LogStatus.INFO, "signup successful");
			DashBoardPage dp = new DashBoardPage(driver, test);
			// DashBoardPage dp = new DashBoardPage(driver,test);
			PageFactory.initElements(driver, dp);
			return dp;
		}
	}

	

}
