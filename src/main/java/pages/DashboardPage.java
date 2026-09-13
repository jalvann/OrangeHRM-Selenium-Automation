package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {
	private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private By userDropdown = By.className("oxd-userdropdown-tab");
    private By logoutLink = By.xpath("//a[text()='Logout']");
    private By quickLaunchWidget = By.xpath("//p[text()='Quick Launch']");
    private By timeAtWorkWidget = By.xpath("//p[text()='Time at Work']");
    private By myActionsWidget = By.xpath("//p[text()='My Actions']");
    private By userName = By.className("oxd-userdropdown-name");
    private By aboutLink = By.xpath("//a[text()='About']");
    private By supportLink = By.xpath("//a[text()='Support']");
    private By changePasswordLink = By.xpath("//a[text()='Change Password']");
    // Time at Work Widget
    private By punchClockIcon = By.cssSelector(".orangehrm-attendance-card-action");
    private By punchStatusText = By.cssSelector(".orangehrm-attendance-card-state");
    // My Actions Widget
    private By pendingSelfReviewLink = By.xpath("//p[contains(.,'Pending Self Review')]");
   //Help
    private By helpButton = By.xpath("//button[@title='Help']");
 // Employees on Leave Today Widget
    private By leaveWidgetGearIcon = By.cssSelector(".oxd-icon.bi-gear-fill");
    private By modalCloseIcon = By.cssSelector(".oxd-dialog-close-archive, button.oxd-dialog-close-button");
 // Quick Launch Widget
    private By applyLeaveQuickLaunchBtn = By.xpath("//button[@title='Apply Leave']");
    
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
 // Actions
    public String getHeaderTitleText() {
        return driver.findElement(dashboardHeader).getText();
    }

    public String getTimeAtWorkHeaderText() {
        return driver.findElement(timeAtWorkWidget).getText();
    }

    public String getMyActionsHeaderText() {
        return driver.findElement(myActionsWidget).getText();
    }

    public String getQuickLaunchHeaderText() {
        return driver.findElement(quickLaunchWidget).getText();
    }

    public void clickUserDropdown() {
        driver.findElement(userDropdown).click();
    }

    public String getUserName() {
        return driver.findElement(userName).getText();
    }

    public void clickAbout() {
        driver.findElement(aboutLink).click();
    }

    public void clickSupport() {
        driver.findElement(supportLink).click();
    }

    public void clickChangePassword() {
        driver.findElement(changePasswordLink).click();
    }

    public void clickLogout() {
        driver.findElement(logoutLink).click();
    }
 // Punchclock navigation
    public void clickPunchClock() {
        wait.until(ExpectedConditions.elementToBeClickable(punchClockIcon)).click();
    }

    public String getPunchStatus() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(punchStatusText)).getText();
    }
    /**
     * Clicks the Help (?) icon located in the top toolbar
     */
    public void clickHelpButton() {
        wait.until(ExpectedConditions.elementToBeClickable(helpButton)).click();
    }
   
    // Quick Launch
    public void clickApplyLeaveShortcut() {
        wait.until(ExpectedConditions.elementToBeClickable(applyLeaveQuickLaunchBtn)).click();
    }
    //My Action widget
    public void clickPendingSelfReview() {
        wait.until(ExpectedConditions.elementToBeClickable(pendingSelfReviewLink)).click();
    }
 // emplyee on leave
    public void clickLeaveWidgetSettings() {
        wait.until(ExpectedConditions.elementToBeClickable(leaveWidgetGearIcon)).click();
    }
    /**
     * Clicks the 'X' (Close) button in the top right corner of the Configurations modal
     */
    public void clickModalCloseIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(modalCloseIcon)).click();
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
}
