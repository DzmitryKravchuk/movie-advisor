package com.godeltech.controller.rest;

import com.godeltech.dto.AuthRequest;
import com.godeltech.dto.AuthResponse;
import com.godeltech.dto.RegistrationRequest;
import com.godeltech.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public final class RestRegistrationController {
    private final AuthenticationService authService;

    @PostMapping("/auth")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/registration")
    public void addUser(@RequestBody @Valid RegistrationRequest regRequest) {
        authService.registerUser(regRequest);
    }
}

