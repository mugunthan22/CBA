package uitest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import api.EndPoints;
import entity.UserRequest;
import report.ExtentTestManager;
import selenium.pages.HomePage;
import selenium.pages.HomePage.Battlefield;
import selenium.pages.HomePage.Option;
import selenium.pages.LoginPage;
import selenium.pages.Page;

public class TestCase {
	public String userCreated=RandomStringUtils.randomAlphabetic(8);
	UserRequest userRequest =new UserRequest(userCreated,userCreated);
	@Test(testName = "Register New user ",priority = 10)
    public void RegisterUser() {
		
		//Registering the User
		LoginPage loginPage=PageFactory.initElements(Page.getDriver(), LoginPage.class);
		boolean result=loginPage.signup(userRequest);
		Assert.assertEquals(result,true,"Sucessfully registered User " + userRequest.getUsername());    
    	ExtentTestManager.getTest().log(Status.INFO,"Sucessfully registered User " + userRequest.getUsername());
        
    	//Login in with registered user
    	result=loginPage.Login(userRequest);
		Assert.assertEquals(result,true,"Sucessfully Logged in with registered User " + userRequest.getUsername());    
    	ExtentTestManager.getTest().log(Status.INFO,"Sucessfully Logged in with registered User " + userRequest.getUsername());
        
    	//Navigating to Bus Tab from Home page 
    	HomePage homePage=PageFactory.initElements(Page.getDriver(), HomePage.class);
    	/*
    	result=homePage.NavigateTo(Battlefield.bus);
    	Assert.assertEquals(result,true,"Sucessfully Navigated to Bus battlefield");    
    	ExtentTestManager.getTest().log(Status.INFO,"Sucessfully Navigated to Bus battlefield");     	
    	result=homePage.AttemptBusBattle(false);
    	Assert.assertEquals(result,true,"Attempted Bus Battlefield and failed and come out of it post time-out");    
    	ExtentTestManager.getTest().log(Status.INFO,"Attempted Battlefield and failed and come out of it post time-out"); 
      	//Navigating to Bus Tab from Home page and attempted and passed
    	result=homePage.NavigateTo(Battlefield.bus);
    	Assert.assertEquals(result,true,"Sucessfully Navigated to Bus battlefield");    
    	ExtentTestManager.getTest().log(Status.INFO,"Sucessfully Navigated to Bus battlefield");   
    	result=homePage.AttemptBusBattle(true);
    	Assert.assertEquals(result,true,"Attempted Bus Battlefield and Passed it and Navigated to Leaderboard");    
    	ExtentTestManager.getTest().log(Status.INFO,"Attempted Bus Battlefield and Passed it and Navigated to Leaderboard"); 
    	*/
    	
    	
        //
    	result=homePage.NavigateTo(Battlefield.bus);
    	Assert.assertEquals(result,true,"Sucessfully Navigated to Bus battlefield");    
    	ExtentTestManager.getTest().log(Status.INFO,"Sucessfully Navigated to Bus battlefield"); 
    	//Attempt to battle with selecting first option
    	result=homePage.AttemptBusBattle(Option.First);
    	if(result)
    	{
    		ExtentTestManager.getTest().log(Status.INFO,"passed bus battlefield with option first and Navigated to Leaderboard"); 
    	}
    	else
    	{
    		ExtentTestManager.getTest().log(Status.INFO,"Failed bus battlefield with option first and Trying again with option second now");
    		result=homePage.NavigateTo(Battlefield.bus);
        	Assert.assertEquals(result,true,"Sucessfully Navigated to Bus battlefield");    
        	ExtentTestManager.getTest().log(Status.INFO,"Sucessfully Navigated to Bus battlefield"); 
        	//Attempt to battle with selecting first option
        	result=homePage.AttemptBusBattle(Option.Second);
        	if(result)
        	{
        		ExtentTestManager.getTest().log(Status.INFO,"passed bus battlefield with option second and Navigated to Leaderboard"); 
        	}
        	else
        	{
        		ExtentTestManager.getTest().log(Status.INFO,"Failed bus battlefield with option second. Hard Luck");
        	}
    	}
    		
    	Page.getDriver().quit();
    }
	

}
