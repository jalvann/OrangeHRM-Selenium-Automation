package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LeaveListPage;
import pages.LoginPage;

public class LeaveListTest extends BaseTest {

    private LoginPage loginPage;
    private LeaveListPage leaveListPage;

    @BeforeClass
    public void setupClass() {
        leaveListPage = new LeaveListPage(driver);
    }

    @BeforeMethod
    public void setupMethod() {
        leaveListPage.navigateToLeaveList();
    }

    @Test(priority = 1, description = "Verify default Leave List search")
    public void testDefaultSearch() {
        leaveListPage.clickSearch();
        
        Assert.assertTrue(
            leaveListPage.isRecordsCountDisplayed() || leaveListPage.isNoRecordsFoundDisplayed(),
            "Search results or No Records message was not displayed properly."
        );
    }

    @Test(priority = 2, description = "Verify Reset button clears input fields")
    public void testResetSearchFilters() {
        leaveListPage.searchByEmployeeName("Invalid User XYZ");
        leaveListPage.clickReset();

        Assert.assertEquals(
            leaveListPage.getEmployeeNameValue(),
            "",
            "Employee Name field should be cleared after clicking Reset."
        );
    }

    @Test(priority = 3, description = "Verify filtering leave list by Sub Unit")
    public void testFilterBySubUnit() {
        leaveListPage.filterBySubUnit("Engineering");

        Assert.assertTrue(
            leaveListPage.isRecordsCountDisplayed() || leaveListPage.isNoRecordsFoundDisplayed(),
            "Search grid failed to update after filtering by Sub Unit."
        );
    }

    @Test(priority = 4, description = "Verify toggling Include Past Employees switch")
    public void testToggleIncludePastEmployees() {
        leaveListPage.toggleIncludePastEmployees();
        leaveListPage.clickSearch();

        Assert.assertTrue(
            leaveListPage.isRecordsCountDisplayed() || leaveListPage.isNoRecordsFoundDisplayed(),
            "Search grid failed to update after toggling Include Past Employees."
        );
    }

    @Test(priority = 5, description = "Verify adding a comment to a leave record")
    public void testAddCommentToLeave() {
        leaveListPage.clickSearch();
        
        if (leaveListPage.hasRecordsInGrid()) {
            String commentText = "Automation comment " + System.currentTimeMillis() / 1000;
            leaveListPage.addCommentToFirstRecord(commentText);

            Assert.assertTrue(
                leaveListPage.isSuccessToastDisplayed(),
                "Success notification toast was not displayed after saving comment."
            );
        } else {
            Assert.assertTrue(
                leaveListPage.isNoRecordsFoundDisplayed(),
                "No records present in grid to perform Add Comment."
            );
        }
    }

    @Test(priority = 6, description = "Verify View Leave Details navigation")
    public void testViewLeaveDetailsNavigation() {
        leaveListPage.selectLeaveStatus("Scheduled");
        leaveListPage.clickSearch();

        if (leaveListPage.hasRecordsInGrid()) {
            leaveListPage.clickLeaveDetails();

            Assert.assertTrue(
                driver.getCurrentUrl().contains("viewLeave"),
                "Failed to navigate to Leave Details page."
            );
        } else {
            Assert.assertTrue(
                leaveListPage.isNoRecordsFoundDisplayed(),
                "No records present in grid to view Leave Details."
            );
        }
    }

    @Test(priority = 7, description = "Verify navigation to employee PIM info from Leave List")
    public void testViewPimInfoNavigation() {
        leaveListPage.clickSearch();

        if (leaveListPage.hasRecordsInGrid()) {
            leaveListPage.clickPimInfo();

            Assert.assertTrue(
                driver.getCurrentUrl().contains("pim"),
                "Failed to navigate to Employee PIM profile."
            );
        } else {
            Assert.assertTrue(
                leaveListPage.isNoRecordsFoundDisplayed(),
                "No records present in grid to view PIM info."
            );
        }
    }

    @Test(priority = 8, description = "Verify canceling a scheduled leave record")
    public void testCancelLeaveRecord() {
        leaveListPage.selectLeaveStatus("Scheduled");
        leaveListPage.clickSearch();

        if (leaveListPage.hasRecordsInGrid()) {
            leaveListPage.cancelLeaveRecord();

            Assert.assertTrue(
                leaveListPage.isSuccessToastDisplayed(),
                "Success notification toast did not appear after canceling leave."
            );
        } else {
            Assert.assertTrue(
                leaveListPage.isNoRecordsFoundDisplayed(),
                "No records present in grid to cancel leave."
            );
        }
    }
}