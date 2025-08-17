package apiTest;
/*
 * API Testing for OrangeHRM website using REST Assured
 * Using Java HashMap Method
 *
 * - (@BeforeClass), we perform an Admin login to capture cookies/session.
 * - These cookies will be used in subsequent API requests for authentication.
 *  Test Cases:
 *    - Create a new Candidate (POST)
 *    - Delete the created Candidate (DELETE)
 */
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrangeHMR_APItesting {
    private String sessionCookie;
    private String candidateId;

    @BeforeClass
    public void setup() {
        //Login vai UI using Selenium WebDriver
        System.setProperty("webdriver.chrome.driver","resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginBtn = driver.findElement(By.cssSelector("button[type='submit']"));
        usernameField.sendKeys("Admin");
        passwordField.sendKeys("admin123");
        loginBtn.click();

        //Get and Save Cookie session
        Set<org.openqa.selenium.Cookie> cookies = driver.manage().getCookies();
        for (org.openqa.selenium.Cookie ck : cookies) {
            System.out.println("Browser Cookie: " + ck.getName() + " = " + ck.getValue());
            if (ck.getName().equals("orangehrm")) {
                sessionCookie = ck.getValue();
            }
        }
        driver.quit();

        RestAssured.baseURI = "https://opensource-demo.orangehrmlive.com";

        if (sessionCookie != null) {
            System.out.println("Captured Session Cookie from Browser: " + sessionCookie);
        } else {
            System.out.println("Failed to capture session cookie!");
        }
    }

    @Test(priority = 1)
    public void addNewCandidate(){
        // Candidate payload
        HashMap<String, Object> candidatePayload = new HashMap<>();
        candidatePayload.put("firstName", "ApiOrangeHRM");
        candidatePayload.put("lastName", "Tester");
        candidatePayload.put("email", "apihrm112tester@example.com");
        candidatePayload.put("contactNumber", "01012345678");

        System.out.println("Request Body: " + candidatePayload);

        Response response = given()
                .cookie("orangehrm", sessionCookie)
                .contentType("application/json")
                .body(candidatePayload)
                .when()
                .post("/web/index.php/api/v2/recruitment/candidates")
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .body("data.firstName", equalTo("ApiOrangeHRM"))
                .body("data.lastName", equalTo("Tester"))
                .body("data.email", equalTo("apihrm112tester@example.com"))
                .extract().response();

        //System.out.println("Create response: " + response.asString());
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response Body:");
        response.prettyPrint();

        candidateId = response.jsonPath().getString("data.id");
        System.out.println("New Candidate ID: " + candidateId);

        //Extra Assertions
        Assert.assertNotNull(candidateId, "WARNING: Candidate ID should not be null!");
        Assert.assertFalse(candidateId.trim().isEmpty(), "WARNING:  Candidate ID should not be empty!");
        Assert.assertTrue(response.asString().contains("ApiOrange"), "WARNING:  Response does not contain candidate firstName!");

        // Optional part – just to double-check status code and catch issues
        /* if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            System.out.println("Candidate created successfully (if API supported).");
        } else if (response.getStatusCode() == 401 || response.getStatusCode() == 403) {
            System.out.println("Unauthorized: check login/session.");
        } else {
            System.out.println("Candidate not created, check endpoint or payload!");
        }*/

    }

    @Test(priority = 2)
    public void deleteRecordCandidate(){

        if (candidateId == null) {
            System.out.println("WARNING: No candidate created, skipping delete.");
            return;
        }
        String requestBody = String.format("{\"ids\":[%s]}", candidateId);

        // Delete Request
        Response response = given()
                .cookie("orangehrm", sessionCookie)
                .contentType("application/json")
                .accept("application/json")
                .body(requestBody)
                .when()
                .delete("/web/index.php/api/v2/recruitment/candidates")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Delete Status Code: " + response.getStatusCode());
        System.out.println("Delete Response Body:");
        response.prettyPrint();

        //Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        System.out.println("Delete request completed successfully");
        
        // Optional part – just to double-check status code and catch issues
        /*
        int statusCode = response.getStatusCode();
        if (statusCode == 200 || statusCode == 204) {
            System.out.println("Delete request sent successfully.");
        } else {
            System.out.println("Delete failed or session expired, status code: " + statusCode);
        }*/
    }
}

