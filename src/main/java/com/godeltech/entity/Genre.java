package com.godeltech.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class Genre extends AbstractEntity {
    @Column
    private String genreName;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "genreId"), inverseJoinColumns = @JoinColumn(name = "movieId"))
    private List<Movie> movieList;
}
