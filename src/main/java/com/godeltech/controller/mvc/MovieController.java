package com.godeltech.controller.mvc;

import com.godeltech.dto.MovieEvaluationRequest;
import com.godeltech.dto.MovieResponse;

import com.godeltech.service.CountryService;
import com.godeltech.service.GenreService;
import com.godeltech.service.MovieDtoService;
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
    private final MovieDtoService movieService;
    private final PageableMovieService pageableMovieService;
    private final GenreService genreService;
    private final CountryService countryService;
    private final MovieUserEvaluationService mueService;

    @GetMapping("/movie/movies/page/{pageNum}")
    public String movieList(final Model model, @PathVariable(name = "pageNum") int pageNum) {
        Page<MovieResponse> page = pageableMovieService.listAll(pageNum);

        List<MovieResponse> listMovies = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("allMovies", listMovies);
        return "/movie/movies";
    }

    @GetMapping("/movie/review{id}")
    public String movieEvaluation(Model model, @PathVariable int id) {
        model.addAttribute("movie", movieService.getById(id));
        model.addAttribute("evaluations", mueService.getMovieEvaluationDTOs(id));
        model.addAttribute("evalRequest", new MovieEvaluationRequest());
        return "/movie/review";
    }

    @GetMapping("/movie/search")
    public String movieSearch(Model model) {
        model.addAttribute("allGenres", genreService.getAll());
        model.addAttribute("allCountries", countryService.getAll());
        return "/movie/search";
    }

    @PostMapping("movie/genreChoose")
    @ResponseBody
    public List<MovieResponse> searchMovieByGenre(@RequestParam(value = "genre", required = false) String genre) {
        return movieService.getByGenre(genre);
    }

    @PostMapping("movie/countryChoose")
    @ResponseBody
    public List<MovieResponse> searchMovieByCountry(@RequestParam(value = "country", required = false) String country) {
        return movieService.getByCountry(country);
    }

    @PostMapping("movie/titleSearch")
    @ResponseBody
    public List<MovieResponse> searchMovieByTitle(@RequestParam(value = "title") String title) {
        return movieService.getByTitle(title);
    }
}
