package com.ApiGateway.API_Gateway.Filter;

import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private Validator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {


            if (validator.predicate.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().toSingleValueMap().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authorization header is missing");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                String token = null;
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }
                    try{
                        jwtUtil.validateToken(token);
                    }catch(Exception e){
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
                    }
                // TODO: validate the token here, and throw/short-circuit if invalid
            }

            return chain.filter(exchange);
        };
    }

    public static class Config{

}


}
