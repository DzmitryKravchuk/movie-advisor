package com.godeltech.security.jwt;

import com.godeltech.entity.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface JwtTokenProviderService {
    String createToken(String userName, List<Role> roles);

    Authentication validateUserAndGetAuthentication(String token);

    String getUserName(String token);

    String parseToken(HttpServletRequest req);

    boolean validateToken(String token);
}
