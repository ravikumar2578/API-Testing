package com.ExtramarksWebsite_Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ExtramarksWebsite_Utils.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
	}

	@FindBy(id = Constants.LOGIN_USERNAME)
	public WebElement usernameField;

	@FindBy(id = Constants.LOGIN_PASSWORD)
	public WebElement passwordField;

	@FindBy(id = Constants.LOGIN_BUTTON)
	public WebElement Submit_button;

	@FindBy(id = "errorusernameLogin")
	public WebElement Error_Username;

	@FindBy(id = "errorpasswdLogin")
	public WebElement Error_Password;

	@FindBy(id = "loginFailed")
	public WebElement LoginFailed;

	public Object doLogin(String username, String password) throws InterruptedException {
		test.log(LogStatus.INFO, "Entering username ->" + username);
		usernameField.sendKeys(username);

		// SendKeys(Username, username);
		test.log(LogStatus.INFO, "Entering password ->" + password);
		passwordField.sendKeys(password);

		WebDriverWait wt = new WebDriverWait(driver, 20);
		wt.until(ExpectedConditions.elementToBeClickable(Submit_button));
		Submit_button.click();
		takeScreenShot();
		System.out.println(driver.getTitle());
		// test.log(LogStatus.INFO, "login suceessfully ->");

		// driver.switchTo().alert().accept();

		// validate the login
		// System.out.println("test data");
		Thread.sleep(5000);
		System.out.println(driver.getTitle());
		String title = driver.getTitle();
		System.out.println(title);
		boolean login = false;
		// if(title.equals("Extramarks India"))
		if (title.equals(
				"Extramarks India"))
			login = true;
		else
			login = false;

		if (!login) {
			// test.log(LogStatus.INFO, "Login successful");
			LoginPage loginPage = new LoginPage(driver, test);
			PageFactory.initElements(driver, loginPage);
			return loginPage;
		} else {
			test.log(LogStatus.INFO, "Login successful");
			DashBoardPage dp = new DashBoardPage(driver, test);
			PageFactory.initElements(driver, dp);
			return dp;
		}

	}
}
