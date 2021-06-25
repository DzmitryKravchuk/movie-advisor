package com.godeltech.service.impl;

import com.godeltech.entity.Country;
import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.exception.UpdateNotMatchIdException;
import com.godeltech.repository.MovieRepository;
import com.godeltech.service.CountryService;
import com.godeltech.service.GenreService;
import com.godeltech.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository repository;
    private final GenreService genreService;
    private final CountryService countryService;

    @Override
    public void save(Movie entity) {
        log.info("MovieServiceImpl save {}", entity);
        LocalDate currentDate = LocalDate.now();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        refreshMovieSetForGenres(entity);
        refreshMovieSetForCountry(entity);
        repository.save(entity);
    }

    private void refreshMovieSetForCountry(Movie entity) {
        Set<Movie> moviesForCountry = getMoviesWithCountryByCountryId(
                entity.getCountry().getId());
        moviesForCountry.add(entity);
    }

    private void refreshMovieSetForGenres(Movie entity) {
        for (Genre gen
                : entity.getGenres()) {
            Set<Movie> moviesForGenre = getMoviesWithGenreByGenreId(gen.getId());
            moviesForGenre.add(entity);
        }
    }

    @Override
    public Movie getById(Integer id) {
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
    public void delete(Integer id) {
        log.info("MovieServiceImpl delete by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void update(Movie entity, Integer id) {
        log.info("MovieServiceImpl update with id: {}", id);
        getById(id);
        if (!entity.getId().equals(id)) {
            throw new UpdateNotMatchIdException(" Object from request has index "
                    + entity.getId()
                    + " and doesnt match index from url " + id);
        }
        save(entity);
    }

    @Override
    public Movie getByIdContainsGenreCountry(Integer id) {
        log.info("MovieServiceImpl get one containing Genre-Country by id: {}", id);
        Movie entity = repository.getMovieById(id);
        if (entity == null) {
            throw new ResourceNotFoundException(" Object with index " + id + " not found");
        }
        return entity;
    }

    @Override
    public List<Movie> getAllWithCountryAndGenre() {
        log.info("getAllWithCountryAndGenre");
        return repository.getAllWithCountryAndGenre();
    }

    @Override
    public List<Movie> getAllByTitleFullInfo(String favorite) {
        log.info("getAllByTitleFullInfo: {}", favorite);
        return repository.findAllByTitleContainingIgnoreCase(favorite);
    }

    @Override
    public List<Movie> getAllByGenreFullInfo(String favorite) {
        log.info("getAllByGenreFullInfo: {}", favorite);
        Genre foundGenre = genreService.getGenreByGenreName(favorite);
        return repository.getAllByGenresIn(Collections.singleton(foundGenre));
    }

    @Override
    public Set<Movie> getAllByCountryFullInfo(String favorite) {
        log.info("getAllByCountryFullInfo: {}", favorite);
        return repository.getAllByCountryCountryName(favorite);
    }

    @Override
    public Set<Movie> getMoviesWithGenreByGenreId(Integer genreId) {
        log.info("MovieServiceImpl getMoviesWithGenreByGenreId: {}", genreId);
        Genre genre = genreService.getGenreWithMoviesByGenreId(genreId);
        if (genre == null) {
            throw new ResourceNotFoundException(
                    " Genre with id " + genreId + " not found");
        } else {
            return genre.getMovies();
        }
    }

    @Override
    public void deleteAll() {
        log.info("deleteAll");
        repository.deleteAll();
    }

    private Set<Movie> getMoviesWithCountryByCountryId(Integer id) {
        log.info("MovieServiceImpl getMoviesWithCountryByCountryId: {}", id);
        Country country = countryService.getCountryWithMoviesByCountryId(id);
        if (country == null) {
            throw new ResourceNotFoundException(
                    " Genre with id " + id + " not found");
        } else {
            return country.getMovies();
        }
    }
}