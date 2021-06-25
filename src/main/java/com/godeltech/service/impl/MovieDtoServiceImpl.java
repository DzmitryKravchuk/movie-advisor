package com.godeltech.service.impl;

import com.godeltech.dto.MovieDTO;
import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieRating;
import com.godeltech.service.MovieDtoService;
import com.godeltech.service.MovieRatingService;
import com.godeltech.service.MovieService;
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

    @Override
    public MovieDTO getByIdFullInfo(Integer id) {
        log.info("getByIdFullInfo by id: {}", id);
        Movie entity = movieService.getByIdContainsGenreCountry(id);
        entity.setAvgSatisfactionGrade(movieRatingService.getRatingByMovieId(id).getRating());
        return MovieDtoConverter.convertToDTO(entity);
    }

    @Override
    public List<MovieDTO> getAllFullInfo() {
        log.info("getAllFullInfo()");
        List<Movie> justMoviesWithCountryAndGenre = movieService.getAllWithCountryAndGenre();
        List<Movie> moviesWithRatings = setRatingsForMovies(justMoviesWithCountryAndGenre);
        return moviesWithRatings.stream().map(MovieDtoConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByTitleFullInfo(String favorite) {
        log.info("getMoviesByTitleFullInfo: {}", favorite);
        List<Movie> justMoviesByTitle = movieService.getAllByTitleFullInfo(favorite);
        List<Movie> moviesWithRatings = setRatingsForMovies(justMoviesByTitle);
        return moviesWithRatings.stream().map(MovieDtoConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByGenreFullInfo(String favorite) {
        log.info("getMoviesByGenreFullInfo: {}", favorite);
        List<Movie> justMoviesByGenre = movieService.getAllByGenreFullInfo(favorite);
        List<Movie> moviesWithRatings = setRatingsForMovies(justMoviesByGenre);
        return moviesWithRatings.stream().map(MovieDtoConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByCountryFullInfo(String favorite) {
        log.info("MovieServiceImpl getMoviesWithGenreAndCountryByCountry: {}", favorite);
        Set<Movie> movieSet = movieService.getAllByCountryFullInfo(favorite);
        List<Movie> justMoviesByCountry = new ArrayList<>(movieSet);
        List<Movie> moviesWithRatings = setRatingsForMovies(justMoviesByCountry);
        return moviesWithRatings.stream().map(MovieDtoConverter::convertToDTO).collect(Collectors.toList());
    }

    private List<Movie> setRatingsForMovies(List<Movie> movieList) {
        Map<Integer, MovieRating> ratingsMap = movieRatingService.getAll().stream()
                .collect(Collectors.toMap(MovieRating::getMovieId, Function.identity()));
        movieList.forEach(m->m.setAvgSatisfactionGrade(ratingsMap.get(m.getId()).getRating()));
        return movieList;
    }
}
