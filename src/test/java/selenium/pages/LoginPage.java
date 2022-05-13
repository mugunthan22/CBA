package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.Status;

import entity.UserRequest;
import report.ExtentTestManager;

public class LoginPage extends Page{
	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	


	@FindBy(id ="rego") 
	WebElement Register;
	
	@FindBy(id ="login") 
	WebElement Login;

	@FindBy(xpath  ="//div[@class='container']/h1[contains(text(),'Sign Up')]/parent::div") 
	WebElement SignupWindow;
	
	@FindBy(xpath  ="//div[@class='container']/h1[contains(text(),'Login with your warrior name')]/parent::div") 
	WebElement LoginWindow;
	
	
	@FindBy(id  ="worrior_username") 
	WebElement LoginWindow_Username;
	
	@FindBy(id  ="worrior_pwd") 
	WebElement LoginWindow_Password;
	
	@FindBy(id  ="warrior") 
	WebElement LoginWindow_LoginLink;
	
	@FindBy(id  ="start") 
	WebElement LoginWindow_StartJounney;
	
	@FindBy(id  ="close") 
	WebElement LoginWindow_CancelLink;
	
	@FindBy(id  ="uname") 
	WebElement SignupWindow_Username;
	@FindBy(id  ="pwd") 
	WebElement SignupWindow_Password;
	
	@FindBy(id  ="psw-repeat") 
	WebElement SignupWindow_RepeatPassword;
	@FindBy(id  ="signupbtn") 
	WebElement SignupWindow_Signup;
	
	@FindBy(className ="cancelbtn") 
	WebElement SignupWindow_Cancel;
	
	@FindBy(id ="welcome_text") 
	WebElement Login_Success;
	
	public boolean signup(UserRequest userRequest)
	{
		Register.click();
		this.ElementPresent(SignupWindow);
		setText(SignupWindow_Username,userRequest.getUsername());
		setText(SignupWindow_Password,userRequest.getPassword());
		setText(SignupWindow_RepeatPassword,userRequest.getPassword());
		SignupWindow_Signup.click();
		return this.ElementPresent(LoginWindow);
		
	}
	
	public boolean Login(UserRequest userRequest)
	{
		if(this.ElementPresent(LoginWindow))
		{
			LoginWindow_CancelLink.click();
		}
		Login.click();
		this.ElementPresent(LoginWindow);
		setText(LoginWindow_Username,userRequest.getUsername());
		setText(LoginWindow_Password,userRequest.getPassword());		
		LoginWindow_LoginLink.click();
	    if(this.ElementPresent(LoginWindow_StartJounney))
	    {
	    	LoginWindow_StartJounney.click();
	    	return this.ElementPresent(Login_Success);
	    }
	    else
	    {
	    	return false;
	    }
	}
	
	
	
}
