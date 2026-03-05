package client;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrdersClient {

    public static Response createOrder(String token, Object body) {

        return given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(body)
        .when()
                .post("/api/orders");
    }

    public static Response getOrder(String token, String id) {

        return given()
                .header("Authorization", "Bearer " + token)
        .when()
                .get("/api/orders/" + id);
    }

    public static Response updateOrder(String token, String id, Object body) {

        return given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(body)
        .when()
                .put("/api/orders/" + id);
    }

    public static Response deleteOrder(String token, String id) {

        return given()
                .header("Authorization", "Bearer " + token)
        .when()
                .delete("/api/orders/" + id);
    }
}