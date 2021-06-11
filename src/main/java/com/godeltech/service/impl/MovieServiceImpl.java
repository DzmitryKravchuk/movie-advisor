package com.godeltech.service.impl;

import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.exception.ServiceEntityNotFoundException;
import com.godeltech.exception.ServiceUpdateNotMatchIdException;
import com.godeltech.repository.MovieRepository;
import com.godeltech.repository.MovieUserEvaluationRepository;
import com.godeltech.service.MovieService;
import com.godeltech.utils.AvgSatisfactionGradeCalc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository repository;
    private final MovieUserEvaluationRepository mueRepository;

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
            throw new ServiceUpdateNotMatchIdException(" Object from request has index " + entity.getId() + " and doesnt match index from url " + id);
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
        Set<MovieUserEvaluation> allByMovieId = mueRepository.getAllByMovieId(id);
        entity.setMovieEvaluations(allByMovieId);
        entity.setAvgSatisfactionGrade(AvgSatisfactionGradeCalc.calculate(allByMovieId));
        return entity;
    }
}