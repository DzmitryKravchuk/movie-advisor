package com.godeltech.service.impl;

import com.godeltech.dto.RegistrationRequest;
import com.godeltech.dto.UserDTO;
import com.godeltech.entity.User;
import com.godeltech.service.RegistrationService;
import com.godeltech.service.RoleService;
import com.godeltech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public UserDTO registerUser(final RegistrationRequest registrationRequest) {
        User u = new User();
        u.setPassword(registrationRequest.getPassword());
        u.setUserName(registrationRequest.getLogin());
        u.setRole(roleService.getById(1));
        userService.save(u);
        return new UserDTO(u.getId());
    }
}
