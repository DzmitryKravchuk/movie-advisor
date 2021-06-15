package com.godeltech.service.impl;

import com.godeltech.entity.Country;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.exception.ServiceEntityNotFoundException;
import com.godeltech.exception.ServiceUpdateNotMatchIdException;
import com.godeltech.repository.MovieRepository;
import com.godeltech.service.CountryService;
import com.godeltech.service.GenreService;
import com.godeltech.service.MovieService;
import com.godeltech.service.MovieUserEvaluationService;
import com.godeltech.utils.AvgSatisfactionGradeCalc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository repository;
    private final MovieUserEvaluationService mueService;
    private final GenreService genreService;
    private final CountryService countryService;

    @Override
    public void save(Movie entity) {
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

    private void refreshMovieSetForCountry(Movie entity) {
        Set<Movie> moviesForCountry = getMoviesWithCountryByCountryId(entity.getCountry().getId());
        moviesForCountry.add(entity);
    }

    private void refreshMovieSetForGenres(Movie entity) {
        for (Genre gen :
                entity.getGenres()) {
            Set<Movie> moviesForGenre = getMoviesWithGenreByGenreId(gen.getId());
            moviesForGenre.add(entity);
        }
    }

    @Override
    public Movie getById(Integer id) {
        log.info("MovieServiceImpl get by id: {}", id);
        return repository.findById(id).
                orElseThrow(() -> new ServiceEntityNotFoundException(" Object with index " + id + " not found"));
    }

    @Override
    public List<Movie> getAll() {
        log.info("MovieServiceImpl find ALL");
        return repository.findAll();
    }

    @Override
    public void delete(Integer id) {
        log.info("MovieServiceImpl delete by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void update(Movie entity, Integer id) {
        log.info("MovieServiceImpl update with id: {}", id);
        getById(id);
        if (!entity.getId().equals(id)) {
            throw new ServiceUpdateNotMatchIdException(" Object from request has index " + entity.getId() + " and doesnt match index from url " + id);
        }
        save(entity);
    }

    @Override
    public List<Movie> getMoviesByDirectorFullInfo(String myFavoriteDirector) {
        log.info("MovieServiceImpl getAllMoviesByDirector with Director: {}", myFavoriteDirector);
        List<Movie> justMoviesByDirector = repository.findAllByDirectorContainingIgnoreCase(myFavoriteDirector);
        return fillMoviesWithEvaluations(justMoviesByDirector,mueService.getAll());
    }

    @Override
    public Movie getByIdContainsGenreCountry(Integer id) {
        log.info("MovieServiceImpl get one containing Genre-Country by id: {}", id);
        Movie entity = repository.getMovieById(id);
        if (entity == null) {
            throw new ServiceEntityNotFoundException(" Object with index " + id + " not found");
        }
        return entity;
    }

    @Override
    public Movie getByIdFullInfo(Integer id) {
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
    public List<Movie> getMoviesByTitle(String favorite) {
        log.info("MovieServiceImpl getAllMoviesByTitle: {}", favorite);
        return repository.findAllByTitleContainingIgnoreCase(favorite);
    }

    @Override
    public List<Movie> getMoviesWithGenreAndCountryByGenre(String favorite) {
        log.info("MovieServiceImpl getMoviesWithGenreAndCountryByGenre: {}", favorite);
        return repository.GetMoviesWithGenreAndCountryByGenre(favorite);
    }

    @Override
    public Set<Movie> getMoviesWithGenreAndCountryByCountry(String favorite) {
        log.info("MovieServiceImpl getMoviesWithGenreAndCountryByCountry: {}", favorite);
        return repository.GetMoviesWithGenreAndCountryByCountry(favorite);
    }

    @Override
    public Set<Movie> getMoviesWithGenreByGenreId(Integer genreId) {
        log.info("MovieServiceImpl getMoviesWithGenreByGenreId: {}", genreId);
        Genre genre = genreService.getGenreWithMoviesByGenreId(genreId);
        if (genre == null) {
            throw new ServiceEntityNotFoundException(" Genre with id " + genreId + " not found");
        } else {
            return genre.getMovies();
        }
    }

    private Set<Movie> getMoviesWithCountryByCountryId(Integer id) {
        log.info("MovieServiceImpl getMoviesWithCountryByCountryId: {}", id);
        Country country = countryService.getCountryWithMoviesByCountryId(id);
        if (country == null) {
            throw new ServiceEntityNotFoundException(" Genre with id " + id + " not found");
        } else {
            return country.getMovies();
        }
    }

    private List<Movie> fillMoviesWithEvaluations(List<Movie> movieList, List<MovieUserEvaluation> mueList) {
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