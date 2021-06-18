package com.godeltech.controller;

import com.godeltech.dto.EvaluationRequest;
import com.godeltech.dto.RegistrationRequest;
import com.godeltech.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public final class MovieController {
    private final MovieService movieService;

    @GetMapping("/movie/movies")
    public String movieList(final Model model) {
        model.addAttribute("allMovies", movieService.getAllFullInfo());
        return "/movie/movies";
    }

    @GetMapping("/movie/review{id}")
    public String movieEvaluation(final Model model, @PathVariable final int id) {
        model.addAttribute("movie", movieService.getByIdFullInfo(id));
        model.addAttribute("evalRequest", new EvaluationRequest());
        return "/movie/review";
    }
}
