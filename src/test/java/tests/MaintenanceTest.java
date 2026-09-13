package tests;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.DirectoryPage;
import pages.LoginPage;
import pages.MaintenancePage;

public class MaintenanceTest extends BaseTest {

    private MaintenancePage maintenancePage;

    @BeforeClass
    public void setupClass() {
        maintenancePage = new MaintenancePage(driver);
        maintenancePage.navigateToMaintenance();
    }
    
 //--- Authentication Test Cases ---

    @Test(priority = 1, description = "TC_MNT_001: Verify Administrator Access authentication prompt")
    public void testAdminAccessPromptDisplayed() {
        Assert.assertTrue(
            maintenancePage.isAdminAccessPromptDisplayed(),
            "Administrator Access authentication screen was not displayed."
        );
    }

    @Test(priority = 2, description = "TC_MNT_002: Verify authentication failure with invalid password")
    public void testInvalidAdminPasswordAuthentication() {
        maintenancePage.authenticateAdmin("InvalidPassword123");

        Assert.assertTrue(
            maintenancePage.isAuthErrorDisplayed(),
            "Authentication error message did not display for invalid password."
        );
    }

    @Test(priority = 3, description = "TC_MNT_003: Verify authentication success with valid password")
    public void testValidAdminPasswordAuthentication() {
        maintenancePage.authenticateAdmin("admin123");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isNavigated = wait.until(ExpectedConditions.urlContains("purgeEmployee"));

        Assert.assertTrue(
            isNavigated,
            "Failed to authenticate and navigate to Maintenance module."
        );
    }

    // --- Purge Employee Records Test Cases ---

    @Test(priority = 4, description = "TC_MNT_004: Verify mandatory field validation on Purge Employee Records")
    public void testPurgeEmployeeMandatoryFieldValidation() {
        maintenancePage.clickSearchWithoutData();

        Assert.assertTrue(
            maintenancePage.isRequiredErrorDisplayed(),
            "Validation message 'Required' did not display when Past Employee was left blank."
        );
    }

    @Test(priority = 5, description = "TC_MNT_005: Verify search past employee for purging")
    public void testSearchPastEmployeeForPurging() {
    String employeeName = "Maria N Jones";
        
        boolean employeeExists = maintenancePage.searchPastEmployee(employeeName);
    
        if (employeeExists) {
        	  Assert.assertTrue(
        	            driver.getCurrentUrl().contains("purgeEmployee"),
        	            "Search execution for past employee failed."
        	        );
        } 
      
    }

    // --- Purge Candidate Records Test Cases ---

