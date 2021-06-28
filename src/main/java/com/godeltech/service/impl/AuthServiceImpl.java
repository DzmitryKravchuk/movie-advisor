package com.godeltech.service.impl;

import com.godeltech.dto.AuthRequest;
import com.godeltech.dto.AuthResponse;
import com.godeltech.dto.RegistrationRequest;
import com.godeltech.entity.User;
import com.godeltech.exception.NotUniqueLoginException;
import com.godeltech.exception.TokenAuthException;
import com.godeltech.security.CustomUserDetailsService;
import com.godeltech.security.jwt.JwtTokenProviderService;
import com.godeltech.service.AuthenticationService;
import com.godeltech.service.RoleService;
import com.godeltech.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;
    private final RoleService roleService;

    private final JwtTokenProviderService jwtTokenProviderService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void registerUser(RegistrationRequest registrationRequest) {
        log.info("registerUser: {}",registrationRequest);
        User u = new User();
        validate(registrationRequest);
        u.setPassword(registrationRequest.getPassword());
        u.setUserName(registrationRequest.getLogin());
        u.setRole(roleService.getByName("ROLE_USER"));
        userService.save(u);
    }

    @Override
    public User getUserFromToken(HttpServletRequest servletRequest) {
        String token = jwtTokenProviderService.parseToken(servletRequest);
        User user = null;
        if (token != null && jwtTokenProviderService.validateToken(token)) {
            String userLogin = jwtTokenProviderService.getUserName(token);
            user = userDetailsService.findByLogin(userLogin);
        }
        return user;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getLogin(), request.getPassword()));

            User user = userDetailsService.findByLogin(request.getLogin());

            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(jwtTokenProviderService.createToken(user.getUserName(), Collections.singletonList(user.getRole())));

            log.info("Login successfully");

            return authResponse;
        } catch (AuthenticationException e) {
            throw new TokenAuthException("Invalid username/password supplied");
        }
    }

    private void validate(RegistrationRequest registrationRequest) {
        final String login = registrationRequest.getLogin();
        List<User> users = userService.getAll();
        List<String> registeredUserLogins = users.stream().map(User::getUserName).collect(Collectors.toList());
        if (registeredUserLogins.contains(login)) {
            throw new NotUniqueLoginException("login must be unique");
        }
    }
}
