package com.chomin;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

@Header(name = "User-Agent", value = "https://chomin.com")
@Header(name = "Accept", value = "application/vnd.github.v3+json, application/json")
@Client(id = "githubv3")
public interface GithubApiClient {

    @Get("/user")
    Publisher<GithubUser> getUser( // <4>
                                   @Header(HttpHeaders.AUTHORIZATION) String authorization);
}

