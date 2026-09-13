package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MaintenancePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators - Sidebar Navigation
    private By maintenanceMenu = By.xpath("//span[text()='Maintenance']");

    // Locators - Administrator Access Modal
    private By adminPasswordInput = By.xpath("//input[@type='password']");
    private By confirmAdminBtn = By.xpath("//button[normalize-space()='Confirm']");
    private By cancelAdminBtn = By.xpath("//button[normalize-space()='Cancel']");
    private By authErrorMsg = By.xpath("//p[contains(@class,'oxd-text--error') or contains(@class,'oxd-alert-content-text')]");

    // Locators - Top Navigation & Sub-Tabs
    private By purgeRecordsDropdown = By.xpath("//span[normalize-space()='Purge Records']");
    private By purgeEmployeeSubTab = By.xpath("//a[text()='Employee Records']");
    private By purgeCandidateSubTab = By.xpath("//a[text()='Candidate Records']");
    private By accessRecordsTab = By.xpath("//a[normalize-space()='Access Records']");

    // Locators - Purge Employee Records Form
    private By pastEmployeeInput = By.xpath("//label[contains(text(),'Past Employee')]/following::input[1]");
    private By searchBtn = By.xpath("//button[normalize-space()='Search']");

    // Locators - Purge Candidate Records Form
    private By vacancyInput = By.xpath("//label[contains(text(),'Vacancy')]/following::input[1]");
    private By purgeAllBtn = By.xpath("//button[normalize-space()='Purge All']");
    private By confirmPurgeModalBtn = By.xpath("//button[normalize-space()='Yes, Purge']");
    private By cancelPurgeModalBtn = By.xpath("//button[normalize-space()='No, Cancel']");
    private By closePurgeModalBtn = By.xpath("//button[@class='oxd-dialog-close-button oxd-dialog-close-button-position']");
    private By confirmationModal = By.xpath("//div[contains(@class,'oxd-dialog-container-default')]");

    // Locators - Access Records (Download Personal Data) Form
    private By accessEmployeeNameInput = By.xpath("//label[contains(text(),'Employee Name')]/following::input[1]");
    private By downloadBtn = By.xpath("//button[normalize-space()='Download']");
    private By selectedEmployeeSection =
    	    By.xpath("//*[normalize-space()='Selected Employee']");

    // Locators - Validation Messages & UI Feedback
    private By requiredFieldError = By.xpath("//span[contains(@class,'oxd-input-field-error-message')]");
    private By noRecordsFoundText = By.xpath("//span[text()='No Records Found'] | //*[contains(text(),'No Records Found')]");
    private By recordsFoundText = By.xpath("//span[contains(normalize-space(), 'Records Found')]");
    private By successToast = By.xpath("//div[contains(@class,'oxd-toast')] | //p[contains(@class,'oxd-text--toast-message')]");
    private By formSpinner = By.xpath("//div[contains(@class,'oxd-form-loader')] | //div[contains(@class,'oxd-loading-spinner')]");

    public MaintenancePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Helper: Wait for overlay spinner to disappear
    private void waitForSpinnerToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(formSpinner));
        } catch (Exception ignored) {}
    }

    // --- Navigation Methods ---
    public void navigateToMaintenance() {
        wait.until(ExpectedConditions.elementToBeClickable(maintenanceMenu)).click();
    }

    public void navigateToPurgeCandidateRecords() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(purgeRecordsDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(purgeCandidateSubTab)).click();
    }

    public void navigateToAccessRecords() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(accessRecordsTab)).click();
    }

    // --- Administrator Access Authentication ---
    public boolean isAdminAccessPromptDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(adminPasswordInput)).isDisplayed();
    }

    public void authenticateAdmin(String password) {
        WebElement pwdField = wait.until(ExpectedConditions.visibilityOfElementLocated(adminPasswordInput));
        pwdField.clear();
        pwdField.sendKeys(password);
        driver.findElement(confirmAdminBtn).click();
    }

    public boolean isAuthErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(authErrorMsg)).isDisplayed();
    }

    // --- Purge Employee Records Actions ---
    public boolean searchPastEmployee(String employeeHint) {
    	   waitForSpinnerToDisappear();
           WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(pastEmployeeInput));
           input.clear();
           input.sendKeys(employeeHint);

           // Locates dropdown options containing any part of the name
           By autocompleteOption = By.xpath("//div[@role='option']//span[contains(text(),'" + employeeHint.split(" ")[0] + "')]");

           try {
               WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
               WebElement option = shortWait.until(ExpectedConditions.elementToBeClickable(autocompleteOption));
               option.click();
               driver.findElement(searchBtn).click();
               return true;
           } catch (Exception e) {
               System.out.println("Employee '" + employeeHint + "' was not found in the dropdown suggestions.");
               return false;
           }
    }

    // --- Purge Candidate Records Actions ---
 // Search candidate vacancy by typing a hint and selecting from dropdown if present
    public boolean searchCandidateVacancyIfExists(String vacancyName) {
        waitForSpinnerToDisappear();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(vacancyInput));
        input.clear();
        input.sendKeys(vacancyName);

        By autocompleteOption = By.xpath("//div[@role='option']//span[contains(text(),'" + vacancyName + "')]");

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement option = shortWait.until(ExpectedConditions.elementToBeClickable(autocompleteOption));
            option.click();
            driver.findElement(searchBtn).click();
            return true;
        } catch (Exception e) {
            System.out.println("Vacancy '" + vacancyName + "' was not found in the dropdown suggestions.");
            return false;
        }
    }
    public void clickSearchWithoutData() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }

    public void clickPurgeAll() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(purgeAllBtn)).click();
    }

    public void confirmPurgeAll() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmPurgeModalBtn)).click();
    }

    public void cancelPurgeAll() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelPurgeModalBtn)).click();
    }
    
    public void closePurgeAll() {
        wait.until(ExpectedConditions.elementToBeClickable(closePurgeModalBtn)).click();
    }

    public boolean isPurgeModalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationModal)).isDisplayed();
    }

    public boolean isPurgeAllButtonDisplayed() {
        waitForSpinnerToDisappear();
        try {
            return driver.findElement(purgeAllBtn).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // --- Access Records Actions ---
 // Search employee and select from dropdown if present; returns true if option was found and clicked
    public boolean searchEmployeeForPersonalDataIfExists(String employeeName) {
        waitForSpinnerToDisappear();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(accessEmployeeNameInput));
        input.clear();
        input.sendKeys(employeeName);

        // Locates dropdown options containing any part of the name
        By autocompleteOption = By.xpath("//div[@role='option']//span[contains(text(),'" + employeeName.split(" ")[0] + "')]");

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement option = shortWait.until(ExpectedConditions.elementToBeClickable(autocompleteOption));
            option.click();
            driver.findElement(searchBtn).click();
            return true;
        } catch (Exception e) {
            System.out.println("Employee '" + employeeName + "' was not found in the dropdown suggestions.");
            return false;
        }
    }

    // Safely check if Selected Employee section is displayed without timing out when absent
    public boolean isSelectedEmployeeSectionDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(selectedEmployeeSection)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public void clickDownloadPersonalData() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(downloadBtn)).click();
    }

    // --- Verification & Helper Methods ---
    public boolean isRequiredErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldError)).isDisplayed();
    }

    public boolean isNoRecordsFoundDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noRecordsFoundText)).isDisplayed();
    }

    public boolean isRecordsFoundDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(recordsFoundText)).isDisplayed();
    }

    public boolean isSuccessToastDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successToast)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getRecordsFoundCount() {
        WebElement recordsElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(recordsFoundText)
        );
        String text = recordsElement.getText();
        String number = text.replaceAll("[^0-9]", "");
        return Integer.parseInt(number);
    }
}