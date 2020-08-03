package com.restapi.template.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * 에러 응답
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
public class ErrorResponse {
    /**
     * 발생 시각
     */
    private final LocalDateTime timestamp;
    /**
     * 상태 코드
     */
    private final int status;
    /**
     * 에러 코드
     */
    private final String error;
    /**
     * 에러 메시지
     */
    private final String message;

    public ErrorResponse(HttpStatus Httpstatus, String errCode, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = Httpstatus.value();
        this.error = errCode;
        this.message = message;
    }


}
