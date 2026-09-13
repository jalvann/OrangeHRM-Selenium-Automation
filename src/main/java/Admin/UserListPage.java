package Admin;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;				

public class UserListPage {
	private WebDriver driver;
    private WebDriverWait wait;
	
	// Locators
    private By adminMenuTab = By.xpath("//span[text()='Admin']");    
    private By pageTitle = By.xpath("//h5[text()='System Users']");
    private By usernameInput = By.xpath("//label[text()='Username']/following::input[1]");
    private By userRoleDropdown = By.xpath("//label[text()='User Role']/following::div[@class='oxd-select-wrapper'][1]");
    private By employeeNameInput = By.xpath("//label[text()='Employee Name']/following::input[1]"); 
    private By statusDropdown = By.xpath("//label[text()='Status']/following::div[contains(@class,'oxd-select-text')][1]");   
    private By resetButton = By.xpath("//button[normalize-space()='Reset']");
    private By addButton = By.xpath("//button[contains(normalize-space(),'Add')]");
    private By deleteIcon = By.xpath(".//i[contains(@class,'bi-trash')]");
    private By editIcon = By.xpath(".//i[contains(@class,'bi-pencil-fill')]");
    private By searchBtn = By.xpath("//button[@type='submit']");
    
 // Search Results Table
    private By tableRows = By.xpath("//div[@class='oxd-table-card']");
    private By usernameCells = By.xpath("//div[@class='oxd-table-card']//div[@role='cell'][2]");
    private By userroleCells = By.xpath("//div[@class='oxd-table-card']//div[@role='cell'][3]");
    private By employeeCells = By.xpath("//div[@class='oxd-table-card']//div[@role='cell'][4]");
    private By statusCells = By.xpath("//div[@class='oxd-table-card']//div[@role='cell'][5]");
    private By noRecordsFound = By.xpath("//span[text()='No Records Found']");
   
    //conSTRUctoR
    public UserListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAdminMenu() {
        driver.findElement(adminMenuTab).click();
    }
    // Get page titled
    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }
    //reset
    public void clickReset() {
        driver.findElement(resetButton).click();
    }

    // Click Add
    public void clickAdd() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By toast = By.cssSelector(".oxd-toast-container");

        // Wait for toast to disappear if it is displayed
        wait.until(ExpectedConditions.invisibilityOfElementLocated(toast));

        // Wait until Add button is clickable
        WebElement add = wait.until(
                ExpectedConditions.elementToBeClickable(addButton)
        );

        add.click();
    }

//search by username
    public void searchUsername(String username) { 
    	WebElement searchBox = driver.findElement(usernameInput);

    	    searchBox.clear();
    	    searchBox.sendKeys(username);
        driver.findElement(searchBtn).click();
    }
    
    //search by userrole
    public void selectUserRole(String role) {

        driver.findElement(userRoleDropdown).click();
        By roleOption = By.xpath(
            "//div[@role='option']//span[text()='" + role + "']"
        );

        driver.findElement(roleOption).click();
        driver.findElement(searchBtn).click();
    }
    //search by employee
    public void enterEmployeeName(String employeeName) {
        driver.findElement(employeeNameInput).sendKeys(employeeName);
        driver.findElement(searchBtn).click();
    }
    //search by status
    public void selectStatus(String status) {

        driver.findElement(statusDropdown).click();
        By Option = By.xpath(
            "//div[@role='option']//span[text()='" + status + "']"
        );

        driver.findElement(Option).click();
        driver.findElement(searchBtn).click();
    }
    
    public boolean isNoRecordsFoundDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noRecordsFound)).isDisplayed();
    }
    
//table record count
    public int getRecordCount() {

        List<WebElement> rows =
                driver.findElements(tableRows);

        return rows.size();
    }
    
 // Check whether username exists in the table
    public boolean isUsernamePresent(String expectedUsername) {
    	
        List<WebElement> usernames = driver.findElements(usernameCells);

        for (WebElement username : usernames) {

            String actualUsername = username.getText();

            System.out.println("Username found: " + actualUsername);

            if (actualUsername.equalsIgnoreCase(expectedUsername)) {
                return true;
            }
        }

        return false;
    }
    public boolean isUsernamePresents(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Wait for OrangeHRM spinner/loader to disappear if present
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'oxd-form-loader') or contains(@class, 'oxd-loading-spinner')]")
            ));
        } catch (Exception ignored) {}

        // Flexible XPath: accounts for space padding and nested spans
        By userLocator = By.xpath("//div[@role='row']//div[normalize-space(text())='" + username + "']");
        
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(userLocator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    // Check whether userrole exists in the table
       public boolean isUserrolePresent(String expectedUserrole) {
       	
           List<WebElement> userroles = driver.findElements(userroleCells);

           for (WebElement userrole : userroles) {

               String actualUserrole = userrole.getText();

               System.out.println("Userrole found: " + actualUserrole);

               if (actualUserrole.equalsIgnoreCase(expectedUserrole)) {
                   return true;
               }
           }

           return false;
       }
       // Check whether employee exists in the table
       public boolean isEmployeePresent(String expectedEmployee) {
       	
           List<WebElement> employees = driver.findElements(employeeCells);

           for (WebElement employee : employees) {

               String actualEmployee = employee.getText();

               System.out.println("Emplyee found: " + actualEmployee);

               if (actualEmployee.equalsIgnoreCase(expectedEmployee)) {
                   return true;
               }
           }

           return false;
       }

       // Check whether status exists in the table
       public boolean isStatusPresent(String expectedStatus) {
       	
           List<WebElement> statuss = driver.findElements(statusCells);

           for (WebElement status : statuss) {

               String actualStatus = status.getText();

               System.out.println("Status found: " + actualStatus);

               if (actualStatus.equalsIgnoreCase(expectedStatus)) {
                   return true;
               }
           }

           return false;
       }
       //edit button
       public void editUser(String username) {

           String currentUrl = driver.getCurrentUrl();
           System.out.print(currentUrl);
    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    	    By userRow = By.xpath(
    	        "//div[@role='row'][.//div[normalize-space()='" + username + "']]"
    	    );

    	    WebElement row = wait.until(
    	        ExpectedConditions.visibilityOfElementLocated(userRow)
    	    );

    	    WebElement edit = row.findElement(editIcon);

    	    wait.until(ExpectedConditions.elementToBeClickable(edit));

    	    edit.click();
    	}

       //delete button
    // Inside your UserListPage.java class
       public void deleteUser(String username) {
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
           
           // Wait until the row containing the username is visible before clicking delete
           By rowLocator = By.xpath("//div[@role='row'][.//div[text()='" + username + "']]");
           WebElement userRow = wait.until(ExpectedConditions.visibilityOfElementLocated(rowLocator));
           
           // Find and click the delete icon inside that specific row
           WebElement deleteButton = userRow.findElement(By.xpath(".//button[contains(@class, 'trash') or .//i[contains(@class, 'oxd-icon')]]"));
           deleteButton.click();
       }
       
       public void confirmDelete() {
    	    driver.findElement(
    	        By.xpath("//button[normalize-space()='Yes, Delete']")
    	    ).click();
    	}
}
