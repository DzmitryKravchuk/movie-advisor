package com.godeltech.service.impl;

import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.exception.ServiceEntityNotFoundException;
import com.godeltech.exception.EntityUpdateNotMatchIdException;
import com.godeltech.repository.MovieUserEvaluationRepository;
import com.godeltech.service.MovieUserEvalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieUserEvalServiceImpl implements MovieUserEvalService {
    private final MovieUserEvaluationRepository repository;

    @Override
    public void save(MovieUserEvaluation entity) {
        log.info("MovieUserEvalServiceImpl save {}", entity);
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        repository.save(entity);
    }

    @Override
    public MovieUserEvaluation getById(String id) {
        log.info("MovieUserEvalServiceImpl get by id: {}", id);
        return repository.findById(id).
                orElseThrow(() -> new ServiceEntityNotFoundException(" Object with index " + id + " not found"));
    }

    @Override
    public List<MovieUserEvaluation> getAll() {
        log.info("MovieUserEvalServiceImpl find ALL");
        return repository.findAll();
    }

    @Override
    public void delete(String id) {
        log.info("MovieUserEvalServiceImpl delete by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void update(MovieUserEvaluation entity, String id) {
        log.info("MovieUserEvalServiceImpl update with id: {}", id);
        getById(id);
        if (!entity.getId().equals(id)) {
            throw new EntityUpdateNotMatchIdException(" Object from request has index " + entity.getId() + " and doesnt match index from url " + id);
        }
        save(entity);
    }

    @Override
    public Set<MovieUserEvaluation> getAllByMovieId(Integer movieId) {
        log.info("MovieUserEvalServiceImpl getByMovieId with id: {}", movieId);
        return repository.getAllByMovieId(movieId);

    }
}
