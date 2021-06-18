package com.godeltech.service.impl;

import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.exception.MovieUserEvaluationPersistenceException;
import com.godeltech.exception.UpdateNotMatchIdException;
import com.godeltech.repository.MovieRepository;
import com.godeltech.repository.MovieUserEvaluationRepository;
import com.godeltech.repository.UserRepository;
import com.godeltech.service.MovieUserEvaluationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public final class MovieUserEvalServiceImpl implements MovieUserEvaluationService {
    private final MovieUserEvaluationRepository repository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Override
    public void save(final MovieUserEvaluation entity) {
        log.info("MovieUserEvalServiceImpl save {}", entity);
        log.info("checkIfMovieExists {}", checkIfMovieExists(entity));
        if (!checkIfMovieExists(entity)) {
            throw new MovieUserEvaluationPersistenceException(" Movie with movieID: " + entity.getMovieId()
                    + " doesn't exist");
        }

        if (!checkIfUserExists(entity)) {
            throw new MovieUserEvaluationPersistenceException(" User with userID: " + entity.getUserId()
                    + " doesn't exist");
        }

        if (checkIfMovieUserEvaluationExists(entity)) {
            throw new MovieUserEvaluationPersistenceException(" MovieUserEvaluation for movieID: " + entity.getMovieId()
                    + " and for userId: " + entity.getUserId() + " already exists");
        }

        if (!checkIfSatisfactionGradeIsValid(entity)) {
            throw new MovieUserEvaluationPersistenceException(" Input SatisfactionGrade: " + entity.getSatisfactionGrade()
                    + ", must be between 0 and " + MAX_GRADE);
        }

        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        repository.save(entity);
    }

    @Override
    public MovieUserEvaluation getById(final String id) {
        log.info("MovieUserEvalServiceImpl get by id: {}", id);
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(" Object with index " + id + " not found"));
    }

    @Override
    public List<MovieUserEvaluation> getAll() {
        log.info("MovieUserEvalServiceImpl find ALL");
        return repository.findAll();
    }

    @Override
    public void delete(final String id) {
        log.info("MovieUserEvalServiceImpl delete by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void update(final MovieUserEvaluation entity, final String id) {
        log.info("MovieUserEvalServiceImpl update with id: {}", id);
        getById(id);
        if (!entity.getId().equals(id)) {
            throw new UpdateNotMatchIdException(" Object from request has index " + entity.getId()
                    + " and doesnt match index from url " + id);
        }
        save(entity);
    }

    @Override
    public List<MovieUserEvaluation> getAllByMovieId(final Integer movieId) {
        log.info("MovieUserEvalServiceImpl getByMovieId with id: {}", movieId);
        return repository.getAllByMovieId(movieId);

    }

    private boolean checkIfMovieUserEvaluationExists(final MovieUserEvaluation entity) {
        return repository.findByMovieIdAndUserId(entity.getMovieId(), entity.getUserId()) != null;
    }

    private boolean checkIfMovieExists(final MovieUserEvaluation entity) {
        return movieRepository.findById(entity.getMovieId()).isPresent();
    }

    private boolean checkIfUserExists(final MovieUserEvaluation entity) {
        return userRepository.findById(entity.getUserId()).isPresent();
    }

    private boolean checkIfSatisfactionGradeIsValid(final MovieUserEvaluation entity) {
        return entity.getSatisfactionGrade() > 0 && entity.getSatisfactionGrade() <= MAX_GRADE;
    }
}
