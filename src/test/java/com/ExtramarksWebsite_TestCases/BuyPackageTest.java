package com.ExtramarksWebsite_TestCases;

import java.util.Hashtable;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ExtramarksWebsite_Pages.BuyPackagePage;
import com.ExtramarksWebsite_Utils.Constants;
import com.ExtramarksWebsite_Utils.DataUtil;
import com.ExtramarksWebsite_Utils.ExtentManager;
import com.ExtramarksWebsite_Utils.Xls_Reader;

public class BuyPackageTest extends BaseTest
{
	@BeforeMethod
	public void init()
	{
		rep = ExtentManager.getInstance();
		test= rep.startTest("BuyPackageTest");
	}
	
	@DataProvider
	public Object[][] getData()
	{
		Xls_Reader xls= new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data= DataUtil.getData(xls, "BuyPackageTest");
		return data;
	}
	
	@Test(dataProvider="getData", priority=1)
	public void openBuyPackage(Hashtable<String, String> data) throws InterruptedException
	{
		defaultLogin(data);
		BuyPackagePage bp= new BuyPackagePage(driver, test);
		bp.BuyPackage(data.get("Coupon"), data.get("PostalCode"), data.get("Address"));
	
	}
}