    @Test(priority = 6, description = "TC_MNT_006: Verify navigation to Purge Candidate Records page")
    public void testNavigateToPurgeCandidateRecords() {
        maintenancePage.navigateToPurgeCandidateRecords();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("purgeCandidateData"),
            "Failed to navigate to Purge Candidate Records page."
        );
    }

    @Test(priority = 7, description = "TC_MNT_007: Verify mandatory field validation on Purge Candidate Records")
    public void testPurgeCandidateMandatoryFieldValidation() {
        maintenancePage.navigateToPurgeCandidateRecords();
        maintenancePage.clickSearchWithoutData();

        Assert.assertTrue(
            maintenancePage.isRequiredErrorDisplayed(),
            "Validation message 'Required' did not display when Vacancy was left blank."
        );
    }

    // --- Purge All Feature Test Cases ---

    @Test(priority = 8, description = "TC_MNT_PGA_001: Verify visibility of 'Purge All' button after search")
    public void testPurgeAllButtonVisibilityAfterSearch() {
     
        maintenancePage.navigateToPurgeCandidateRecords();
        maintenancePage.searchCandidateVacancyIfExists("Payroll Administrator");

        Assert.assertTrue(
            maintenancePage.isPurgeAllButtonDisplayed(),
            "'Purge All' button is not displayed after performing search."
        );
    }

    @Test(priority = 9, description = "TC_MNT_PGA_002: Verify confirmation modal appears upon clicking 'Purge All'")
    public void testPurgeAllConfirmationModal() {
        maintenancePage.navigateToPurgeCandidateRecords();
        maintenancePage.searchCandidateVacancyIfExists("Senior QA Lead");
        maintenancePage.clickPurgeAll();

        Assert.assertTrue(
            maintenancePage.isPurgeModalDisplayed(),
            "Confirmation modal did not open upon clicking 'Purge All'."
        );
        maintenancePage.closePurgeAll();
    }

    @Test(priority = 10, description = "TC_MNT_PGA_003: Verify cancellation of 'Purge All' action")
    public void testCancelPurgeAll() {
        maintenancePage.navigateToPurgeCandidateRecords();
        maintenancePage.searchCandidateVacancyIfExists("Senior QA Lead");
        maintenancePage.clickPurgeAll();
        maintenancePage.cancelPurgeAll();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("purgeCandidateData"),
            "User was not returned to grid after canceling purge."
        );
    }

    @Test(priority = 11, description = "TC_MNT_PGA_004: Verify successful execution of 'Purge All' action")
    public void testExecutePurgeAll() {
        maintenancePage.navigateToPurgeCandidateRecords();
        maintenancePage.searchCandidateVacancyIfExists("Software Engineer");
        maintenancePage.clickPurgeAll();
        maintenancePage.confirmPurgeAll();

        Assert.assertTrue(
            maintenancePage.isSuccessToastDisplayed() || maintenancePage.getRecordsFoundCount() == 0,
            "Purge All execution did not succeed or update candidate records."
        );
    }


    @Test(priority = 12, description = "TC_MNT_PGA_005: Verify 'Purge All' button state when no records are found")
    public void testPurgeAllButtonStateWhenNoRecordsFound() {
        maintenancePage.navigateToPurgeCandidateRecords();

        // Select an existing vacancy from dropdown that has no candidate applications
        String vacancyName = "Junior Account Assistant";
        boolean vacancyExists = maintenancePage.searchCandidateVacancyIfExists(vacancyName);

        if (vacancyExists) {
            // Check if search returned zero candidate records
            if (maintenancePage.isNoRecordsFoundDisplayed()) {
                // Verify 'Purge All' button is NOT displayed/enabled when 0 records are found
                Assert.assertFalse(
                    maintenancePage.isPurgeAllButtonDisplayed(),
                    "'Purge All' button should be hidden or disabled when 'No Records Found' is displayed."
                );
            } else {
                // If records were found for this vacancy, verify 'Purge All' button IS displayed
                Assert.assertTrue(
                    maintenancePage.isPurgeAllButtonDisplayed(),
                    "'Purge All' button should be displayed when records are found."
                );
            }
        } else {
            Assert.fail("No valid Vacancy options were available in the dropdown to perform search.");
        }
    }
    // --- Access Records & Download Personal Data Test Cases ---

    @Test(priority = 13, description = "TC_MNT_008: Verify navigation to Access Records page")
    public void testNavigateToAccessRecords() {
        maintenancePage.navigateToAccessRecords();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("accessEmployeeData"),
            "Failed to navigate to Access Records page."
        );
    }

    @Test(priority = 14, description = "TC_MNT_009: Verify searching employee for personal data download")
    public void testSearchEmployeeForPersonalData() {
        maintenancePage.navigateToAccessRecords();

        String employeeName = "Maria N Jones";
        
        boolean employeeExists = maintenancePage.searchEmployeeForPersonalDataIfExists(employeeName);
    
        if (employeeExists) {
            Assert.assertTrue(
                maintenancePage.isSelectedEmployeeSectionDisplayed(),
                "Selected Employee section was not displayed after selecting employee from dropdown."
            );
        } else {
            Assert.assertFalse(
                maintenancePage.isSelectedEmployeeSectionDisplayed(),
                "Selected Employee section should not be displayed when no employee is selected."
            );
        }
    }
    @Test(
    	    priority = 15,
    	    description = "TC_MNT_010: Verify Download Personal Data functionality"
    	)
    	public void testDownloadPersonalData() {

    	    maintenancePage.navigateToAccessRecords();

    	    String employeeName = "Ranga  Akunuri";

    	    boolean employeeExists =
    	        maintenancePage.searchEmployeeForPersonalDataIfExists(employeeName);
    	    if (employeeExists) {
    	    Assert.assertTrue(
    	        employeeExists,
    	        "Employee '" + employeeName + "' was not found in the dropdown."
    	    );

    	    // Verify employee details are displayed
    	    Assert.assertTrue(
    	        maintenancePage.isSelectedEmployeeSectionDisplayed(),
    	        "Selected Employee section was not displayed."
    	    );

    	    // Get Downloads folder
    	    String downloadPath =
    	        System.getProperty("user.home") + File.separator + "Downloads";

    	    File downloadFolder = new File(downloadPath);

    	    // Record files existing before download
    	    File[] filesBefore = downloadFolder.listFiles();

    	    // Click Download Personal Data
    	    maintenancePage.clickDownloadPersonalData();

    	    // Wait for a new file to appear
    	    WebDriverWait wait = new WebDriverWait(
    	        driver,
    	        Duration.ofSeconds(10)
    	    );

    	    boolean fileDownloaded = wait.until(driver -> {

    	        File[] filesAfter = downloadFolder.listFiles();

    	        if (filesAfter == null) {
    	            return false;
    	        }

    	        // Check whether a new file appeared
    	        for (File fileAfter : filesAfter) {

    	            boolean existedBefore = false;

    	            if (filesBefore != null) {
    	                for (File fileBefore : filesBefore) {
    	                    if (fileBefore.getName()
    	                            .equals(fileAfter.getName())) {
    	                        existedBefore = true;
    	                        break;
    	                    }
    	                }
    	            }

    	            if (!existedBefore && fileAfter.isFile()) {
    	                return true;
    	            }
    	        }

    	        return false;
    	    });

    	    // Verify download
    	    Assert.assertTrue(
    	        fileDownloaded,
    	        "Personal data file was not downloaded."
    	    );
    	}  else {   
    		System.out.println(
    	               "Employee '" + employeeName + "' does NOT exist."
    	        );

    	        Assert.assertFalse(
    	            maintenancePage.isSelectedEmployeeSectionDisplayed(),
    	            "Selected Employee section should not be displayed when employee does not exist."
    	        );
    	    }
    }
}

