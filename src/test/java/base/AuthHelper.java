package base;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AuthHelper {

    public static String getToken() {

        Response response =
                given()
                        .contentType("application/json")
                        .body("""
                        {
                          "username": "zulmairzamsyah",
                          "password": "test123"
                        }
                        """)
                .when()
                        .post("/api/orders");

        return response.jsonPath().getString("token");
    }
}