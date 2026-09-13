package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Admin.UserListPage;
import Admin.UserPage;
import pages.LoginPage;

public class UserTest  extends BaseTest {
	 LoginPage loginPage;
	 UserListPage userListPage;
	 UserPage userPage;

	    @BeforeClass
	    public void setup() {
	        userListPage = new UserListPage(driver);
	        userPage = new UserPage(driver);

	    }
	    @BeforeMethod
	    public void setupMethod() {
	        // Ensure every test starts cleanly on the Add User page
	        userListPage.clickAdminMenu();
	        userListPage.clickAdd();
	    }
	    @Test(priority = 1, description = "Verify Cancel button")
	    public void testCancelButton() {

	        userPage.clickCancel();

	        String currentUrl = driver.getCurrentUrl();

	        Assert.assertTrue(
	                currentUrl.contains("/admin/viewSystemUsers"),
	                "User List page was not displayed"
	        );
	    }
	@Test
	public void verifyUserSavedSuccessfully() {

        String dynamicUsername = "user_" + System.currentTimeMillis() / 1000;

	    userPage.addUser(
	        "ESS",
	        "David Miller",
	        "Enabled",
	        dynamicUsername,
	        "Test@12345",
	        "Test@12345"
	    );
	    
	 // Assertion 1: Check success toast popup
	    boolean isSaved = userPage.isSuccessToastDisplayed();
	    Assert.assertTrue(isSaved, "Success toast message was not displayed after creating user.");
	 // Assertion 2: Verify user exists in search list
	    Assert.assertTrue(
	        userListPage.isUsernamePresent(dynamicUsername),
	        "User was not saved successfully"
	    );
	}
	@Test(priority = 3, description = "TC-USR-56: Verify special characters in username validation message")
	public void verifySpecialCharactersInUsername() {

	    // 2. Enter invalid username containing special characters
	    String invalidUsername = "#$%%";

	    // Trigger validation (click Save or blur the field)
	    userPage.addUser(
		        "ESS",
		        "David Miller",
		        "Enabled",
		        invalidUsername,
		        "Test@12345",
		        "Test@12345");

	    // 3. Assertion: Verify validation error message appears for invalid characters
	    String expectedErrorMessage = "Should not contain special characters"; // Adjust exact string based on application UI
	    String actualErrorMessage = userPage.getUsernameValidationError();

	    Assert.assertEquals(
	        actualErrorMessage, 
	        expectedErrorMessage, 
	        "Validation message mismatch when entering special characters in username."
	    );
	}
}
