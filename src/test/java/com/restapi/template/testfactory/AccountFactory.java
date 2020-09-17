package com.restapi.template.testfactory;

import com.restapi.template.api.user.data.Users;
import com.restapi.template.api.user.data.UsersRepository;
import com.restapi.template.security.data.UserStatus;
import com.restapi.template.security.response.SignInResponse;
import com.restapi.template.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountFactory {

    @Autowired
    private AuthService authService;
    @Autowired
    protected UsersRepository usersRepository;

    @Transactional
    public SignInResponse generateUser(int i) {
         return authService.signUp(
                 "TestUser" + i,
                "password",
                "테스트 유저 " + i
        );
    }
    @Transactional
    public Users generateUserAndGetUser(int i) {
        authService.signUp(
                "TestUser" + i,
                "password",
                "테스트 유저 " + i
        );
        return usersRepository.findByUserIdAndState("TestUser"+ i, UserStatus.NORMAL, Users.class)
                .get();
    }
}
