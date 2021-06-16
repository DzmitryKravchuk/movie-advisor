package com.godeltech.service;

import com.godeltech.dto.RegistrationRequest;
import com.godeltech.dto.UserDTO;

public interface RegistrationService {
    UserDTO registerUser(RegistrationRequest registrationRequest);
}
