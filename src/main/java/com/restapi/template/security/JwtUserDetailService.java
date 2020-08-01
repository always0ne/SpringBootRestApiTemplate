package com.restapi.template.security;

import com.restapi.template.account.exception.UserNotFoundException;
import com.restapi.template.account.AccountRepository;
import com.restapi.template.account.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JwtUserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userId) {
        return accountRepository.findByUserIdAndState(userId, UserStatus.NORMAL)
                .orElseThrow( () -> new UserNotFoundException(userId));
    }
}