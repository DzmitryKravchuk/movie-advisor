package com.godeltech.repository;

import com.godeltech.entity.Genre;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @EntityGraph(attributePaths = {"movies"})
    Optional<Genre> findOneById(Integer genreId);

    Genre findOneByGenreName(String genreName);
}
