package com.godeltech.controller.handler;

import com.godeltech.dto.ErrorResponse;
import com.godeltech.exception.MovieUserEvaluationPersistenceException;
import com.godeltech.exception.NotUniqueLoginException;
import com.godeltech.exception.PasswordIncorrectException;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.exception.TokenAuthException;
import com.godeltech.exception.UpdateNotMatchIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public final class WebExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final RuntimeException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({UpdateNotMatchIdException.class,
            NotUniqueLoginException.class, PasswordIncorrectException.class,
            MovieUserEvaluationPersistenceException.class, TokenAuthException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final RuntimeException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUncaughtException(final Exception e) {
        log.error("Something went wrong", e);
        return new ErrorResponse("Something went wrong");
    }

}
