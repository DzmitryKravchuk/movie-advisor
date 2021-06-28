package com.godeltech.dto;

import lombok.Data;

import java.util.Set;

@Data
public class MovieRequest {
    private String title;
    private int releaseYear;
    private String director;
    private Integer countryId;
    private Set<Integer> genreIds;
    private String description;
}
