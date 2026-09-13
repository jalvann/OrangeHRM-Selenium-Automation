package tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RecruitmentCandidatesPage;

public class RecruitmentCandidatesTest extends BaseTest {

    private LoginPage loginPage;
    private RecruitmentCandidatesPage recruitmentPage;

    @BeforeClass
    public void setupClass() {
        recruitmentPage = new RecruitmentCandidatesPage(driver);
    }

    @BeforeMethod
    public void setupMethod() {
        recruitmentPage.navigateToCandidates();
    }

    @Test(priority = 1, description = "Verify navigation to Recruitment Candidates page")
    public void testNavigationToCandidates() {
        Assert.assertTrue(
            driver.getCurrentUrl().contains("viewCandidates"),
            "Failed to navigate to Recruitment Candidates page."
        );
    }

    @Test(priority = 2, description = "Verify candidate search displays records")
    public void testSearchCandidates() {
        recruitmentPage.clickSearch();
        Assert.assertTrue(
            recruitmentPage.isRecordFoundDisplayed(),
            "Record count banner was not displayed upon searching."
        );
        Assert.assertTrue(
            recruitmentPage.getTableRowCount() > 0,
            "Candidate records table is empty."
        );
    }

    @Test(priority = 3, description = "Verify filter reset functionality")
    public void testResetFilters() {
        recruitmentPage.selectJobTitle("Payroll Administrator");
        recruitmentPage.clickReset();
        Assert.assertTrue(
            driver.getCurrentUrl().contains("viewCandidates"),
            "Page failed to refresh correctly after reset."
        );
    }

    @Test(priority = 4, description = "Verify navigation to Add Candidate page")
    public void testNavigateToAddCandidate() {
        recruitmentPage.clickAddCandidate();
        Assert.assertTrue(
            driver.getCurrentUrl().contains("addCandidate"),
            "Failed to navigate to Add Candidate screen."
        );
    }
    @Test(priority = 5, description = "Verify viewing candidate profile details")
    public void testViewCandidateDetails() {
        recruitmentPage.clickSearch();
        recruitmentPage.clickFirstCandidateView();
        
        Assert.assertTrue(
            driver.getCurrentUrl().contains("addCandidate"),
            "Failed to navigate to Candidate details screen."
        );
    }

    @Test(priority = 6, description = "Verify cancelling candidate deletion")
    public void testCancelCandidateDeletion() {
        recruitmentPage.clickSearch();
        recruitmentPage.clickFirstCandidateDelete();
        
        Assert.assertTrue(
            recruitmentPage.isDeleteModalDisplayed(),
            "Delete confirmation modal did not open."
        );
        
        recruitmentPage.cancelDeletion();
        Assert.assertFalse(
            recruitmentPage.isDeleteModalDisplayed(),
            "Delete confirmation modal failed to close."
        );
    }

    @Test(priority = 7, description = "Verify deleting a candidate record")
    public void testDeleteCandidate() {
        recruitmentPage.clickSearch();
        recruitmentPage.clickFirstCandidateDelete();
        recruitmentPage.confirmDeletion();
        
        Assert.assertTrue(
        	    recruitmentPage.isDeleteSuccessToastDisplayed(),
        	    "Success notification toast failed to display upon deleting candidate."
        	);
    }

  
    @Test(priority = 8, description = "Verify downloading candidate resume attachment")
    public void testDownloadCandidateResume() {
        recruitmentPage.clickSearch();

        // 1. Check if any row in the table contains a resume download button
        boolean hasResumeToDownload = recruitmentPage.isDownloadResumeAvailableInList();

        if (!hasResumeToDownload) {
            // Option A: Skip the test gracefully if no test data has a resume attached
            throw new SkipException("Skipping test: No candidate in the current table list has an attached resume to download.");
        }

        // 2. Click the download icon on the first row that actually has a resume attached
        boolean downloadClicked = recruitmentPage.clickAvailableCandidateDownloadResume();

        // 3. Assert that the click action was executed successfully
        Assert.assertTrue(
            downloadClicked,
            "Failed to click the candidate resume download button."
        );
    }
    
    @Test(priority = 9, description = "Verify mandatory field validations when saving empty form")
    public void testMandatoryFieldsValidation() {
    	recruitmentPage.clickAddCandidate();
        recruitmentPage.clickSave();
        Assert.assertTrue(
            recruitmentPage.isRequiredErrorDisplayed(),
            "Validation errors were not displayed for mandatory fields."
        );
    }

    @Test(priority = 10, description = "Verify adding a new candidate successfully")
    public void testAddCandidateSuccess() {
    	recruitmentPage.clickAddCandidate();
        long timestamp = System.currentTimeMillis();
        String firstName = "Test";
        String lastName = "User" + timestamp;
        String email = "testuser" + timestamp + "@example.com";

        recruitmentPage.enterCandidateName(firstName, "Automation", lastName);
        recruitmentPage.enterContactDetails(email, "9876543210");
        recruitmentPage.enterKeywordsAndNotes("Java, Selenium, TestNG", "Automated Candidate Entry");
        recruitmentPage.clickSave();

        Assert.assertTrue(
            recruitmentPage.isSuccessToastDisplayed(),
            "Success notification toast did not appear after saving new candidate."
        );
    }

    @Test(priority = 11, description = "Verify invalid email format error")
    public void testInvalidEmailValidation() {
    	recruitmentPage.clickAddCandidate();
        recruitmentPage.enterCandidateName("John", "D", "Doe");
        recruitmentPage.enterContactDetails("invalid-email-format", "1234567890");
        recruitmentPage.clickSave();

        Assert.assertTrue(
            recruitmentPage.isRequiredErrorDisplayed(),
            "Expected format error message for invalid email did not display."
        );
    }
    
    @Test(priority = 12, description = "TC_RC_129: Verify shortlisting a candidate found via Application Initiated status")
    public void verifyShortlistCandidate() {

    	// 2. Locate and open the candidate who has 'Application Initiated' status directly from the table
        recruitmentPage.openCandidateWithStatus("Application Initiated");

        // 4. Click Shortlist and Save
        recruitmentPage.clickShortlist();
        recruitmentPage.clickSaveShortlist();

        // 5. Assertions: Check for application errors and verify status transition
        boolean hasUnexpectedError = recruitmentPage.isUnexpectedErrorDisplayed();
        Assert.assertFalse(
            hasUnexpectedError,
            "Defect Captured: Red 'Unexpected Error Occurred' message was displayed when shortlisting candidate."
        );

        String actualStatus = recruitmentPage.getCandidateStatus();
        Assert.assertEquals(
            actualStatus,
            "Shortlisted",
            "Candidate status was not updated to 'Shortlisted'."
        );
    }
}