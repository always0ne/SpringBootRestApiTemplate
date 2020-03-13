package com.restapi.templet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResponse(HttpStatus Httpstatus, Exception e){
        this.timestamp = LocalDateTime.now();
        this.status  = Httpstatus.value();
        this.error = Httpstatus.toString();
        this.message = e.getMessage();
    }


}
