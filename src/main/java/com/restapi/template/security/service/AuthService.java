package com.restapi.template.security.service;

import com.restapi.template.account.Account;
import com.restapi.template.account.AccountRepository;
import com.restapi.template.account.UserStatus;
import com.restapi.template.security.JwtTokenProvider;
import com.restapi.template.security.exception.IdAlreadyExistsException;
import com.restapi.template.security.exception.UserNotFoundException;
import com.restapi.template.security.response.SignInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public SignInResponse signIn(String id, String password) {
        Account account = this.accountRepository.findByUserIdAndState(id, UserStatus.NORMAL)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (!passwordEncoder.matches(password, account.getPassword()))
            throw new UserNotFoundException(id);

        return SignInResponse.builder()
                .authToken(jwtTokenProvider.createToken(account.getUsername(), account.getRoles()))
                .build();
    }

    public SignInResponse signUp(String id, String password, String name) {
        Account account = this.accountRepository.save(Account.builder()
                .userId(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .state(UserStatus.NORMAL)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return SignInResponse.builder()
                .authToken(jwtTokenProvider.createToken(account.getUsername(), account.getRoles()))
                .build();
    }

    public void idCheck(String id) {
        if (this.accountRepository.findByUserIdAndStateIsNot(id, UserStatus.WITHDRAWAL).isPresent())
            throw new IdAlreadyExistsException(id);
    }
}
