package com.godeltech.dto;

import lombok.Data;

import java.util.Set;

@Data
public class MovieDTO {
    private Integer id;
    private String title;
    private int releaseYear;
    private String director;
    private String country;
    private Set<String> genres;
    private int rating;
}
