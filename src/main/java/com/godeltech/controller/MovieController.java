package com.godeltech.controller;

import com.godeltech.dto.EvaluationRequest;
import com.godeltech.dto.MovieDTO;

import com.godeltech.service.CountryService;
import com.godeltech.service.GenreService;
import com.godeltech.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public final class MovieController {
    private final MovieService movieService;
    private final GenreService genreService;
    private final CountryService countryService;

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

    @GetMapping("/movie/search")
    public String movieSearch(final Model model) {
        model.addAttribute("allGenres", genreService.getAll());
        model.addAttribute("allCountries", countryService.getAll());
        model.addAttribute("allMovies", movieService.getAll());
        return "/movie/search";
    }

    @PostMapping("movie/genreChoose")
    @ResponseBody
    public List<MovieDTO> searchMovieByGenre(@RequestParam(value = "genre", required = false) final String genre, final Model model) {
        return movieService.getMoviesByGenreFullInfo(genre);
    }

    @PostMapping("movie/countryChoose")
    @ResponseBody
    public List<MovieDTO> searchMovieByCountry(@RequestParam(value = "country", required = false) final String country, final Model model) {
        return movieService.getMoviesByCountryFullInfo(country);
    }
}
