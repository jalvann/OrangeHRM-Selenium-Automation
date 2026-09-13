package Admin;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserPage {
	
	private WebDriver driver;
    private WebDriverWait wait;
	
	//Locators
	private By userRole = By.xpath( "//label[text()='User Role']/following::div[contains(@class,'oxd-select-wrapper')][1]");
	 private By statusInput = By.xpath(
		    "//label[text()='Status']/following::div[contains(@class,'oxd-select-wrapper')][1]"
			);
	private By employeeName = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private By employeeAutoCompleteOption = By.xpath("//div[@role='listbox']//div[@role='option'] | //div[contains(@class,'oxd-autocomplete-option')]");

    private By usernameInput = By.xpath("//label[text()='Username']/following::input[1]");
    private By passwordInput = By.xpath("//label[text()='Password']/following::input[1]");
    private By confirmPasswordInput = By.xpath( "//label[text()='Confirm Password']/following::input[1]");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By cancelButton = By.xpath("//button[normalize-space()='Cancel']");
    private By successToast = By.xpath("//div[contains(@class,'oxd-toast-container')]//p[contains(@class,'oxd-text--toast-message')] | //div[contains(@class,'oxd-toast--success')]");

    //constructor
    public UserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    // Click Cancel
    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    //adduser
    public void addUser(String role, String employee, String status, String username,
            String password, String confirmPassword) {

	// Select User Role
	driver.findElement(userRole).click();
	
	driver.findElement(
	By.xpath("//div[@role='option']//span[text()='" + role + "']")
	).click();
	
	// Select Status
		driver.findElement(statusInput).click();
		
		driver.findElement(
		By.xpath("//div[@role='option']//span[text()='" + status + "']")
		).click();
	
     // Employee Name
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement empInput = wait.until(
                ExpectedConditions.elementToBeClickable(employeeName)
        );

        empInput.clear();
        empInput.sendKeys(employee);

        // Wait for matching employee suggestion
        By employeeOption = By.xpath(
                "//div[@role='listbox']//div[@role='option']" +
                "[contains(normalize-space(),'" + employee + "')]"
        );

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(employeeOption)
        );

        option.click(  );
	// Username
	driver.findElement(usernameInput).sendKeys(username);
	
	// Password
	driver.findElement(passwordInput).sendKeys(password);
	
	// Confirm Password
	driver.findElement(confirmPasswordInput).sendKeys(confirmPassword);
	
	// Save
	driver.findElement(saveButton).click();
	}
    public String getUsernameValidationError() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // OrangeHRM validation message element below input fields
        WebElement errorMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-field')]//span[contains(@class,'oxd-input-field-error-message')]")
            )
        );
        return errorMessage.getText().trim();
    }
    public boolean isSuccessToastDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Use presenceOfElementLocated to catch it even during dynamic render/fade
            return wait.until(ExpectedConditions.presenceOfElementLocated(successToast)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
