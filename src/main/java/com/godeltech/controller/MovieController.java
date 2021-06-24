package com.godeltech.controller;

import com.godeltech.dto.EvaluationRequest;
import com.godeltech.dto.MovieDTO;

import com.godeltech.service.CountryService;
import com.godeltech.service.GenreService;
import com.godeltech.service.MovieService;
import com.godeltech.service.MovieUserEvaluationService;
import com.godeltech.service.PageableMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class MovieController {
    private final MovieService movieService;
    private final PageableMovieService pageableMovieService;
    private final GenreService genreService;
    private final CountryService countryService;
    private final MovieUserEvaluationService mueService;

    @GetMapping("/movie/movies/page/{pageNum}")
    public String movieList(final Model model, @PathVariable(name = "pageNum") int pageNum) {
        Page<MovieDTO> page = pageableMovieService.listAll(pageNum);

        List<MovieDTO> listMovies = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("allMovies", listMovies);
        return "/movie/movies";
    }

    @GetMapping("/movie/review{id}")
    public String movieEvaluation(final Model model, @PathVariable final int id) {
        model.addAttribute("movie", movieService.getByIdFullInfo(id));
        model.addAttribute("evaluations", mueService.getMovieEvaluationDTOs(id));
        model.addAttribute("evalRequest", new EvaluationRequest());
        return "/movie/review";
    }

    @GetMapping("/movie/search")
    public String movieSearch(final Model model) {
        model.addAttribute("allGenres", genreService.getAll());
        model.addAttribute("allCountries", countryService.getAll());
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

    @PostMapping("movie/titleSearch")
    @ResponseBody
    public List<MovieDTO> searchMovieByTitle(@RequestParam(value = "title") final String title, final Model model) {
        return movieService.getMoviesByTitleFullInfo(title);
    }
}
