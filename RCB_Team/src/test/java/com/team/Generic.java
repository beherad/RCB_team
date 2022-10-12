package com.team;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Generic  { 
	
	ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static String Report;
	public String testCaseName;
	public String resourcespath=System.getProperty("user.dir")+"/src/test/resources/";
	public String filepath=System.getProperty("Filename")+".json";
	//########## Start- TestNG annotations ######################
	
	@BeforeSuite
	public void beforeSuite() throws IOException {
		
		Report=resourcespath+"/AU_Reports/"+filepath+".html";
		htmlReporter = new ExtentHtmlReporter(Report);
		htmlReporter.loadXMLConfig(resourcespath+"ExtendConfig.xml");
		htmlReporter.config().setDocumentTitle("./AutomationReports Report.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("UserName", System.getProperty("user.name"));
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
	}
	@BeforeTest
	public void beforeTest() {
		test=extent.createTest(getClass().getName());
	}

	
	@BeforeMethod
	public void beforeMethod(Method method) {
		testCaseName = method.getName();
		//test.log(Status.INFO, "*******"+ testCaseName + "test case started**********");
		ReportinfoEvent("*******"+ testCaseName + " test case started**********");
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL,"Status of "+ result.getName() + "test case: Failed \n>>>Message>>>"+ result.getThrowable().getMessage());

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Status of "+ result.getName() + "test case: passed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, result.getThrowable());
		}
		ReportinfoEvent("*******"+ testCaseName + " test case Completed**********");
		extent.flush();

	}
	
	
	public void ReportPassEvent(String PassEventDescription) {
		test.log(Status.PASS, PassEventDescription);
	}

	/**
	 * This method is used to report a fail event in extent report
	 * 
	 * @param FailEventDescription description of fail event
	 */

	public void ReportFailEvent(String FailEventDescription) {
		test.log(Status.FAIL, FailEventDescription);
	}
	
	public void ReportinfoEvent(String infoEventDescription) {
		test.log(Status.INFO, infoEventDescription);
	}
		
	}
