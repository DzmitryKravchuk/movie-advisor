package com.godeltech.service.impl;

import com.godeltech.dto.MovieEvaluationRequest;
import com.godeltech.dto.MovieEvaluationResponse;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.entity.User;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.exception.MovieUserEvaluationPersistenceException;
import com.godeltech.exception.UpdateNotMatchIdException;
import com.godeltech.repository.MovieUserEvaluationRepository;
import com.godeltech.security.CustomUserDetailsService;
import com.godeltech.service.AuthenticationService;
import com.godeltech.service.MovieService;
import com.godeltech.service.MovieUserEvaluationService;
import com.godeltech.service.UserService;
import com.godeltech.utils.MovieEvaluationDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieUserEvalServiceImpl implements MovieUserEvaluationService {
    private final MovieUserEvaluationRepository repository;
    private final UserService userService;
    private final MovieService movieService;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationService authenticationService;


    private User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userDetailsService.findByLogin(currentPrincipalName);
    }

    @Override
    public void save(MovieEvaluationRequest entityRequest, Integer userId) {
        log.info(" save {}", entityRequest);
        // Integer userId = getLoggedUser().getId();
        MovieUserEvaluation entity = MovieEvaluationDtoConverter
                .convertFromRequest(entityRequest, userId);
        validate(entity);
        setCreatedUpdated(entity);
        repository.save(entity);
    }

    private void setCreatedUpdated(MovieUserEvaluation entity) {
        LocalDate currentDate = LocalDate.now();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
    }

    private void validate(MovieUserEvaluation entity) {
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
    }

    @Override
    public void saveWithToken(MovieEvaluationRequest request, HttpServletRequest servletRequest) {
        log.info(" saveWithToken {}", request);
        User user = authenticationService.getUserFromToken(servletRequest);
        MovieUserEvaluation entity = MovieEvaluationDtoConverter
                .convertFromRequest(request, user.getId());
        validate(entity);
        setCreatedUpdated(entity);
        repository.save(entity);
    }

    @Override
    public MovieUserEvaluation getById(String id) {
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
    public void delete(String id) {
        log.info("MovieUserEvalServiceImpl delete by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void deleteEvaluationsByMovieId(Integer movieId) {
        log.info("deleteEvaluationsByMovieId by movieId: {}", movieId);
        repository.deleteAllByMovieId(movieId);
    }

    @Override
    public void update(MovieEvaluationRequest entityRequest, String id, HttpServletRequest servletRequest) {
        log.info("MovieUserEvalServiceImpl update with id: {}", id);
        User user = authenticationService.getUserFromToken(servletRequest);
        MovieUserEvaluation entity = MovieEvaluationDtoConverter
                .convertFromRequest(entityRequest, user.getId());
        MovieUserEvaluation entityFromBase = getById(id);
        if (!entity.getUserId().equals(entityFromBase.getUserId())
                || !entity.getMovieId().equals(entityFromBase.getMovieId())) {
            throw new UpdateNotMatchIdException(" Object from request doesnt match with entity from base with id ");
        }
        saveWithToken(entityRequest, servletRequest);
    }

    @Override
    public List<MovieUserEvaluation> getAllByMovieId(Integer movieId) {
        log.info("MovieUserEvalServiceImpl getByMovieId with id: {}", movieId);
        return repository.getAllByMovieId(movieId);

    }

    @Override
    public MovieUserEvaluation getByMovieIdAndByUserId(Integer movieId, Integer userId) {
        log.info("getByMovieIdAndByUserId with movieId: {}, and userId: {}", movieId, userId);

        return repository.findByMovieIdAndUserId(movieId, userId).
                orElseThrow(() -> new ResourceNotFoundException(" Object with movieId: " + movieId
                        + " and userId: " + userId + " not found"));
    }

    @Override
    public List<MovieEvaluationResponse> getMovieEvaluationDTOs(Integer movieId) {
        log.info("getMovieEvaluationDTOs by movieId: {}", movieId);
        return getAllByMovieId(movieId).stream()
                .map(mue -> MovieEvaluationDtoConverter.convertToDTO(mue,
                        userService.getById(mue.getUserId()).getUserName()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        log.info("deleteAll");
        repository.deleteAll();
    }

    private boolean checkIfMovieUserEvaluationExists(MovieUserEvaluation entity) {
        return repository.findByMovieIdAndUserId(entity.getMovieId(), entity.getUserId()).isPresent();
    }

    private boolean checkIfMovieExists(MovieUserEvaluation entity) {
        try {
            movieService.getById(entity.getMovieId());
        } catch (ResourceNotFoundException e) {
            return false;
        }
        return true;
    }

    private boolean checkIfUserExists(MovieUserEvaluation entity) {
        try {
            userService.getById(entity.getUserId());
        } catch (ResourceNotFoundException e) {
            return false;
        }
        return true;
    }

    private boolean checkIfSatisfactionGradeIsValid(MovieUserEvaluation entity) {
        return entity.getSatisfactionGrade() > 0 && entity.getSatisfactionGrade() <= MAX_GRADE;
    }
}
