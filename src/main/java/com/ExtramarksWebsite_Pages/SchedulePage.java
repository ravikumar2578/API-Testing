package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SchedulePage extends BasePage
{
	@FindBy(partialLinkText="Add Schedule")
	public WebElement AddSchedule;
	
	@FindBy(id="title")
	public WebElement AddTitle;
	
	@FindBy(xpath="//div[@class='modal-body']//input[@id='radio1']/parent::div")
	List<WebElement> CategoryRadio;
	
	@FindBy(id="classDropDown")
	public WebElement ClassDropDwn;
	@FindBy(xpath="//select[@id='subjectDropDown']")
	WebElement SubjectDropDown;
	@FindBy(xpath="//select[@id='subjectDropDown_edit']")
	WebElement EditSubject;
	
	@FindBy(id="chapterDropDown")
	public WebElement ChapterDropDown;
	@FindBy(xpath="//select[@id='chapterDropDown_edit']")
	public WebElement EditChapter;
	
	@FindBy(xpath="//input[@id='schedule_date6']")
	WebElement StartDateTime;
	@FindBy(xpath="//input[@id='schedule_date7']")
	WebElement EndDate;
	
	@FindBy(id="addlessonSubmit")
	WebElement AddLesson;
	
	@FindBy(xpath="//a[@class='btn change-subject-btn whitebg text-lightblue mr10']")
	WebElement MySchedule;
	
	@FindBy(xpath="//i[@class='fa fa-pencil']")
	WebElement EditIcon;
	@FindBy(xpath="//button[@id='addlessonSubmit_edit']")
	WebElement EditSchedule;
	@FindBy(xpath="//a[@title='Close Schedule']")
	List<WebElement> CloseSchedule;
	@FindBy(partialLinkText="Overdue")
	WebElement Overdue;
	@FindBy(partialLinkText="Suggested")
	WebElement Suggested;
	
	@FindBy(partialLinkText="Recently Added")
	WebElement RecentlyAdded;
	
	@FindBy(partialLinkText= "Exam Schedule")
	WebElement ExamSchedule;
	
	@FindBy(xpath="(//div[@class='datetimepicker-days'])[1]//table[@class]//td[@class='day']")
	List<WebElement> Day_1;
	@FindBy(xpath="(//div[@class='datetimepicker-hours'])[1]//table[@class=' table-condensed']//td//span")
	List<WebElement> Hours_1;
	@FindBy(xpath="(//div[@class='datetimepicker-minutes'])[1]//table[@class=' table-condensed']//span[@class='minute']")
	List<WebElement> Minutes_1;
	
	@FindBy(xpath="(//div[@class='datetimepicker-days'])[2]//table[@class]//td[@class='day']")
	List<WebElement> Day_2;
	@FindBy(xpath="(//div[@class='datetimepicker-hours'])[2]//table[@class=' table-condensed']//td//span")
	List<WebElement>Hours_2;
	@FindBy(xpath="(//div[@class='datetimepicker-minutes'])[2]//table[@class=' table-condensed']//span[@class='minute']")
	List<WebElement> Minutes_2;
	
	

	
	
	
	public SchedulePage(WebDriver driver, ExtentTest test) 
	{
		super(driver, test);
		PageFactory.initElements(driver, this);
	}
	LoginPage lp= new LoginPage(driver, test);

	public void clickAddSchedule(String Title, String Class, String Subject, String Chapter) throws InterruptedException
	{
		WebDriverWait wait=  new WebDriverWait(driver, 20);
		AddSchedule.click();
		Thread.sleep(2000);
		lp.takeScreenShot();
		test.log(LogStatus.INFO, "Schedule Page");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
		AddTitle.sendKeys(Title);
		
		//wait.until(ExpectedConditions.elementToBeClickable(By.id("radio1")));
		CategoryRadio.get(0).click();
		
		//wait.until(ExpectedConditions.elementToBeSelected(By.id("classDropDown")));
		ClassDropDwn.click();
		Select sel= new Select(ClassDropDwn);
		sel.selectByVisibleText(Class);
		
		Thread.sleep(3000);
		SubjectDropDown.click();
		Select sel1= new Select(SubjectDropDown);
		sel1.selectByVisibleText(Subject);
		
		ChapterDropDown.click();
		Select sel2= new Select(ChapterDropDown);
		sel2.selectByVisibleText(Chapter);
		
		StartDateTime.click();
		Day_1.get(0).click();
		Hours_1.get(2).click();
		Minutes_1.get(5).click();
		
		EndDate.click();
		Day_2.get(1).click();
		Hours_2.get(4).click();
		Minutes_2.get(2).click();
		
		lp.takeScreenShot();
		AddLesson.click();
		test.log(LogStatus.INFO, "Adding new Schedule");
	}
	
	public void clickMySchedule(String editSubject, String editChapter) throws InterruptedException
	{
		WebDriverWait wt= new WebDriverWait(driver, 20);
		wt.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("My Schedule")));

		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", MySchedule);
		//MySchedule.click();
		lp.takeFullScreenshot();
		test.log(LogStatus.INFO, "My Schedule" );
		EditIcon.click();
		Thread.sleep(3000);
		EditSubject.click();
		Select sel1= new Select(EditSubject);
		sel1.selectByVisibleText(editSubject);
		Thread.sleep(2000);
		EditChapter.click();
		Select sel= new Select(EditChapter);
		sel.selectByVisibleText(editChapter);
		Thread.sleep(3000);
		EditSchedule.click();
		
		//wt.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Close Schedule']")));
		Thread.sleep(5000);
		CloseSchedule.get(0).click();
		
		Overdue.click();
		test.log(LogStatus.INFO, "Overdued schedule");
		lp.takeFullScreenshot();
		
		Suggested.click();
		RecentlyAdded.click();
		test.log(LogStatus.INFO, "Recently added");
		
		List<WebElement> Completed = driver.findElements(By.xpath("//span[text()='completed']"));
		int CompletedTest = Completed.size();
		System.out.println("Total number of completed tests = "+CompletedTest);
	}
	
	public void ExamSchedule()
	{
		ExamSchedule.click();
		test.log(LogStatus.INFO, "Exam Schedule");
		lp.takeScreenShot();
	}
		
}
