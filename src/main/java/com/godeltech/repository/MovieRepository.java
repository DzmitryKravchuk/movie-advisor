package com.godeltech.repository;

import com.godeltech.entity.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findAllByDirectorContainingIgnoreCase(String director);

    @EntityGraph(attributePaths = {"country", "genres"})
    Movie getMovieById(Integer id);
}
