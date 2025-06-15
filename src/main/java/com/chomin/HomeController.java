package com.chomin;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @Secured(SecurityRule.IS_ANONYMOUS)
    @View("home")
    @Get
    //Swagger annotations
    @Operation(summary = "Log in page",
            description = "A HTML page is returned that allows to log in with github and access Swagger UI"
    )
    @ApiResponse(
            content = @Content(mediaType = "text/html",
                    schema = @Schema(type="string"))
    )
    @Tag(name = "Log in")
    public Map<String, Object> index() {
        return new HashMap<>();
    }
}