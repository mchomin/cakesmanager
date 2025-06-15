package com.chomin;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller("/cakes")
public class CakesController {

    private final CakeRepository repo;

    public CakesController(CakeRepository repo) {
        this.repo = repo;
    }

    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Tag(name = "Cakes Manager")
    public List<Cake> list() {
        return repo.findAll();
    }

    @Get("/{id}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Tag(name = "Cakes Manager")
    @ApiResponse(responseCode = "200", description = "Cake found")
    @ApiResponse(responseCode = "404", description = "Cake ID not found")
    public Optional<Cake> get(int id) {
        return repo.findById(id);
    }

    @Post
    @Status(HttpStatus.CREATED)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Tag(name = "Cakes Manager")
    @ApiResponse(responseCode = "201", description = "Cake created OK")
    @ApiResponse(responseCode = "400", description = "Invalid Cake Supplied")
    public Cake create(@Body @Valid CakeRequest cake) {
        return repo.add(cake);
    }

    @Put("/{id}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Tag(name = "Cakes Manager")
    @ApiResponse(responseCode = "200", description = "Cake updated OK")
    @ApiResponse(responseCode = "400", description = "Invalid Cake Supplied")
    @ApiResponse(responseCode = "404", description = "Cake ID not found")
    public HttpStatus update(int id, @Body @Valid CakeRequest cake) {
        return repo.update(id, cake) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

    @Delete("/{id}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Tag(name = "Cakes Manager")
    @ApiResponse(responseCode = "200", description = "Cake deleted OK")
    @ApiResponse(responseCode = "404", description = "Cake ID not found")
    public HttpStatus delete(int id) {
        return repo.delete(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }
}