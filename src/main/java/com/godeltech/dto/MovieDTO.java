package com.godeltech.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class MovieDTO {
    private Integer id;
    private String title;
    private int releaseYear;
    private String director;
    private String country;
    private Set<String> genres;
    private String description;
    private int rating;
    private List<MovieEvaluationDTO> evaluations = new ArrayList<>();
}
