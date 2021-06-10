package com.godeltech.service.impl;

import com.godeltech.entity.Movie;
import com.godeltech.exception.ServiceEntityNotFoundException;
import com.godeltech.exception.EntityUpdateNotMatchIdException;
import com.godeltech.repository.MovieRepository;
import com.godeltech.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository repository;

    @Override
    public void save(Movie entity) {
        log.info("MovieServiceImpl save {}", entity);
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        repository.save(entity);
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
            throw new EntityUpdateNotMatchIdException(" Object from request has index " + entity.getId() + " and doesnt match index from url " + id);
        }
        save(entity);
    }

    @Override
    public List<Movie> getAllMoviesByDirector(String myFavoriteDirector) {
        log.info("MovieServiceImpl getAllMoviesByDirector with Director: {}", myFavoriteDirector);
        return repository.findAllByDirectorContainingIgnoreCase(myFavoriteDirector);
    }

    @Override
    public Movie getByIdContainsGenreCountry(Integer id) {
        log.info("MovieServiceImpl get one ContainsGenreCountry by id: {}", id);
        Movie entity = repository.getMovieById(id);
        if (entity == null) {
            throw new ServiceEntityNotFoundException(" Object with index " + id + " not found");
        }
        return entity;
    }
}