package com.godeltech.repository;

import com.godeltech.entity.Genre;
import com.godeltech.entity.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @EntityGraph(attributePaths = {"country", "genres"})
    List<Movie> findAllByDirectorContainingIgnoreCase(String director);

    @EntityGraph(attributePaths = {"country", "genres"})
    List<Movie> findAllByTitleContainingIgnoreCase(String title);

    @EntityGraph(attributePaths = {"country", "genres"})
    Movie getMovieById(Integer id);

    @EntityGraph(attributePaths = {"country", "genres"})
    @Query("SELECT m FROM Movie m")
    List<Movie> getAllWithCountryAndGenre();

    @EntityGraph(attributePaths = {"country", "genres"})
    List<Movie> getAllByGenresIn(Set<Genre> genres);

    @EntityGraph(attributePaths = {"country", "genres"})
    Set<Movie> getAllByCountryCountryName(String countryName);
}
