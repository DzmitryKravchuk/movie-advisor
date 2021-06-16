package com.godeltech.service.impl;

import com.godeltech.entity.Country;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.exception.UpdateNotMatchIdException;
import com.godeltech.repository.MovieRepository;
import com.godeltech.service.CountryService;
import com.godeltech.service.GenreService;
import com.godeltech.service.MovieService;
import com.godeltech.service.MovieUserEvaluationService;
import com.godeltech.utils.AvgSatisfactionGradeCalc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public final class MovieServiceImpl implements MovieService {
    private final MovieRepository repository;
    private final MovieUserEvaluationService mueService;
    private final GenreService genreService;
    private final CountryService countryService;

    @Override
    public void save(final Movie entity) {
        log.info("MovieServiceImpl save {}", entity);
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        refreshMovieSetForGenres(entity);
        refreshMovieSetForCountry(entity);
        repository.save(entity);
    }

    private void refreshMovieSetForCountry(final Movie entity) {
        Set<Movie> moviesForCountry = getMoviesWithCountryByCountryId(entity.getCountry().getId());
        moviesForCountry.add(entity);
    }

    private void refreshMovieSetForGenres(final Movie entity) {
        for (Genre gen
                : entity.getGenres()) {
            Set<Movie> moviesForGenre = getMoviesWithGenreByGenreId(gen.getId());
            moviesForGenre.add(entity);
        }
    }

    @Override
    public Movie getById(final Integer id) {
        log.info("MovieServiceImpl get by id: {}", id);
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(" Object with index " + id + " not found"));
    }

    @Override
    public List<Movie> getAll() {
        log.info("MovieServiceImpl find ALL");
        return repository.findAll();
    }

    @Override
    public void delete(final Integer id) {
        log.info("MovieServiceImpl delete by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void update(final Movie entity, final Integer id) {
        log.info("MovieServiceImpl update with id: {}", id);
        getById(id);
        if (!entity.getId().equals(id)) {
            throw new UpdateNotMatchIdException(" Object from request has index " + entity.getId() + " and doesnt match index from url " + id);
        }
        save(entity);
    }

    @Override
    public List<Movie> getMoviesByDirectorFullInfo(final String myFavoriteDirector) {
        log.info("MovieServiceImpl getAllMoviesByDirector with Director: {}", myFavoriteDirector);
        List<Movie> justMoviesByDirector = repository.findAllByDirectorContainingIgnoreCase(myFavoriteDirector);
        return fillMoviesWithEvaluations(justMoviesByDirector, mueService.getAll());
    }

    @Override
    public Movie getByIdContainsGenreCountry(final Integer id) {
        log.info("MovieServiceImpl get one containing Genre-Country by id: {}", id);
        Movie entity = repository.getMovieById(id);
        if (entity == null) {
            throw new ResourceNotFoundException(" Object with index " + id + " not found");
        }
        return entity;
    }

    @Override
    public Movie getByIdFullInfo(final Integer id) {
        log.info("MovieServiceImpl get full info by id: {}", id);
        Movie entity = getByIdContainsGenreCountry(id);
        Set<MovieUserEvaluation> allByMovieId = mueService.getAllByMovieId(id);
        entity.setMovieEvaluations(allByMovieId);
        entity.setAvgSatisfactionGrade(AvgSatisfactionGradeCalc.calculate(allByMovieId));
        return entity;
    }

    @Override
    public List<Movie> getAllFullInfo() {
        log.info("MovieServiceImpl get All with full info");
        List<Movie> movieList = repository.getAllWithCountryAndGenre();
        List<MovieUserEvaluation> mueList = mueService.getAll();
        return fillMoviesWithEvaluations(movieList, mueList);
    }

    @Override
    public List<Movie> getMoviesByTitleFullInfo(final String favorite) {
        log.info("MovieServiceImpl getAllMoviesByTitle: {}", favorite);
        List<Movie> justMoviesByTitle = repository.findAllByTitleContainingIgnoreCase(favorite);
        return fillMoviesWithEvaluations(justMoviesByTitle, mueService.getAll());
    }

    @Override
    public List<Movie> getMoviesByGenreFullInfo(final String favorite) {
        log.info(" getMoviesByGenre: {}", favorite);
        Genre foundGenre = genreService.getGenreByGenreName(favorite);
        List<Movie> justMoviesByGenre = repository.getAllByGenresIn(Collections.singleton(foundGenre));
        return fillMoviesWithEvaluations(justMoviesByGenre, mueService.getAll());
    }

    @Override
    public List<Movie> getMoviesByCountryFullInfo(final String favorite) {
        log.info("MovieServiceImpl getMoviesWithGenreAndCountryByCountry: {}", favorite);
        Set<Movie> movieSet = repository.getAllByCountryCountryName(favorite);
        List<Movie> justMoviesByCountry = new ArrayList<>(movieSet);
        return fillMoviesWithEvaluations(justMoviesByCountry, mueService.getAll());
    }

    @Override
    public Set<Movie> getMoviesWithGenreByGenreId(final Integer genreId) {
        log.info("MovieServiceImpl getMoviesWithGenreByGenreId: {}", genreId);
        Genre genre = genreService.getGenreWithMoviesByGenreId(genreId);
        if (genre == null) {
            throw new ResourceNotFoundException(" Genre with id " + genreId + " not found");
        } else {
            return genre.getMovies();
        }
    }

    private Set<Movie> getMoviesWithCountryByCountryId(final Integer id) {
        log.info("MovieServiceImpl getMoviesWithCountryByCountryId: {}", id);
        Country country = countryService.getCountryWithMoviesByCountryId(id);
        if (country == null) {
            throw new ResourceNotFoundException(" Genre with id " + id + " not found");
        } else {
            return country.getMovies();
        }
    }

    private List<Movie> fillMoviesWithEvaluations(final List<Movie> movieList, final List<MovieUserEvaluation> mueList) {
        Map<Integer, Set<MovieUserEvaluation>> mueMap = new HashMap<>();
        for (MovieUserEvaluation mue : mueList) {
            Set<MovieUserEvaluation> evaluationsForMovie;
            if (!mueMap.containsKey(mue.getMovieId())) {
                evaluationsForMovie = new HashSet<>();
            } else {
                evaluationsForMovie = mueMap.get(mue.getMovieId());
            }
            evaluationsForMovie.add(mue);
            mueMap.put(mue.getMovieId(), evaluationsForMovie);
        }

        for (Movie movie : movieList) {
            Set<MovieUserEvaluation> evaluationsForMovie = mueMap.get(movie.getId());
            if (evaluationsForMovie != null) {
                movie.setMovieEvaluations(evaluationsForMovie);
                movie.setAvgSatisfactionGrade(AvgSatisfactionGradeCalc.calculate(evaluationsForMovie));
            }
        }
        return movieList;
    }
}