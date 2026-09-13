package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RecruitmentCandidatesPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Navigation Locators
    private By recruitmentMenu = By.xpath("//span[text()='Recruitment']");
    private By candidatesTab = By.xpath("//a[text()='Candidates']");

    // Filter Locators
    private By jobTitleDropdown = By.xpath("//label[text()='Job Title']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By vacancyDropdown = By.xpath("//label[text()='Vacancy']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By candidateNameInput = By.xpath("//label[text()='Candidate Name']/following::input[1]");
    private By statusDropdown = By.xpath("//label[text()='Status']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By candidateAutoCompleteOption = By.xpath("//div[@role='listbox']//div[@role='option'] | //div[contains(@class,'oxd-autocomplete-option')]");

    // Action Locators
    private By searchBtn = By.xpath("//button[normalize-space()='Search']");
    private By resetBtn = By.xpath("//button[normalize-space()='Reset']");
    private By addCandidateBtn = By.xpath("//button[normalize-space()='+ Add'] | //button[contains(.,'Add')]");
    
    // Result Table Locators
    private By recordCountText = By.xpath("//span[contains(@class,'oxd-text')] [contains(.,'Record') or contains(.,'Found')]");
    private By tableRows = By.xpath("//div[contains(@class,'oxd-table-card')]");
    private By spinner = By.xpath("//div[contains(@class,'oxd-loading-spinner')] | //div[contains(@class,'oxd-form-loader')]");
 // --- Action Button Locators ---
    private By viewIcon = By.xpath("//div[contains(@class,'oxd-table-card')][1]//button[contains(@class,'oxd-icon-button')][.//*[contains(@class,'bi-eye')]]");
    private By deleteIcon = By.xpath("//div[contains(@class,'oxd-table-card')][1]//button[contains(@class,'oxd-icon-button')][.//*[contains(@class,'bi-trash')]]");
    private By downloadIcon = By.xpath("//div[contains(@class,'oxd-table-card')][1]//a[contains(@class,'oxd-icon-button')][.//*[contains(@class,'bi-download')]] | //div[contains(@class,'oxd-table-card')][1]//button[contains(@class,'oxd-icon-button')][.//*[contains(@class,'bi-download')]]");
   private By downloadIconInRow = By.xpath(".//*[contains(@class,'bi-download')]/ancestor::button | .//*[contains(@class,'bi-download')]/ancestor::a");
    // --- Modal & Toast Locators ---
    private By confirmDeleteBtn = By.xpath("//button[contains(@class,'oxd-button--label-danger') or normalize-space()='Yes, Delete']");
    private By cancelDeleteBtn = By.xpath("//button[normalize-space()='No, Cancel']");
    private By toastMessage = By.xpath("//div[contains(@class,'oxd-toast')] | //p[contains(@class,'oxd-text--toast-message')]");
    private By successToast = By.xpath(
    	    "//div[contains(@class,'oxd-toast')]"
    	    + "//p[contains(@class,'oxd-text--toast-message')]"
    	);
 // --- Input Field Locators ---
    private By firstNameInput = By.name("firstName");
    private By middleNameInput = By.name("middleName");
    private By lastNameInput = By.name("lastName");
    private By vacancyAddDropdown = By.xpath("//label[text()='Vacancy']/following::div[contains(@class,'oxd-select-wrapper')][1]");
    private By emailInput = By.xpath("//label[text()='Email']/following::input[1]");
    private By contactNumberInput = By.xpath("//label[text()='Contact Number']/following::input[1]");
    private By resumeFileInput = By.xpath("//input[@type='file']");
    private By keywordsInput = By.xpath("//label[text()='Keywords']/following::input[1]");
    private By notesTextArea = By.xpath("//label[text()='Notes']/following::textarea[1]");
    private By consentCheckbox = By.xpath("//span[contains(@class,'oxd-checkbox-input')]");

    // --- Action Buttons & Status Locators ---
    private By saveBtn = By.xpath("//button[@type='submit' or normalize-space()='Save']");
    private By cancelBtn = By.xpath("//button[normalize-space()='Cancel']");
    private By shortlistButton = By.xpath("//button[normalize-space()='Shortlist']");
    private By requiredFieldError = By.xpath("//span[contains(@class,'oxd-input-field-error-message')]");
    private By successAddToast = By.xpath("//div[contains(@class,'oxd-toast-start')] | //p[contains(@class,'oxd-text--toast-message')]");
    private By formSpinner = By.xpath("//div[contains(@class,'oxd-loading-spinner')] | //div[contains(@class,'oxd-form-loader')]");
    private By candidateStatusText = By.xpath("//div[contains(@class,'orangehrm-recruitment-status')]//p | //span[contains(@class,'oxd-text--subtitle-2')]");

    // Error Toast element
    private By unexpectedErrorToast = By.xpath("//div[contains(@class,'oxd-toast--error')] | //p[text()='Unexpected Error Occurred']");
   
    public RecruitmentCandidatesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void waitForSpinnerToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(spinner));
        } catch (Exception ignored) {}
    }

    public void navigateToCandidates() {
        wait.until(ExpectedConditions.elementToBeClickable(recruitmentMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(candidatesTab)).click();
        waitForSpinnerToDisappear();
    }

    public void selectJobTitle(String jobTitle) {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(jobTitleDropdown)).click();
        By optionLocator = By.xpath("//div[@role='option']//span[text()='" + jobTitle + "']");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }

    public void enterCandidateName(String nameHint) {
        waitForSpinnerToDisappear();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(candidateNameInput));
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        input.sendKeys(nameHint);

        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(candidateAutoCompleteOption));
        option.click();
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        waitForSpinnerToDisappear();
    }

    public void clickReset() {
        wait.until(ExpectedConditions.elementToBeClickable(resetBtn)).click();
        waitForSpinnerToDisappear();
    }

    public void clickAddCandidate() {
        wait.until(ExpectedConditions.elementToBeClickable(addCandidateBtn)).click();
        waitForSpinnerToDisappear();
    }

    public boolean isRecordFoundDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(recordCountText)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getTableRowCount() {
        waitForSpinnerToDisappear();
        return driver.findElements(tableRows).size();
    }
    public void clickFirstCandidateView() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(viewIcon)).click();
        waitForSpinnerToDisappear();
    }

    public void clickFirstCandidateDelete() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(deleteIcon)).click();
    }

    public void confirmDeletion() {

        wait.until(
            ExpectedConditions.elementToBeClickable(confirmDeleteBtn)
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(successToast)
        );
    }

    public void cancelDeletion() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelDeleteBtn)).click();
    }

    public void clickFirstCandidateDownloadResume() {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(downloadIcon)).click();
    }
    
    public boolean isDownloadResumeAvailableInList() {
        waitForSpinnerToDisappear();
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            if (!row.findElements(downloadIconInRow).isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean clickAvailableCandidateDownloadResume() {
        waitForSpinnerToDisappear();
        List<WebElement> rows = driver.findElements(tableRows);
        
        for (WebElement row : rows) {
            List<WebElement> downloadBtns = row.findElements(downloadIconInRow);
            if (!downloadBtns.isEmpty()) {
                WebElement downloadBtn = downloadBtns.get(0);
                wait.until(ExpectedConditions.elementToBeClickable(downloadBtn)).click();
                return true;
            }
        }
        return false; // No row in the current table has a downloadable resume
    }
    
    public boolean isToastMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isDeleteSuccessToastDisplayed() {

        try {

            WebElement toast = wait.until(
                ExpectedConditions.visibilityOfElementLocated(successToast)
            );

            return toast.getText().contains("Successfully Deleted");

        } catch (Exception e) {

            return false;
        }
    }
    public boolean isDeleteModalDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmDeleteBtn)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public void enterCandidateName(String firstName, String middleName, String lastName) {
        waitForSpinnerToDisappear();
        WebElement first = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        first.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        first.sendKeys(firstName);

        WebElement middle = driver.findElement(middleNameInput);
        middle.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        middle.sendKeys(middleName);

        WebElement last = driver.findElement(lastNameInput);
        last.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        last.sendKeys(lastName);
    }

    public void selectVacancy(String vacancyName) {
        waitForSpinnerToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(vacancyAddDropdown)).click();
        By optionLocator = By.xpath("//div[@role='option']//span[text()='" + vacancyName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }

    public void enterContactDetails(String email, String contactNumber) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        emailField.sendKeys(email);

        if (contactNumber != null && !contactNumber.isEmpty()) {
            WebElement contactField = driver.findElement(contactNumberInput);
            contactField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
            contactField.sendKeys(contactNumber);
        }
    }

    public void uploadResume(String absoluteFilePath) {
        WebElement fileInput = driver.findElement(resumeFileInput);
        fileInput.sendKeys(absoluteFilePath);
    }

    public void enterKeywordsAndNotes(String keywords, String notes) {
        if (keywords != null && !keywords.isEmpty()) {
            WebElement keyField = driver.findElement(keywordsInput);
            keyField.sendKeys(keywords);
        }
        if (notes != null && !notes.isEmpty()) {
            WebElement notesField = driver.findElement(notesTextArea);
            notesField.sendKeys(notes);
        }
    }
    
    public void openCandidateWithStatus(String targetStatus) {
        // Dynamic XPath to locate the table row containing the specific status, then find its view (eye) icon button
        By targetCandidateEyeIcon = By.xpath(
            "//div[@role='row'][.//div[normalize-space()='" + targetStatus + "']]//button[contains(@class,'oxd-icon-button') and .//i[contains(@class,'bi-eye')]]"
        );

        WebElement viewButton = wait.until(ExpectedConditions.elementToBeClickable(targetCandidateEyeIcon));
        viewButton.click();
    }
    
    public void clickShortlist() {
        wait.until(ExpectedConditions.elementToBeClickable(shortlistButton)).click();
    }

    public void clickSaveShortlist() {
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
    }

    public boolean isUnexpectedErrorDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(unexpectedErrorToast)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCandidateStatus() {
        WebElement statusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(candidateStatusText));
        return statusElement.getText().trim();
    }
    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
    }

    public boolean isRequiredErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(requiredFieldError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSuccessToastDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successAddToast)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}