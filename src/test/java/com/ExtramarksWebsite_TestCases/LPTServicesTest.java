package com.ExtramarksWebsite_TestCases;

import java.lang.reflect.Method;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.ExtramarksWebsite_Pages.ChapterPage;
import com.ExtramarksWebsite_Pages.ClassPage;
import com.ExtramarksWebsite_Pages.DashBoardPage;
import com.ExtramarksWebsite_Pages.LaunchPage;
import com.ExtramarksWebsite_Pages.LoginPage;
import com.ExtramarksWebsite_Pages.SubjectPage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class LPTServicesTest extends BaseTest {
	SoftAssert sAssert = new SoftAssert();

	@BeforeMethod
	public void init(Method method) {
		rep = ExtentManager.getInstance();
		String testMethodName = method.getName();
		test = rep.startTest(testMethodName);
	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();
	}

	@Test(dataProvider = "getData", priority = 1)
	public void verifyLogin(Hashtable<String, String> data) throws InterruptedException {

		String expectedResult = "Login_PASS";
		String actualResult = "";
		String browser = data.get("Browser");
		test.log(LogStatus.INFO, "Login test started");
		if (!DataUtil.isTestRunnable(xls, "LPTServicesTest") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");

			throw new SkipException("skipping the test");
		}

		openBrowser(browser);
		test.log(LogStatus.INFO, "Browser Opened");
		LaunchPage launch = new LaunchPage(driver, test);

		LoginPage lp = launch.goToHomePage();
		String title = driver.getTitle();
		System.out.println(title);
		test.log(LogStatus.INFO, "Extramarks Website Home Page");
		lp.takeScreenShot();

		WebElement signin = driver.findElement(By.xpath("//*[@class='signin']"));
		signin.click();
		test.log(LogStatus.INFO, "Trying to Login");
		Object resultPage = lp.doLogin(data.get("Username"), data.get("Password"));
		Thread.sleep(5000);

		if (resultPage instanceof LoginPage) {
			test.log(LogStatus.INFO, "Not able to Login");
			actualResult = "Login_FAIL";
			System.out.println("Not logged in");
		} else if (resultPage instanceof DashBoardPage) {
			test.log(LogStatus.INFO, "Able to Login");
			actualResult = "Login_PASS";
			System.out.println("Logged in");
		}

		if (!expectedResult.equals(actualResult)) { // take screenshot
			lp.takeScreenShot();
			test.log(LogStatus.FAIL, "Got actual result as " + actualResult);
			sAssert.fail("Got actual result as " + actualResult);
		} else {
			test.log(LogStatus.PASS, "Login Test Passed");

		}
		sAssert.assertAll();
	}

	@DataProvider
	public Object[][] getData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "LPTServicesTest");
		return data;
	}

