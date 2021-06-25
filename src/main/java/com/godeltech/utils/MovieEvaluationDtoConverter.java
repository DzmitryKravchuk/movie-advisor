package com.godeltech.utils;

import com.godeltech.dto.EvaluationRequest;
import com.godeltech.dto.MovieEvaluationDTO;
import com.godeltech.entity.MovieUserEvaluation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MovieEvaluationDtoConverter {

    public static MovieEvaluationDTO convertToDTO(final MovieUserEvaluation mue, final String userName) {
        MovieEvaluationDTO dto = new MovieEvaluationDTO();
        dto.setReview(mue.getReview());
        dto.setReviewDate(convertToSimpleDateFormat(mue.getUpdated()));
        dto.setSatisfactionGrade(mue.getSatisfactionGrade());
        dto.setUserName(userName);

        return dto;
    }

    public static MovieUserEvaluation convertFromRequest(final EvaluationRequest evalRequest, final Integer userId) {
        MovieUserEvaluation mue = new MovieUserEvaluation();
        mue.setReview(evalRequest.getReview());
        mue.setSatisfactionGrade(evalRequest.getSatisfactionGrade());
        mue.setMovieId(evalRequest.getMovieId());
        mue.setUserId(userId);
        return mue;
    }

    private static String convertToSimpleDateFormat(final LocalDate updated) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return updated.format(formatter);
    }
}
