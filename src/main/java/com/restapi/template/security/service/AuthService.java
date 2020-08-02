package com.restapi.template.security.service;

import com.restapi.template.account.Account;
import com.restapi.template.account.AccountRepository;
import com.restapi.template.account.UserStatus;
import com.restapi.template.security.JwtTokenProvider;
import com.restapi.template.security.exception.CantSignInException;
import com.restapi.template.security.exception.IdAlreadyExistsException;
import com.restapi.template.security.response.SignInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 회원 인증 서비스
 *
 * @author always0ne
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 인증토큰 발급받기
     *
     * @param id       사용자 ID
     * @param password 사용자 비밀번호
     * @return accessToken
     * @throws CantSignInException 회원가입이 되어있지 않거나 잠긴 계정입니다.
     */
    public SignInResponse signIn(String id, String password) {
        Account account = this.accountRepository.findByUserIdAndState(id, UserStatus.NORMAL)
                .orElseThrow(() -> new CantSignInException(id));
        if (!passwordEncoder.matches(password, account.getPassword()))
            throw new CantSignInException(id);

        return SignInResponse.builder()
                .accessToken(jwtTokenProvider.createToken(account.getUsername(), account.getRoles()))
                .build();
    }

    /**
     * 회원 가입 하기
     *
     * @param id       사용자 ID
     * @param password 사용자 비밀번호
     * @param name     사용자 이름
     * @return accessToken
     */
    public SignInResponse signUp(String id, String password, String name) {
        Account account = this.accountRepository.save(Account.builder()
                .userId(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .state(UserStatus.NORMAL)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return SignInResponse.builder()
                .accessToken(jwtTokenProvider.createToken(account.getUsername(), account.getRoles()))
                .build();
    }

    /**
     * 중복 아이디 체크
     *
     * @param id 사용자 ID
     * @throws IdAlreadyExistsException 이미 사용중인 아이디입니다.
     */
    public void idCheck(String id) {
        if (this.accountRepository.findByUserIdAndStateIsNot(id, UserStatus.WITHDRAWAL).isPresent())
            throw new IdAlreadyExistsException(id);
    }
}
