package com.godeltech.service.impl;

import com.godeltech.dto.MovieDTO;
import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.service.MovieDtoService;
import com.godeltech.service.MovieService;
import com.godeltech.service.MovieUserEvaluationService;
import com.godeltech.utils.AvgSatisfactionGradeCalc;
import com.godeltech.utils.MovieDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieDtoServiceImpl implements MovieDtoService {
    private final MovieService movieService;
    private final MovieUserEvaluationService mueService;

    @Override
    public MovieDTO getByIdFullInfo(Integer id) {
        log.info("getByIdFullInfo by id: {}", id);
        Movie entity = movieService.getByIdContainsGenreCountry(id);
        List<MovieUserEvaluation> mueByMovieId = mueService.getAllByMovieId(id);
        entity.setMovieEvaluations(mueByMovieId);
        entity.setAvgSatisfactionGrade(AvgSatisfactionGradeCalc.calculate(mueByMovieId));

        return MovieDtoConverter.convertToDTO(entity);
    }

    @Override
    public List<MovieDTO> getAllFullInfo() {
        log.info("getAllFullInfo()");
        List<Movie> justMoviesWithCountryAndGenre = movieService.getAllWithCountryAndGenre();
        List<MovieUserEvaluation> mueList = mueService.getAll();
        List<Movie> movieListWithEval = fillMoviesWithEvaluations(justMoviesWithCountryAndGenre, mueList);
        return movieListWithEval.stream().map(MovieDtoConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByTitleFullInfo(String favorite) {
        log.info("getMoviesByTitleFullInfo: {}", favorite);
        List<Movie> justMoviesByTitle = movieService.getAllByTitleFullInfo(favorite);
        List<Movie> movieListWithEval = fillMoviesWithEvaluations(justMoviesByTitle, mueService.getAll());
        return movieListWithEval.stream().map(MovieDtoConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByGenreFullInfo(String favorite) {
        log.info("getMoviesByGenreFullInfo: {}", favorite);
        List<Movie> justMoviesByGenre = movieService.getAllByGenreFullInfo(favorite);
        List<Movie> movieListWithEval = fillMoviesWithEvaluations(justMoviesByGenre, mueService.getAll());
        return movieListWithEval.stream().map(MovieDtoConverter::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByCountryFullInfo(String favorite) {
        log.info("MovieServiceImpl getMoviesWithGenreAndCountryByCountry: {}", favorite);
        Set<Movie> movieSet = movieService.getAllByCountryFullInfo(favorite);
        List<Movie> justMoviesByCountry = new ArrayList<>(movieSet);
        List<Movie> movieListWithEval = fillMoviesWithEvaluations(justMoviesByCountry, mueService.getAll());
        return movieListWithEval.stream().map(MovieDtoConverter::convertToDTO).collect(Collectors.toList());
    }

    private List<Movie> fillMoviesWithEvaluations(List<Movie> movieList, final List<MovieUserEvaluation> mueList) {
        Map<Integer, List<MovieUserEvaluation>> mueMap = new HashMap<>();
        for (MovieUserEvaluation mue : mueList) {
            List<MovieUserEvaluation> evaluationsForMovie;
            if (!mueMap.containsKey(mue.getMovieId())) {
                evaluationsForMovie = new ArrayList<>();
            } else {
                evaluationsForMovie = mueMap.get(mue.getMovieId());
            }
            evaluationsForMovie.add(mue);
            mueMap.put(mue.getMovieId(), evaluationsForMovie);
        }

        for (Movie movie : movieList) {
            List<MovieUserEvaluation> evaluationsForMovie = mueMap.get(movie.getId());
            if (evaluationsForMovie != null) {
                movie.setMovieEvaluations(evaluationsForMovie);
                movie.setAvgSatisfactionGrade(AvgSatisfactionGradeCalc
                        .calculate(evaluationsForMovie));
            }
        }
        return movieList;
    }
}
