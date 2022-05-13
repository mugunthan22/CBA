package selenium.pages;

import java.time.Duration;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import config.Configuration;
import report.ExtentTestManager;

public class Page {

	private static WebDriver driver;
	
	public Page(WebDriver wd)
	{
		driver=wd;
	}
	public static WebDriver getDriver() {
		return driver;
	}
	public void setText(WebElement we,String text)
	{
		we.click();
		we.clear();
		we.sendKeys(text);
	}
	private  static void setDriver(String browserType, String appURL) {
		switch (browserType) {
		case "chrome":
			driver = initChromeDriver(appURL);
			break;
		case "firefox":
			driver = initFirefoxDriver(appURL);
			break;
		default:
			System.out.println("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
			driver = initFirefoxDriver(appURL);
		}
	}

	private static WebDriver initChromeDriver(String appURL) {
		System.out.println("Launching google chrome with new profile..");
		 System.setProperty("webdriver.chrome.driver", Configuration.getInstance().getCHROMEDRIVERPATH());
		
		 ChromeOptions chromeOptions = new ChromeOptions();
         //chromeOptions.addArguments("--headless");
         chromeOptions.addArguments("--no-sandbox");

         WebDriver driver = new ChromeDriver(chromeOptions);		
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	private static WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		 FirefoxBinary firefoxBinary = new FirefoxBinary();
		 firefoxBinary.addCommandLineOptions("--no-sandbox");
		 FirefoxOptions firefoxOptions = new FirefoxOptions();
		 firefoxOptions.setBinary(firefoxBinary);
		System.setProperty("webdriver.gecko.driver",Configuration.getInstance().getFIREFOXDRIVERPATH());
		 FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	
	public static void initializeTestBaseSetup(String browserType) {
		try {
			if (browserType.isEmpty())
			{
				browserType=Configuration.getInstance().getBROWSERTYPE();
			}
			setDriver(browserType, Configuration.getInstance().getAPPLICATIONURL());

		} catch (Exception e) {
			System.out.println("Error....." + e.getStackTrace());
		}
	}
	
	public String getPageTitle() {
		String title = driver.getTitle();
		return title;
	}
	
	public boolean ElementPresent(WebElement we)
	{
		 Duration duration =Duration.ZERO;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)					
				.withTimeout(duration.withSeconds(30)) 			
				.pollingEvery(duration.withSeconds(5)) 			
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.elementToBeClickable(we));
		if (!we.isDisplayed())
		{
			ExtentTestManager.getTest().log(Status.INFO, "Timeout occured to find the element");
			return false;
		}
		else
		{
			return true;
		}
			
	}
	public boolean ElementPresent(WebElement we,int timeout)
	{
		 Duration duration =Duration.ZERO;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)					
				.withTimeout(duration.withSeconds(timeout)) 			
				.pollingEvery(duration.withSeconds(5)) 			
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.elementToBeClickable(we));
		if (!we.isDisplayed())
		{
			ExtentTestManager.getTest().log(Status.INFO, "Timeout occured to find the element");
			return false;
		}
		else
		{
			return true;
		}
			
	}

	
}