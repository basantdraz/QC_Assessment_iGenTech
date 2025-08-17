package apiTest;
/*
 * API Testing for OrangeHRM website using REST Assured
 * Using Java HashMap Method
 *
 * Note:
 * - The demo URL (https://opensource-demo.orangehrmlive.com/...)
 *   is UI-only; it does NOT provide real API endpoints.
 * - Capturing cookies or Authorization via API will NOT work.
 * - Code serves as a demonstration/mock and will only work
 *   on a locally hosted or ABI-enabled OrangeHRM server.
 *
 *
 * - (@BeforeClass), we perform an Admin login to capture cookies/session.
 * - These cookies will be used in subsequent API requests for authentication.
 *  Test Cases:
 *    - Create a new Candidate (POST)
 *    - Delete the created Candidate (DELETE)
 */

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class OrangeHMR_APItesting {
    private Cookies cookies;
    private String candidateId;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://opensource-demo.orangehrmlive.com";

        // Login payload
        HashMap<String, String> loginPayload = new HashMap<>();
        loginPayload.put("username", "Admin");
        loginPayload.put("password", "admin123");

        // Send login request with assertions

        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .redirects().follow(false)
                .when()
                .post("/web/index.php/auth/validate")
                .then()
                .statusCode(200) //Expected to return 302
                .cookie("orangehrm", notNullValue())
                .extract().response();

        // Get cookies and print it
        cookies = response.detailedCookies();
        System.out.println("\nCaptured Cookies: " + cookies);
        System.out.println("Login Status code: " + response.getStatusCode());

      /*  if (cookies.asList().size() > 0) {
            System.out.println("Login success, cookies captured.");
        } else {
            System.out.println("Login failed or session expired!");
        }*/
    }
    @Test(priority = 1)
    public void addNewCandidate(){
        // Candidate payload
        HashMap<String, Object> candidatePayload = new HashMap<>();
        candidatePayload.put("firstName", "Api2");
        candidatePayload.put("lastName", "Tester");
        candidatePayload.put("email", "Api112tester@example.com");
        candidatePayload.put("contactNumber", "01012345678");

        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .body(candidatePayload)
                .when()
                .post("/web/index.php/recruitment/viewCandidates")
                .then()
                .statusCode(201)
                .body("firstName", equalTo("Api2"))
                .body("lastName", equalTo("Tester"))
                .body("email", equalTo("Api112tester@example.com"))
                .body("id", notNullValue())
                .extract().response();

        System.out.println("Create response: " + response.asString());
        System.out.println("Status code: " + response.getStatusCode());

        candidateId = response.jsonPath().getString("id");
        System.out.println("New Candidate ID: " + candidateId);

        if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            System.out.println("Candidate created successfully (if API supported).");
        } else if (response.getStatusCode() == 401 || response.getStatusCode() == 403) {
            System.out.println("Unauthorized: check login/session.");
        } else {
            System.out.println("Candidate not created, check endpoint or payload!");
        }

    }

    @Test(priority = 1)
    public void deleteRecordCandidate(){

        //This is a mock ID, it could be replaced manually
        //candidateId = "22";

        Response response = given()
                .cookies(cookies)
                .when()
                .delete("/web/index.php/api/v2/recruitment/candidates/" + candidateId)
                .then()
                .extract().response();

        System.out.println("Delete response: " + response.asString());

        int statusCode = response.getStatusCode();
        if (statusCode == 200 || statusCode == 204) {
            System.out.println("Delete request sent successfully.");
        } else {
            System.out.println("Delete failed or session expired, status code: " + statusCode);
        }
    }
}
