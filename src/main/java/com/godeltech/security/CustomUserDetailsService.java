package com.godeltech.security;

import com.godeltech.entity.User;
import com.godeltech.exception.PasswordIncorrectException;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public final class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        User user = findByLogin(login);
        return CustomUserDetails.fromUserToCustomUserDetails(user);
    }

    public User findByLoginAndPassword(final String login, final String password) {
        User user = findByLogin(login);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new PasswordIncorrectException("wrong password");
    }

    public User findByLogin(final String login) {
        log.info("findByLogin: {}", login);
        return userRepository.findByUserName(login)
                .orElseThrow(() -> new ResourceNotFoundException(" User with name " + login + " not found"));
    }
}
