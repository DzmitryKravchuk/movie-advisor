package com.godeltech;

import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.entity.User;
import com.godeltech.exception.ServiceMovieUserEvaluationPersistanceException;
import com.godeltech.service.MovieUserEvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class MovieUserEvaluationServiceTest extends AbstractCreationTest {

    @Test
    public void createMovieUserEvaluationTest() {
        final User user = createNewUser();
        final Movie movie = createNewMovie();
        final MovieUserEvaluation entity = createNewMueWithRandomSatisfactionGrade(movie.getId(), user.getId());
        MovieUserEvaluation entityFromBase = mueService.getById(entity.getId());
        log.info("createMovieUserEvaluationTest() entityFromBase {}", entityFromBase);
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getReview(), entityFromBase.getReview());
        mueService.delete(entity.getId());
    }

    @Test
    public void selectAllByMovieIdTest() {
        final Movie movie = createNewMovie();
        final Integer usersCount = getRandomInt(99);
        for (int i = 0; i < usersCount; i++) {
            User user = createNewUser();
            createNewMueWithRandomSatisfactionGrade(movie.getId(), user.getId());
        }
        assertEquals(mueService.getAllByMovieId(movie.getId()).size(), usersCount);
    }

    @Test
    public void throwExceptionTest() {
        assertThrows(ServiceMovieUserEvaluationPersistanceException.class, () -> createNewMueWithRandomSatisfactionGrade(-1, -1));
    }

    @Test
    public void throwExceptionTest1() {
        final User user = createNewUser();
        final Movie movie = createNewMovie();
        createNewMueWithRandomSatisfactionGrade(movie.getId(), user.getId());
        assertThrows(ServiceMovieUserEvaluationPersistanceException.class, () -> createNewMueWithRandomSatisfactionGrade(movie.getId(), user.getId()));
    }

    @Test
    public void throwExceptionTest2() {
        final User user = createNewUser();
        final Movie movie = createNewMovie();
        assertThrows(ServiceMovieUserEvaluationPersistanceException.class, ()
                -> createNewMue(movie.getId(), user.getId(), MovieUserEvaluationService.MAX_GRADE + 1));
    }

}
