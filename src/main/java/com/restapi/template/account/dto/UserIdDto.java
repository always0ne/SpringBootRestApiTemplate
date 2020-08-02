package com.restapi.template.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 ID 데이터 전송 객체
 * 내부에서 데이터 이동시 사용된다.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public class UserIdDto {
    /**
     * 사용자 ID
     */
    private String userId;
}
