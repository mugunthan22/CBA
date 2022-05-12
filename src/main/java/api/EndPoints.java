package api;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import api.response.APIResponse;
import api.response.IAPIResponse;
import config.Configuration;
import entity.User;
import entity.UserRequest;
import entity.Users;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndPoints {
	private static EndPoints apiUtilsInstance = null;
    private RequestSpecification request;
    private static String ApplicationToken="";
    private static String UserToken="";
    public static EndPoints getInstance() {

        if (apiUtilsInstance == null)
            apiUtilsInstance = new EndPoints();
        return apiUtilsInstance;
    }
    public void resetRestAssured() {
        apiUtilsInstance = null;
    }
    public EndPoints()
    {
    	request=null;
    	RestAssured.baseURI=Configuration.getInstance().getBASEAPIURL();
    	request = RestAssured.given();
        request.header("Content-Type", "application/json");
        ApplicationToken=Configuration.getInstance().getActiveToken();
    }    
    public RequestSpecification getRequestSpecification() {
        return request;
    }
    public boolean VerifyToken() {
        Response response = request.header("Authorization",ApplicationToken)
        		.get(Route.verifyToken());
        if (response.statusCode() != HttpStatus.SC_OK)
            throw new RuntimeException("Authorization Failed. Content of failed Response: " + response.toString() + " , Status Code : " + response.statusCode());
        return true;
    }
    public Response RegisterUser(UserRequest user,boolean willcreate) {
        Response response = request.header("Authorization",ApplicationToken).body(user)
        		.post(Route.userRegister());
        if (response.statusCode() == HttpStatus.SC_BAD_REQUEST && willcreate)
            throw new RuntimeException("User Registeration Failed(User Already Exist). Content of failed Response: " + response.toString() + " , Status Code : " + response.statusCode());
        else if (response.statusCode() == HttpStatus.SC_UNAUTHORIZED && willcreate)
        	throw new RuntimeException("User Registeration Failed(User Already Exist). Content of failed Response: " + response.toString() + " , Status Code : " + response.statusCode());
        else
        	return  response;
        	
    }
    public Response LoginUser(UserRequest user,boolean willlogin) {
        Response response = request.header("Authorization",ApplicationToken).body(user)
        		.post(Route.userLogin());
        if (response.statusCode() != HttpStatus.SC_OK && willlogin) {
        	UserToken="";
            throw new RuntimeException("User Login Failed. Content of failed Response: " + response.toString() + " , Status Code : " + response.statusCode());
        }else {
        	if (willlogin) {
        	UserToken=response.body().jsonPath().get("token");}
        	else
        	{
        		UserToken="";
        	}
        	return response;}
    }
    

       
    public List<User> getUsers() {
    	
        Response response = request.header("Authorization",ApplicationToken).get(Route.userOperation());
        if (response.statusCode() == HttpStatus.SC_OK) {
        	JsonParser parser = new JsonParser();
        	JsonArray array =  (JsonArray) parser.parse(response.asString()).getAsJsonArray();
        	//Traverse the list
        	List<User> user = new ArrayList<User>() ;
        	for(int i=0;i<array.size();i++)
			{
				JsonObject obj =  (JsonObject)array.get(i);
				User a=new User(obj.get("username").getAsString(),obj.get("score").getAsInt());
				user.add(a);
			}
        	return user;
    	}
        else
        {
        	 throw new RuntimeException("Fetch List of user in leaderboard Failed. Content of failed Response: " + response.toString() + " , Status Code : " + response.statusCode());
        }
    }
    
    public Response AddNewUsertoLeaderBoard(User user) {
    	
        Response response = request.header("Authorization",ApplicationToken).body(user)
        		.post(Route.userOperation());
        
        if (response.statusCode() != HttpStatus.SC_CREATED) {
        	throw new RuntimeException("Not able to create New user in Leaderboard. Content of failed Response: " + response.toString() + " , Status Code : " + response.statusCode());
        }
        return response;
        
    }
 public Response UpdateUserinLeaderBoard(User user) {
	 this.resetRestAssured();            //reset existing instance
 	 this.getInstance();     //get new instance
        Response response = request.header("Authorization",ApplicationToken).body(user)
        		.put(Route.userOperation());
        if (response.statusCode() != HttpStatus.SC_NO_CONTENT) {
        	throw new RuntimeException("Not able to update user in Leaderboard. Content of failed Response: " + response.toString() + " , Status Code : " + response.statusCode());
        }
        return response;
    }
    
 
 public Response DeleteUserinLeaderBoard(String username) {
	 this.resetRestAssured();            //reset existing instance
 	 this.getInstance();  
     Response response = request.header("Authorization",ApplicationToken).header("delete-key",username)
     		.delete(Route.userOperation());
     if (response.statusCode() != HttpStatus.SC_NO_CONTENT) {
     	throw new RuntimeException("Not able to delete user in Leaderboard. Content of failed Response: " + response.toString() + " , Status Code : " + response.statusCode());
     }
     return response;
 }
 
   
    public User getUserbyUsername(String username) {
    	
    	System.out.println(ApplicationToken);
    	
    	 Response response = this.request.header("Authorization",UserToken).log().all()
         		.get(Route.fetchUser(username));
    	 JsonParser parser = new JsonParser();
     	JsonArray array =  (JsonArray) parser.parse(response.asString()).getAsJsonArray();
     	//Traverse the list
     	if (array.size()==0)
     	{
     		throw new RuntimeException("Not able to find user "+ username + " in Leaderboard.");
     	    
     	}else if(array.size()>1)
     	{
     		throw new RuntimeException("More than one entry found for user "+ username + " in Leaderboard.");
     	}
     	JsonObject obj =  (JsonObject)array.get(0);
		User a=new User(obj.get("username").getAsString(),obj.get("score").getAsInt());
     	
     	return a;
     	
    }

}