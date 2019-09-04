package flightBooking;

import java.io.FileInputStream;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import baseClass.BaseClass;
import xlsxreader.XlsxReader;


public class OneWay extends BaseClass{

	Properties loc = new Properties();

	@Test(priority=1)
	public void setUp() throws IOException
	{
		FileInputStream input = new FileInputStream(".\\src\\test\\resources\\locators\\locators.properties");

		loc.load(input);
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@Test(priority=2,dataProvider="SearchProvider",dataProviderClass=XlsxReader.class)
	public void search(String[] arr) throws InterruptedException
	{
		driver.get(loc.getProperty("url"));
		driver.findElement(By.xpath(loc.getProperty("domestic"))).click();
		driver.findElement(By.xpath(loc.getProperty("oneway"))).click();
		WebElement from = waitAndGetElement(30, driver,"(//input[@class='search'])[1]");
		from.sendKeys(arr[0]);
		Thread.sleep(1000);
		WebElement to = waitAndGetElement(30, driver,"(//input[@class='search'])[2]");
		to.sendKeys(arr[1]);
		Thread.sleep(1000);
		driver.findElement(By.xpath(loc.getProperty("depart"))).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(loc.getProperty("departdate"))).click();
		Thread.sleep(1000);
		//driver.findElement(By.xpath(loc.getProperty("adult"))).click();
	    Thread.sleep(1000);
		  
		driver.findElement(By.xpath(loc.getProperty("class"))).click();
		driver.findElement(By.xpath(loc.getProperty("submit"))).click();
		Thread.sleep(5000);
		WebElement book=waitAndGetElement(30,driver,"(//button[text()='BOOK'])[1]");
        book.click();
        Thread.sleep(3000);
		driver.findElement(By.xpath(loc.getProperty("email"))).sendKeys(arr[2]);
		String phoneno=arr[3].replace(".", "").replace("E9", "");
		
		driver.findElement(By.xpath(loc.getProperty("mobile"))).sendKeys(phoneno);
		driver.findElement(By.xpath(loc.getProperty("continue"))).click();
	    driver.findElement(By.xpath(loc.getProperty("dropdown1"))).click();
	    driver.findElement(By.xpath(loc.getProperty("Mr"))).click();
		driver.findElement(By.xpath(loc.getProperty("firstname1"))).sendKeys(arr[4]);
		driver.findElement(By.xpath(loc.getProperty("lastname1"))).sendKeys(arr[5]);
		//driver.findElement(By.xpath(loc.getProperty("dropdown2"))).click();
		//driver.findElement(By.xpath(loc.getProperty("Ms"))).click();
	
		driver.findElement(By.xpath(loc.getProperty("submitlast"))).click();
	
		  
		  
	}
	public WebElement waitAndGetElement(long seconds,WebDriver driver,final String xpath) {
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(seconds))
				.pollingEvery(Duration.ofSeconds(2))
				.ignoring(NoSuchElementException.class);
		
		WebElement element = fluentWait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver dvr) {
				return dvr.findElement(By.xpath(xpath));
			}
		});
		return element;
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}
}