//.......................................................LEARN SERVICE..............................................................................

	@Test(priority = 2, enabled = true)
	public void verifyLearn() throws InterruptedException {
		LoginPage lp = new LoginPage(driver, test);
		SubjectPage sp = new SubjectPage(driver, test);
		ChapterPage chPg = new ChapterPage(driver, test);
		ClassPage cp = new ClassPage(driver, test);

		DashBoardPage dp = new DashBoardPage(driver, test);
		dp.openstudytab();

		Thread.sleep(5000);
		try {
			int subsize = cp.getTotalSub();
			for (int subj = 0; subj < subsize; subj++) {
				WebDriverWait wt = new WebDriverWait(driver, 60);
		try {
				wt.until(ExpectedConditions.visibilityOfAllElements(cp.getSubjectLinks()));
		}catch(Exception e) {
		
		}
				String subject = cp.getSubjectLinks().get(subj).getText();
				byte ptext[] = subject.getBytes("ISO-8859-1");
				String utfSubject=new String(ptext,"UTF-8");
				System.out.println("Subjects:" + utfSubject);
				test.log(LogStatus.INFO, "Subjects:" + utfSubject);

				try {
					cp.getSubjectLinks().get(subj).click();
				} catch (Exception e) {
					test.log(LogStatus.FAIL, "Error on click on subject Exception is " + e.getMessage());
					System.out.println("Error on click on subject Exception is " + e.getMessage());
					lp.takeScreenShot();
				}
				Thread.sleep(2000);
				lp.takeScreenShot();
				Thread.sleep(3000);
				int subSubjsize = cp.getTotalSubSubj();
				if (subSubjsize != 0) {
					for (int subSubj = 0; subSubj < subSubjsize; subSubj++) {
						String subSubject = cp.getSubSubjectLinks().get(subSubj).getText();
						System.out.println("Sub Subjects:" + subSubject);
						test.log(LogStatus.INFO, "Sub Subjects:" + subSubject);
						cp.getSubSubjectLinks().get(subSubj).click();
						Thread.sleep(2000);
						lp.takeScreenShot();
						int chapSize = sp.getMainChapter().size();
						System.out.println("No. of chapters in this subject = " + chapSize);
						test.log(LogStatus.INFO, "No. of chapters in this subject = " + chapSize);
						Thread.sleep(1000);
						if (chapSize != 0) {
							for (int ch = 0; ch < chapSize; ch++) {
								wt.until(ExpectedConditions.visibilityOfAllElements(sp.getMainChapter()));
								Thread.sleep(5000);
								String chapter = sp.getMainChapter().get(ch).getText();
								System.out.println("Main Chapter : " + chapter);
								test.log(LogStatus.INFO, "Main Chapter : " + chapter);

								try {
									sp.getMainChapter().get(ch).click();
								} catch (Exception e) {
									test.log(LogStatus.FAIL,
											"Error on click on chapter Exception is " + e.getMessage());
									System.out.println("Error on click on chapter Exception is " + e.getMessage());
									lp.takeScreenShot();
								}
								Thread.sleep(5000);
								lp.takeScreenShot();

								int subChapSize = sp.getSubChapter().size();

								System.out.println("No.Sub Chapters = " + subChapSize);
								test.log(LogStatus.INFO, "No.Sub Chapters = " + subChapSize);

								if (subChapSize != 0)
								// ..............................................
								{
									for (int su = 0; su < subChapSize; ++su) {
										System.out.println("Chapter : " + sp.getSubChapter().get(su).getText());
										test.log(LogStatus.INFO, "Chapter : " + sp.getSubChapter().get(su).getText());
										Thread.sleep(1000);
										sp.getSubChapter().get(su).click();

										Thread.sleep(2000);
										lp.takeScreenShot();

										int postSubChap = sp.getPostSubChap().size();
										test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
										System.out.println("No. of Sub-sub Chapters = " + postSubChap);

										if (postSubChap != 0) {
											for (int ps = 0; ps < postSubChap; ps++) {
												System.out.println(
														"Subchapter : " + sp.getPostSubChap().get(ps).getText());
												Thread.sleep(1000);
												sp.getPostSubChap().get(ps).click();
												test.log(LogStatus.INFO, "Open sub chapters");

												Thread.sleep(2000);
												lp.takeScreenShot();

												int LearnPresent = chPg.getLearnTB().size();
												if (LearnPresent != 0) {
													chPg.Learn();
												} else {

													test.log(LogStatus.FAIL,
															"Learn Tab is not Present, Location is --> " + subject
																	+ " -- >" + subSubject + " -- >" + chapter);
													System.out.println("Learn Tab is not Present, Location is --> "
															+ subject + " -- >" + subSubject + " -- >" + chapter);
													lp.takeScreenShot();

												}
												driver.navigate().back();
												Thread.sleep(5000);
												sp.getMainChapter().get(ch).click();
												Thread.sleep(5000);
												sp.getSubChapter().get(su).click();
												Thread.sleep(5000);
											}

										} else {
											int LearnPresent = chPg.getLearnTB().size();
											if (LearnPresent != 0) {
												chPg.Learn();
											} else {

												test.log(LogStatus.FAIL, "Learn Tab is not Present, Location is --> "
														+ subject + " -- >" + subSubject + " -- >" + chapter);
												System.out.println("Learn Tab is not Present, Location is --> "
														+ subject + " -- >" + subSubject + " -- >" + chapter);
												lp.takeScreenShot();

											}
											driver.navigate().back();
											Thread.sleep(5000);
											sp.getMainChapter().get(ch).click();
											Thread.sleep(5000);
										}

									}

								}
// Sub Chap Else Start ...............................................................................................................................................

								else {
									int LearnPresent = chPg.getLearnTB().size();
									if (LearnPresent != 0) {
										chPg.Learn();
									} else {

										test.log(LogStatus.FAIL, "Learn Tab is not Present, Location is --> " + subject
												+ " -- >" + subSubject + " -- >" + chapter);
										System.out.println("Learn Tab is not Present, Location is --> " + subject
												+ " -- >" + subSubject + " -- >" + chapter);
										lp.takeScreenShot();

									}
									driver.navigate().back();
									Thread.sleep(5000);
								}

							}
						} else {
							System.out.println(
									"Chapter page is not Displayed, Location is --> " + subject + " -- >" + subSubject);
							test.log(LogStatus.FAIL,
									"Chapter page is not Displayed, Location is --> " + subject + " -- >" + subSubject);
							lp.takeScreenShot();

						}
						driver.navigate().back();
						Thread.sleep(5000);

					}

				} else {
					int chapSize = sp.getMainChapter().size();
					System.out.println("No. of chapters in this subject = " + chapSize);
					test.log(LogStatus.INFO, "No. of chapters in this subject = " + chapSize);
					Thread.sleep(1000);
					if (chapSize != 0) {
						for (int ch = 0; ch < chapSize; ch++) {
							
							wt.until(ExpectedConditions.visibilityOfAllElements(sp.getMainChapter()));
					
							Thread.sleep(5000);
							String chapter = sp.getMainChapter().get(ch).getText();
							System.out.println("Main Chapter : " + chapter);
							test.log(LogStatus.INFO, "Main Chapter : " + chapter);
							try {
								sp.getMainChapter().get(ch).click();
							} catch (Exception e) {
								test.log(LogStatus.FAIL, "Error on click on chapter Exception is " + e.getMessage());
								System.out.println("Error on click on chapter Exception is " + e.getMessage());
								lp.takeScreenShot();
							}
							Thread.sleep(5000);
							lp.takeScreenShot();

							int subChapSize = sp.getSubChapter().size();

							System.out.println("No.Sub Chapters = " + subChapSize);
							test.log(LogStatus.INFO, "No.Sub Chapters = " + subChapSize);

							if (subChapSize != 0)
							// ..............................................
							{
								for (int su = 0; su < subChapSize; ++su) {
									System.out.println("Chapter : " + sp.getSubChapter().get(su).getText());
									test.log(LogStatus.INFO, "Chapter : " + sp.getSubChapter().get(su).getText());
									Thread.sleep(1000);
									sp.getSubChapter().get(su).click();

									Thread.sleep(5000);
									lp.takeScreenShot();

									int postSubChap = sp.getPostSubChap().size();
									test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
									System.out.println("No. of Sub-sub Chapters = " + postSubChap);

									if (postSubChap != 0) {
										for (int ps = 0; ps < postSubChap; ps++) {
											System.out.println("Subchapter : " + sp.getPostSubChap().get(ps).getText());
											Thread.sleep(1000);
											sp.getPostSubChap().get(ps).click();
											test.log(LogStatus.INFO, "Open sub chapters");

											Thread.sleep(5000);
											lp.takeScreenShot();

											int LearnPresent = chPg.getLearnTB().size();
											if (LearnPresent != 0) {
												chPg.Learn();
											} else {

												test.log(LogStatus.FAIL, "Learn Tab is not Present, Location is --> "
														+ subject + " -- >" + chapter);
												System.out.println("Learn Tab is not Present, Location is --> "
														+ subject + " -- >" + chapter);
												lp.takeScreenShot();

											}
											driver.navigate().back();
											Thread.sleep(5000);
											sp.getMainChapter().get(ch).click();

											Thread.sleep(5000);
											sp.getSubChapter().get(su).click();
											Thread.sleep(5000);
										}

									} else {
										int LearnPresent = chPg.getLearnTB().size();
										if (LearnPresent != 0) {
											chPg.Learn();
										} else {

											test.log(LogStatus.FAIL, "Learn Tab is not Present, Location is --> "
													+ subject + " -- >" + chapter);
											System.out.println("Learn Tab is not Present, Location is --> " + subject
													+ " -- >" + chapter);
											lp.takeScreenShot();

										}
										driver.navigate().back();
										Thread.sleep(5000);
										sp.getMainChapter().get(ch).click();
									}

								}

							}
//...............................................................................................................................................

							else {
								int LearnPresent = chPg.getLearnTB().size();
								if (LearnPresent != 0) {
									chPg.Learn();
								} else {

									test.log(LogStatus.FAIL,
											"Learn Tab is not Present, Location is --> " + subject + " -- >" + chapter);
									System.out.println(
											"Learn Tab is not Present, Location is --> " + subject + " -- >" + chapter);
									lp.takeScreenShot();

								}
								driver.navigate().back();
								Thread.sleep(5000);
							}

						}

					} else {
						System.out.println("Chapter page is not Displayed, Location is --> " + subject);
						test.log(LogStatus.FAIL, "Chapter page is not Displayed, Location is --> " + subject);
						lp.takeScreenShot();
						//driver.navigate().back();
						Thread.sleep(5000);
					}

				}
				driver.navigate().back();
				Thread.sleep(3000);

			}
			test.log(LogStatus.PASS, "Learn Pass");
			lp.takeScreenShot();
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Getting Error, Test Fail " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			sAssert.fail("Getting Error" + e.getMessage());

		}
		sAssert.assertAll();
	}

	// ..................................................Practice
	// SERVISE..............................................................................//

	@Test(enabled = false, priority = 3)
	public void verifyPractise() throws InterruptedException {
		LoginPage lp = new LoginPage(driver, test);
		SubjectPage sp = new SubjectPage(driver, test);
		ChapterPage chPg = new ChapterPage(driver, test);
		ClassPage cp = new ClassPage(driver, test);

		DashBoardPage dp = new DashBoardPage(driver, test);

		dp.openstudytab();
		test.log(LogStatus.INFO, "Open Study Tab");

		lp.takeScreenShot();
		Thread.sleep(5000);
		Thread.sleep(5000);
		try {
			int subsize = cp.getTotalSub();
			for (int subj = 0; subj < subsize; subj++) {
				WebDriverWait wt = new WebDriverWait(driver, 60);
				wt.until(ExpectedConditions.visibilityOfAllElements(cp.getSubjectLinks()));
				String subject = cp.getSubjectLinks().get(subj).getText();
				System.out.println("Subjects:" + subject);
				test.log(LogStatus.INFO, "Subjects:" + subject);
				try {
					cp.getSubjectLinks().get(subj).click();
				} catch (Exception e) {
					test.log(LogStatus.FAIL, "Error on click on subject Exception is " + e.getMessage());
					System.out.println("Error on click on subject Exception is " + e.getMessage());
					lp.takeScreenShot();
				}
				Thread.sleep(2000);
				lp.takeScreenShot();
				Thread.sleep(3000);
				int subSubjsize = cp.getTotalSubSubj();
				if (subSubjsize != 0) {
					for (int subSubj = 0; subSubj < subSubjsize; subSubj++) {
						String subSubject = cp.getSubSubjectLinks().get(subSubj).getText();
						System.out.println("Sub Subjects:" + subSubject);
						test.log(LogStatus.INFO, "Sub Subjects:" + subSubject);
						cp.getSubSubjectLinks().get(subSubj).click();
						Thread.sleep(2000);
						lp.takeScreenShot();
						int chapSize = sp.getMainChapter().size();
						System.out.println("No. of chapters in this subject = " + chapSize);
						test.log(LogStatus.INFO, "No. of chapters in this subject = " + chapSize);
						Thread.sleep(1000);
						if (chapSize != 0) {
							for (int ch = 0; ch < chapSize; ch++) {
								wt.until(ExpectedConditions.visibilityOfAllElements(sp.getMainChapter()));
								Thread.sleep(5000);
								String chapter = sp.getMainChapter().get(ch).getText();
								System.out.println("Main Chapter : " + chapter);
								test.log(LogStatus.INFO, "Main Chapter : " + chapter);
								try {
									sp.getMainChapter().get(ch).click();
								} catch (Exception e) {
									test.log(LogStatus.FAIL,
											"Error on click on chapter Exception is " + e.getMessage());
									System.out.println("Error on click on chapter Exception is " + e.getMessage());
									lp.takeScreenShot();
								}
								Thread.sleep(2000);
								lp.takeScreenShot();

								int subChapSize = sp.getSubChapter().size();

								System.out.println("No.Sub Chapters = " + subChapSize);
								test.log(LogStatus.INFO, "No.Sub Chapters = " + subChapSize);

								if (subChapSize != 0)
								// ..............................................
								{
									for (int su = 0; su < subChapSize; ++su) {
										System.out.println("Chapter : " + sp.getSubChapter().get(su).getText());
										test.log(LogStatus.INFO, "Chapter : " + sp.getSubChapter().get(su).getText());
										Thread.sleep(1000);
										sp.getSubChapter().get(su).click();

										Thread.sleep(2000);
										lp.takeScreenShot();

										int postSubChap = sp.getPostSubChap().size();
										test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
										System.out.println("No. of Sub-sub Chapters = " + postSubChap);

										if (postSubChap != 0) {
											for (int ps = 0; ps < postSubChap; ps++) {
												System.out.println(
														"Subchapter : " + sp.getPostSubChap().get(ps).getText());
												Thread.sleep(1000);
												sp.getPostSubChap().get(ps).click();
												test.log(LogStatus.INFO, "Open sub chapters");

												Thread.sleep(2000);
												lp.takeScreenShot();

												int PractisePresent = chPg.getPracticeTb().size();
												if (PractisePresent != 0) {
													chPg.practise();
												} else {

													test.log(LogStatus.FAIL,
															"Practice Tab is not Present, Location is --> " + subject
																	+ " --> " + subSubject + " --> " + chapter);
													System.out.println("Practice Tab is not Present, Location is --> "
															+ subject + " --> " + subSubject + " --> " + chapter);
													lp.takeScreenShot();

												}
												driver.navigate().back();
												Thread.sleep(5000);
												sp.getMainChapter().get(ch).click();
												Thread.sleep(3000);
												sp.getSubChapter().get(su).click();
												Thread.sleep(3000);
											}

										}

										// Post Sub Chap
										// .................................................ELSE...............................................................................//
										else {
											int PractisePresent = chPg.getPracticeTb().size();
											if (PractisePresent != 0) {
												chPg.practise();
											} else {

												test.log(LogStatus.FAIL, "Practice Tab is not Present, Location is --> "
														+ subject + " --> " + subSubject + " --> " + chapter);
												System.out.println("Practice Tab is not Present, Location is --> "
														+ subject + " --> " + subSubject + " --> " + chapter);
												lp.takeScreenShot();

											}
											driver.navigate().back();
											Thread.sleep(5000);
											sp.getMainChapter().get(ch).click();
											Thread.sleep(3000);
											// ............................................................

										}

									}
								} else {
									int PractisePresent = chPg.getPracticeTb().size();
									if (PractisePresent != 0) {
										chPg.practise();
									} else {

										test.log(LogStatus.FAIL, "Practice Tab is not Present, Location is --> "
												+ subject + " --> " + subSubject + " --> " + chapter);
										System.out.println("Practice Tab is not Present, Location is --> " + subject
												+ " --> " + subSubject + " --> " + chapter);
										lp.takeScreenShot();

									}
									driver.navigate().back();
									Thread.sleep(5000);
								}

							}

						} else {
							System.out.println(
									"Chapter page is not Displayed, Location is --> " + subject + " --> " + subSubject);
							test.log(LogStatus.FAIL,
									"Chapter page is not Displayed, Location is --> " + subject + " --> " + subSubject);
							lp.takeScreenShot();

						}
						driver.navigate().back();
						Thread.sleep(5000);

					}
				} else {
					int chapSize = sp.getMainChapter().size();
					System.out.println("No. of chapters in this subject = " + chapSize);
					test.log(LogStatus.INFO, "No. of chapters in this subject = " + chapSize);
					Thread.sleep(1000);
					if (chapSize != 0) {
						for (int ch = 0; ch < chapSize; ch++) {
							Thread.sleep(5000);
							wt.until(ExpectedConditions.visibilityOfAllElements(sp.getMainChapter()));
							String chapter = sp.getMainChapter().get(ch).getText();
							System.out.println("Main Chapter : " + chapter);
							test.log(LogStatus.INFO, "Main Chapter : " + chapter);
							try {
								sp.getMainChapter().get(ch).click();
							} catch (Exception e) {
								test.log(LogStatus.FAIL, "Error on click on chapter Exception is " + e.getMessage());
								System.out.println("Error on click on chapter Exception is " + e.getMessage());
								lp.takeScreenShot();
							}
							Thread.sleep(2000);
							lp.takeScreenShot();

							int subChapSize = sp.getSubChapter().size();

							System.out.println("No.Sub Chapters = " + subChapSize);
							test.log(LogStatus.INFO, "No.Sub Chapters = " + subChapSize);

							if (subChapSize != 0)

							{
								for (int su = 0; su < subChapSize; ++su) {
									System.out.println("Chapter : " + sp.getSubChapter().get(su).getText());
									test.log(LogStatus.INFO, "Chapter : " + sp.getSubChapter().get(su).getText());
									Thread.sleep(1000);
									sp.getSubChapter().get(su).click();

									Thread.sleep(2000);
									lp.takeScreenShot();

									int postSubChap = sp.getPostSubChap().size();
									test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
									System.out.println("No. of Sub-sub Chapters = " + postSubChap);

									if (postSubChap != 0) {
										for (int ps = 0; ps < postSubChap; ps++) {
											System.out.println("Subchapter : " + sp.getPostSubChap().get(ps).getText());
											Thread.sleep(1000);
											sp.getPostSubChap().get(ps).click();
											test.log(LogStatus.INFO, "Open sub chapters");

											Thread.sleep(2000);
											lp.takeScreenShot();
											int PractisePresent = chPg.getPracticeTb().size();
											if (PractisePresent != 0) {
												chPg.practise();
											} else {

												test.log(LogStatus.FAIL, "Practice Tab is not Present, Location is --> "
														+ subject + " --> " + chapter);
												System.out.println("Practice Tab is not Present, Location is --> "
														+ subject + " --> " + chapter);
												lp.takeScreenShot();

											}
											driver.navigate().back();
											Thread.sleep(5000);
											sp.getMainChapter().get(ch).click();
											Thread.sleep(3000);
											sp.getSubChapter().get(su).click();
											Thread.sleep(3000);
										}

									}

									// Post Sub Chap Else Block

									else {
										int PractisePresent = chPg.getPracticeTb().size();
										if (PractisePresent != 0) {
											chPg.practise();
										} else {

											test.log(LogStatus.FAIL, "Practice Tab is not Present, Location is --> "
													+ subject + " --> " + chapter);
											System.out.println("Practice Tab is not Present, Location is --> " + subject
													+ " --> " + chapter);
											lp.takeScreenShot();

										}
										driver.navigate().back();
										Thread.sleep(5000);
										sp.getMainChapter().get(ch).click();
										Thread.sleep(3000);

									}

								}
							} else {
								int PractisePresent = chPg.getPracticeTb().size();
								if (PractisePresent != 0) {
									chPg.practise();
								} else {

									test.log(LogStatus.FAIL, "Practice Tab is not Present, Location is --> " + subject
											+ " --> " + chapter);
									System.out.println("Practice Tab is not Present, Location is --> " + subject
											+ " --> " + chapter);
									lp.takeScreenShot();

								}
								driver.navigate().back();
								Thread.sleep(5000);
							}

						}

					} else {
						System.out.println("Chapter page is not Displayed, Location is --> " + subject);
						test.log(LogStatus.FAIL, "Chapter page is not Displayed, Location is --> " + subject);
						lp.takeScreenShot();

					}

				}

				driver.navigate().back();
				Thread.sleep(3000);
			}
			test.log(LogStatus.PASS, "Practice Pass");
			lp.takeScreenShot();
		} catch (

		Exception e) {
			test.log(LogStatus.FAIL,
					"Getting Error, Practice Fail " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			lp.takeScreenShot();
			sAssert.fail("Getting Error" + e.getMessage());

		}
		sAssert.assertAll();
	}

//.....................................................TEST SERVISE..............................................................................//	

	@Test(priority = 4, enabled = false)
	public void verifyTest() throws InterruptedException {
		LoginPage lp = new LoginPage(driver, test);
		SubjectPage sp = new SubjectPage(driver, test);
		ChapterPage chPg = new ChapterPage(driver, test);
		ClassPage cp = new ClassPage(driver, test);

		DashBoardPage dp = new DashBoardPage(driver, test);
		WebDriverWait wt = new WebDriverWait(driver, 60);

		dp.openstudytab();

		Thread.sleep(5000);
		try {
			int subSize = cp.getTotalSub();
			if (subSize != 0) {
				for (int subj = 0; subj < subSize; subj++) {

					wt.until(ExpectedConditions.visibilityOfAllElements(cp.getSubjectLinks()));
					String subject = cp.getSubjectLinks().get(subj).getText();
					System.out.println("Subjects:" + subject);

					try {
						cp.getSubjectLinks().get(subj).click();
					} catch (Exception e) {
						test.log(LogStatus.FAIL, "Error on click on subject Exception is " + e.getMessage());
						System.out.println("Error on click on subject Exception is " + e.getMessage());
						lp.takeScreenShot();
					}
					Thread.sleep(2000);
					lp.takeScreenShot();
					lp.takeScreenShot();
					Thread.sleep(3000);
					int subSubjsize = cp.getTotalSubSubj();
					if (subSubjsize != 0) {
						for (int subSubj = 0; subSubj < subSubjsize; subSubj++) {
							String subSubject = cp.getSubSubjectLinks().get(subSubj).getText();
							System.out.println("Sub Subjects:" + subSubject);
							test.log(LogStatus.INFO, "Sub Subjects:" + subSubject);
							cp.getSubSubjectLinks().get(subSubj).click();
							Thread.sleep(2000);
							lp.takeScreenShot();
							Thread.sleep(3000);
							int chapSize = sp.getMainChapter().size();
							System.out.println("No. of chapters in this subject = " + chapSize);
							Thread.sleep(1000);
							if (chapSize != 0) {
								for (int ch = 0; ch < chapSize; ch++) {
									wt.until(ExpectedConditions.visibilityOfAllElements(sp.getMainChapter()));
									Thread.sleep(5000);
									String chapter = sp.getMainChapter().get(ch).getText();
									System.out.println(chapter);
									try {
										sp.getMainChapter().get(ch).click();
									} catch (Exception e) {
										test.log(LogStatus.FAIL,
												"Error on click on chapter Exception is " + e.getMessage());
										System.out.println("Error on click on chapter Exception is " + e.getMessage());
										lp.takeScreenShot();
									}
									Thread.sleep(2000);
									lp.takeScreenShot();

									int sizeChap = sp.getSubChapter().size();

									System.out.println("No.Sub Chapters = " + sizeChap);

									if (sizeChap != 0)
									// ..............................................
									{
										for (int su = 0; su < sizeChap; ++su) {
											System.out.println("Chapter : " + sp.getSubChapter().get(su).getText());
											test.log(LogStatus.INFO,
													"Chapter : " + sp.getSubChapter().get(su).getText());
											Thread.sleep(1000);
											sp.getSubChapter().get(su).click();

											Thread.sleep(2000);
											lp.takeScreenShot();

											int postSubChap = sp.getPostSubChap().size();
											test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
											System.out.println("No. of Sub-sub Chapters = " + postSubChap);

											if (postSubChap != 0) {
												for (int ps = 0; ps < postSubChap; ps++) {
													System.out.println(
															"Subchapter : " + sp.getPostSubChap().get(ps).getText());
													Thread.sleep(1000);
													sp.getPostSubChap().get(ps).click();
													test.log(LogStatus.INFO, "Open sub chapters");

													Thread.sleep(2000);
													lp.takeScreenShot();

													int TestPresent = chPg.getTestTb().size();
													if (TestPresent != 0) {
														chPg.test();

													} else {

														test.log(LogStatus.FAIL,
																"Test Tab is not Present, Location is --> " + subject
																		+ " --> " + subSubject + " --> " + chapter);
														System.out.println("Test Tab is not Present, Location is --> "
																+ subject + " --> " + subSubject + " --> " + chapter);
														lp.takeScreenShot();

													}
													driver.navigate().back();
													Thread.sleep(5000);
													sp.getMainChapter().get(ch).click();

													Thread.sleep(3000);
													sp.getSubChapter().get(su).click();
												}

											}

											else {
												int TestPresent = chPg.getTestTb().size();
												if (TestPresent != 0) {
													chPg.test();

												} else {

													test.log(LogStatus.FAIL, "Test Tab is not Present, Location is --> "
															+ subject + " --> " + subSubject + " --> " + chapter);
													System.out.println("Test Tab is not Present, Location is --> "
															+ subject + " --> " + subSubject + " --> " + chapter);
													lp.takeScreenShot();

												}
												driver.navigate().back();
												Thread.sleep(5000);
												sp.getMainChapter().get(ch).click();
											}

										}

									} else {
										int TestPresent = chPg.getTestTb().size();
										if (TestPresent != 0) {
											chPg.test();
										} else {

											test.log(LogStatus.FAIL, "Test Tab is not Present, Location is --> "
													+ subject + " --> " + subSubject + " --> " + chapter);
											System.out.println("Test Tab is not Present, Location is --> " + subject
													+ " --> " + subSubject + " --> " + chapter);
											lp.takeScreenShot();

										}
										driver.navigate().back();
										Thread.sleep(5000);

									}
								}

							} else {
								System.out.println("Chapter page is not Displayed, Location is --> " + subject + " -->"
										+ subSubject);
								test.log(LogStatus.FAIL, "Chapter page is not Displayed, Location is --> " + subject
										+ " -->" + subSubject);
								lp.takeScreenShot();

							}
							driver.navigate().back();
							Thread.sleep(5000);
						}
					} else {
						Thread.sleep(3000);
						int chapSize = sp.getMainChapter().size();
						System.out.println("No. of chapters in this subject = " + chapSize);
						Thread.sleep(1000);
						if (chapSize != 0) {
							for (int ch = 0; ch < chapSize; ch++) {
								wt.until(ExpectedConditions.visibilityOfAllElements(sp.getMainChapter()));
								Thread.sleep(5000);
								String chapter = sp.getMainChapter().get(ch).getText();
								System.out.println(chapter);

								try {
									sp.getMainChapter().get(ch).click();
								} catch (Exception e) {
									test.log(LogStatus.FAIL,
											"Error on click on chapter Exception is " + e.getMessage());
									System.out.println("Error on click on chapter Exception is " + e.getMessage());
									lp.takeScreenShot();
								}
								Thread.sleep(2000);
								lp.takeScreenShot();

								int sizeChap = sp.getSubChapter().size();

								System.out.println("No.Sub Chapters = " + sizeChap);

								if (sizeChap != 0)
								// ..............................................
								{
									for (int su = 0; su < sizeChap; ++su) {
										System.out.println("Chapter : " + sp.getSubChapter().get(su).getText());
										test.log(LogStatus.INFO, "Chapter : " + sp.getSubChapter().get(su).getText());
										Thread.sleep(1000);
										sp.getSubChapter().get(su).click();

										Thread.sleep(2000);
										lp.takeScreenShot();

										int postSubChap = sp.getPostSubChap().size();
										test.log(LogStatus.INFO, "No. of Sub-Sub Chapter : " + postSubChap);
										System.out.println("No. of Sub-sub Chapters = " + postSubChap);

										if (postSubChap != 0) {
											for (int ps = 0; ps < postSubChap; ps++) {
												System.out.println(
														"Subchapter : " + sp.getPostSubChap().get(ps).getText());
												Thread.sleep(1000);
												sp.getPostSubChap().get(ps).click();
												test.log(LogStatus.INFO, "Open sub chapters");

												Thread.sleep(2000);
												lp.takeScreenShot();

												int TestPresent = chPg.getTestTb().size();
												if (TestPresent != 0) {
													chPg.test();
												} else {

													test.log(LogStatus.FAIL, "Test Tab is not Present, Location is --> "
															+ subject + " --> " + chapter);
													System.out.println("Test Tab is not Present, Location is --> "
															+ subject + " --> " + chapter);
													lp.takeScreenShot();

												}
												driver.navigate().back();
												Thread.sleep(5000);
												sp.getMainChapter().get(ch).click();

												Thread.sleep(3000);
												sp.getSubChapter().get(su).click();
											}

										}

										else {
											int TestPresent = chPg.getTestTb().size();
											if (TestPresent != 0) {
												chPg.test();
											} else {

												test.log(LogStatus.FAIL, "Test Tab is not Present, Location is --> "
														+ subject + " --> " + chapter);
												System.out.println("Test Tab is not Present, Location is --> " + subject
														+ " --> " + chapter);
												lp.takeScreenShot();

											}
											driver.navigate().back();
											Thread.sleep(5000);
											sp.getMainChapter().get(ch).click();
										}

									}

								} else {
									int TestPresent = chPg.getTestTb().size();
									if (TestPresent != 0) {
										chPg.test();
									} else {

										test.log(LogStatus.FAIL, "Test Tab is not Present, Location is --> " + subject
												+ " --> " + chapter);
										System.out.println("Test Tab is not Present, Location is --> " + subject
												+ " --> " + chapter);
										lp.takeScreenShot();

									}
									driver.navigate().back();
									Thread.sleep(5000);
								}
							}

						} else {
							System.out.println("Chapter page is not Displayed, Location is --> " + subject);
							test.log(LogStatus.FAIL, "Chapter page is not Displayed, Location is --> " + subject);
							lp.takeScreenShot();
						}

					}
					driver.navigate().back();
					Thread.sleep(5000);
				}
			}
			test.log(LogStatus.PASS, "Test Pass");
			lp.takeScreenShot();
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Getting Error, Test Fail " + e.getMessage() + " Location is " + driver.getCurrentUrl());
			sAssert.fail("Getting Error" + e.getMessage());
			lp.takeScreenShot();

		}
		sAssert.assertAll();
	}

}