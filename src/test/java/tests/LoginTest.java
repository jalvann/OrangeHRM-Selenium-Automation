package tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.LoginPage;
import Utilities.ExcelUtility;

public class LoginTest {

	protected WebDriver driver;
    
	@BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @BeforeMethod
    public void openLoginPage() {
        // Navigates back to clean login page before every test method/data iteration
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }
    
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {

        String filePath = "src/test/resources/LoginData.xlsx";

        ExcelUtility excel =
                new ExcelUtility(filePath, "LoginData");

        int rows = excel.getRowCount();

        Object[][] data = new Object[rows - 1][4];

        for (int i = 1; i < rows; i++) {

            data[i - 1][0] = excel.getCellData(i, 0); // username
            data[i - 1][1] = excel.getCellData(i, 1); // password
            data[i - 1][2] = excel.getCellData(i, 2); // testType
            data[i - 1][3] = excel.getCellData(i, 3); // expectedResult
        }

        excel.closeWorkbook();

        return data;
    }

    @Test(dataProvider = "loginData", priority = 2)
    public void testLogin(String username,
            String password,
            String testType,
            String expectedResult) {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);
        if (testType.equalsIgnoreCase("invalid")) {

            Assert.assertTrue(
                    loginPage.getErrorMessage()
                             .contains(expectedResult),
                    "Invalid credentials message was not displayed"
            );

        } else if (testType.equalsIgnoreCase("emptyUsername")) {

            Assert.assertEquals(
                    loginPage.getUsernameErrorMessage(),
                    expectedResult,
                    "Username Required message was not displayed"
            );

        } else if (testType.equalsIgnoreCase("emptyPassword")) {

            Assert.assertEquals(
                    loginPage.getPasswordErrorMessage(),
                    expectedResult,
                    "Password Required message was not displayed"
            );
        }else  if (testType.equalsIgnoreCase("valid")) {
        	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
             boolean isNavigated = wait.until(ExpectedConditions.urlContains("dashboard"));

             Assert.assertTrue(
                 isNavigated,
                 "Valid login failed"
             );

        }
    }
    

    @Test(priority = 1, description = "Verify forgot password button")
    public void testForgotPasswordLink() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.clickForgotPasswordLink();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains("/auth/requestPasswordResetCode"),
                "Forgot password page was not displayed"
        );
    }
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}