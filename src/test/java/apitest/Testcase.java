package apitest;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import api.EndPoints;
import api.response.APIResponse;
import api.response.IAPIResponse;
import config.Configuration;
import entity.User;
import entity.UserRequest;

import io.restassured.response.Response;
import report.ExtentTestManager;

import com.aventstack.extentreports.Status;

public class Testcase {

	private EndPoints endPoints;
    private Response response;
    
    public String userCreated=RandomStringUtils.randomAlphabetic(8);
   
    @BeforeTest
   public void iAmAnAuthorizedUser() {
    	
    	
    	endPoints = new EndPoints();   
    	Assert.assertTrue(endPoints.VerifyToken(),"Verification of AUthorization to access API feature is failed");
    	
    }
    
    @BeforeMethod
    public void beforeNewRequest(){
    	endPoints.resetRestAssured();            //reset existing instance
    	endPoints = endPoints.getInstance();     //get new instance
    }
    
    
    @AfterTest
    public void Cleanup() {
    	endPoints = null;
    	
    }
    
    @Test(testName = "Connect to API interface and try to register the new user",priority = 10)
    public void RegisterUserPass() {
    	ExtentTestManager.getTest().log(Status.INFO,"Connect to API interface and try to register the new user '"+ userCreated + "'");
    	endPoints = new EndPoints();
    	
    	UserRequest UserRequest1=new UserRequest(userCreated, userCreated);
    	response=endPoints.RegisterUser(UserRequest1,true);
    	Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "User " + userCreated + " is successfully Inserted");    
    	ExtentTestManager.getTest().log(Status.INFO,"new user '"+ userCreated + "' is created from API and Status code returned is " + HttpStatus.SC_OK);
    }
    @Test(testName = "Connect to API interface and try to register the Already user",priority = 20)
    public void RegisterUserFailed()  {
    	ExtentTestManager.getTest().log(Status.INFO,"Connect to API interface and try to register the Already user '"+ "user1" + "'");
    	
    	endPoints = new EndPoints();     	
    	UserRequest UserRequest1=new UserRequest("user1", "user1");
    	response=endPoints.RegisterUser(UserRequest1,false);
    	ExtentTestManager.getTest().log(Status.INFO,"Existing user '"+ "user1" + "' is failed to create in API and Status code returned is " + HttpStatus.SC_BAD_REQUEST);
    	Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, "User1 is Aready Created and status of Response is " + response.getStatusCode());    	
    }
    
    @Test(testName = "Connect to API interface and try to login with newly created user",priority = 30)
    public void LoginwithNewlyCreatedUser()  {
    	ExtentTestManager.getTest().log(Status.INFO,"connect to API interface and try to login withnewly created user '"+ userCreated + "'");
    	
    	endPoints = new EndPoints();     	
    	UserRequest UserRequest1=new UserRequest(userCreated, userCreated);
    	response=endPoints.LoginUser(UserRequest1,true);
    	Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, ""+userCreated + " is able to login application and status of Response is " + response.getStatusCode());    	
    	ExtentTestManager.getTest().log(Status.INFO,"createExisting user '"+ userCreated + "' is able to login API and Status code returned is " + HttpStatus.SC_OK);
    	
    }
    @Test(testName = "Connect to API interface and try to login with invalid created user",priority = 5)
    public void LoginwithInvalidUser()  {
    	ExtentTestManager.getTest().log(Status.INFO,"connect to API interface and try to login with invalid user '"+ userCreated + "'");
    	
    	endPoints = new EndPoints();     	
    	UserRequest UserRequest1=new UserRequest(userCreated, userCreated);
    	response=endPoints.LoginUser(UserRequest1,false);
    	Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_BAD_REQUEST, ""+userCreated + " is not able to login application and status of Response is " + response.getStatusCode());    	
    	ExtentTestManager.getTest().log(Status.INFO,"createExisting user '"+ userCreated + "' is not able to login API and Status code returned is " + HttpStatus.SC_BAD_REQUEST);
    	
    }
    
    @Test(testName  ="get all the User present in the Leaderboard and print in the log",priority =4)
    public void GetAllUserScore()  {
    	ExtentTestManager.getTest().log(Status.INFO,"connect to API interface and get all the User's score in the leaderboard'");
    	
    	endPoints = new EndPoints();     	
		
		  UserRequest UserRequest1=new UserRequest(userCreated, userCreated);
		  List<User> users=endPoints.getUsers(); 
		  Assert.assertTrue(users.size()>0,"LeaderBoard User are successfully fetched and total User count is " +users.size()); 
		  
		  ExtentTestManager.getTest().log(Status.INFO,"LeaderBoard User are successfully fetched and total User count is " +users.size()); 
		  
		  for (User user: users)
		  { //
		  ExtentTestManager.getTest().log(Status.INFO,"Username: " + user.getUsername()
		  + ", Score :" + user.getScore() ); 
		  }
		     	
    }
    @Test(testName  ="Insert one user's score entry",priority =60)
    public void InsertUserScore()  {
    	ExtentTestManager.getTest().log(Status.INFO,"connect to API interface and Insert one user's score entry");
    	
    	endPoints = new EndPoints();     	
		  User user=new User(userCreated,2000);
		
		  response=endPoints.AddNewUsertoLeaderBoard(user);
		  Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED, ""+user.getUsername() + " is entered with " + user.getScore());    	
	      ExtentTestManager.getTest().log(Status.INFO,""+user.getUsername() + " is entered with " + user.getScore()+"and Status code returned is " + HttpStatus.SC_CREATED);
	      UserRequest UserRequest1=new UserRequest(userCreated, userCreated);
	      //UserRequest UserRequest1=new UserRequest("admin", "admin");
	      response=endPoints.LoginUser(UserRequest1,true);
	      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, ""+userCreated + " is able to login application and status of Response is " + response.getStatusCode());    	
	      ExtentTestManager.getTest().log(Status.INFO,"createExisting user '"+ userCreated + "' is able to login API and Status code returned is " + HttpStatus.SC_OK);
	      endPoints.resetRestAssured();            //reset existing instance
	    	endPoints = endPoints.getInstance(); 
	      User userGot=endPoints.getUserbyUsername(userCreated);
	      Assert.assertEquals(userGot.getScore(),user.getScore(),userGot.getUsername() + " have Score of "+ userGot.getScore());   	
	      ExtentTestManager.getTest().log(Status.INFO,userGot.getUsername() + " have Score of "+ userGot.getScore());
	      
	      
		     	
    }
    @Test(testName  ="update user's score entry",priority =70)
    public void UpdateUserScore()  {
    	ExtentTestManager.getTest().log(Status.INFO,"connect to API interface and Insert one user's score entry");
    	
    	endPoints = new EndPoints();     	
		  User user=new User(userCreated,4000);
		  response=endPoints.UpdateUserinLeaderBoard(user);
		  Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NO_CONTENT, ""+user.getUsername() + " is entered with " + user.getScore());    	
	      ExtentTestManager.getTest().log(Status.INFO,""+user.getUsername() + " is entered with " + user.getScore()+"and Status code returned is " + HttpStatus.SC_NO_CONTENT);
	      UserRequest UserRequest1=new UserRequest(userCreated, userCreated);
	      response=endPoints.LoginUser(UserRequest1,true);
	      Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, ""+userCreated + " is able to login application and status of Response is " + response.getStatusCode());    	
	      ExtentTestManager.getTest().log(Status.INFO,"createExisting user '"+ userCreated + "' is able to login API and Status code returned is " + HttpStatus.SC_OK);
	      endPoints.resetRestAssured();            //reset existing instance
	    	endPoints = endPoints.getInstance(); 
	      User userGot=endPoints.getUserbyUsername(userCreated);
	      Assert.assertEquals(userGot.getScore(),user.getScore(),userGot.getUsername() + " have Score of "+ userGot.getScore());   	
	      ExtentTestManager.getTest().log(Status.INFO,userGot.getUsername() + " have Score of "+ userGot.getScore());
	     
		     	
    }
    @Test(testName  ="Delete user's score entry",priority =80)
    public void DeleteUserScore()  {
    	ExtentTestManager.getTest().log(Status.INFO,"connect to API interface and delete one user's score entry");
    	
    	  endPoints = new EndPoints();     	
		 response=endPoints.DeleteUserinLeaderBoard(userCreated);
		  Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NO_CONTENT, ""+userCreated + " score is deleted ");    	
	      ExtentTestManager.getTest().log(Status.INFO,""+userCreated + " score is deleted and Status code returned is " + HttpStatus.SC_NO_CONTENT);
	     
		     	
    }
    

}