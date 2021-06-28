package com.godeltech.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class MovieResponse {
    private Integer id;
    private String title;
    private int releaseYear;
    private String director;
    private String country;
    private Set<String> genres;
    private String description;
    private int rating;
    private List<MovieEvaluationResponse> evaluations;
}
