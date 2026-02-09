package com.inventory.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class RawMaterialResourceITest {

    @Test
    void shouldCreateRawMaterial() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "name": "Steel",
                  "stockQuantity": 50
                }
            """)
        .when()
            .post("/raw-materials")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("name", is("Steel"))
            .body("stockQuantity", is(50));
    }
}
