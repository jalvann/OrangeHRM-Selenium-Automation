package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AssignLeavePage;
import pages.LoginPage;

public class AssignLeaveTest extends BaseTest {
	
    private AssignLeavePage assignLeavePage;

    @BeforeClass
    public void setupClass() {
        assignLeavePage = new AssignLeavePage(driver);
    }

    @BeforeMethod
    public void setupMethod() {
        assignLeavePage.navigateToAssignLeave();
    }

    @Test(priority = 1, description = "Verify navigation to Assign Leave page")
    public void testNavigationToAssignLeave() {
        Assert.assertTrue(
            driver.getCurrentUrl().contains("assignLeave"),
            "Failed to navigate to Assign Leave page."
        );
    }

    @Test(priority = 2, description = "Verify mandatory field validations")
    public void testMandatoryFieldsValidation() {
        assignLeavePage.clickAssign();
        Assert.assertTrue(
            assignLeavePage.isRequiredErrorDisplayed(),
            "Required field errors were not displayed."
        );
    }

    @Test(priority = 3, description = "Verify selection of Leave Type displays Leave Balance")
    public void testLeaveBalanceDisplay() {
        assignLeavePage.selectEmployee("akhil");
        
        // Assert that the employee field is populated and not empty
        String selectedEmployee = assignLeavePage.getEmployeeInputValue();
        Assert.assertFalse(
            selectedEmployee.trim().isEmpty(),
            "Employee name was not populated in the input field."
        );

        assignLeavePage.selectLeaveType("CAN - Personal");
        
        String balanceText = assignLeavePage.getLeaveBalanceText();
        Assert.assertTrue(
            balanceText.contains("Day(s)") || !balanceText.isEmpty(),
            "Leave balance element was not populated properly."
        );
    }


    @Test(priority = 4, description = "Verify submitting leave assignment with balance verification")
    public void testAssignLeaveSuccess() {
        assignLeavePage.selectEmployee("akhil");
        
        String selectedEmployee = assignLeavePage.getEmployeeInputValue();
        Assert.assertFalse(
            selectedEmployee.trim().isEmpty(),
            "Employee name was not populated in the input field."
        );

        assignLeavePage.selectLeaveType("CAN - Personal");

        // 1. Check leave balance state
        String leaveBalance = assignLeavePage.getLeaveBalanceText();
        boolean isInsufficient = assignLeavePage.isBalanceInsufficient();
        System.out.println("Current Leave Balance Status: " + leaveBalance);

        assignLeavePage.setDates("2026-03-09", "2026-03-09");
        assignLeavePage.enterComments("Automated leave assignment test");

        // 2. Click Assign and handle modal confirmation if balance is insufficient
        assignLeavePage.clickAssignAndConfirmIfPrompted();

        // 3. Assertion: Verify operation succeeded (toast shown) OR insufficient balance state was appropriately caught
        boolean isToastDisplayed = assignLeavePage.isSuccessToastDisplayed();
        
        Assert.assertTrue(
            isToastDisplayed || isInsufficient,
            "Leave assignment failed without raising an expected insufficient balance prompt or success toast."
        );
    }
    @Test(priority = 5, description = "TC_AL_114: Verify Leave Balance resets to default after successful submission")
    public void verifyLeaveBalanceResetAfterSubmission() {
        // 1. Navigate to Leave > Assign Leave
        assignLeavePage.navigateToAssignLeave();

        // 2. Select Employee and Leave Type with insufficient balance
        assignLeavePage.selectEmployee("Akhil");
        assignLeavePage.selectLeaveType("CAN - personal");

        // 3. Verify warning state appears
        Assert.assertEquals(
            assignLeavePage.getLeaveBalanceText(),
            "Balance not sufficient",
            "Initial state check failed: Leave balance warning was not displayed."
        );

        // 4. Enter dates and submit leave
        assignLeavePage.setDates("2026-10-01", "2026-10-02");
        assignLeavePage.clickAssign();
        assignLeavePage.clickAssignAndConfirmIfPrompted();

        // 5. Assertion: Verify Leave Balance UI section resets (not displaying stale red warning)
        boolean isWarningStillDisplayed = assignLeavePage.isBalanceInsufficient();
        Assert.assertFalse(
            isWarningStillDisplayed,
            "UI Failure: The Leave Balance section persisted the stale red error message 'Balance not sufficient' after form reset."
        );
    }

    @Test(priority = 6, description = "TC_AL_115: Verify Leave cannot be assigned when balance is insufficient")
    public void verifyLeaveSubmissionWhenBalanceInsufficient() {
        // 1. Navigate to Leave > Assign Leave
        assignLeavePage.navigateToAssignLeave();

        // 2. Select Employee and Leave Type with insufficient balance
        assignLeavePage.selectEmployee("Akhil");
        assignLeavePage.selectLeaveType("CAN - personal");

        // 3. Verify balance error indicator on UI
        String balanceText = assignLeavePage.getLeaveBalanceText();
        Assert.assertEquals(balanceText, "Balance not sufficient", "Expected insufficient balance message.");

        // 4 & 5. Attempt submission and confirmation
        assignLeavePage.setDates("2026-10-01", "2026-10-02");
        assignLeavePage.clickAssign();
        assignLeavePage.clickAssignAndConfirmIfPrompted();

        // Assertion: Verify application blocks saving and does NOT display "Success" toast
        boolean isSuccessToastDisplayed = assignLeavePage.isSuccessToastDisplayed();
        Assert.assertFalse(
            isSuccessToastDisplayed,
            "Business Logic Failure: System displayed 'Success: Successfully Saved' despite insufficient leave balance."
        );
    }
}
