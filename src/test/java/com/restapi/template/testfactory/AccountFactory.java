package com.restapi.template.testfactory;

import com.restapi.template.account.Account;
import com.restapi.template.account.AccountRepository;
import com.restapi.template.account.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
public class AccountFactory {

    @Autowired
    protected AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Account generateUser(int i) {
        return accountRepository.save(
                Account.builder()
                        .userId("TestUser" + i)
                        .password(passwordEncoder.encode("password"))
                        .name("테스트 유저 " + i)
                        .state(UserStatus.NORMAL)
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build()
        );
    }
}
