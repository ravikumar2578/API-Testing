package com.ExtramarksWebsite_Pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DashBoardPage extends BasePage {

	public DashBoardPage(WebDriver driver, ExtentTest test) {
		super(driver, test);
		PageFactory.initElements(driver, this);
	}

	LoginPage lp = new LoginPage(driver, test);

	@FindBy(partialLinkText = "Dashboard")
	public WebElement Dashboard;

	@FindBy(partialLinkText = "Schedule")
	public WebElement Schedule;

	@FindBy(partialLinkText = "Notes")
	public WebElement Notes;
	@FindBy(partialLinkText = "Assessment")
	public WebElement Assessment;

	@FindBy(partialLinkText = "Report")
	public WebElement Report;

	@FindBy(xpath = "//a[@class='btn btn-padding-tugas lightblue text-white font16']")
	public WebElement StartLearning;
	/*
	 * @FindBy(xpath="//div[@id='recent_more']//a[@class='btn btn-border-orange']")
	 * WebElement ViewActivity;
	 */
	@FindBy(partialLinkText = "Add Notes")
	WebElement AddNotes;
	@FindBy(xpath = "//a[contains(text(),'Attempt Now')]")
	WebElement AssessmentAttempt;
	@FindBy(partialLinkText = "Add Schedule")
	public WebElement AddSchedule;
	
	@FindBy(partialLinkText = "Study")
	public WebElement Study;
	
	@FindBy(xpath = "//a[text()='LOGOUT']")
	public WebElement LogOut;

	public void openstudytab() throws InterruptedException {
		Thread.sleep(3000);
		Study.click();
	}

	public Object openStudent() {
		Mentor_StudentPage msp = new Mentor_StudentPage(driver, test);
		Student.get(1).click();
		return msp;
	}

	@FindBy(xpath = "//a[contains(text(),'Student')]")
	List<WebElement> Student;

	// ..................................................Mentor
	// Dashboard.........................................................//

	@FindBy(xpath = "//div[@class='col-sm-5 text-right']//a")
	public WebElement Mentor_AddSchedule;

	@FindBy(xpath = "//div[@id='recent_more']//a[@class='btn btn-border-orange']")
	WebElement ViewActivity;

	public Object openSchedule() {
		SchedulePage sp = new SchedulePage(driver, test);
		Schedule.click();
		return sp;
	}

	public Object openNotes() {
		NotesPage np = new NotesPage(driver, test);
		Notes.click();
		return np;
	}

	public Object openAssessment() {
		AssessmentPage ap = new AssessmentPage(driver, test);
		Assessment.click();
		return ap;
	}

	public Object openReport() {
		ReportPage rp = new ReportPage(driver, test);
		Report.click();
		return rp;

	}

	public void MentorAddSchedule() {
		Mentor_AddSchedule.click();
	}

	public void ViewActivities() throws InterruptedException, IOException {
		ViewActivity.click();
		Thread.sleep(5000);
		lp.takeFullScreenshot();
		test.log(LogStatus.INFO, "All Activities");

		driver.navigate().back();
	}

//............................................Parent Dashboard..............................................................	

	@FindBy(partialLinkText = "Add Child")
	public WebElement AddChild;

	@FindBy(partialLinkText = "My Child")
	public WebElement MyChild;

	@FindBy(xpath = "//a[contains(text(),' Child Subscription')]")
	public WebElement ChildSubscription;

	@FindBy(xpath = "//li[@class='active ']//a[contains(text(),'Parent')]")
	public List<WebElement> ParentTab;

	public Object openMyChild() {
		Parent_MyChildPage pmc = new Parent_MyChildPage(driver, test);
		MyChild.click();
		return pmc;
	}

	public Object openChildSubs() {
		ChildSubscriptionPage csp = new ChildSubscriptionPage(driver, test);
		ChildSubscription.click();
		return csp;
	}

	public int ParentTab() {
		int ParentTabPresent = ParentTab.size();
		// System.out.println(ParentTabPresent);
		return ParentTabPresent;
	}

}
