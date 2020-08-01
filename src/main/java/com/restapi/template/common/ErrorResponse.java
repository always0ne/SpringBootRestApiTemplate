package com.restapi.template.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private int error;
    private String message;

    public ErrorResponse(HttpStatus Httpstatus,int errCode, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = Httpstatus.value();
        this.error = errCode;
        this.message = message;
    }


}
