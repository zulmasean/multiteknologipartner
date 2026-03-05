package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static String token;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = "https://api.multiteknologipartner.com";

        token = AuthHelper.getToken();
    }
}