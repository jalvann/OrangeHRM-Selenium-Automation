package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReportPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators - Reports Search & List
    private By pimMenu = By.xpath("//span[text()='PIM']");
    private By reportsTab = By.xpath("//a[text()='Reports']");
    private By reportNameSearchInput = By.xpath("//label[text()='Report Name']/following::input[1]");
    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By resetButton = By.xpath("//button[normalize-space()='Reset']");
    private By addReportBtn = By.xpath("//button[normalize-space()='Add']");
    private By recordsFoundText = By.xpath("//span[contains(.,'Records Found')]");

    // Locators - Add Report Form
    private By reportNameInput = By.xpath("//label[text()='Report Name']/following::input[1]");
    private By displayFieldGroupDropdown = By.xpath("//label[text()='Select Display Field Group']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By displayFieldDropdown = By.xpath("//label[text()='Select Display Field']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By addDisplayFieldBtn = By.xpath("//label[text()='Select Display Field']/following::button[1]");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By cancelButton = By.xpath("//button[normalize-space()='Cancel']");
    
    // Locators - Grid Row Actions
    private By firstRowRunBtn = By.xpath("(//div[@class='oxd-table-body']//button[i[contains(@class,'bi-file-text-fill')]])[1]");
    private By firstRowEditBtn = By.xpath("(//div[@class='oxd-table-body']//button[i[contains(@class,'bi-pencil-fill')]])[1]");
    private By firstRowDeleteBtn = By.xpath("(//div[@class='oxd-table-body']//button[i[contains(@class,'bi-trash')]])[1]");
    private By confirmDeleteModalBtn = By.xpath("//button[normalize-space()='Yes, Delete']");
    
 // Locators - Alerts & Headers
    private By requiredErrorMsg = By.xpath("//span[contains(@class,'oxd-input-field-error-message')][text()='Required']");
    private By reportHeaderTitle = By.xpath("//h6[contains(@class,'orangehrm-main-title')]");
    private By successToast = By.xpath("//div[contains(@class,'oxd-toast-container')]//p[contains(@class,'oxd-text--toast-message')] | //div[contains(@class,'oxd-toast--success')]");
    private By loadingSpinner = By.xpath("//div[contains(@class,'oxd-loading-spinner')]");
    public ReportPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

   
    private void safeClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    public void navigateToReports() {
        safeClick(pimMenu);
        safeClick(reportsTab);
    }

    public void clickAddReport() {
        safeClick(addReportBtn);
    }

    public void searchReport(String reportName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(reportNameSearchInput));
        input.clear();
        input.sendKeys(reportName);
        
        // Select matching option from autocomplete popup if visible
        By dropdownOption = By.xpath("//div[@role='listbox']//div[@role='option']//span");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(dropdownOption)).click();
        } catch (Exception ignored) {}
        
        safeClick(searchButton);
    }

    public void clickReset() {
        safeClick(resetButton);
    }

    public void createReport(String reportName, String fieldGroup, String displayField) {
      //  waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(reportNameInput)).sendKeys(reportName);

        // Select Display Field Group
        safeClick(displayFieldGroupDropdown);
        safeClick(By.xpath("//div[@role='option']//span[text()='" + fieldGroup + "']"));

        // Select Display Field
        safeClick(displayFieldDropdown);
        safeClick(By.xpath("//div[@role='option']//span[text()='" + displayField + "']"));

        // Click Add Field (+) Button
        safeClick(addDisplayFieldBtn);

        // Save Form
        safeClick(saveButton);
    }
    public void addDisplayField(String fieldGroup, String displayField) {
        // Select Display Field Group dropdown option
        safeClick(displayFieldGroupDropdown);
        safeClick(By.xpath("//div[@role='option']//span[text()='" + fieldGroup + "']"));

        // Select Display Field dropdown option
        safeClick(displayFieldDropdown);
        safeClick(By.xpath("//div[@role='option']//span[text()='" + displayField + "']"));

        // Click the '+' button to attach the field
        safeClick(addDisplayFieldBtn);
    }
    public void createReportWithMultipleFields(String reportName, String fieldGroup, String field1, String field2) {
 
        wait.until(ExpectedConditions.visibilityOfElementLocated(reportNameInput)).sendKeys(reportName);

        // Add first field
        addDisplayField(fieldGroup, field1);
        
        // Add second field
        addDisplayField(fieldGroup, field2);

        safeClick(saveButton);
    }
    
    public void clickSaveWithoutData() {
        safeClick(saveButton);
    }

    public void clickCancel() {
        safeClick(cancelButton);
    }
    
    public void runFirstReport() {
        safeClick(firstRowRunBtn);
    }

    public void editFirstReport() {
        safeClick(firstRowEditBtn);
    }

    public void deleteFirstReport() {
        safeClick(firstRowDeleteBtn);
        safeClick(confirmDeleteModalBtn);
    }
    public boolean isRequiredErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(requiredErrorMsg)).isDisplayed();
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
    public void typeReportName(String reportName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(reportNameInput));
        input.clear();
        input.sendKeys(reportName);
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        // Wait for table to reload after search click
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        } catch (Exception ignored) {}
    }

    public boolean isReportPresentInTable(String reportName) {
        By reportLocator = By.xpath("//div[@role='row']//div[normalize-space(text())='" + reportName + "']");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(reportLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isRecordsCountDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(recordsFoundText)).isDisplayed();
    }
    
    public boolean isReportHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(reportHeaderTitle)).isDisplayed();
    }
}