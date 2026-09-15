package com.ApiGateway.API_Gateway.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/employeeServiceFallback")
    public Mono<String> employeeServiceFallback(){
        return Mono.just("Employee Service is down please try after some time");
    }

    @GetMapping("/AddressServiceFallback")
    public Mono<String> AddressServiceFallback(){
        return Mono.just("Address Service is down please try after some time");

    }
}
