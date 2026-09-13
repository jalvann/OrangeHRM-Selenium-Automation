package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ReportPage;

public class ReportTest extends BaseTest {

    private ReportPage reportPage;

    @BeforeClass
    public void setupClass() {
        reportPage = new ReportPage(driver);
    }

    @BeforeMethod
    public void setupMethod() {
        reportPage.navigateToReports();
    }

    @Test(priority = 1, description = "Verify mandatory field validation on Add Report")
    public void testAddReportValidation() {
        reportPage.clickAddReport();
        reportPage.clickSaveWithoutData();

        Assert.assertTrue(
            reportPage.isRequiredErrorDisplayed(),
            "Validation message 'Required' did not display on empty report submission."
        );
    }

    @Test(priority = 2, description = "Verify creating a custom employee report")
    public void testCreateReportSuccessfully() {
        reportPage.clickAddReport();

        String reportName = "Custom_Report_" + System.currentTimeMillis() / 1000;
        reportPage.createReport(reportName, "Personal", "Employee First Name");

        Assert.assertTrue(
            reportPage.isSuccessToastDisplayed(),
            "Success notification toast was not displayed after creating new report."
        );
    }

    @Test(priority = 3, description = "Verify cancel button on Add Report form")
    public void testCancelAddReport() {
        reportPage.clickAddReport();
        reportPage.clickCancel();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("viewDefinedPredefinedReports"),
            "Failed to navigate back to Reports list after clicking Cancel."
        );
    }
    
    @Test(priority = 4, description = "Verify running report from grid")
    public void testRunReport() {
        reportPage.runFirstReport();

        Assert.assertTrue(
            reportPage.isReportHeaderDisplayed(),
            "Report output title/header was not loaded after clicking Run Report."
        );
    }
    
    @Test(priority = 5, description = "Verify navigating to edit report form")
    public void testEditReportNavigation() {
        reportPage.editFirstReport();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("definePredefinedReport"),
            "Failed to navigate to Edit Report view."
        );
    }
    
    @Test(priority = 6, description = "Verify Reset search filters functionality")
    public void testResetReportSearch() {
        reportPage.searchReport("All Employee");
        reportPage.clickReset();

        Assert.assertTrue(
            reportPage.isRecordsCountDisplayed(),
            "Records count label did not render properly after resetting report filters."
        );
    }
    
    @Test(priority = 7, description = "Verify deleting custom report")
    public void testDeleteReport() {
        reportPage.deleteFirstReport();

        Assert.assertTrue(
            reportPage.isSuccessToastDisplayed(),
            "Success notification toast was not displayed after deleting report."
        );
    }
    @Test(priority = 91, description = "TC-REP-91: Verify autocompletion option on Report search when clicking Search directly")
    public void verifyReportSearchAutocompletion() {
        // 1. Navigate to Reports List (PIM > Reports)
        reportPage.navigateToReports();

        // 2. Type existing Report Name into search field without clicking a drop-down suggestion
        String reportName = "All Employee Sub Unit Hierarchy Report";
        reportPage.typeReportName(reportName);

        // 3. Click Search directly without selecting from autocompletion suggestions
        reportPage.clickSearch();

        // 4. Assertion: Verify matching records are filtered instead of showing "No Records Found" / invalid message
        boolean isReportFound = reportPage.isReportPresentInTable(reportName);

        Assert.assertTrue(
            isReportFound,
            "Search operation failed: Typing complete report name and clicking search directly showed invalid message or failed to filter records."
        );
    }
    
}