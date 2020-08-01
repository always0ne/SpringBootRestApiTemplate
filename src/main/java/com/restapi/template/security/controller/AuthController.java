package com.restapi.template.security.controller;

import com.restapi.template.security.service.AuthService;
import com.restapi.template.security.request.SignInRequest;
import com.restapi.template.security.request.SignUpRequest;
import com.restapi.template.security.response.SignInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = MediaTypes.HAL_JSON_VALUE)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponse signIn(
            @RequestBody SignInRequest signInRequest
    ) {
        return this.authService.signIn(signInRequest.getId(), signInRequest.getPassword());
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponse signUp(
            @RequestBody SignUpRequest signUpRequest
    ) {
        return this.authService.signUp(signUpRequest.getId(), signUpRequest.getPassword(), signUpRequest.getName());
    }

    @GetMapping("/checkid/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String idCheck(
            @PathVariable String userId
    ) {
        this.authService.idCheck(userId);
        return "사용가능한 아이디입니다.";
    }

}
