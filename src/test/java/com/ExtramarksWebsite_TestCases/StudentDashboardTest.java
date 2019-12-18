package com.ExtramarksWebsite_TestCases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.NotesPage;
import com.ExtramarksWebsite_Pages.ProfilePage;
import com.ExtramarksWebsite_Pages.SchedulePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;

public class StudentDashboardTest extends BaseTest {
	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("StudentDashboardTest");
	}

	/*
	 * @AfterMethod public void quit() { rep.endTest(test); rep.flush(); }
	 */

	@DataProvider
	public Object[][] getData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "StudentDashboardTest");
		return data;
	}

	@Test(dataProvider = "getData")
	public void StudentDashboard(Hashtable<String, String> data) throws InterruptedException, IOException {

		defaultLogin(data);
		DashBoardPage dp = new DashBoardPage(driver, test);
		SchedulePage sp = new SchedulePage(driver, test);
		NotesPage np = new NotesPage(driver, test);
		dp.ViewActivities();
		sp.clickAddSchedule(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"));
		Thread.sleep(5000);
		sp.clickMySchedule(data.get("EditSubject"), data.get("EditChapter"));
		dp.Dashboard.click();
		np.AddNotes.click();
		np.AddNotes(data.get("Title"), data.get("Class"), data.get("Subject"), data.get("Chapter"),
				data.get("Description"));
		Thread.sleep(4000);
		dp.Dashboard.click();
	}

	@AfterTest
	public void LogOut() throws InterruptedException {
		ProfilePage pg = new ProfilePage(driver, test);
		DashBoardPage dp = new DashBoardPage(driver, test);
		Thread.sleep(3000);
		pg.SettingsIcon.click();
		dp.LogOut.click();
		driver.quit();
	}

}
