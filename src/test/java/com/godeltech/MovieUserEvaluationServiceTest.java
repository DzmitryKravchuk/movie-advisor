package com.godeltech;

import com.godeltech.entity.Movie;
import com.godeltech.entity.MovieUserEvaluation;
import com.godeltech.entity.User;
import com.godeltech.exception.MovieUserEvaluationPersistenceException;
import com.godeltech.service.MovieUserEvaluationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieUserEvaluationServiceTest extends AbstractCreationTest {

    @Test
    public void createMovieUserEvaluationTest() {
        final User user = createNewUser();
        final Movie movie = createNewMovie();
        final MovieUserEvaluation entity = createNewMueWithRandomSatisfactionGrade(movie.getId(), user.getId());
        MovieUserEvaluation entityFromBase = mueService.getById(entity.getId());
        assertNotNull(entityFromBase.getId());
        assertEquals(entity.getReview(), entityFromBase.getReview());
        mueService.delete(entity.getId());
    }

    @Test
    public void selectAllByMovieIdTest() {
        final Movie movie = createNewMovie();
        for (int i = 0; i < 5; i++) {
            User user = createNewUser();
            createNewMue(movie.getId(), user.getId(),i+1);
        }
        assertEquals(mueService.getAllByMovieId(movie.getId()).size(), 5);
        assertEquals(movieService.getByIdFullInfo(movie.getId()).getRating(),3);
    }

    @Test
    public void throwExceptionTest() {
        assertThrows(MovieUserEvaluationPersistenceException.class, () -> createNewMueWithRandomSatisfactionGrade(-1, -1));
    }

    @Test
    public void throwExceptionTest1() {
        final User user = createNewUser();
        final Movie movie = createNewMovie();
        createNewMueWithRandomSatisfactionGrade(movie.getId(), user.getId());
        assertThrows(MovieUserEvaluationPersistenceException.class, () -> createNewMueWithRandomSatisfactionGrade(movie.getId(), user.getId()));
    }

    @Test
    public void throwExceptionTest2() {
        final User user = createNewUser();
        final Movie movie = createNewMovie();
        assertThrows(MovieUserEvaluationPersistenceException.class, ()
                -> createNewMue(movie.getId(), user.getId(), MovieUserEvaluationService.MAX_GRADE + 1));
    }

}
