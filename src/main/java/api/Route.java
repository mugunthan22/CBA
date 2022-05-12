package api;

public class Route {

	private static final String AUTHORISATION = "/auth";   
	private static final String USERAUTHORISATION = "/user";
	private static final String USEROPERATION = "/user";
    private static final String VERSION = "/v1";
    
    
    public static String generateToken(){
    	return AUTHORISATION + "/gentoken";
    }
    
    public static String verifyToken(){
    	return AUTHORISATION + "/verifytoken";
    }
    public static String userRegister(){
    	return AUTHORISATION + USERAUTHORISATION + "/register";
    }
    public static String userLogin(){
    	return AUTHORISATION + USERAUTHORISATION + "/login";
    }
    public static String userOperation(){
    	return VERSION + USEROPERATION;
    }
  

    public static String fetchUser(String username){
    	return VERSION + USEROPERATION + "/" + username; 
    }

}