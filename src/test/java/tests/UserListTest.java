package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Admin.UserListPage;
import pages.LoginPage;

public class UserListTest  extends BaseTest {
    LoginPage loginPage;
    UserListPage userListPage;

    @BeforeClass
    public void setup() {
        userListPage = new UserListPage(driver);

        // Click Admin menu
        userListPage.clickAdminMenu();
    }

    @Test
    public void testResetButton() {

        userListPage.searchUsername("Admin");
        userListPage.clickReset();
        String usernameValue = driver.findElement(By.xpath("//label[text()='Username']/following::input[1]")).getAttribute("value");
        Assert.assertEquals("", usernameValue,
                "Username field was not reset");

    }

    @Test(
            priority = 2,
            description = "Verify existing user can be searched"
        )
        public void testSearchExistingUser() {

            String username = "Admin";

            // Search Admin
            userListPage.searchUsername(username);

            // Check whether Admin exists
            boolean result =
                    userListPage.isUsernamePresent(username);

            // Assertion
            Assert.assertTrue(
                    result,
                    "Username was not found"
            );

        }
    
    @Test(priority = 3, description = "Verify search behavior when searching for a non-existent user")
    public void testSearchNonExistentUser() {

        userListPage.clickReset();
        String invalidUsername = "InvalidUser_9999";
        
        userListPage.searchUsername(invalidUsername);
        Assert.assertTrue(
                userListPage.isNoRecordsFoundDisplayed(),
                "Invalid user should not be displayed"
            );
    }

  
    @Test(priority = 4, description = "Verify Add button")
    public void testAddButton() {

        userListPage.clickAdd();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains("/admin/saveSystemUser"),
                "Add User page was not displayed"
        );
    }
    
    @Test(priority = 5, description = "Verify Edit icon")
    public void verifyEditUser() {

        driver.navigate().back();
        userListPage.clickReset();
        userListPage.editUser("Admin");
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains("/admin/saveSystemUser"),
                "Edit User page was not displayed"
        );
    }
    
    @Test(priority = 8, description = "Verify Delete icon")
    public void verifyDeleteUser() {
        driver.navigate().back();
        userListPage.clickReset();

        String targetUser = "FMLName";

        // 1. Verify if user exists before attempting delete
        boolean userExists = userListPage.isUsernamePresents(targetUser);
        
        // Fail fast with a clear message if the user isn't in the table
        Assert.assertTrue(userExists, "Cannot delete: User '" + targetUser + "' was not found in the list.");

        // 2. Perform delete sequence
        userListPage.deleteUser(targetUser);	
        userListPage.confirmDelete();

        // 3. Verify user is no longer present after deletion
        Assert.assertFalse(
            userListPage.isUsernamePresent(targetUser),
            "User was not deleted successfully"
        );
    }
}
