package com.ApiGateway.API_Gateway.Filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.function.Predicate;

@Component
public class Validator {

    private final AntPathMatcher matcher = new AntPathMatcher();

    public static final List<String> endpoints = List.of(
            "/register",
            "/login",
            "/validate-token/{token}"

    );

    public Predicate<ServerHttpRequest> predicate = serverHttpRequest -> {

        String path = serverHttpRequest.getURI().getPath();
        return endpoints.stream().noneMatch(endpoint -> matcher.match(path, endpoint));
           };
    //Take the incoming HTTP request, get its path,
    // check whether that path matches any endpoint in my public-endpoint list.
    // If it doesn't match any public endpoint, return true.

}
