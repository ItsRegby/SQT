import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BookingTests {

    private static String token;

    /**
     * <test>
     *     <name>Setup</name>
     *     <description>Initializes the base URI and obtains an authentication token</description>
     *     <expected>Authentication token is successfully retrieved</expected>
     * </test>
     */
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        token = given()
                .contentType(ContentType.JSON)
                .body("{\"username\":\"admin\",\"password\":\"password123\"}")
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    /**
     * <test>
     *     <name>Get Booking IDs</name>
     *     <description>Retrieves all booking IDs from the system</description>
     *     <steps>
     *         <step>Send GET request to /booking endpoint</step>
     *     </steps>
     *     <expected>
     *         <assert>Status code is 200</assert>
     *         <assert>Response body contains at least one booking ID</assert>
     *     </expected>
     * </test>
     */
    @Test
    public void testGetBookingIds() {
        given()
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    /**
     * <test>
     *     <name>Create Booking</name>
     *     <description>Creates a new booking in the system</description>
     *     <steps>
     *         <step>Prepare booking data</step>
     *         <step>Send POST request to /booking endpoint with booking data</step>
     *     </steps>
     *     <expected>
     *         <assert>Status code is 200</assert>
     *         <assert>Response body contains correct firstname</assert>
     *         <assert>Response body contains correct lastname</assert>
     *     </expected>
     * </test>
     */
    @Test
    public void testCreateBooking() {
        String bookingBody = "{\"firstname\":\"Jim\",\"lastname\":\"Brown\",\"totalprice\":111,\"depositpaid\":true,\"bookingdates\":{\"checkin\":\"2023-01-01\",\"checkout\":\"2023-01-02\"}}";

        given()
                .contentType(ContentType.JSON)
                .body(bookingBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .body("booking.firstname", equalTo("Jim"))
                .body("booking.lastname", equalTo("Brown"));
    }

    /**
     * <test>
     *     <name>Update Booking</name>
     *     <description>Updates an existing booking in the system</description>
     *     <steps>
     *         <step>Prepare updated booking data</step>
     *         <step>Send PUT request to /booking/1 endpoint with updated data and auth token</step>
     *     </steps>
     *     <expected>
     *         <assert>Status code is 200</assert>
     *         <assert>Response body contains updated firstname</assert>
     *     </expected>
     * </test>
     */
    @Test
    public void testUpdateBooking() {
        String updateBody = "{\"firstname\":\"James\",\"lastname\":\"Brown\",\"totalprice\":111,\"depositpaid\":true,\"bookingdates\":{\"checkin\":\"2023-01-01\",\"checkout\":\"2023-01-02\"}}";

        given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + token)
                .body(updateBody)
                .when()
                .put("/booking/1")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("James"));
    }

    /**
     * <test>
     *     <name>Partial Update Booking</name>
     *     <description>Partially updates an existing booking in the system</description>
     *     <steps>
     *         <step>Prepare partial booking data</step>
     *         <step>Send PATCH request to /booking/1 endpoint with partial data and auth token</step>
     *     </steps>
     *     <expected>
     *         <assert>Status code is 200</assert>
     *         <assert>Response body contains updated firstname</assert>
     *         <assert>Response body contains updated lastname</assert>
     *     </expected>
     * </test>
     */
    @Test
    public void testPartialUpdateBooking() {
        String partialUpdateBody = "{\"firstname\":\"Jim\",\"lastname\":\"Brown\"}";

        given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + token)
                .body(partialUpdateBody)
                .when()
                .patch("/booking/1")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Jim"))
                .body("lastname", equalTo("Brown"));
    }

    /**
     * <test>
     *     <name>Delete Booking</name>
     *     <description>Deletes an existing booking from the system</description>
     *     <steps>
     *         <step>Send DELETE request to /booking/1 endpoint with auth token</step>
     *     </steps>
     *     <expected>
     *         <assert>Status code is 201</assert>
     *     </expected>
     * </test>
     */
    @Test
    public void testDeleteBooking() {
        given()
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/1")
                .then()
                .statusCode(201);
    }

    /**
     * <test>
     *     <name>Unauthorized Access</name>
     *     <description>Attempts to update a booking without proper authorization</description>
     *     <steps>
     *         <step>Prepare booking data</step>
     *         <step>Send PUT request to /booking/1 endpoint without auth token</step>
     *     </steps>
     *     <expected>
     *         <assert>Status code is 403 (Forbidden)</assert>
     *     </expected>
     * </test>
     */
    @Test
    public void testUnauthorizedAccess() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"firstname\":\"Unauthorized\",\"lastname\":\"Access\"}")
                .when()
                .put("/booking/1")
                .then()
                .statusCode(403);
    }
}