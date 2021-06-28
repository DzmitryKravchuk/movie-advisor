package com.godeltech.service.impl;

import com.godeltech.dto.MovieRequest;
import com.godeltech.dto.MovieResponse;
import com.godeltech.dto.MovieEvaluationResponse;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieRating;
import com.godeltech.service.CountryService;
import com.godeltech.service.GenreService;
import com.godeltech.service.MovieDtoService;
import com.godeltech.service.MovieRatingService;
import com.godeltech.service.MovieService;
import com.godeltech.service.MovieUserEvaluationService;
import com.godeltech.utils.MovieDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieDtoServiceImpl implements MovieDtoService {
    private final MovieService movieService;
    private final MovieRatingService movieRatingService;
    private final MovieUserEvaluationService mueService;
    private final CountryService countryService;
    private final GenreService genreService;

    @Override
    public MovieResponse getById(Integer id) {
        log.info("getById by id: {}", id);
        Movie entity = movieService.getByIdContainsGenreCountry(id);
        entity.setAvgSatisfactionGrade(movieRatingService.getRatingByMovieId(id).getRating());
        return MovieDtoConverter.convertToResponse(entity);
    }

    @Override
    public MovieResponse getMovieWithEvaluationsById(Integer id) {
        log.info("getMovieWithEvaluationsById by id: {}", id);
        Movie entity = movieService.getByIdContainsGenreCountry(id);
        entity.setAvgSatisfactionGrade(movieRatingService.getRatingByMovieId(id).getRating());
        MovieResponse dto = MovieDtoConverter.convertToResponse(entity);
        setEvaluations(dto);
        return dto;
    }

    private void setEvaluations(MovieResponse dto) {
        List<MovieEvaluationResponse> evaluations = mueService.getMovieEvaluationDTOs(dto.getId());
        dto.setEvaluations(evaluations);
    }

    @Override
    public List<MovieResponse> getAll() {
        log.info("getAllFullInfo()");
        List<Movie> justMoviesWithCountryAndGenre = movieService.getAllWithCountryAndGenre();
        List<Movie> moviesWithRatings = setRatingsForMovies(justMoviesWithCountryAndGenre);
        return moviesWithRatings.stream().map(MovieDtoConverter::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<MovieResponse> getByTitle(String favorite) {
        log.info("getMoviesByTitleFullInfo: {}", favorite);
        List<Movie> justMoviesByTitle = movieService.getAllByTitleFullInfo(favorite);
        List<Movie> moviesWithRatings = setRatingsForMovies(justMoviesByTitle);
        return moviesWithRatings.stream().map(MovieDtoConverter::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<MovieResponse> getByGenre(String favorite) {
        log.info("getMoviesByGenreFullInfo: {}", favorite);
        List<Movie> justMoviesByGenre = movieService.getAllByGenreFullInfo(favorite);
        List<Movie> moviesWithRatings = setRatingsForMovies(justMoviesByGenre);
        return moviesWithRatings.stream().map(MovieDtoConverter::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<MovieResponse> getByCountry(String favorite) {
        log.info(" getByCountry: {}", favorite);
        Set<Movie> movieSet = movieService.getAllByCountryFullInfo(favorite);
        List<Movie> justMoviesByCountry = new ArrayList<>(movieSet);
        List<Movie> moviesWithRatings = setRatingsForMovies(justMoviesByCountry);
        return moviesWithRatings.stream().map(MovieDtoConverter::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public void saveNewMovie(MovieRequest request) {
        log.info(" saveNewMovie: {}", request);
        Movie movie = MovieDtoConverter.convertFromRequest(request);
        movie.setCountry(countryService.getById(request.getCountryId()));
        Set<Genre> genres = request.getGenreIds().stream()
                .map(genreService::getById)
                .collect(Collectors.toSet());
        movie.setGenres(genres);
        movieService.save(movie);
    }

    @Override
    public void update(Integer id, MovieResponse res) {
        log.info(" update: {}", res);
        Movie movie = MovieDtoConverter.convertFromResponse(res);
        movie.setCountry(countryService.getByCountryName(res.getCountry()));
        Set<Genre> genres = res.getGenres().stream()
                .map(genreService::getGenreByGenreName)
                .collect(Collectors.toSet());
        movie.setGenres(genres);
        movieService.update(movie,id);
    }

    private List<Movie> setRatingsForMovies(List<Movie> movieList) {
        Map<Integer, MovieRating> ratingsMap = movieRatingService.getAll().stream()
                .collect(Collectors.toMap(MovieRating::getMovieId, Function.identity()));
        movieList.forEach(m -> m.setAvgSatisfactionGrade(getRating(ratingsMap, m)));
        return movieList;
    }

    private Integer getRating(Map<Integer, MovieRating> ratingsMap, Movie m) {
        if (ratingsMap.containsKey(m.getId())) {
            return ratingsMap.get(m.getId()).getRating();
        } else return 0;
    }
}
