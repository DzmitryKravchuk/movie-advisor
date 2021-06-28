package com.godeltech.dto;

import lombok.Data;

@Data
public class MovieEvaluationRequest {
    private int movieId;
    private int satisfactionGrade;
    private String review;
}
