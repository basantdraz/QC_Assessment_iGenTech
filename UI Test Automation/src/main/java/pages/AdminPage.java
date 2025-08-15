package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AdminPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By addButton = By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--secondary']");
    private By recordText = By.cssSelector("div[class='orangehrm-horizontal-padding orangehrm-vertical-padding']");
    private By searchByNameField =By.xpath("//label[text()='Username']/following::input[1]");
    private By searchButton =By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']");
    private By deleteButton = By.cssSelector("i[class='oxd-icon bi-trash']");
    private By popDeleteButton = By.xpath("//button[contains(@class, 'oxd-button--label-danger') and contains(., 'Yes, Delete')]");

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public int getCurrentRecordCount(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(recordText));
        String text = driver.findElement(recordText).getText();
        return Integer.parseInt(text.split("\\(")[1].split("\\)")[0]);
    }

    public void clickAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        driver.findElement(addButton).click();
    }

    public void searchUser(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchByNameField));
        driver.findElement(searchByNameField).sendKeys(username);

        driver.findElement(searchButton).click();

    }
    public void deleteUser() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        driver.findElement(deleteButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(popDeleteButton));
        driver.findElement(popDeleteButton).click();

    }
}
