package com.ExtramarksWebsite_Pages;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class NotesPage extends BasePage
{
	@FindBy(partialLinkText= "Add Notes")
	public WebElement AddNotes;
	@FindBy(id="title")
	public WebElement AddTitle;
	@FindBy(id="classDropDown")
	public WebElement ClassDropDwn;
	@FindBy(xpath="//select[@id='subjectDropDown']")
	WebElement SubjectDropDown;
	@FindBy(id="editSubjectDropDown")
	WebElement EditSubject;
	@FindBy(id="editChapterDropDown")
	WebElement EditChapter;
	
	@FindBy(id="subSubjectDiv")
	WebElement SubSubjectDropDwn;
	@FindBy(id="subSubjectDiv")
	List<WebElement> SubSubject;
	
	
	@FindBy(id="chapterDropDown")
	WebElement ChapterDropDown;
	@FindBy(id="notesDescription")
	WebElement Description; 
	@FindBy(id="addlessonSubmit")
	WebElement Submit;
	@FindBy(xpath="//i[@class='fa fa-link']")
	WebElement Attachment;
	@FindBy(xpath="//a[@title='Edit Notes']")
	List<WebElement> EditNotes;
	@FindBy(id="saveedit")
	WebElement SaveEdit;
	@FindBy(xpath="//a[@title='Delete Notes' ]//i[@class='fa fa-trash-o']")
	List<WebElement> DeleteNote;
	@FindBy(xpath="//button[@id='deleteButton']")
	WebElement DeleteButton;
	
	public NotesPage(WebDriver driver, ExtentTest test)
	{
		super(driver, test);
		PageFactory.initElements(driver, this);
	}
	
	LoginPage lp= new LoginPage(driver, test);
	public void AddNotes(String Title,String Class,String Subject, String Chapter,String description) throws IOException, InterruptedException
	{
		AddNotes.click();
		AddTitle.sendKeys(Title);
		Select sel= new Select(ClassDropDwn);
		sel.selectByVisibleText(Class);
		
		Thread.sleep(3000);
		SubjectDropDown.click();
		Select sel1= new Select(SubjectDropDown);
		sel1.selectByVisibleText(Subject);
		
		int SubSubjectPresent = SubSubject.size();
		System.out.println(SubSubjectPresent);
		
		/*if(SubSubjectPresent!=0)
		{
		SubSubjectDropDwn.click();
		Select sel2= new Select (SubSubjectDropDwn);
		sel2.selectByIndex(3);
		}*/
		
		ChapterDropDown.click();
		Select sel3= new Select(ChapterDropDown);
		sel3.selectByVisibleText(Chapter);
		
		Description.sendKeys(description);
	/*	Attachment.click();
		new ProcessBuilder("D:\\AutoIT\\FileUpload1.exe").start();
		//Runtime.getRuntime().exec("D:\\FileUpload01.exe");
		*/
		Thread.sleep(5000);
		lp.takeScreenShot();
		test.log(LogStatus.INFO, "Adding Notes");
		Submit.click();
	}
	
	public void EditNotes(String editSubject, String editChapter) throws InterruptedException
	{
		int TotalNotes = EditNotes.size();
		for(int i=0;i<TotalNotes; i++)
		{
			Thread.sleep(4000);
			EditNotes.get(i).click();
			Thread.sleep(2000);
			test.log(LogStatus.INFO	, "Edit notes clicked");
			lp.takeScreenShot();
			
			EditSubject.click();
			Select sel= new Select(EditSubject);
			sel.selectByVisibleText(editSubject);
			
			EditChapter.click();
			Select sel1= new Select(EditChapter);
			sel1.selectByVisibleText(editChapter);
			
			lp.takeScreenShot();
			test.log(LogStatus.INFO, "Editing Notes");
			SaveEdit.click();
			
			Thread.sleep(2000);
		}
	}
	public void DeleteNotes() throws InterruptedException
	{
		int TotalNotes=DeleteNote.size();
		System.out.println(TotalNotes);
		/*WebDriverWait wt= new WebDriverWait(driver, 20);
		wt.until(ExpectedConditions.visibilityOfAllElements(DeleteNote));
		*/
		Thread.sleep(3000);
		int j=0;
		DeleteNote.get(j).click();
		DeleteButton.click();
		
	}
	
	

}
