package apiTest;

/*
 * API Testing for Localhost OrangeHRM (REST Assured)
 *
 * Note:
 * - This environment has API/ABI enabled.
 * - Ensure the server is running and OAuth client is registered
 *   to obtain access token.
 * - Cookies or Bearer token are used for Authorization.
 *
 * Test Cases:
 *    1. Admin login (capture token/cookies)
 *    2. Create Candidate (POST)
 *    3. Delete Candidate (DELETE)
 */

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrangeHMR_APItestingLocalHost {
    private String accessToken;
    private String candidateId;

    @BeforeClass
    public void setup() {
        //LocalHost Base URL
        // Replace Base URL With your host
        RestAssured.baseURI = "http://localhost:8080";

        // Login payload
        HashMap<String, String> loginPayload = new HashMap<>();
        loginPayload.put("username", "Admin");
        loginPayload.put("password", "admin123");

        // Send login request to API endpoint
        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post("/api/v1/login")
                .then()
                .statusCode(200) 
                .cookie("orangehrm", notNullValue())
                .extract().response();

        int statusCode = response.getStatusCode();
        System.out.println("Login Status code: " + statusCode);
        System.out.println("Login Response: " + response.asString());

        if (statusCode == 200) {
            // Get Token and Print it
            accessToken = response.jsonPath().getString("token");
            System.out.println("Access Token: " + accessToken);
        } else {
            throw new RuntimeException("Login failed, cannot continue tests.");
        }
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
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(candidatePayload)
                .when()
                .post("/api/v1/recruitment/candidate")
                .then()
                .statusCode(201)
                .body("firstName", equalTo("Api2"))
                .body("lastName", equalTo("Tester"))
                .body("email", equalTo("Api112tester@example.com"))
                .body("id", notNullValue())
                .extract().response();

        System.out.println("Create Candidate Response: " + response.asString());
        System.out.println("Status code: " + response.getStatusCode());

        if (response.getStatusCode() == 201) {
            candidateId = response.jsonPath().getString("id");
            System.out.println("New Candidate ID: " + candidateId);
            System.out.println("Candidate created successfully.");
        } else {
            throw new RuntimeException("Candidate creation failed, status code: " + response.getStatusCode());
        }
    }

    @Test(priority = 2)
    public void deleteCandidate(){
        if(candidateId == null){
            throw new RuntimeException("No candidate ID available, cannot delete.");
        }

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete("/api/v1/recruitment/candidate/" + candidateId)
                .then()
                .extract().response();

        System.out.println("Delete Candidate Response: " + response.asString());
        int statusCode = response.getStatusCode();

        if(statusCode == 200 || statusCode == 204){
            System.out.println("Candidate deleted successfully.");
        } else {
            System.out.println("Delete failed or session expired, status code: " + statusCode);
        }
    }
}

