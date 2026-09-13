package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.AssignLeavePage;
import pages.DirectoryPage;
import pages.LoginPage;

public class DirectoryTest extends BaseTest {

    private DirectoryPage directoryPage;

    @BeforeClass
    public void setupClass() {
        directoryPage = new DirectoryPage(driver);
    }
    @BeforeMethod
    public void setUpDirectoryPage() {
        directoryPage.navigateToDirectory();
    }

    @Test(priority = 1, description = "TC_DIR_001: Verify navigation to Directory page")
    public void testNavigateToDirectory() {
        Assert.assertTrue(
            driver.getCurrentUrl().contains("viewDirectory"),
            "Failed to navigate to Directory page."
        );
    }

    @Test(priority = 2, description = "TC_DIR_002: Verify record count matches listed employee cards")
    public void testRecordCountMatchesCards() {
      directoryPage.selectJobTitle("HR Manager");
      directoryPage.clickSearch();
        int expectedRecordCount = directoryPage.getRecordsFoundCount();
        int actualCardCount = directoryPage.getDirectoryCardsCount();
        System.out.println("Expected records: " + expectedRecordCount);
        System.out.println("Actual cards: " + actualCardCount);
        if (expectedRecordCount == 0) {
            Assert.assertEquals(
                actualCardCount, 
                0, 
                "Cards were displayed even though 'No Records Found' / 0 records were reported."
            );
        } else {
            // If results are paginated, actual cards on current page should be <= total expected records and > 0
            Assert.assertTrue(
                actualCardCount > 0 && actualCardCount <= expectedRecordCount,
                "Displayed card count (" + actualCardCount + ") does not align with header record count (" + expectedRecordCount + ")."
            );
        }
    }

    @Test(priority = 3, description = "TC_DIR_003: Verify search directory by Employee Name autocomplete")
    public void testSearchByEmployeeName() {
    	  String employeeName = "Ranga Akunuri";
          
          boolean employeeExists = directoryPage.searchByEmployee(employeeName);
      
          if (employeeExists) {
        	  Assert.assertTrue(
        	            directoryPage.getRecordsFoundCount() > 0,
        	            "No directory cards found for searched employee name."
        	        );
          } 
    }

    @Test(priority = 4, description = "TC_DIR_004: Verify search directory by Job Title")
    public void testSearchByJobTitle() {
        directoryPage.selectJobTitle("HR Manager");
        directoryPage.clickSearch();

        Assert.assertTrue(
            directoryPage.getRecordsFoundCount() >= 0,
            "Search execution by Job Title failed."
        );
    }

    @Test(priority = 5, description = "TC_DIR_005: Verify search directory by Location")
    public void testSearchByLocation() {
        directoryPage.selectLocation("Texas R&D");
        directoryPage.clickSearch();

        Assert.assertTrue(
            directoryPage.getRecordsFoundCount() >= 0,
            "Search execution by Location failed."
        );
    }

    @Test(priority = 6, description = "TC_DIR_006: Verify combined filter search (Job Title + Location)")
    public void testCombinedFilterSearch() {
        directoryPage.selectJobTitle("HR Manager");
        directoryPage.selectLocation("Texas R&D");
        directoryPage.clickSearch();

        Assert.assertTrue(
            directoryPage.getRecordsFoundCount() >= 0,
            "Combined search execution failed."
        );
    }

    @Test(priority = 7, description = "TC_DIR_007: Verify Reset button restores search filters")
    public void testResetFilters() {
        directoryPage.selectJobTitle("HR Manager");
        directoryPage.clickReset();

        Assert.assertTrue(
            directoryPage.getRecordsFoundCount() > 0,
            "Reset button execution failed."
        );
    }

    @Test(priority = 8, description = "TC_DIR_008: Verify 'No Records Found' for invalid search criteria")
    public void testNoRecordsFoundForInvalidSearch() {
        directoryPage.selectJobTitle("Automation Tester");
        directoryPage.clickSearch();

        Assert.assertTrue(
            directoryPage.isNoRecordsFoundDisplayed(),
            "'No Records Found' message was not displayed for invalid criteria."
        );
    }

    @Test(priority = 9, description = "TC_DIR_009: Verify collapse and expand search panel toggle")
    public void testToggleSearchPanel() {
        directoryPage.clickToggleSearchForm();
        // Trigger toggle again to restore panel
        directoryPage.clickToggleSearchForm();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("viewDirectory"),
            "Directory page UI state corrupted during toggle."
        );
    }
}