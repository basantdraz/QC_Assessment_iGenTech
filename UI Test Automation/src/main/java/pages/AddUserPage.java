package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AddUserPage {
    private WebDriver driver;
    private WebDriverWait wait;
   // private By employeeName = By.cssSelector("input[placeholder='Type for hints...']");
   private By userRoleDropdown = By.xpath("//label[text()='User Role']/following::div[1]");
    private By statusDropdown = By.xpath("//label[text()='Status']/following::div[1]");
    private By employeeNameField = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private By usernameField = By.xpath("//label[text()='Username']/following::input[1]");
    private By passwordField = By.xpath("//label[text()='Password']/following::input[1]");
    private By confirmPasswordField = By.xpath("//label[text()='Confirm Password']/following::input[1]");
    private By saveButton = By.cssSelector("button[type='submit']");

    public AddUserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }
    public void addUser(String role, String status, String employeeName, String username, String password) {
        // Select User Role
        wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown));
        driver.findElement(userRoleDropdown).click();
        driver.findElement(By.xpath("//div[@role='option']/span[text()='" + role + "']")).click();

        // Select Status
        driver.findElement(statusDropdown).click();
        driver.findElement(By.xpath("//div[@role='option']/span[text()='" + status + "']")).click();

        // Enter Employee Name
        WebElement empNameField = driver.findElement(employeeNameField);
        empNameField.sendKeys(employeeName.substring(0, 3));

            By dropdownOption = By.cssSelector("div.oxd-autocomplete-option > span");
            wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownOption));

            List<WebElement> options = driver.findElements(dropdownOption);
            for (WebElement option : options) {
                if (option.getText().contains(employeeName)) {
                    option.click();
                    break;
                }
            }

        // Enter Username and Password
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(password);

        driver.findElement(saveButton).click();
    }
}
