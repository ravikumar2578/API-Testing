package com.ExtramarksWebsite_Pages;

import java.util.Hashtable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.ExtramarksWebsite_Utils.Constants;
import com.relevantcodes.extentreports.ExtentTest;

public class LaunchPage extends BasePage {
	public LaunchPage(WebDriver dr, ExtentTest t) {
		super(dr, t);
	}

	public void openpage() {
		driver.get(Constants.URL);
		PageFactory.initElements(driver, this);

	}

	/*
	 * public class LaunchPage extends BasePage {
	 * 
	 * public LaunchPage(WebDriver dr,ExtentTest t){ super(dr,t); }
	 */

	public LoginPage goToHomePage() {
		driver.get(Constants.URL);
		LoginPage lp = new LoginPage(driver, test);
		PageFactory.initElements(driver, lp);
		// test.log(LogStatus.INFO, "Reached Emscc_Admin homepage");
		return lp;

	}

	public SignupPage goToDashboardPage() {
		driver.get(Constants.URL);
		SignupPage sp = new SignupPage(driver, test);
		PageFactory.initElements(driver, sp);
		// test.log(LogStatus.INFO, "Reached Emscc_Admin homepage");
		return sp;

	}

}
