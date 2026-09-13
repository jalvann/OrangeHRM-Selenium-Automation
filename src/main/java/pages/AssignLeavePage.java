package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AssignLeavePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Navigation Locators
    private By leaveMenu = By.xpath("//span[text()='Leave']");
    private By assignLeaveTab = By.xpath("//a[text()='Assign Leave']");

    // Form Field Locators
    private By employeeNameInput = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private By employeeAutoCompleteOptions =
            By.xpath("//div[@role='option']");
    private By leaveTypeDropdown = By.xpath("//label[text()='Leave Type']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By noRecordsFoundText = By.xpath("//div[contains(@class,'oxd-autocomplete-option') and normalize-space()='No Records Found']");
    
    // Updated Leave Balance Locator based on UI DOM Structure
    private By leaveBalanceText =
            By.xpath("//p[contains(normalize-space(),'Day(s)')]");
    
    private By fromDateInput = By.xpath("(//label[text()='From Date']/following::input[1])");
    private By toDateInput = By.xpath("(//label[text()='To Date']/following::input[1])");
    private By commentsTextArea = By.xpath("//label[text()='Comments']/following::textarea[1]");
    
    // Buttons & Dialogs
    private By assignBtn = By.xpath("//button[@type='submit']");
    private By confirmOkBtn = By.xpath("//button[normalize-space()='Ok'] | //button[normalize-space()='Confirm']");
    private By successToast = By.xpath(
    	    "//div[contains(@class,'oxd-toast')]//*[contains(normalize-space(),'Success')]"
    		);
    private By requiredFieldError = By.xpath("//span[contains(@class,'oxd-input-field-error-message')]");
    private By formSpinner = By.xpath("//div[contains(@class,'oxd-form-loader')] | //div[contains(@class,'oxd-loading-spinner')]");
 // Locators for confirmation modal when balance is insufficient
    
    private By confirmModalOkBtn = By.xpath("//button[normalize-space()='Ok'] | //button[contains(@class,'oxd-button--secondary') and normalize-space()='Ok']");
    private By insufficientBalanceWarning = By.xpath("//p[contains(@class,'oxd-text') and contains(text(),'Balance not sufficient')]");
   
    public AssignLeavePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void waitForSpinnerToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(formSpinner));
        } catch (Exception ignored) {}
    }

    public void navigateToAssignLeave() {
        wait.until(ExpectedConditions.elementToBeClickable(leaveMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(assignLeaveTab)).click();
        waitForSpinnerToDisappear();
    }

//    public void selectEmployee(String nameHint) {
//        waitForSpinnerToDisappear();
//        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
//        input.click();
//        input.sendKeys(Keys.chord(Keys.CONTROL, "d"), Keys.BACK_SPACE);
//        input.sendKeys(nameHint);
//
//        // Wait for autocomplete container to be visible and select the first matching option
//        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeAutoCompleteOption));
//        option.click();
//    }
//
//    public void selectEmployee(String nameHint) {
//
//        waitForSpinnerToDisappear();
//
//        WebElement input = wait.until(
//            ExpectedConditions.elementToBeClickable(employeeNameInput)
//        );
//
//        input.click();
//
//        // Clear existing value
//        input.sendKeys(Keys.CONTROL, "a");
//        input.sendKeys(Keys.BACK_SPACE);
//
//        // Enter employee name
//        input.sendKeys(nameHint);
//
//        /*
//         * Wait for autocomplete options.
//         */
//        wait.until(
//            ExpectedConditions.visibilityOfElementLocated(
//                employeeAutoCompleteOptions
//            )
//        );
//
//        /*
//         * Find the exact/matching employee.
//         *
//         * Example:
//         * Daniel Martin Lesir
//         */
//        By employeeOption = By.xpath(
//            "//div[@role='option']" +
//            "[contains(normalize-space(),'" + nameHint + "')]"
//        );
//
//        WebElement option = wait.until(
//            ExpectedConditions.elementToBeClickable(employeeOption)
//        );
//
//        option.click();
//
//        /*
//         * Verify that the invalid message disappears.
//         */
//        try {
//
//            wait.until(
//                ExpectedConditions.invisibilityOfElementLocated(
//                    By.xpath("//span[normalize-space()='Invalid']")
//                )
//            );
//
//        } catch (Exception ignored) {
//            // Invalid message may not exist
//        }
//    }
    public void selectEmployee(String primaryName) {
        waitForSpinnerToDisappear();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        
        typeAndSearch(input, primaryName);

        // Check if "No Records Found" is displayed
        if (!driver.findElements(noRecordsFoundText).isEmpty()) {
            System.out.println("Employee '" + primaryName + "' not found. Falling back to default letter 'a'...");
            typeAndSearch(input, "a");
        }

        // Wait for listbox options and click the first valid option
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(employeeAutoCompleteOptions));
        option.click();
    }

    private void typeAndSearch(WebElement input, String query) {
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        input.sendKeys(query);
        try {
            Thread.sleep(1200); // Allow autocomplete network call to return options
        } catch (InterruptedException ignored) {}
    }

    public String getEmployeeInputValue() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        return input.getAttribute("value");
    }
    public void selectLeaveType(String leaveType) {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(leaveTypeDropdown)).click();
        By optionLocator = By.xpath("//div[@role='option']//span[text()='" + leaveType + "']");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }

    public void setDates(String fromDate, String toDate) {
        waitForSpinnerToDisappear();
        WebElement fromInput = wait.until(ExpectedConditions.visibilityOfElementLocated(fromDateInput));
        fromInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        fromInput.sendKeys(fromDate);

        WebElement toInput = wait.until(ExpectedConditions.visibilityOfElementLocated(toDateInput));
        toInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        toInput.sendKeys(toDate);
    }

    public void enterComments(String comment) {
        WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(commentsTextArea));
        textarea.clear();
        textarea.sendKeys(comment);
    }
    public void clickAssign() {
        wait.until(ExpectedConditions.elementToBeClickable(assignBtn)).click();
        
        // Handle confirmation modal if balance is low or date overlaps
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement confirmBtn = shortWait.until(ExpectedConditions.elementToBeClickable(confirmOkBtn));
            confirmBtn.click();
        } catch (Exception ignored) {}
    }
    public String getLeaveBalanceText() {
        waitForSpinnerToDisappear();
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(leaveBalanceText)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isBalanceInsufficient() {
        String balance = getLeaveBalanceText();
        return balance.toLowerCase().contains("not sufficient") || balance.startsWith("0.00");
    }

    public void clickAssignAndConfirmIfPrompted() {
        wait.until(ExpectedConditions.elementToBeClickable(assignBtn)).click();
        
        // Handle "Balance not sufficient" confirmation modal if it pops up
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement confirmBtn = shortWait.until(ExpectedConditions.elementToBeClickable(confirmModalOkBtn));
            confirmBtn.click();
        } catch (Exception ignored) {
            // Modal did not appear, standard assignment proceeded
        }
    }
    public boolean isSuccessToastDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successToast)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessToastMessage() {

        try {

            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    successToast
                )
            ).getText();

        } catch (Exception e) {

            return "";
        }
    }
    public boolean isRequiredErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}