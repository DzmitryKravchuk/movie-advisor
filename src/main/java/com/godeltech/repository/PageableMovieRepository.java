package com.godeltech.repository;

import com.godeltech.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageableMovieRepository extends JpaRepository<Movie, Integer> {
    @EntityGraph(attributePaths = {"country", "genres"})
    Page<Movie> findAll(Pageable pageable);
}
