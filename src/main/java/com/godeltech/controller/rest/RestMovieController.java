package com.godeltech.controller.rest;

import com.godeltech.dto.MovieDTO;
import com.godeltech.service.MovieDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies/")
@RequiredArgsConstructor
public class RestMovieController {
    private final MovieDtoService movieDtoService;

    @GetMapping()
    public List<MovieDTO> getAllMovies() {
        return movieDtoService.getAll();
    }
}
