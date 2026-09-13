package tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EmployeePage;
import pages.LoginPage;

public class EmployeeTest extends BaseTest {

    private LoginPage loginPage;
    private EmployeePage employeePage;

    @BeforeClass
    public void setupClass() {
        employeePage = new EmployeePage(driver);
    }

    @BeforeMethod
    public void setupMethod() {
        employeePage.navigateToPIM();
    }

    @Test(priority = 1, description = "Verify validation on empty submission")
    public void testMandatoryFieldsValidation() {
        employeePage.clickAddEmployee();
        employeePage.clickSaveWithoutData();

        Assert.assertTrue(
            employeePage.isRequiredErrorDisplayed(),
            "Validation message 'Required' did not display on submitting empty form."
        );
    }

    @Test(priority = 2, description = "Verify adding a new employee")
    public void testAddEmployeeSuccessfully() {
        employeePage.clickAddEmployee();

        String fName = "Thomas";
        String lName = "Tester" + System.currentTimeMillis() / 1000;

        employeePage.addEmployee(fName, "J", lName);

        Assert.assertTrue(
            employeePage.isSuccessToastDisplayed(),
            "Success notification was not displayed after saving employee."
        );
    }

    @Test(priority = 3, description = "Verify cancel button on Add Employee form")
    public void testCancelAddEmployee() {
        employeePage.clickAddEmployee();
        employeePage.clickCancel();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("viewEmployeeList"),
            "Failed to navigate back to Employee List after clicking Cancel."
        );
    }

    @Test(priority = 4, description = "Verify Reset functionality")
    public void testResetSearchFilters() {
        employeePage.searchByEmployeeId("99999");
        employeePage.clickReset();

        Assert.assertTrue(
            employeePage.isRecordsCountDisplayed(),
            "Records count grid was not refreshed after resetting search criteria."
        );
    }
    @Test(priority = 5, description = "Verify empty state for invalid search")
    public void testNoRecordsFound() {
        employeePage.searchByEmployeeId("999999");
        Assert.assertTrue(
            employeePage.isNoRecordsFoundDisplayed(), 
            "No Records Found message should be visible for invalid search."
        );
    }

    @Test(priority = 6, description = "Verify navigation to Edit Employee profile")
    public void testEditEmployeeNavigation() {
        employeePage.clickEditFirstEmployee();
        Assert.assertTrue(
            driver.getCurrentUrl().contains("viewPersonalDetails"), 
            "Failed to navigate to Employee Personal Details page."
        );
    }
    @Test(priority = 7, description = "Verify enabling Create Login Details toggle")
    public void testToggleCreateLoginDetails() {
        employeePage.clickAddEmployee();
        employeePage.toggleCreateLoginDetails();
        
        Assert.assertTrue(
            employeePage.isUsernameFieldVisible(),
            "Username input field was not displayed after enabling Create Login Details switch."
        );
    }

    @Test(priority = 8, description = "Verify creating employee with login credentials")
    public void testAddEmployeeWithLoginDetails() {
    	employeePage.clickAddEmployee();
        String firstName = "David";
        String lastName = "Miller";
        String dynamicUsername = "user_" + System.currentTimeMillis() / 1000;
        String securePassword = "SecureP@ss1234";

        employeePage.addEmployeeWithLoginDetails(
            firstName,
            lastName,
            dynamicUsername,
            securePassword,
            true // Enable status
        );

        Assert.assertTrue(
            employeePage.isSuccessToastDisplayed(),
            "Success notification toast was not displayed after saving employee with login details."
        );
    }
    @Test(priority = 9, description = "Verify updating Nationality & Marital Status")
    public void testUpdateNationalityAndMaritalStatus() {
    	employeePage.clickEditFirstEmployee();
        employeePage.selectNationalityAndMaritalStatus("Indian", "Single");

        Assert.assertTrue(
            employeePage.isSuccessToastDisplayed(),
            "Success toast was not displayed after updating Nationality and Marital Status."
        );
    }

    @Test(priority = 10, description = " Verify updating Gender selection")
    public void testUpdateGender() {
    	employeePage.clickEditFirstEmployee();
        employeePage.selectGender(true); // Select Male

        Assert.assertTrue(
            employeePage.isSuccessToastDisplayed(),
            "Success toast was not displayed after updating Gender."
        );
   }
    @Test(
    	    priority = 11,
    	    description = "Verify successful file upload under Attachments"
    	)
    	public void testUploadAttachment() throws IOException {

    	    employeePage.clickEditFirstEmployee();

    	    // Create a real temporary text file
    	    File tempFile = File.createTempFile("test_upload_", ".txt");

    	    try (FileWriter writer = new FileWriter(tempFile)) {
    	        writer.write("This is a dummy file created for Selenium upload testing.");
    	    }

    	    tempFile.deleteOnExit();

    	    String dummyFilePath = tempFile.getAbsolutePath();
    	    String uploadComment = "Automation dummy file upload comment " + System.currentTimeMillis() / 1000;

    	    employeePage.clickAddAttachment();

    	    employeePage.uploadAttachment(
    	        dummyFilePath,uploadComment
    	    );

    	    Assert.assertTrue(
	        employeePage.isSuccessToastDisplayed(),
   	        "Success notification toast did not appear after uploading attachment."
   	    );
  	}
    @Test(description = "Verify Records Found count equals total employee rows")
    public void testRecordsFoundMatchesTableRows() {

        int expectedRecordCount = employeePage.getRecordsFoundCount();

        int actualRowCount = employeePage.getTotalListedRowsAcrossPages();

        System.out.println("Expected Records Found: " + expectedRecordCount);
        System.out.println("Actual Employee Rows: " + actualRowCount);

        Assert.assertEquals(
                actualRowCount,
                expectedRecordCount,
                "Mismatch between Records Found count and total employee rows."
        );
    }
    @Test(priority = 12, description = "TC-EM-75: Verify special characters in employee name validation message")
    public void verifySpecialCharactersInEmployeeName() {
    	employeePage.clickAddEmployee();

        // 2. Enter First and Last Name with special characters
        String invalidName = "#$%%";

        employeePage.addEmployee(invalidName, "J", invalidName);
        // 4. Assertion: Verify validation error message for special characters
        String expectedErrorMessage = "Should not contain special characters"; // Adjust to exact UI text if different
        String actualErrorMessage = employeePage.getFirstNameValidationError();

        Assert.assertEquals(
            actualErrorMessage,
            expectedErrorMessage,
            "Validation message mismatch when entering special characters in First Name."
        );
    }

    @Test(priority = 13, description = "TC-EM-76: Verify duplicate employee name validation message")
    public void verifyDuplicateEmployeeName() {
    	employeePage.clickAddEmployee();
        // 2. Enter existing/duplicate First and Last Name
        String firstName = "David";
        String lastName = "Miller";

        employeePage.addEmployee(firstName, "", lastName);

        // 4. Assertion: Verify duplicate warning or error message
        String expectedDuplicateMessage = "duplicate entry not accepted"; // Adjust to exact UI text if different
        String actualDuplicateMessage = employeePage.getDuplicateWarningMessage();

        Assert.assertEquals(
            actualDuplicateMessage,
            expectedDuplicateMessage,
            "Validation message mismatch for duplicate employee creation."
        );
    }
}