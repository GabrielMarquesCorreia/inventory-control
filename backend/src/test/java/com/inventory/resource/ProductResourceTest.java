package com.inventory.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

@QuarkusTest
class ProductResourceTest {

    @Test
    void shouldListProducts() {
        given()
          .when().get("/products")
          .then()
             .statusCode(200);
    }
}
