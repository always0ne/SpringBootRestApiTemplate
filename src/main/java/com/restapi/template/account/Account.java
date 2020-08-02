package com.restapi.template.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
public class Account implements UserDetails {
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
     * 사용자 권한
     */
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (state == UserStatus.NORMAL)
            return true;
        else
            return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (state == UserStatus.NORMAL)
            return true;
        else
            return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (state == UserStatus.NORMAL)
            return true;
        else
            return false;
    }
}
