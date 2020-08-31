package com.restapi.template.account;

import com.restapi.template.security.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 계정 엔터티
 *
 * @author always0ne
 * @version 1.0
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    /**
     * pk
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 사용자 ID
     */
    @Column(unique = true)
    private String userId;

    /**
     * 비밀번호
     */
    private String password;

    /**
     * 사용자 이름
     */
    private String name;

    /**
     * 게정 상태
     */
    private UserStatus state;

    /**
     * Refresh Token
     */
    private String refreshToken;

    /**
     * 사용자 권한
     */
    @ElementCollection(fetch = FetchType.LAZY)
    private List<UserRole> roles;

    /**
     * Refresh Token 갱신
     *
     * @param refreshToken RefreshToken
     */
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
