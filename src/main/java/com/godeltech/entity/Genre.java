package com.godeltech.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table
public final class Genre extends AbstractEntity {
    @Column
    private String genreName;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "movie_genre",
            joinColumns = {@JoinColumn(name = "genreId")},
            inverseJoinColumns = {@JoinColumn(name = "movieId")}
    )
    private Set<Movie> movies = new HashSet<>();

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Genre genre = (Genre) o;
        return genreName.equals(genre.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), genreName);
    }
}
