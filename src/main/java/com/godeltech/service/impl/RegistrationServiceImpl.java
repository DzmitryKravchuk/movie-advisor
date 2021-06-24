package com.godeltech.service.impl;

import com.godeltech.dto.RegistrationRequest;
import com.godeltech.entity.User;
import com.godeltech.exception.NotUniqueLoginException;
import com.godeltech.service.RegistrationService;
import com.godeltech.service.RoleService;
import com.godeltech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void registerUser(RegistrationRequest registrationRequest) {
        User u = new User();
        validate(registrationRequest);
        u.setPassword(registrationRequest.getPassword());
        u.setUserName(registrationRequest.getLogin());
        u.setRole(roleService.getById(1));
        userService.save(u);
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
