package com.godeltech.dto;

import lombok.Data;

@Data
public class EvaluationRequest {
    private int movieId;
    private int satisfactionGrade;
    private String userName;
    private String review;
}
