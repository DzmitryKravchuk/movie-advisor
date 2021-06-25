package com.godeltech.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@Data
@AllArgsConstructor
public class MovieRating {
    @Id
    private Integer movieId;
    private Integer rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieRating that = (MovieRating) o;
        return movieId.equals(that.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }
}
