package com.ExtramarksWebsite_Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class Mentor_Assessment extends BasePage
{
	@FindBy(id="mentor_paper_name")
	public WebElement AssessmentTitle;
	
	@FindBy(xpath="//a[@class='btn change-subject-btn orange mr10']")
	WebElement DraftAssessment;
	
	@FindBy(xpath="//a[@class='text-lightblue font-semibold chapter-name pull-right']")
	List<WebElement> EvaluateAssessment;
	
	@FindBy(xpath="//a[text()='View Assessment']")
	List<WebElement> ViewAssessment;
	
	
	@FindBy(xpath="//select[@id='kelas']")
	public WebElement Classes;
	@FindBy(xpath="//select[@id='subject-list']")
	WebElement Subject;
	@FindBy(xpath="//select[@id='chapter-list']")
	WebElement Chapter;
	@FindBy(xpath="//div[@class='row']//input[@onkeypress]")
	List<WebElement> WorkDuration;
	@FindBy(xpath="//input[@name='mentor_paper_instruction[]']")
	WebElement AssessmentInstr;
	@FindBy(xpath="//a[@id='first-lanjut']")
	WebElement Continue;
	@FindBy(xpath="//a[@id='second-lanjut']")
	WebElement Continue2;
	@FindBy(xpath="//a[@class='btn change-subject-btn lightblue']")
	public WebElement AddAssessment;
	@FindBy(xpath="//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']")
	WebElement QuesArea;
	@FindBy(xpath="//iframe[@title='Rich Text Editor, ques_editor_0']")
	WebElement iframe;
	@FindBy(xpath="//select[@id='poin']")
	WebElement MarksDropDwn;
	@FindBy(xpath="//a[@id='addtugasquestion']")
	WebElement AddQuestion;
	@FindBy(xpath="//a[@class='add-more-tugas-ques']")
	WebElement AddMoreQues;
	@FindBy(xpath="//input[@id='add-all-murid']")
	WebElement AddAllStudents; 
	@FindBy(xpath="//button[@id='create-tugas']")
	WebElement SendAssessment;
	@FindBy(xpath="//a[contains(@class,'btn font12 btn-border-orange ')]")
	List<WebElement> AssessmentList;
	@FindBy(xpath="//p[@class='font16 font-bold text-lightblue mb0']")
	List<WebElement> SubjectName;
	@FindBy(xpath="//p[@class='font12 text-black mb0']")
	List<WebElement> ChapterName;
	
	@FindBy(xpath="//div[@class='col-sm-8 pdl0']")
	List<WebElement> Questions;
	@FindBy(xpath="//select[@id='poin']")
	List<WebElement> Marks;
	
	@FindBy(xpath="//a[@id='student-answer']")
	WebElement MarksCalculation;
	
	@FindBy(xpath="//button[@id='lanjut-evaluate']")
	WebElement ContinueBtn;
	
	public Mentor_Assessment(WebDriver driver, ExtentTest test)
	{
		super(driver, test);
		PageFactory.initElements(driver, this);
	}
	LoginPage lp= new LoginPage(driver, test);
	
	public void DraftAssessment() throws InterruptedException
	{
		DraftAssessment.click();
		Thread.sleep(3000);
		lp.takeScreenShot();
		driver.navigate().back();
		
	}
	
	
	public Object AssessmentDetails(String Title,String Class,String subject,String chapter,String Hours_1, String Hours_2,String Minutes_1, String Minutes_2,String AssessmentInstruction) throws InterruptedException
	{
		
		AddAssessment.click();
		test.log(LogStatus.INFO, "Adding Assessment Details");
		
		AssessmentTitle.sendKeys(Title);
		Classes.click();
		Select sel= new Select(Classes);
		sel.selectByVisibleText(Class);
		Thread.sleep(3000);
		Subject.click();
		Select sel1= new Select(Subject);
		sel1.selectByVisibleText(subject);
		Thread.sleep(3000);
		Chapter.click();
		Select sel2= new Select(Chapter);
		sel2.selectByVisibleText(chapter);
		
		WorkDuration.get(0).sendKeys(Hours_1);
		WorkDuration.get(1).sendKeys(Hours_2);
		WorkDuration.get(2).sendKeys(Minutes_1);
		WorkDuration.get(3).sendKeys(Minutes_2);
		
		AssessmentInstr.sendKeys(AssessmentInstruction);
		Thread.sleep(4000);
		lp.takeFullScreenshot();
		
		Continue.click();
		test.log(LogStatus.INFO,"Assessment Details Added");
		Mentor_Assessment ma = new Mentor_Assessment(driver, test);
		return ma;
	}
	
	public Object createAssessment(String Ques_Area) throws InterruptedException
	{
		
		driver.switchTo().frame(iframe);
		lp.takeFullScreenshot();
		test.log(LogStatus.INFO, "Creating Assessment");
		QuesArea.click();
		QuesArea.sendKeys(Ques_Area);
		driver.switchTo().defaultContent();
		Select sel=new Select(MarksDropDwn);
		sel.selectByIndex(5);
		AddQuestion.click();
		AddMoreQues.click();
		driver.switchTo().frame(iframe);
		QuesArea.sendKeys(Ques_Area);
		driver.switchTo().defaultContent();
	//	Select sel1=new Select(MarksDropDwn);
		sel.selectByIndex(5);
		AddQuestion.click();
		Thread.sleep(4000);
		lp.takeFullScreenshot();
		test.log(LogStatus.INFO, "Assessment Created");
		
		Continue2.click();
		AddAllStudents.click();
		Thread.sleep(3000);
		lp.takeScreenShot();
		SendAssessment.click();
		test.log(LogStatus.INFO, "Assessment sent");
		Mentor_Assessment ma = new Mentor_Assessment(driver, test);
		return ma;
		
	}
	
	public void AssessmentList(String marks) throws InterruptedException
	{
		int TotalAssessment=AssessmentList.size();
		System.out.println("Total Assessments : "+TotalAssessment);
		test.log(LogStatus.INFO, "Total Assessments : "+TotalAssessment);

		int view = ViewAssessment.size();

		System.out.println("Assessment yet to be evaluate = "+view);
		test.log(LogStatus.INFO, "Assessment yet to be evaluate = "+view);
	
		
		for(int i=view; i>=0; i--)
		{
			test.log(LogStatus.INFO,"Assessment for " +SubjectName.get(i).getText()+ " of the chapter "+ChapterName.get(i).getText() );
			System.out.println("Assessment for " +SubjectName.get(i).getText()+ " of the chapter "+ChapterName.get(i).getText() );
			ViewAssessment.get(0).click();
			
			int ALreadyEvaluated = EvaluateAssessment.size();
			System.out.println("Evaluate Assessment present : "+ALreadyEvaluated);
			test.log(LogStatus.INFO, "Evaluate Assessment present : "+ALreadyEvaluated);
			
			if(ALreadyEvaluated==0)
			{
				
				lp.takeFullScreenshot();
				driver.navigate().back();
			}
			else
			{
				EvaluateAssessment.get(0).click();
				int TotalQues=Questions.size();
				System.out.println("Total Questions: "+TotalQues);
				test.log(LogStatus.INFO, "Total Questions: "+TotalQues);
				
				for(int j=0;j<TotalQues;j++)
				{
				Select sel= new Select(Marks.get(j));
				sel.selectByVisibleText(marks);
				}
				MarksCalculation.click();
				
				ContinueBtn.click();
			}
		}
	}
	
}
