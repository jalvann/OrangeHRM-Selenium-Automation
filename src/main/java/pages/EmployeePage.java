package pages;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators - Employee List Page
    private By pimMenu = By.xpath("//span[text()='PIM']");
    private By employeeIdSearchInput = By.xpath("//label[text()='Employee Id']/following::input[1]");
    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By resetButton = By.xpath("//button[normalize-space()='Reset']");
    private By addEmployeeBtn = By.xpath("//button[normalize-space()='+ Add'] | //a[text()='Add Employee']");
   private By recordsFoundText = By.xpath("//span[contains(.,'Records Found')]");
    private By employmentStatusDropdown = By.xpath("//label[text()='Employment Status']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By jobTitleDropdown = By.xpath("//label[text()='Job Title']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By subUnitDropdown = By.xpath("//label[text()='Sub Unit']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By noRecordsFoundText = By.xpath("//span[text()='No Records Found']");
    private By firstRowEditBtn = By.xpath("(//div[@class='oxd-table-body']//button[i[contains(@class,'bi-pencil-fill')]])[1]");
    private By firstRowDeleteBtn = By.xpath("(//div[@class='oxd-table-body']//button[i[contains(@class,'bi-trash')]])[1]");
    private By confirmDeleteModalBtn = By.xpath("//button[normalize-space()='Yes, Delete']");
    
    // Locators - Add Employee Form
    private By firstNameInput = By.name("firstName");
    private By middleNameInput = By.name("middleName");
    private By lastNameInput = By.name("lastName");
    private By employeeIdFormInput = By.xpath("//label[text()='Employee Id']/following::input[1]");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By cancelButton = By.xpath("//button[normalize-space()='Cancel']");
    private By successToast = By.xpath("//div[contains(@class,'oxd-toast')] | //p[contains(@class,'oxd-text--toast-message')] | //*[contains(text(),'Success')]");
    private By requiredFieldError = By.xpath("//span[contains(@class,'oxd-input-field-error-message')][text()='Required']");
 
    // Create Login Details Toggle & Fields
    private By createLoginToggle = By.xpath("//span[contains(@class,'oxd-switch-input')]");
    private By usernameInput = By.xpath("//label[text()='Username']/following::input[1]");
    private By statusEnabledRadio = By.xpath("//label[text()='Status']/following::label[contains(.,'Enabled')]");
    private By statusDisabledRadio = By.xpath("//label[text()='Status']/following::label[contains(.,'Disabled')]");
    private By passwordInput = By.xpath("//label[text()='Password']/following::input[1]");
    private By confirmPasswordInput = By.xpath("//label[text()='Confirm Password']/following::input[1]");
 
    // Dropdowns
    private By nationalityDropdown = By.xpath("//label[text()='Nationality']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By maritalStatusDropdown = By.xpath("//label[text()='Marital Status']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    
    // Gender Radio Buttons
    private By maleRadioBtn = By.xpath("//label[text()='Gender']/following::label[contains(.,'Male')]");
    private By femaleRadioBtn = By.xpath("//label[text()='Gender']/following::label[contains(.,'Female')]");

    // Save Buttons (Personal Details vs Custom Fields)
    private By personalDetailsSaveBtn = By.xpath("//h6[text()='Personal Details']/following::button[normalize-space()='Save'][1]");
 
    // Add Attachment Form Controls
    private By addAttachmentBtn = By.xpath("//h6[text()='Attachments']/following::button[normalize-space()='Add'][1]");
    private By fileInput = By.xpath("//input[@type='file']");
    private By commentTextArea = By.xpath("//h6[text()='Add Attachment']/following::textarea[1]");
    private By saveAttachmentBtn = By.xpath("//h6[text()='Add Attachment']/following::button[normalize-space()='Save'][1]");
    private By cancelAttachmentBtn = By.xpath("//h6[text()='Add Attachment']/following::button[normalize-space()='Cancel'][1]");
    private By attachmentTableRow = By.xpath("//div[contains(@class,'oxd-table-card')]");

    
 // Loading overlay spinners
    private By formSpinner = By.xpath("//div[contains(@class,'oxd-form-loader')] | //div[contains(@class,'oxd-loading-spinner')]");
   
    // Records Found text
    private By recordsFound = By.xpath("//span[contains(normalize-space(), 'Records Found')]");

    // Employee table rows
   private By tableRows = By.xpath("//div[contains(@class,'oxd-table-body')]//div[contains(@class,'oxd-table-row')]");

    // Next page button
    private By nextButton = By.xpath(
    	    "//button[.//i[contains(@class,'bi-chevron-right')]]"
    	);
 // Field-level validation error locator under First Name
    private By firstNameErrorMsg = By.xpath("//input[@name='firstName']/following::span[contains(@class,'oxd-input-field-error-message')][1]");
    
    // Toast notification or form header validation message for duplicates
    private By duplicateWarningMsg = By.xpath("//div[contains(@class,'oxd-toast-content')]//p[2] | //span[contains(@class,'oxd-input-field-error-message')]");
    
    public EmployeePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToPIM() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenu)).click();
    }

    public void clickAddEmployee() {
    	waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeBtn)).click();
    }
    private void waitForSpinnerToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(formSpinner));
        } catch (Exception ignored) {}
    }
    public void searchByEmployeeId(String empId) {
        WebElement empIdInput = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeIdSearchInput));
        empIdInput.clear();
        empIdInput.sendKeys(empId);
        driver.findElement(searchButton).click();
    }

    public void clickReset() {
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
    }

    public void addEmployee(String firstName, String middleName, String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
        driver.findElement(middleNameInput).sendKeys(middleName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(saveButton).click();
    }

    public void clickSaveWithoutData() {
    	waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }
    public void selectNationalityAndMaritalStatus(String nationality, String maritalStatus) {
    	waitForSpinnerToDisappear();
        // Select Nationality
        wait.until(ExpectedConditions.elementToBeClickable(nationalityDropdown)).click();
        By nationalityOption = By.xpath("//div[@role='option']//span[text()='" + nationality + "']");
        wait.until(ExpectedConditions.elementToBeClickable(nationalityOption)).click();

        // Select Marital Status
        wait.until(ExpectedConditions.elementToBeClickable(maritalStatusDropdown)).click();
        By maritalOption = By.xpath("//div[@role='option']//span[text()='" + maritalStatus + "']");
        wait.until(ExpectedConditions.elementToBeClickable(maritalOption)).click();

        wait.until(ExpectedConditions.elementToBeClickable(personalDetailsSaveBtn)).click();
    }

    public void selectGender(boolean isMale) {
    	waitForSpinnerToDisappear();
        if (isMale) {
            wait.until(ExpectedConditions.elementToBeClickable(maleRadioBtn)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(femaleRadioBtn)).click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(personalDetailsSaveBtn)).click();
    }

    public boolean isSuccessToastDisplayed() {
        try {
            WebElement toast = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[contains(@class,'oxd-text--toast-message')]")
                )
            );

            System.out.println("TOAST = " + toast.getText());

            return true;

        } catch (Exception e) {
            System.out.println("SUCCESS TOAST NOT FOUND");
            return false;
        }
    }
    public boolean isRequiredErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldError)).isDisplayed();
    }

    public boolean isRecordsCountDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(recordsFoundText)).isDisplayed();
    }
    public void filterByJobTitle(String title) {
        wait.until(ExpectedConditions.elementToBeClickable(jobTitleDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@role='option']//span[text()='" + title + "']"))).click();
        driver.findElement(searchButton).click();
    }

    public void clickEditFirstEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(firstRowEditBtn)).click();
        waitForSpinnerToDisappear();
    }

    public void deleteFirstEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(firstRowDeleteBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteModalBtn)).click();
    }

    public boolean isNoRecordsFoundDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noRecordsFoundText)).isDisplayed();
    }
    public void toggleCreateLoginDetails() {
        wait.until(ExpectedConditions.elementToBeClickable(createLoginToggle)).click();
    }

    public void addEmployeeWithLoginDetails(String fName, String lName, String username, String password, boolean enableStatus) {
        // Basic Info
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(fName);
        driver.findElement(lastNameInput).sendKeys(lName);

        // Enable Toggle
        toggleCreateLoginDetails();

        // Login Credentials
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(username);

        if (enableStatus) {
            driver.findElement(statusEnabledRadio).click();
        } else {
            driver.findElement(statusDisabledRadio).click();
        }

        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(confirmPasswordInput).sendKeys(password);

        // Submit
        driver.findElement(saveButton).click();
    }
    public String getFirstNameValidationError() {
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameErrorMsg));
        return errorElement.getText().trim();
    }

    public String getDuplicateWarningMessage() {
        WebElement warningElement = wait.until(ExpectedConditions.visibilityOfElementLocated(duplicateWarningMsg));
        return warningElement.getText().trim();
    }
    public boolean isUsernameFieldVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).isDisplayed();
    }
    public void clickAddAttachment() {
    	waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(addAttachmentBtn)).click();
    }
    public void uploadAttachment(String absoluteFilePath, String comment) {

        waitForSpinnerToDisappear();

        WebElement input = wait.until(
            ExpectedConditions.presenceOfElementLocated(fileInput)
        );

        input.sendKeys(absoluteFilePath);

        if (comment != null && !comment.isEmpty()) {

            WebElement commentBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(commentTextArea)
            );

            commentBox.clear();
            commentBox.sendKeys(comment);
        }
        wait.until(
            ExpectedConditions.elementToBeClickable(saveAttachmentBtn)
        ).click();

    }
    
  
    public boolean isAttachmentPresent(String textToVerify) {
        waitForSpinnerToDisappear();
        try {
            By rowLocator = By.xpath("//div[contains(@class,'oxd-table-card')]//div[contains(text(), '" + textToVerify + "')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(rowLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    // Get number from "(123) Records Found"
    public int getRecordsFoundCount() {

        WebElement recordsElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(recordsFound)
        );

        String text = recordsElement.getText();

        System.out.println("Records text: " + text);

        // Example: "(128) Records Found"
        String number = text.replaceAll("[^0-9]", "");

        return Integer.parseInt(number);
    }
    
 // Count rows on all pages
    public int getTotalListedRowsAcrossPages() {

    int totalRows = 0;

    // Get total records from "(108) Records Found"
    int expectedRecordCount = getRecordsFoundCount();

    while (totalRows < expectedRecordCount) {

        // Wait for rows on current page
        wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows)
        );

        // Get current page rows
        List<WebElement> rows =
                driver.findElements(tableRows);

        int currentPageRows = rows.size();

        System.out.println(
            "Rows on current page: " + currentPageRows
        );

        // Add current page rows
        totalRows += currentPageRows;

        System.out.println(
            "Total rows counted: " + totalRows
        );

        // STOP when all records have been counted
        if (totalRows >= expectedRecordCount) {
            System.out.println("All records counted.");
            break;
        }

        // Save first row before clicking Next
        WebElement oldFirstRow = rows.get(0);

        // Click Next
        WebElement next = wait.until(
            ExpectedConditions.elementToBeClickable(nextButton)
        );

        next.click();

        // Wait for page to change
        wait.until(
            ExpectedConditions.stalenessOf(oldFirstRow)
        );
    }

    System.out.println(
        "Expected records: " + expectedRecordCount
    );

    System.out.println(
        "Total rows across pages: " + totalRows
    );

    return totalRows;
}
}
