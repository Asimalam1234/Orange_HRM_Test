
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

	WebDriver driver;

	// Setup method to initialize WebDriver
	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().driverVersion("131.0.6778.205").setup();

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/");
	}

	// Test case for successful login
	@Test
	public void testLogin() {
		WebElement username = driver.findElement(By.xpath("//input[@name='username']"));
		WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

		username.click();
		username.sendKeys("Admin");
		password.click();
		password.sendKeys("admin123");

		loginButton.click();
		WebElement logo = driver.findElement(By.xpath("//a[@class='oxd-brand']"));
		Assert.assertTrue(logo.isDisplayed(), "Login failed or dashboard is not displayed!");
	}

	@Test
	public void forrgotPasswordLink() {
		WebElement forgetPasswordLink=driver.findElement(By.xpath("//p[contains(@class,'orangehrm-login-forgot-header')]"));
		forgetPasswordLink.click();
		WebElement userNameField=driver.findElement(By.xpath("//input[@name='username']"));
		userNameField.sendKeys("Adim");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		 // Find the element (for example, an element with id 'hiddenElement')
        WebElement passwrodLink = driver.findElement(By.xpath("//h6[text()='Reset Password link sent successfully']"));

        // Assert that the element is not visible
        Assert.assertTrue(passwrodLink.isDisplayed(), "The Text is visible");
		
	}
	
	@Test
	public void testLogOut() {

		WebElement username = driver.findElement(By.xpath("//input[@name='username']"));
		WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

		username.click();
		username.sendKeys("Admin");
		password.click();
		password.sendKeys("admin123");
		loginButton.click();
		WebElement profileClick = driver.findElement(By.xpath("//li[@class='oxd-userdropdown']"));
		profileClick.click();
		driver.findElement(By.xpath("//ul[@class='oxd-dropdown-menu']/descendant::li[4]")).click();
		WebElement loginText = driver.findElement(By.xpath("//h5[text()='Login']"));
		Assert.assertTrue(loginText.isDisplayed());

	}

	@Test
	public void validationErrorMessage() {

		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

		loginButton.click();

		WebElement errorForUserName = driver.findElement(
				By.xpath("//div[@class='orangehrm-login-form']/descendant::div[6]/following-sibling::span"));
		Assert.assertTrue(errorForUserName.isDisplayed());
		WebElement errorForPassword = driver.findElement(By.xpath("//div[@class='oxd-form-row'][2]/descendant::span"));
		Assert.assertTrue(errorForPassword.isDisplayed());
	}

	@Test
	public void enterUsernameWithEmptyPasswordErrorMessage() {
		WebElement username = driver.findElement(By.xpath("//input[@name='username']"));
		WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
		username.sendKeys("Admin");
		loginButton.click();

		WebElement errorForPassword = driver.findElement(By.xpath("//div[@class='oxd-form-row'][2]/descendant::span"));
		Assert.assertTrue(errorForPassword.isDisplayed());
	}

    // Tear down method to close WebDriver
    @AfterMethod
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
}
