package com.godeltech.service.impl;

import com.godeltech.entity.Genre;
import com.godeltech.exception.ResourceNotFoundException;
import com.godeltech.exception.UpdateNotMatchIdException;
import com.godeltech.repository.GenreRepository;
import com.godeltech.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;

    @Override
    public void save(Genre entity) {
        log.info("GenreServiceImpl save {}", entity);
        Date currentDate = new Date();
        entity.setUpdated(currentDate);
        if (entity.getId() == null) {
            entity.setCreated(currentDate);
        }
        repository.save(entity);
    }

    @Override
    public Genre getById(Integer id) {
        log.info("GenreServiceImpl get by id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" Object with index " + id + " not found"));
    }

    @Override
    public Genre getGenreWithMoviesByGenreId(Integer genreId) {
        log.info("GenreServiceImpl getGenreWithMoviesByGenreId: {}", genreId);
        return repository.findOneById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException(" Object with index " + genreId + " not found"));
    }

    @Override
    public Genre getGenreByGenreName(String genreName) {
        log.info(" getGenreByGenreName: {}", genreName);
        return repository.findOneByGenreName(genreName);
    }


    @Override
    public List<Genre> getAll() {
        log.info("GenreServiceImpl find ALL");
        return repository.findAll();
    }

    @Override
    public void delete(Integer id) {
        log.info("GenreServiceImpl delete by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void update(Genre entity, Integer id) {
        log.info("GenreServiceImpl update with id: {}", id);
        getById(id);
        if (!entity.getId().equals(id)) {
            throw new UpdateNotMatchIdException(" Object from request has index "
                    + entity.getId() + " and doesnt match index from url " + id);
        }
        save(entity);
    }

    @Override
    public void deleteAll() {
        log.info("deleteAll");
        repository.deleteAll();
    }
}