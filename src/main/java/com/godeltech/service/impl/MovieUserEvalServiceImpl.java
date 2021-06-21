package com.godeltech.service.impl;

import com.godeltech.dto.EvaluationRequest;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.exception.MovieUserEvaluationPersistenceException;
import com.godeltech.exception.UpdateNotMatchIdException;
import com.godeltech.repository.MovieRepository;
import com.godeltech.repository.MovieUserEvaluationRepository;
import com.godeltech.repository.UserRepository;
import com.godeltech.security.CustomUserDetailsService;
import com.godeltech.service.MovieUserEvaluationService;
import com.godeltech.utils.MovieEvaluationDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public final class MovieUserEvalServiceImpl implements MovieUserEvaluationService {
    private final MovieUserEvaluationRepository repository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public void save(final EvaluationRequest entityRequest) {
        log.info("MovieUserEvalServiceImpl save {}", entityRequest);
        MovieUserEvaluation entity = MovieEvaluationDtoConverter
                .convertFromRequest(entityRequest, userDetailsService.findByLogin(entityRequest.getUserName()).getId());
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
    public void deleteEvaluationsByMovieId(final Integer movieId) {
        log.info("deleteEvaluationsByMovieId by movieId: {}", movieId);
        repository.deleteAllByMovieId(movieId);
    }

    @Override
    public void update(final EvaluationRequest entityRequest, final String id) {
        log.info("MovieUserEvalServiceImpl update with id: {}", id);
        MovieUserEvaluation entity = MovieEvaluationDtoConverter
                .convertFromRequest(entityRequest, userDetailsService.findByLogin(entityRequest.getUserName()).getId());
        MovieUserEvaluation entityFromBase = getById(id);
        if (!entity.getUserId().equals(entityFromBase.getUserId())
                || !entity.getMovieId().equals(entityFromBase.getMovieId())) {
            throw new UpdateNotMatchIdException(" Object from request doesnt match with entity from base with id ");
        }
        save(entityRequest);
    }

    @Override
    public List<MovieUserEvaluation> getAllByMovieId(final Integer movieId) {
        log.info("MovieUserEvalServiceImpl getByMovieId with id: {}", movieId);
        return repository.getAllByMovieId(movieId);

    }

    @Override
    public MovieUserEvaluation getByMovieIdAndByUserId(final Integer movieId, final Integer userId) {
        log.info("getByMovieIdAndByUserId with movieId: {}, and userId: {}", movieId, userId);

        return repository.findByMovieIdAndUserId(movieId, userId).
                orElseThrow(() -> new ResourceNotFoundException(" Object with movieId: " + movieId
                        + " and userId: " + userId + " not found"));
    }

    @Override
    public void deleteAll() {
        log.info("deleteAll");
        repository.deleteAll();
    }

    private boolean checkIfMovieUserEvaluationExists(final MovieUserEvaluation entity) {
        return repository.findByMovieIdAndUserId(entity.getMovieId(), entity.getUserId()).isPresent();
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
