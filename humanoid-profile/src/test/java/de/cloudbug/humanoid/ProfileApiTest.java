package de.cloudbug.humanoid;

import de.cloudbug.humanoid.profile.Profile;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
//@QuarkusTestResource(DatabaseResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileApiTest {

    private static final String DEFAULT_FIRSTNAME = "Firstname";
    private static final String DEFAULT_LASTNAME = "Lastname";
    private static final String DEFAULT_EMPLOYEEID = "123456";
    private static final String UPDATED_FIRSTNAME = "New Firstname";
    private static final String UPDATED_LASTNAME = "New Lastname";
    private static final String UPDATED_EMPLOYEEID = "987654";

    private static String profileId;

    @Test
    @Order(2)
    void shouldAddAnItem() {
        Profile profile = new Profile();
        profile.firstName = DEFAULT_FIRSTNAME;
        profile.lastName = DEFAULT_LASTNAME;
        profile.employeeId = DEFAULT_EMPLOYEEID;

        String location = given()
                .body(profile)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .post("/api/v1/profile")
                .then()
                .statusCode(CREATED.getStatusCode())
                .extract().header("Location");
        assertTrue(location.contains("/api/v1/profile"));

        // Stores the id
        String[] segments = location.split("/");
        profileId = segments[segments.length - 1];
        assertNotNull(profileId);

        given()
                .pathParam("id", profileId)
                .when().get("/api/v1/profile/{id}")
                .then()
                .statusCode(OK.getStatusCode())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body("firstName", Is.is(DEFAULT_FIRSTNAME))
                .body("lastName", Is.is(DEFAULT_LASTNAME))
                .body("employeeId", Is.is(DEFAULT_EMPLOYEEID));

    }

    @Test
    @Order(3)
    void shouldUpdateAnItem() {
        Profile profile = new Profile();
        profile.firstName = UPDATED_FIRSTNAME;
        profile.lastName = UPDATED_LASTNAME;
        profile.employeeId = UPDATED_EMPLOYEEID;

        given()
                .body(profile)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .put("/api/v1/profile")
                .then()
                .statusCode(OK.getStatusCode())
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body("firstName", Is.is(UPDATED_FIRSTNAME))
                .body("lastName", Is.is(UPDATED_LASTNAME))
                .body("employeeId", Is.is(UPDATED_EMPLOYEEID));
    }

    @Test
    @Order(4)
    void shouldRemoveAnItem() {
        given()
                .pathParam("id", profileId)
                .when().delete("/api/v1/profile/{id}")
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

    @Test
    void shouldPingOpenAPI() {
        given()
                .header(ACCEPT, APPLICATION_JSON)
                .when().get("/openapi")
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void shouldPingSwaggerUI() {
        given()
                .when().get("/swagger-ui")
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void shouldPingLiveness() {
        given()
                .when().get("/health/live")
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void shouldPingReadiness() {
        given()
                .when().get("/health/ready")
                .then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void shouldPingMetrics() {
        given()
                .header(ACCEPT, APPLICATION_JSON)
                .when().get("/metrics/application")
                .then()
                .statusCode(OK.getStatusCode());
    }

    private TypeRef<List<Profile>> getProfileTypeRef() {
        return new TypeRef<>() {
            // Kept empty on purpose
        };
    }

}