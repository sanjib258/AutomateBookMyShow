package Academain;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookMyShowSignInTest {
	static {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
	}
	public static void main(String[] args) throws InterruptedException {
        // Create a new instance of ChromeDriver
        WebDriver driver = new ChromeDriver();
        //Maximize browser
        driver.manage().window().maximize();
        
        // Step 1: Open https://in.bookmyshow.com/explore/home/
        driver.get("https://in.bookmyshow.com/explore/home/");
        
        //implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        // Step 2: If a city is asked - Select 
        driver.findElement(By.xpath("//span[text()='Bengaluru']")).click();
        
         //Step 3: Click on SignIn
        WebElement signIn = driver.findElement(By.xpath("//div[text()='Sign in']"));
        signIn.click();
        
        // Step 4: Click on Continue with Email
        WebElement continueWithEmail = driver.findElement(By.xpath("//div[text()='Continue with Email']"));
        continueWithEmail.click();
        
        // Step 5: Enter seleniumauto@yopmail.com and click on continue
        String email = "seleniumauto@yopmail.com";
        WebElement emailInput = driver.findElement(By.id("emailId"));
        emailInput.sendKeys(email);
        WebElement continueButton = driver.findElement(By.xpath("//button[text()='Continue']"));
        continueButton.click();
           // Wait for a few seconds
            Thread.sleep(5000);
            
            // open a new tab and switch to new tab
            ((JavascriptExecutor)driver).executeScript("window.open()");
        	ArrayList<String> tabs=new ArrayList<>(driver.getWindowHandles());
        	driver.switchTo().window(tabs.get(1));
        	
       // Step 6: Go to Yopmail.com
       driver.get("http://www.yopmail.com/en/");
        
        // Step 7: Type seleniumauto@yopmail.com and access inbox 
       String email1 = "seleniumauto@yopmail.com";
       WebElement emailInput1 = driver.findElement(By.xpath("//input[@type='text']"));
       emailInput1.sendKeys(email);

       WebElement checkInboxBtn = driver.findElement(By.xpath("(//button)[1]"));
       checkInboxBtn.click();
       
           // Wait for a few seconds to let the inbox load
       
       WebDriverWait wait = new WebDriverWait(driver, 10);
       wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Home']")));
       
            //Switch to the latest email frame
       driver.switchTo().frame("ifinbox");
       
     //  Step 8: Locate the latest email from BookMyShow and fetch the OTP
        WebElement latestEmail = driver.findElement(By.xpath("//div[@class='m'][1]"));
       latestEmail.click();
       
       // Switch to parent frame and go to otp frame
       driver.switchTo().parentFrame();
       driver.switchTo().frame("ifmail");
       
      // Step 9: Fetch the OTP from the email content
       WebElement otpElement = driver.findElement(By.xpath("//td[text()=' Please enter the below OTP to continue to BookMyShow.']/../../tr[2]/td/table/tbody/tr/td"));
       String otp = otpElement.getText();
        
        // Step 9: Come back to Sign in Page and enter the OTP
        driver.switchTo().window(tabs.get(0));
        WebElement otpInput = driver.findElement(By.xpath("//div[text()='Verify your Email Address']/../div[3]/input[1]"));
        otpInput.click();
        otpInput.sendKeys(otp);
        
        //Wait for sometime
        Thread.sleep(3000);
        
        // Step 10: Click on the 'Continue' button
        WebElement continueOTPButton = driver.findElement(By.xpath("//button[text()='Continue']"));
        continueOTPButton.click();
        
        // Step 11: Validate user is successfully signed in and "Hi, Guest" is displayed
        WebElement greetingElement = driver.findElement(By.xpath("//div[contains(text(), 'Hi, Guest')]"));
        String greetingText = greetingElement.getText();
        if (greetingText.contains("Hi, Guest")) {
            System.out.println("User is successfully signed in.");
        } else {
            System.out.println("Sign-in failed.");
        }
        
        // Close the browser
        driver.quit();
    }
    
}