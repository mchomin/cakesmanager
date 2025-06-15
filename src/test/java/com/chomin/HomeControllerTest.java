package com.chomin;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

@MicronautTest
class HomeControllerTest {

    @Test
    public void testLoginEndpoint(RequestSpecification spec) {
        spec
                .when()
                .get("/")
                .then()
                .statusCode(200);
    }

}
