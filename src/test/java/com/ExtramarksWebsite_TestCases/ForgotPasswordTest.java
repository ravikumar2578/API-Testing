package com.ExtramarksWebsite_TestCases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.ForgotPasswordPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;

public class ForgotPasswordTest extends BaseTest {
	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("ForgotPasswordTest");
	}

	@DataProvider
	public Object[][] getData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "ForgotPasswordTest");
		return data;
	}

	@Test(dataProvider = "getData", priority = 1)
	public void forgotPassword(Hashtable<String, String> data)
			throws InterruptedException, SQLException, ClassNotFoundException {
		String browser = data.get("Browser");
		openBrowser(browser);
		LaunchPage launch = new LaunchPage(driver, test);
		launch.openpage();
		ForgotPasswordPage fp = new ForgotPasswordPage(driver,test);
		PageFactory.initElements(driver, fp);
		WebDriverWait wt = new WebDriverWait(driver, 20);
		wt.until(ExpectedConditions.elementToBeClickable(fp.SignIn));

		fp.SignIn.click();
		fp.forgotPassword(data.get("RegisteredMobile/Email"));

		String dbUrl = "jdbc:mysql://10.1.17.6/phpauto/";
		String username1 = "auto_test";
		String password1 = "Abc!@#456";

		String impl1 = data.get("RegisteredMobile/Email");
		String a = impl1;
		System.out.println(a);

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(dbUrl, username1, password1);
		Statement stmt = con.createStatement();
		String query = "SELECT sms_text FROM `t_sms_log` WHERE mobile_number=?";
		PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
		preparedStmt.setString(1, a);
		preparedStmt.executeUpdate();
		con.close();

	}

}
