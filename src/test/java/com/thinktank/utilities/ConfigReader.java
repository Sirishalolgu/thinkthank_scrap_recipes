package com.thinktank.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
	
Properties prop ;
	
	public ConfigReader() {
		File src= new File( "./src/test/resources/config.properties");
		
			try {
			FileInputStream fis = new FileInputStream(src);
			prop = new Properties();
			prop.load(fis);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		}
	
	public String getAppUrl() {
		String url=prop.getProperty("url");
		return url;
	}
	
	public String getbrowser() {
		String browser = prop.getProperty("browser");
		return browser;
	}
	
	
	
	
	

}
