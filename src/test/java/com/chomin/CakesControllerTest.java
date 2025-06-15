package com.chomin;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CakesControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testListReturnsInitialCakes() {
        List<Cake> cakes = client.toBlocking().retrieve(HttpRequest.GET("/cakes"), Argument.listOf(Cake.class));
        assertNotNull(cakes);
        assertFalse(cakes.isEmpty());
    }

    @Test
    void testCreateCake() {
        CakeRequest request = new CakeRequest("Carrot", "Moist and delicious", "http://carrot.jpg");

        HttpRequest<CakeRequest> httpReq = HttpRequest.POST("/cakes", request)
                .contentType(MediaType.APPLICATION_JSON);

        HttpResponse<Cake> response = client.toBlocking().exchange(httpReq, Cake.class);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        Cake created = response.body();
        assertNotNull(created);
        assertEquals("Carrot", created.getTitle());
        assertEquals("Moist and delicious", created.getDesc());
        assertEquals("http://carrot.jpg", created.getImage());
    }

    @Test
    void testGetCakeById() {
        // First create a cake
        CakeRequest request = new CakeRequest("Lemon", "Tangy", "http://lemon.jpg");
        Cake created = client.toBlocking().retrieve(
                HttpRequest.POST("/cakes", request).contentType(MediaType.APPLICATION_JSON),
                Cake.class
        );

        // Then get it
        Cake result = client.toBlocking().retrieve(HttpRequest.GET("/cakes/" + created.getId()), Cake.class);
        assertEquals("Lemon", result.getTitle());
    }

    @Test
    void testGetCakeNotFound() {
        HttpClientResponseException ex = assertThrows(HttpClientResponseException.class, () ->
                client.toBlocking().retrieve(HttpRequest.GET("/cakes/999999"), Cake.class));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void testUpdateCakeSuccess() {
        CakeRequest createRequest = new CakeRequest("Vanilla", "Soft", "http://vanilla.jpg");
        Cake created = client.toBlocking().retrieve(
                HttpRequest.POST("/cakes", createRequest).contentType(MediaType.APPLICATION_JSON),
                Cake.class
        );

        CakeRequest updateRequest = new CakeRequest("Vanilla 2.0", "Softer", "http://vanilla2.jpg");
        HttpRequest<CakeRequest> updateHttp = HttpRequest.PUT("/cakes/" + created.getId(), updateRequest)
                .contentType(MediaType.APPLICATION_JSON);

        HttpResponse<?> response = client.toBlocking().exchange(updateHttp);
        assertEquals(HttpStatus.OK, response.getStatus());

        Cake updated = client.toBlocking().retrieve(HttpRequest.GET("/cakes/" + created.getId()), Cake.class);
        assertEquals("Vanilla 2.0", updated.getTitle());
    }

    @Test
    void testUpdateCakeNotFound() {
        CakeRequest updateRequest = new CakeRequest("Ghost", "No one", "http://none.jpg");
        HttpRequest<CakeRequest> req = HttpRequest.PUT("/cakes/99999", updateRequest)
                .contentType(MediaType.APPLICATION_JSON);

        HttpClientResponseException ex = assertThrows(HttpClientResponseException.class, () ->
                client.toBlocking().exchange(req));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void testDeleteCakeSuccess() {
        CakeRequest request = new CakeRequest("Temp", "Will be deleted", "http://tmp.jpg");
        Cake created = client.toBlocking().retrieve(
                HttpRequest.POST("/cakes", request).contentType(MediaType.APPLICATION_JSON),
                Cake.class
        );

        HttpResponse<?> response = client.toBlocking().exchange(HttpRequest.DELETE("/cakes/" + created.getId()));
        assertEquals(HttpStatus.OK, response.getStatus());

        HttpClientResponseException ex = assertThrows(HttpClientResponseException.class, () ->
                client.toBlocking().retrieve(HttpRequest.GET("/cakes/" + created.getId()), Cake.class));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void testDeleteCakeNotFound() {
        HttpRequest<?> request = HttpRequest.DELETE("/cakes/999999");

        HttpClientResponseException ex = assertThrows(HttpClientResponseException.class, () ->
                client.toBlocking().exchange(request));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void testCreateCakeMissingFields() {
        String invalidJson = "{\"title\":\"Only Title\"}"; // Missing desc and image

        HttpRequest<String> request = HttpRequest.POST("/cakes", invalidJson)
                .contentType(MediaType.APPLICATION_JSON);

        HttpClientResponseException ex = assertThrows(HttpClientResponseException.class, () ->
                client.toBlocking().exchange(request, String.class));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }
}