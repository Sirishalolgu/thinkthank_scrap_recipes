package com.thinktank.core;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.thinktank.utilities.ConfigReader;

public class BaseClass {

	public static WebDriver driver;
	static ConfigReader configreader = new ConfigReader();
	public static String url = configreader.getAppUrl();
	public static String browserName = configreader.getbrowser();

	@BeforeTest
	public static void initializeBrowser() throws InterruptedException {
		if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
		driver.get(url);
		driver.manage().window().maximize();

		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

	}
	
	@Test
	public void scrapPages() {
		try {
		MainScrapper.scrapingrecipes(driver);
	} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@AfterTest
	public static void teardown() {

		driver.close();
	}

}
