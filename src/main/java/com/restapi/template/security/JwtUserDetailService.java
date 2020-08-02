package com.restapi.template.security;

import com.restapi.template.account.AccountRepository;
import com.restapi.template.account.UserStatus;
import com.restapi.template.security.exception.CantSignInException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Security의 인증토큰을 받기 위한 서비스
 *
 * @author always0ne
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class JwtUserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    /**
     * 시큐리티 인증 토큰 발급을 위한 메소드
     *
     * @param userId 사용자 아이디
     * @return 시큐리티에서 사용하는 유저의 데이터
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userId) {
        return accountRepository.findByUserIdAndState(userId, UserStatus.NORMAL)
                .orElseThrow(() -> new CantSignInException(userId));
    }
}