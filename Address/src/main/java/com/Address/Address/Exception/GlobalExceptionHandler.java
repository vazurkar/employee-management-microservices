package com.Address.Address.Exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponse reponse = new ErrorResponse(ex.getMessage(),ex.getStatus());
        return new ResponseEntity<>(reponse, ex.getStatus());
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex){
        ErrorResponse reponse = new ErrorResponse(ex.getMessage(),ex.getStatus());
        return new ResponseEntity<>(reponse, ex.getStatus());
    }
    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameterException(MissingParameterException ex){
        ErrorResponse reponse = new ErrorResponse(ex.getMessage(),ex.getStatus());
        return new ResponseEntity<>(reponse, ex.getStatus());
    }
}
