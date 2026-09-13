package pages;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DirectoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators - Sidebar Navigation
    private By directoryMenu = By.xpath("//span[text()='Directory']");

    // Locators - Filter Search Controls
    private By employeeNameInput = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private By jobTitleDropdown = By.xpath("//label[text()='Job Title']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By locationDropdown = By.xpath("//label[text()='Location']/following::div[contains(@class,'oxd-select-wrapper')][1]");

    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By resetButton = By.xpath("//button[normalize-space()='Reset']");
    private By toggleFormButton = By.xpath("//button[.//i[contains(@class,'bi-caret')]]");

    // Locators - Directory Grid & Record Counts
    private By recordsFoundText = By.xpath("//span[contains(normalize-space(), 'Records Found')]");
    private By profileImage =
            By.cssSelector("img.orangehrm-profile-picture-img");
    private By directoryCards =
    	    By.cssSelector("div.orangehrm-directory-card");
    private By noRecordsFoundText = By.xpath("//*[contains(normalize-space(.),'No Records Found')]");
    private By formSpinner = By.xpath("//div[contains(@class,'oxd-form-loader')] | //div[contains(@class,'oxd-loading-spinner')]");

    public DirectoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToDirectory() {
        wait.until(ExpectedConditions.elementToBeClickable(directoryMenu)).click();
    }

    private void waitForSpinnerToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(formSpinner));
        } catch (Exception ignored) {}
    }

    public void searchByEmployeeName(String nameHint) {
        waitForSpinnerToDisappear();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        input.clear();
        input.sendKeys(nameHint);
        
        By option = By.xpath("//div[@role='option']//span[contains(text(),'" + nameHint + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }
    public boolean searchByEmployee(String employeeHint) {
 	   waitForSpinnerToDisappear();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        input.clear();
        input.sendKeys(employeeHint);

        // Locates dropdown options containing any part of the name
        By autocompleteOption = By.xpath("//div[@role='option']//span[contains(text(),'" + employeeHint.split(" ")[0] + "')]");

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement option = shortWait.until(ExpectedConditions.elementToBeClickable(autocompleteOption));
            option.click();
            driver.findElement(searchButton).click();
            return true;
        } catch (Exception e) {
            System.out.println("Employee '" + employeeHint + "' was not found in the dropdown suggestions.");
            return false;
        }
 }
    public void selectJobTitle(String title) {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(jobTitleDropdown)).click();
        By option = By.xpath("//div[@role='option']//span[text()='" + title + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void selectLocation(String location) {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(locationDropdown)).click();
        By option = By.xpath("//div[@role='option']//span[text()='" + location + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void clickReset() {
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
    }

    public void clickToggleSearchForm() {
        wait.until(ExpectedConditions.elementToBeClickable(toggleFormButton)).click();
    }

    public boolean isNoRecordsFoundDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noRecordsFoundText)).isDisplayed();
    }

    public int getRecordsFoundCount() {
    	waitForSpinnerToDisappear();
        try {
            WebElement recordsElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(recordsFoundText)
            );

            String text = recordsElement.getText().trim();
            System.out.println("Records text: [" + text + "]");

            if (text.equalsIgnoreCase("No Records Found")) {
                return 0;
            }

            String number = text.replaceAll("[^0-9]", "");
            return number.isEmpty() ? 0 : Integer.parseInt(number);
        } catch (Exception e) {
            System.out.println("Could not locate records found text element within timeout.");
            return 0;
        }
    }
    public int getDirectoryCardsCount() {
        waitForSpinnerToDisappear();
        List<WebElement> cards = driver.findElements(directoryCards);
        return cards.size();
    }
 
}