package com.godeltech.controller;

import com.godeltech.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movies")
    public String userList(final Model model) {
        model.addAttribute("allMovies", movieService.getAllFullInfo());
        return "movies";
    }
}
