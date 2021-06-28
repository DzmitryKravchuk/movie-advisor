package com.godeltech.controller.mvc;

import com.godeltech.dto.RegistrationRequest;
import com.godeltech.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public final class RegistrationController {
    private final AuthenticationService registrationService;

    @GetMapping("/registration")
    public String registration(final Model model) {
        model.addAttribute("regRequest", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("regRequest") @Valid final RegistrationRequest regRequest, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        registrationService.registerUser(regRequest);
        return "redirect:/login";
    }
}

