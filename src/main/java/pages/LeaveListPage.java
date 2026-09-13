package pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LeaveListPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators - Leave List Navigation & Filter Controls
    private By leaveMenu = By.xpath("//span[text()='Leave']");
    private By leaveListTab = By.xpath("//a[text()='Leave List']");
    private By fromDateInput = By.xpath("(//input[@placeholder='yyyy-dd-mm' or @placeholder='yyyy-mm-dd'])[1]");
    private By toDateInput = By.xpath("(//input[@placeholder='yyyy-dd-mm' or @placeholder='yyyy-mm-dd'])[2]");
    private By statusDropdown = By.xpath(
    	    "//label[contains(normalize-space(),'Show Leave with Status')]/following::i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'][1]"
    	); 
    private By leaveTypeDropdown = By.xpath("//label[text()='Leave Type']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By subUnitDropdown = By.xpath("//label[text()='Sub Unit']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By employeeNameInput = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private By includePastEmployeesToggle = By.xpath("//span[contains(@class,'oxd-switch-input')]");

    // Locators - Form Action Buttons
    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By resetButton = By.xpath("//button[normalize-space()='Reset']");
    private By statusChipDismiss = By.xpath("//span[contains(@class,'oxd-chip')]//i");
    
    // Locators - Data Grid & Messages
    private By recordsFoundText = By.xpath("//span[contains(.,'Record Found') or contains(.,'Records Found')]");
    private By noRecordsFoundText = By.xpath("//span[text()='No Records Found'] | //p[text()='No Records Found']");
    private By successToast = By.xpath("//p[contains(@class,'oxd-text--toast-message')] | //div[contains(@class,'oxd-toast')]");
    private By tableRows = By.xpath("//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-card')]");
    
    // Locators - Grid Context Action Menu ( 3-dots locator)
    private By firstRowActionMenuBtn = By.xpath("(//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-card')])[1]//button[i[contains(@class,'bi-three-dots')]] | (//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-card')])[1]//i[contains(@class,'bi-three-dots')]/ancestor::button");
    private By addCommentOption = By.xpath("//p[contains(.,'Add Comment')]");
    private By viewLeaveDetailsOption = By.xpath("//p[contains(.,'View Leave Details')]");
    private By viewPimInfoOption = By.xpath("//p[contains(.,'View PIM Info')]");
    private By cancelLeaveOption = By.xpath("//p[contains(.,'Cancel Leave')]");
    
    // Locators - Comment Modal Dialog
    private By commentTextArea = By.xpath("//textarea[contains(@class,'oxd-textarea')]");
    private By saveCommentBtn = By.xpath("//button[normalize-space()='Save']");
    
    // Loading Spinner
    private By formSpinner = By.xpath("//div[contains(@class,'oxd-form-loader')] | //div[contains(@class,'oxd-loading-spinner')]");

    public LeaveListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void waitForSpinnerToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(formSpinner));
        } catch (Exception ignored) {}
    }

    public void navigateToLeaveList() {
        wait.until(ExpectedConditions.elementToBeClickable(leaveMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(leaveListTab)).click();
        waitForSpinnerToDisappear();
    }

    public void searchByEmployeeName(String name) {
        waitForSpinnerToDisappear();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        input.clear();
        input.sendKeys(name);
        clickSearch();
    }

    public void filterBySubUnit(String subUnitName) {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(subUnitDropdown)).click();
        By optionLocator = By.xpath("//div[@role='option']//span[text()='" + subUnitName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
        clickSearch();
    }

    public void removeStatusChip() {
        waitForSpinnerToDisappear();
        try {
            WebElement chip = wait.until(ExpectedConditions.elementToBeClickable(statusChipDismiss));
            chip.click();
        } catch (Exception e) {
            System.out.println("No status chip found to dismiss.");
        }
    }

    public void toggleIncludePastEmployees() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(includePastEmployeesToggle)).click();
    }
    
    public void selectLeaveStatus(String statusName) {
        waitForSpinnerToDisappear();
        // Click status dropdown to open options
        wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)).click();

        // Dynamic XPath for selecting the requested option (e.g., "Scheduled")
        By optionXpath = By.xpath(
                "//div[@role='option']//span[normalize-space()='" + statusName + "']"
            );
        wait.until(ExpectedConditions.elementToBeClickable(optionXpath)).click();
    }
    
    public void clickSearch() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        waitForSpinnerToDisappear();
    }

    public void clickReset() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
        waitForSpinnerToDisappear();
    }

    // --- Action Context Menu Handlers ---
    public boolean hasRecordsInGrid() {
        waitForSpinnerToDisappear();
        try {
            List<WebElement> rows = driver.findElements(tableRows);
            return !rows.isEmpty() && rows.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void openFirstRowContextMenu() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(firstRowActionMenuBtn)).click();
    }

    public void addCommentToFirstRecord(String commentText) {
        openFirstRowContextMenu();
        wait.until(ExpectedConditions.elementToBeClickable(addCommentOption)).click();
        
        WebElement textArea = wait.until(ExpectedConditions.visibilityOfElementLocated(commentTextArea));
        textArea.clear();
        textArea.sendKeys(commentText);
        wait.until(ExpectedConditions.elementToBeClickable(saveCommentBtn)).click();
    }

    public void clickLeaveDetails() {
        openFirstRowContextMenu();
        wait.until(ExpectedConditions.elementToBeClickable(viewLeaveDetailsOption)).click();
    }

    public void clickPimInfo() {
        openFirstRowContextMenu();
        wait.until(ExpectedConditions.elementToBeClickable(viewPimInfoOption)).click();
    }

    public void cancelLeaveRecord() {
        openFirstRowContextMenu();
        wait.until(ExpectedConditions.elementToBeClickable(cancelLeaveOption)).click();
    }

    // --- Assertions & Verifications ---
    public boolean isSuccessToastDisplayed() {
        try {
            WebElement toast = wait.until(
                ExpectedConditions.visibilityOfElementLocated(successToast)
            );
            System.out.println("TOAST MESSAGE = " + toast.getText());
            return true;
        } catch (Exception e) {
            System.out.println("SUCCESS TOAST NOT FOUND");
            return false;
        }
    }

    public boolean isNoRecordsFoundDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(noRecordsFoundText)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRecordsCountDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(recordsFoundText)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmployeeNameValue() {
        waitForSpinnerToDisappear();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput)).getAttribute("value");
    }
}