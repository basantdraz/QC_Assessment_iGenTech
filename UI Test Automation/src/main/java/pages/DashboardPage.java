package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By adminTab = By.xpath("//a[contains(@class, 'oxd-main-menu-item') and contains(@href, 'admin')]");
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void clickAdminTab() {
        wait.until(ExpectedConditions.elementToBeClickable(adminTab));
        driver.findElement(adminTab).click();
    }
}
