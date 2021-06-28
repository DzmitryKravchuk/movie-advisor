package com.godeltech.service;

import com.godeltech.dto.AuthRequest;
import com.godeltech.dto.AuthResponse;
import com.godeltech.dto.RegistrationRequest;
import com.godeltech.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    void registerUser(RegistrationRequest registrationRequest);

    AuthResponse login(AuthRequest request);

    User getUserFromToken(HttpServletRequest servletRequest);
}
