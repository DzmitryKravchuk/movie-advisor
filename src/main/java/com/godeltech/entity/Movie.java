package com.godeltech.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public final class Movie extends AbstractEntity {
    @Column
    private String title;
    @Column
    private int releaseYear;
    @Column
    private String director;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Country country;
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "movie_genre", joinColumns = {@JoinColumn(name = "movieId")}, inverseJoinColumns = {@JoinColumn(name = "genreId")})
    private Set<Genre> genres= new HashSet<>();
    @Transient
    private int avgSatisfactionGrade;
    @Transient
    private List<MovieUserEvaluation> movieEvaluations;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Movie movie = (Movie) o;
        return releaseYear == movie.releaseYear && avgSatisfactionGrade == movie.avgSatisfactionGrade
                && title.equals(movie.title) && director.equals(movie.director)
                && description.equals(movie.description) && country.equals(movie.country) && Objects.equals(genres, movie.genres) && Objects.equals(movieEvaluations, movie.movieEvaluations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, releaseYear, director, description, country, genres, avgSatisfactionGrade, movieEvaluations);
    }

    @Override
    public String toString() {
        return "Movie{"
                + "title='" + title + '\''
                + ", releaseYear=" + releaseYear
                + ", director='" + director + '\''
                + ", description='" + description + '\''
                + ", country=" + country.getCountryName()
                + ", genres=" + genres.stream().map(Genre::getGenreName).collect(Collectors.toList())
                + ", avgSatisfactionGrade=" + avgSatisfactionGrade
                + ", movieEvaluations=" + movieEvaluations
                + '}';
    }
}
