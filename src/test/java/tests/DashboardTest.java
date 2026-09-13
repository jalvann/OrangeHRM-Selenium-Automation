package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.LoginPage;

public class DashboardTest extends BaseTest {
	private LoginPage loginPage;
    private DashboardPage dashboardPage;
    
	@BeforeClass
    public void prepareDashboardPage() {
        dashboardPage = new DashboardPage(driver);
    }
	
	@Test(priority = 1, description = "Verify Clock In/Out functionality")
    public void testClockInOut() {
        dashboardPage.clickPunchClock();
        Assert.assertTrue(dashboardPage.getCurrentUrl().contains("punchIn") || 
                          dashboardPage.getCurrentUrl().contains("attendance"), 
                          "Failed to navigate to Punch In/Out page.");
        driver.navigate().back();
        Assert.assertTrue(dashboardPage.getPunchStatus().contains("Punched"), "Punch status text missing.");
    }

	@Test(priority = 2, description = "Verify successful landing on Dashboard page after login")
    public void testDashboardHeaderIsDisplayed() {
        Assert.assertEquals(dashboardPage.getHeaderTitleText(), "Dashboard");
    }
	
	@Test(priority = 3, description = "Verify core widgets exist on the Dashboard")
    public void testDashboardWidgetsVisibility() {
        Assert.assertEquals(dashboardPage.getQuickLaunchHeaderText(), "Quick Launch");
        Assert.assertEquals(dashboardPage.getTimeAtWorkHeaderText(), "Time at Work");
        Assert.assertEquals(dashboardPage.getMyActionsHeaderText(), "My Actions");
    }


    @Test(priority = 4, description = "Verify logged-in user profile component displays details")
    public void testUserProfileDisplay() {
    	dashboardPage.clickUserDropdown();
        String profileName = dashboardPage.getUserName();
        Assert.assertNotNull(profileName, "User profile dropdown text is null.");
        Assert.assertFalse(profileName.isEmpty(), "User profile name is empty on the dashboard.");
    }


    @Test(priority = 5, description = "Verify support option on the userdropdown")
    public void testClickSupport() {
    	dashboardPage.clickUserDropdown();
    	dashboardPage.clickSupport();
        Assert.assertTrue(driver.getCurrentUrl().contains("help/support"), "Did not navigate to Support page");
        driver.navigate().back();
    }

    @Test(priority = 6, description = "Verify changepassword option on the userdropdown")
    public void testClickChangePassword() {
    	dashboardPage.clickUserDropdown();
    	dashboardPage.clickChangePassword();
        Assert.assertTrue(driver.getCurrentUrl().contains("updatePassword"), "Did not navigate to Change Password page");
        driver.navigate().back();
    }

    @Test(priority = 7, description = "Verify Help icon opens support documentation")
    public void testHelpButtonRedirection() {
        String originalWindow = driver.getWindowHandle();
        
        // Perform action
        dashboardPage.clickHelpButton();
        
        // Switch to newly opened tab
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        // Assertion: Check that URL contains help portal domain
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
            currentUrl.contains("help.orangehrm.com") || currentUrl.contains("support"), 
            "Help icon failed to redirect to external support page. Current URL: " + currentUrl
        );
        
        // Cleanup window tab
        driver.close();
        driver.switchTo().window(originalWindow);
    }
    @Test(priority = 8, description = "Verify navigation from My Actions links")
    public void testMyActionsNavigation() {
        dashboardPage.clickPendingSelfReview();
        Assert.assertTrue(dashboardPage.getCurrentUrl().contains("performance") || 
                          dashboardPage.getCurrentUrl().contains("review"), 
                          "Failed to navigate to Self Review page.");
        driver.navigate().back();
    }
    @Test(priority = 9, description = "Verify Quick Launch shortcut navigation")
    public void testQuickLaunchNavigation() {
        dashboardPage.clickApplyLeaveShortcut();
        Assert.assertTrue(dashboardPage.getCurrentUrl().contains("applyLeave"), "Failed to navigate via Quick Launch to Apply Leave page.");
        driver.navigate().back();
    }
    @Test(priority = 10, description = "Verify widget configuration via settings icon")
    public void testWidgetConfigurationSettings() {
        dashboardPage.clickLeaveWidgetSettings();
        // Verifies interaction with the configuration gear button on the widget
        Assert.assertTrue(dashboardPage.getCurrentUrl().contains("dashboard"), "User navigated away unexpectedly after clicking configuration gear.");
        dashboardPage.clickModalCloseIcon();
    }

    @Test(priority = 12, description = "Verify logout on the userdropdown")
    public void testClickLogout() {
    	dashboardPage.clickUserDropdown();
    	dashboardPage.clickLogout();
        Assert.assertTrue(driver.getCurrentUrl().contains("auth/login"), "Did not navigate to Login page after logout");
    }
}
