package config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public  class Configuration {
	private Properties properties;
	private static Configuration configuration;
	public static String propertyFilePath="";

    private Configuration() {
		BufferedReader reader;
	    	
	        try {
	            reader = new BufferedReader(new FileReader(propertyFilePath));
	            properties = new Properties();
	            try {
	                properties.load(reader);
	                reader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	            throw new RuntimeException("properties not found at " + propertyFilePath);
	        }		
	}

    public static Configuration getInstance( ) {
    	if(configuration == null) {
    		configuration = new Configuration();
    	}
        return configuration;
    }

    public String getBASEAPIURL() {
        String BASEAPIURL = properties.getProperty("BASEAPIURL");
        if(BASEAPIURL != null) return BASEAPIURL;
        else throw new RuntimeException("BASEAPIURL not specified in the properties file.");
    }
    public  String getActiveToken() {
        String APITOKEN = properties.getProperty("APPLICATIONTOKEN");
        if(APITOKEN != null) return APITOKEN;
        else throw new RuntimeException("APITOKEN not specified in the properties file.");
    }
    public String getAPPLICATIONURL() {
        String APPLICATIONURL = properties.getProperty("APPLICATIONURL");
        if(APPLICATIONURL != null) return APPLICATIONURL;
        else throw new RuntimeException("APPLICATIONURL not specified in the properties file.");
    }
    public String getBROWSERTYPE() {
        String BROWSERTYPE = properties.getProperty("BROWSERTYPE");
        if(BROWSERTYPE != null) return BROWSERTYPE;
        else throw new RuntimeException("BROWSERTYPE not specified in the properties file.");
    }
    
    public String getCHROMEDRIVERPATH() {
        String CHROMEDRIVERPATH = properties.getProperty("CHROMEDRIVERPATH");
        if(CHROMEDRIVERPATH != null) return CHROMEDRIVERPATH;
        else throw new RuntimeException("CHROMEDRIVERPATH not specified in the properties file.");
    }
    
    public String getFIREFOXDRIVERPATH() {
        String FIREFOXDRIVERPATH = properties.getProperty("FIREFOXDRIVERPATH");
        if(FIREFOXDRIVERPATH != null) return FIREFOXDRIVERPATH;
        else throw new RuntimeException("FIREFOXDRIVERPATH not specified in the properties file.");
    }
    
    

  
}