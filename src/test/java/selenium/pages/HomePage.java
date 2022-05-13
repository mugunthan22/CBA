package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.Status;

import entity.UserRequest;
import report.ExtentTestManager;

public class HomePage extends Page{
	public HomePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	 public enum Battlefield{
		office,bus,news,restaurant
	}
	 
	 public enum Option{
			First,Second
		}
	@FindBy(id ="welcome_text") 
	WebElement Login_Success;
	
	@FindBy(id  ="office") 
	WebElement HomePage_office;
	
	@FindBy(id  ="bus") 
	WebElement HomePage_bus;
	
	@FindBy(id  ="news") 
	WebElement HomePage_news;
	
	@FindBy(id  ="restaurant") 
	WebElement HomePage_restaurant;
	

	
	@FindBy(xpath = "//h5[contains(text(),'You are seated at a restaurant..')]"+
	"/ancestor::div[@role='document']//div[@class='modal-footer']/button")
	WebElement HomePage_restaurant_Window;
	
	@FindBy(xpath = "//h5[contains(text(),'No second chances!')]"+
			"/ancestor::div[@role='document']//div[@class='modal-footer']/button") 
	WebElement HomePage_news_Window;
	
	@FindBy(xpath = "//h5[contains(text(),'You have taken the public bus..')]"+
			"/ancestor::div[@role='document']//div[@class='modal-footer']/button") 
	WebElement HomePage_bus_Window;
	
	@FindBy(xpath = "//h5[contains(text(),'You have entered the office..')]"+
			"/ancestor::div[@role='document']//div[@class='modal-footer']/button") 
	WebElement HomePage_office_Window;
	
	
	@FindBy(xpath = "//div[@class='main']//a[contains(@id,'bus_answer') and contains"+
	"(text(),'Use your superheroes Mask and sanitizer')]")
	WebElement HomePage_bus_correctanswer;
	
	@FindBy(xpath = "//div[@class='main']//a[contains(@id,'bus_answer') and contains"+
	"(text(),'travelling during peak times')]")
	WebElement HomePage_bus_wronganswer;
	
	@FindBy(xpath = "//a[@id='bus_answer_1']")
	WebElement HomePage_bus_ANS_option1;
	
	@FindBy(xpath = "//a[@id='bus_answer_2']")
	WebElement HomePage_bus_ANS_option2;
	
	@FindBy(xpath = "//div[@class='modal-header']/h5[contains(text(),'That is correct!')]")
	WebElement Battle_Correct;
	
	@FindBy(xpath = "//div[@class='modal-header']/h5[contains(text(),\"That doesn't sound right!\")]")
	WebElement Battle_Wrong;
	
	@FindBy(xpath = "//div[@class='modal-header']/h5[contains(text(),\"Time's Up!\")]")
	WebElement Battle_TimeUp;
	
	@FindBy(xpath = "//h5[contains(text(),\"Time's Up!\")]"+
	"/ancestor::div[@role='document']//button[contains(text(),'Return Home')]")
	WebElement Battle_TimeUp_ReturnHome;
	
	@FindBy(xpath = "//h5[contains(text(),\"Time's Up!\")]"+
			"/ancestor::div[@role='document']//button[contains(text(),'Try again')]")
	WebElement Battle_TimeUp_TryAgain;
	
	@FindBy(xpath = "//button[@id='leaderboard_link']")
	WebElement LeaderBoard_Link;
	public boolean NavigateTo(Battlefield  battlefield)
	{
		switch(battlefield)
		{
		case office:
			this.ElementPresent(HomePage_office);	
			HomePage_office.click();
			return this.ElementPresent(HomePage_office_Window);	
		case bus:
			this.ElementPresent(HomePage_bus);	
			HomePage_bus.click();
			return this.ElementPresent(HomePage_bus_Window);	
		case news:
			this.ElementPresent(HomePage_news);	
			HomePage_news.click();
			return this.ElementPresent(HomePage_news_Window);	
		case restaurant:
			this.ElementPresent(HomePage_restaurant);	
			HomePage_restaurant.click();
			return this.ElementPresent(HomePage_restaurant_Window);	
			
		}
		return false;
	}
	
	public boolean AttemptBusBattle(boolean Success)
	{
		HomePage_bus_Window.click();
		if(Success)
		{
			HomePage_bus_correctanswer.click();
			if (this.ElementPresent(Battle_Correct))
					{
				this.ElementPresent(LeaderBoard_Link);
				LeaderBoard_Link.click();
				return true;
					}
			else
			{
				return false;
			}
		}
		else
		{
			HomePage_bus_wronganswer.click();
			if (this.ElementPresent(Battle_Wrong))
					{
				this.ElementPresent(Battle_TimeUp);
				Battle_TimeUp_ReturnHome.click();
				return true;
					}
			else
			{
				return false;
			}
		}
		
	}
	
	public boolean AttemptBusBattle(Option option)
	{
		HomePage_bus_Window.click();
		if(option==Option.First)
		{
			HomePage_bus_ANS_option1.click();
		}
		else if(option==Option.Second)
		{
			HomePage_bus_ANS_option2.click();
		}
		if(this.ElementPresent(Battle_Correct,10))
		{
			ExtentTestManager.getTest().log(Status.INFO,"Option First is correct. Navigating to Leaderboard"); 
			this.ElementPresent(LeaderBoard_Link);
			LeaderBoard_Link.click();
			return true;
		}
		else if(this.ElementPresent(Battle_Wrong,10))
		{
			ExtentTestManager.getTest().log(Status.INFO,"Option First is Wrong. waiting for Timeup to navigate to Home page"); 
			if (this.ElementPresent(Battle_Wrong))
			{
				this.ElementPresent(Battle_TimeUp);
				Battle_TimeUp_ReturnHome.click();
				return false;
			}
		}
		
		return false;
		
		
	}
	
	
	
}
