import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import api.EndPoints;
import config.Configuration;
import selenium.pages.Page;

public class Precondition {
	@Parameters({ "browserType"})
	@Test
	   public void LoadProperties(String browserType) {
	    	
	    	
		Configuration.propertyFilePath= System.getProperty("user.dir") +"/src/test/resources/configs/configuration.properties";
		Configuration.getInstance();
		Page.initializeTestBaseSetup(browserType);
	}
	
	
	
	    }
	
