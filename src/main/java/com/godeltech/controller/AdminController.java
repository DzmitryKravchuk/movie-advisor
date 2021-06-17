package com.godeltech.controller;

import com.godeltech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public final class AdminController {
    private final UserService userService;

    @GetMapping("/admin/users")
    public String userList(final Model model) {
        model.addAttribute("allUsers", userService.getAll());
        return "admin/users";
    }

    @PostMapping("/admin/users")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") final Integer userId,
                             final Model model) {
            userService.delete(userId);
        return "redirect:/admin/users";
    }
}
