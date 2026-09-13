package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VacancyPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators - Menu & Tabs
    private By recruitmentMenu = By.xpath("//span[text()='Recruitment']");
    private By vacanciesTab = By.xpath("//a[text()='Vacancies']");

    // Locators - Filter Search Controls
    private By jobTitleDropdown = By.xpath("//label[text()='Job Title']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By vacancyDropdown = By.xpath("//label[text()='Vacancy']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By hiringManagerDropdown = By.xpath("//label[text()='Hiring Manager']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By statusDropdown = By.xpath("//label[text()='Status']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    
    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By resetButton = By.xpath("//button[normalize-space()='Reset']");
    private By addButton = By.xpath("//button[normalize-space()='+ Add'] | //button[contains(.,'Add')]");

    // Locators - Table Grid & Pagination
    private By recordsFound = By.xpath("//span[contains(.,'Record Found') or contains(.,'Records Found')]");
    private By tableRows = By.xpath(
    	    "//div[contains(@class,'oxd-table-body')]//div[@role='row']"
    		);
    private By nextButton = By.xpath("//button[.//i[contains(@class,'bi-chevron-right')]]");
    private By noRecordsFoundText = By.xpath("//span[text()='No Records Found'] | //*[contains(text(),'No Records Found')]");

    // Locators - Checkboxes & Action Buttons
    private By headerCheckbox = By.xpath("//div[@class='oxd-table-header']//input[@type='checkbox']/following-sibling::span");
    private By rowCheckboxes = By.xpath("//div[@class='oxd-table-body']//input[@type='checkbox']");
    private By firstRowEditBtn = By.xpath("(//div[@class='oxd-table-body']//button[i[contains(@class,'bi-pencil-fill') or contains(@class,'bi-pencil')]])[1]");
    private By firstRowDeleteBtn = By.xpath("(//div[@class='oxd-table-body']//button[i[contains(@class,'bi-trash-fill') or contains(@class,'bi-trash')]])[1]");
    private By confirmDeleteModalBtn = By.xpath("//button[normalize-space()='Yes, Delete']");
    private By cancelDeleteModalBtn = By.xpath("//button[normalize-space()='No, Cancel']");

    // Locators - Add Vacancy Form
    private By vacancyNameInput = By.xpath("//label[text()='Vacancy Name']/following::input[1]");
    private By jobTitleFormDropdown = By.xpath("//label[text()='Job Title']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By descriptionInput = By.xpath("//label[text()='Description']/following::textarea[1]");
    private By hiringManagerInput = By.xpath("//label[text()='Hiring Manager']/following::input[1]");
    private By managerAutoCompleteOptions = By.xpath("//div[@role='listbox']//div[@role='option'] | //div[contains(@class,'oxd-autocomplete-option')]");
    private By firstJobTitleOption = By.xpath("//div[@role='option'][2]");
    private By firstManagerOption = By.xpath("//div[@role='option'][1]");
    private By numberOfPositionsInput = By.xpath("//label[text()='Number of Positions']/following::input[1]");
 // Validation Error Message Locator
    private By vacancyNameErrorMsg = By.xpath("//label[text()='Vacancy Name']/ancestor::div[contains(@class,'oxd-input-field')]//span[contains(@class,'oxd-input-field-error-message')]");
    private By activeToggle = By.xpath("//label[text()='Active']/following::span[contains(@class,'oxd-switch-input')][1]");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By cancelButton = By.xpath("//button[normalize-space()='Cancel']");
    private By requiredFieldError = By.xpath("//span[contains(@class,'oxd-input-field-error-message')]");
    private By formSpinner = By.xpath("//div[contains(@class,'oxd-form-loader')] | //div[contains(@class,'oxd-loading-spinner')]");

    public VacancyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToVacancies() {
        wait.until(ExpectedConditions.elementToBeClickable(recruitmentMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(vacanciesTab)).click();
    }

    private void waitForSpinnerToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(formSpinner));
        } catch (Exception ignored) {}
    }

    public void selectJobTitle(String title) {
        waitForSpinnerToDisappear();
        // Select Job Title
        wait.until(ExpectedConditions.elementToBeClickable(jobTitleDropdown)).click();
        By option = By.xpath("//div[@role='option']//span[text()='" + title + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void selectStatus(String status) {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)).click();
        By option = By.xpath("//div[@role='option']//span[text()='" + status + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

 
    public String getSelectedJobTitleText() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitleDropdown));
        return input.getText();
    }
    
    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void clickReset() {
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
    }

    public void clickAddVacancy() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void clickSaveWithoutData() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }
    
    public void selectHiringManager(String nameHint) {
        waitForSpinnerToDisappear();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(hiringManagerInput));
        input.clear();
        input.sendKeys(nameHint);

        // Match option in autocomplete dropdown
        By autocompleteOption = By.xpath("//div[@role='option']//span[contains(text(),'" + nameHint.split(" ")[0] + "')]");

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement option = shortWait.until(ExpectedConditions.elementToBeClickable(autocompleteOption));
            option.click();
        } catch (Exception e) {
            System.out.println("Hiring Manager '" + nameHint + "' option was not clickable in dropdown.");
        }
    }

    public void addVacancy(String vacancyName, String jobTitle, String managerHint, String positions) {
    																																																																																																																																																			waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(vacancyNameInput)).sendKeys(vacancyName);
        
        // Select Job Title
        wait.until(ExpectedConditions.elementToBeClickable(jobTitleFormDropdown)).click();
        By option = By.xpath("//div[@role='option']//span[text()='" + jobTitle + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();

        // Enter Hiring Manager autocomplete
        selectHiringManager(managerHint);
        
     // 4. Enter Number of Positions (if provided)
        if (positions != null && !positions.isEmpty()) {
            WebElement posField = driver.findElement(numberOfPositionsInput);
            posField.clear();
            posField.sendKeys(positions);
        }

        // 5. Click Save Button & Wait for saving process
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        waitForSpinnerToDisappear();

    }
    
    public void enterVacancyName(String vacancyName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(vacancyNameInput));
        input.clear();
        input.sendKeys(vacancyName);
    }

    public void selectJobTitleFirstOption() {
        wait.until(ExpectedConditions.elementToBeClickable(jobTitleDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(firstJobTitleOption)).click();
    }

    public void enterHiringManager(String managerPrefix) {
        WebElement managerField = wait.until(ExpectedConditions.visibilityOfElementLocated(hiringManagerInput));
        managerField.sendKeys(managerPrefix);
        wait.until(ExpectedConditions.elementToBeClickable(firstManagerOption)).click();
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public String getVacancyNameValidationError() {
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(vacancyNameErrorMsg));
        return errorElement.getText().trim();
    }
    public boolean isRequiredErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldError)).isDisplayed();
    }

    public boolean isNoRecordsFoundDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noRecordsFoundText)).isDisplayed();
    }
    
    public boolean isRecordsCountDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(recordsFound)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickEditFirstVacancy() {
        wait.until(ExpectedConditions.elementToBeClickable(firstRowEditBtn)).click();
        waitForSpinnerToDisappear();
    }

    public void clickDeleteFirstVacancy() {
        wait.until(ExpectedConditions.elementToBeClickable(firstRowDeleteBtn)).click();
    }

    public void confirmDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteModalBtn)).click();
    }

    public void cancelDelete() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelDeleteModalBtn)).click();
    }

    public void selectAllHeaderCheckbox() {
        wait.until(ExpectedConditions.elementToBeClickable(headerCheckbox)).click();
    }

    public boolean areAllRowCheckboxesSelected() {
        List<WebElement> checkboxes = driver.findElements(rowCheckboxes);
        return !checkboxes.isEmpty() && checkboxes.stream().allMatch(WebElement::isSelected);
    }

    // Extract numerical count from "(7) Records Found"
    public int getRecordsFoundCount() {
        WebElement recordsElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(recordsFound)
        );

        String text = recordsElement.getText();
        System.out.println("Records text: " + text);

        String number = text.replaceAll("[^0-9]", "");
        return Integer.parseInt(number);
    }

    // Traverse pagination pages and count total table rows
    public int getTotalListedRowsAcrossPages() {
        int totalRows = 0;
        int expectedRecordCount = getRecordsFoundCount();

        while (totalRows < expectedRecordCount) {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
            List<WebElement> rows = driver.findElements(tableRows);

            int currentPageRows = rows.size();
            System.out.println("Rows on current page: " + currentPageRows);

            totalRows += currentPageRows;
            System.out.println("Total rows counted: " + totalRows);

            if (totalRows >= expectedRecordCount) {
                System.out.println("All records counted.");
                break;
            }

            WebElement oldFirstRow = rows.get(0);
            WebElement next = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
            next.click();

            wait.until(ExpectedConditions.stalenessOf(oldFirstRow));
        }

        System.out.println("Expected records: " + expectedRecordCount);
        System.out.println("Total rows across pages: " + totalRows);

        return totalRows;
    }
}