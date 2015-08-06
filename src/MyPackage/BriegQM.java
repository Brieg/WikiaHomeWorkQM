/**
* The BriegmannQAHome program implements a automatic specific test case
* 
*
* @author  Mariusz Briegmann
* @version 1.0
*/

package MyPackage;

import java.util.concurrent.TimeUnit;

//library for read input from console
import java.util.Scanner;

//library for all web driver commands 
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BriegQM {
	
	//Create a new instance of Firefox Browser
    static WebDriver Driver;
    
    static String BaseUrl = "http://qm-homework.wikia.com";
    
    /**
     * After starting the program, the user will decide which browser wants to run.
     * WebBrowser 1 means Firefox
     * WebBrowser 2 means Chrome
     */
    static int WebBrowser = 0;
    
    // This variable describe how long page should be fully loaded.
    static long PageSecondsLoaded = 24;
    
    // These variables kept here the user name and password created the current account to log on.
    static String Login = "MarBriegQM";
	static String Password = "MyP4##w0rd"; 
	
	// This variable contains the url of the movie.
	static String URLVideo = "http://www.youtube.com/watch?v=h9tRIZyTXTI";
	
    /**
     * This is main method where whole test case is executed.
     */
	public static void main(String[] args)
	{		
		Scanner in = new Scanner(System.in);
		System.out.println("Select the browser:\n1. Firefox\n2. Chrome\n\n0. Close TC.");
		System.out.print("Select the option: ");
		WebBrowser = in.nextInt();
		in.close();
		
		if(WebBrowser == 1)
		{
			//Proceed in Firefox browser
			Driver = new FirefoxDriver();
			System.out.println("Starting up: Firefox.");
		}
		else if(WebBrowser == 2)
		{
			//Proceed in Chrome browser
			Driver = new ChromeDriver(); 
			System.out.println("Starting up: Google Chrome.");
		}
		else
		{
			System.out.println("Invalid number.\nTerminate the program.");
			System.exit(0);
		}
		
		// Go to baseURl page
		Driver.get(BaseUrl);
		
		//Console information
		System.out.println("Page: "+ BaseUrl +" is opening.");
	       
		//Get the current page URL and store the value in variable 'FullURL'
		String FullURL = Driver.getCurrentUrl();
		System.out.println("The current URL is " + FullURL);
		
		/*
		 * We have to wait to be sure that a website to fully loaded.
		 * Sometimes advertising after loading load the parties and this causes some problems.
		 */
		Driver.manage().timeouts().pageLoadTimeout(PageSecondsLoaded, TimeUnit.SECONDS);
	       
		//There is start automatic SCENARIO 1 - LOGIN
		LoginIn(Login,Password);
	      
		//There is start automatic SCENARIO 2 - ADD VIDEO
		if(AddAdStartVideo(URLVideo))
		{
			System.out.println("Challenge completed.");
		}
		
	}
	
	/**
	 * This function is used to log on to previously created account for Wikia.com automatically
	 * @param Login - current existing user name
	 * @param Password - string of characters that access to your account
	 */
	static void LoginIn(String LoginAccount, String PasswordAccount)
	{
		//There is full automatet SCENARIO 1 - LOGIN
		System.out.println("Account "+ LoginAccount +" start sign in.");
			 
		Driver.findElement(By.id("AccountNavigation")).click();
		// To be sure, we remove the contents to enter the value below.
		Driver.findElement(By.id("usernameInput")).clear();
		Driver.findElement(By.id("usernameInput")).sendKeys(LoginAccount);
			
		// To be sure, we remove the contents to enter the value below.
		Driver.findElement(By.id("passwordInput")).clear();
		Driver.findElement(By.id("passwordInput")).sendKeys(PasswordAccount);
		
		//Click to "Log in" buton
		Driver.findElement(By.cssSelector("input.login-button")).click();
		 
		System.out.println("Account "+ Login +" is logged acomplished!");
		
		//Fix for Chrome browser
		if(WebBrowser == 2)
		{
			Driver.manage().timeouts().pageLoadTimeout(PageSecondsLoaded, TimeUnit.SECONDS);
		}
	}
	
	/**
	 * This function is used to add video from User Control Panel.
	 * 
	 * @param YTVideoURL Video URL path from YouTube
	 * @return true if video was added successfully
	 */
	static Boolean AddAdStartVideo(String YTVideoURL)
	{
		Driver.manage().timeouts().implicitlyWait(PageSecondsLoaded, TimeUnit.SECONDS);
		Driver.findElement(By.xpath("//header[@id='WikiHeader']/div/nav")).click();
		Driver.findElement(By.linkText("Add a Video")).click();
		
		// To be sure, we remove the contents to enter the value below.
		Driver.findElement(By.id("wpWikiaVideoAddUrl")).clear();
		// Wypelnienie textboxa naszym linkiem do filmu
		Driver.findElement(By.id("wpWikiaVideoAddUrl")).sendKeys(URLVideo);
		
		Driver.findElement(By.cssSelector("div.submits > input[type=\"submit\"]")).click();
		
		// We have to wait to be sure that a website to fully loaded.
		Driver.manage().timeouts().implicitlyWait(PageSecondsLoaded, TimeUnit.SECONDS);
		
		System.out.println(Driver.findElement(By.cssSelector("div.msg")).getText());
		
		String MSG = Driver.findElement(By.xpath("//div[@class='msg']/a")).getAttribute("href");
		
		if(!MSG.isEmpty())
		{
			Driver.get(MSG);
			return true;
		}
		else
			return false;
	}
	
	 private boolean isElementPresent(By by) {
		    try {
		      Driver.findElement(by);
		      return true;
		    } catch (NoSuchElementException e) {
		    	 System.out.println(e.toString());
		      return false;
		    }
	}
	 
}
