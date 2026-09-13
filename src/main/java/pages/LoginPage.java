package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	private WebDriver driver;

    private By usernameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By loginBtn = By.xpath("//button[@type='submit']");   
    private By forgotPasswordLink = By.xpath("//div[@class='orangehrm-login-forgot']");   
    private By errorMessage =  By.cssSelector(".oxd-alert-content-text");
     private By usernameError = By.xpath("//input[@name='username']/following::span[1]");
     private By passwordError =
    	        By.xpath("//input[@name='password']/following::span[1]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginBtn).click();
    }
    
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
    public String getUsernameErrorMessage() {
        return driver.findElement(usernameError).getText();
    }

    public String getPasswordErrorMessage() {
    return driver.findElement(passwordError).getText();
    }
    
    public void clickForgotPasswordLink() {
        driver.findElement(forgotPasswordLink).click();
    }
}

