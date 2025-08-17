package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddUserPage;
import pages.AdminPage;
import pages.DashboardPage;
import pages.LoginPage;

import static org.testng.Assert.assertEquals;

public class AdminTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

    }

    @Test
    public void AddClearUserTest(){
        // 1. Navigate to website (handled in SetUP)

        // 2. Login With correct admin's credentials
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin","admin123");

        // 3. Click Admin tab in the lift-side menu in dashboard page
        DashboardPage dashboardPage= new DashboardPage(driver);
        dashboardPage.clickAdminTab();

        // 4. Get initial record count
        AdminPage adminPage = new AdminPage(driver);
        int initrecord = adminPage.getCurrentRecordCount();
        System.out.println("\nInit Record Found: "+ initrecord);

        //5,6.Add new user, fill user data and Save
        adminPage.clickAddButton();
        AddUserPage addUserPage =new AddUserPage(driver);
        addUserPage.addUser("ESS", "Enabled", "Jan Test Kowalski", "testeruser","test12345");

        // 7. Verify record count increased by 1
        int afterAddRecordCount = adminPage.getCurrentRecordCount();
        System.out.println("Record Found After add new user: "+ afterAddRecordCount);
        assertEquals(afterAddRecordCount, initrecord + 1, "Record count should increase by 1 after adding user");

        // 8. Search for the new user
        adminPage.searchUser("testeruser");

        // 9. Delete The user added
        adminPage.deleteUser();

        //10. Verify the record number decreased by 1
        driver.navigate().refresh();
        int afterDeleteRecordCount = adminPage.getCurrentRecordCount();
        System.out.println("Record Found After Delete the new user: "+ afterDeleteRecordCount);
        assertEquals(afterDeleteRecordCount, initrecord, "Record count should return to initial after deletion");

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
