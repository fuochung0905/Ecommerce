package com.utc2.it.Ecommerce.exception;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

public class ResourceNotFoundException extends RuntimeException {
    String resource;
    String filedName;
    Long filedValue;
    public ResourceNotFoundException(String _resource, String _filedName, Long _filedValue){
        super(String.format("%s not found with %s: %s",_resource,_filedName,_filedValue));
        this.filedName=_filedName;
        this.resource=_resource;
        this.filedValue=_filedValue;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handlerMethodArgsNotValidException(MethodArgumentNotValidException ex){
        Map<String,String>response= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String fileName=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            response.put(fileName,message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
