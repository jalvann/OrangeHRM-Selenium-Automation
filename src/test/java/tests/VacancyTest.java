package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.EmployeePage;
import pages.LoginPage;
import pages.VacancyPage;

public class VacancyTest extends BaseTest {

    private LoginPage loginPage;
    private VacancyPage vacancyPage;
    
    @BeforeClass
    public void setupClass() {
        vacancyPage = new VacancyPage(driver);
    }
    
    @BeforeMethod
    public void setUpVacancyPage() {
        vacancyPage.navigateToVacancies();
    }

    @Test(priority = 1, description = "TC_VAC_128: Verify navigation to Vacancies page")
    public void testNavigateToVacancies() {
        Assert.assertTrue(
            driver.getCurrentUrl().contains("viewJobVacancy"),
            "Failed to navigate to Vacancies page."
        );
    }

    @Test(priority = 2, description = "TC_VAC_129: Verify total record count matches table rows across pages")
    public void testRecordsFoundMatchesTableRows() {
        int expectedRecordCount = vacancyPage.getRecordsFoundCount();
        int actualRowCount = vacancyPage.getTotalListedRowsAcrossPages();
        Assert.assertEquals(
            actualRowCount, 
            expectedRecordCount, 
            "Mismatch between header 'Records Found' count and total listed table rows."
        );
    }

    @Test(priority = 3, description = "TC_VAC_130: Verify search filtering by Job Title")
    public void testSearchByJobTitle() {
        vacancyPage.selectJobTitle("QA Lead");
        vacancyPage.clickSearch();
        
        Assert.assertTrue(
                vacancyPage.getRecordsFoundCount() > 0,
                "No records found for selected Job Title."
            );
    }

    @Test(priority = 4, description = "TC_VAC_132: Verify search filtering by Status")
    public void testSearchByStatus() {
        vacancyPage.selectStatus("Active");
        vacancyPage.clickSearch();

        Assert.assertTrue(
            vacancyPage.getRecordsFoundCount() > 0,
            "No records found for active status."
        );
    }

    @Test(priority = 5, description = "TC_VAC_134: Verify Reset button clears search filters")
    public void testResetButtonClearsFilters() {
        vacancyPage.selectJobTitle("QA Lead");
        vacancyPage.clickReset();

        Assert.assertTrue(
                vacancyPage.getSelectedJobTitleText().contains("-- Select --"), 
                "Job Title dropdown was not reset."
            );
    }

    @Test(priority = 6, description = "TC_VAC_135: Verify navigation to Add Vacancy page")
    public void testNavigateToAddVacancyPage() {
        vacancyPage.clickAddVacancy();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("addJobVacancy"),
            "Failed to navigate to Add Vacancy form."
        );
    }

    @Test(priority = 7, description = "TC_VAC_141: Verify mandatory field validation on Add Vacancy form")
    public void testMandatoryFieldsValidationOnAddVacancy() {
        vacancyPage.clickAddVacancy();
        vacancyPage.clickSaveWithoutData();

        Assert.assertTrue(
            vacancyPage.isRequiredErrorDisplayed(),
            "Validation message 'Required' did not appear on blank submission."
        );
    }

    @Test(priority = 8, description = "TC_VAC_142: Verify successful creation of new vacancy")
    public void testAddNewVacancySuccessfully() {
        vacancyPage.clickAddVacancy();
        vacancyPage.addVacancy(
            "Automation Lead " + System.currentTimeMillis(),
            "QA Lead",
            "Ranga Akunuri",
            "2"
        );
     vacancyPage.clickCancel();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("viewJobVacancy"),
            "Failed to save vacancy and redirect to grid."
        );
    }

    @Test(priority = 9, description = "TC_VAC_143: Verify Cancel button discards data and returns to grid")
    public void testCancelAddVacancy() {
        vacancyPage.clickAddVacancy();
        vacancyPage.clickCancel();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("viewJobVacancy"),
            "Cancel button failed to redirect back to Vacancies grid."
        );
    }

    @Test(priority = 10, description = "TC_VAC_136: Verify Edit Icon opens Edit Vacancy page")
    public void testEditVacancyNavigation() {
        vacancyPage.clickEditFirstVacancy();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("addJobVacancy"),
            "Failed to navigate to Edit Vacancy page."
        );
    }

    @Test(priority = 11, description = "TC_VAC_139: Verify Bulk Select Checkbox highlights all rows")
    public void testBulkSelectAllCheckboxes() {
        vacancyPage.selectAllHeaderCheckbox();

        Assert.assertTrue(
            vacancyPage.areAllRowCheckboxesSelected(),
            "Not all row checkboxes were selected."
        );
    }
    
    @Test(priority = 12, description = "TC-VAC-148: Verify special characters in vacancy name validation message")
    public void verifySpecialCharactersInVacancyName() {
        vacancyPage.clickAddVacancy();
        // 2. Enter Vacancy Name containing special characters and fill mandatory fields
        String invalidVacancyName = "#$%%";
        vacancyPage.enterVacancyName(invalidVacancyName);
        vacancyPage.selectJobTitleFirstOption();
        vacancyPage.selectHiringManager("Ranga Akunuri"); // Triggers autocomplete to pick manager

        // 3. Click Save
        vacancyPage.clickSave();

        // 4. Assertion: Verify validation error message for special characters
        String expectedErrorMessage = "Should not contain special characters"; // Adjust to exact UI error text if different
        String actualErrorMessage = vacancyPage.getVacancyNameValidationError();

        Assert.assertEquals(
            actualErrorMessage,
            expectedErrorMessage,
            "Defect Captured: System saved the vacancy name containing special characters instead of showing validation error."
        );
    }
}