package com.Address.Address.Exception;

import org.springframework.http.HttpStatus;

public class ServiceNotAvailableException extends RuntimeException{

    private String message;
    private HttpStatus status;
    public ServiceNotAvailableException(String message,  HttpStatus status){
        this.message = message;
        this.status = status;
    }
    @Override
    public String getMessage() {return message;}
    public HttpStatus getStatus() {return status;}

}
