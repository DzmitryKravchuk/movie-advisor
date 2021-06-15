package com.godeltech.repository;

import com.godeltech.entity.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findAllByDirectorContainingIgnoreCase(String director);

    List<Movie> findAllByTitleContainingIgnoreCase(String title);

    @EntityGraph(attributePaths = {"country", "genres"})
    Movie getMovieById(Integer id);

    @EntityGraph(attributePaths = {"country", "genres"})
    @Query("SELECT m FROM Movie m")
    List<Movie> getAllWithCountryAndGenre();

    @Query(value = "SELECT * FROM movie m " +
            "INNER JOIN movie_genre m_g ON m.id = m_g.movie_id " +
            "INNER JOIN genre g ON g.id = m_g.genre_id " +
            "INNER JOIN country c ON c.id = m.country_id " +
            "WHERE g.genre_name = ?1",nativeQuery = true)
    List<Movie> GetMoviesWithGenreAndCountryByGenre(String genreName);

    @Query(value = "SELECT * FROM movie m " +
            "INNER JOIN movie_genre m_g ON m.id = m_g.movie_id " +
            "INNER JOIN genre g ON g.id = m_g.genre_id " +
            "INNER JOIN country c ON c.id = m.country_id " +
            "WHERE c.country_name = ?1"
            ,nativeQuery = true)
    Set<Movie> GetMoviesWithGenreAndCountryByCountry(String countryName);
}
