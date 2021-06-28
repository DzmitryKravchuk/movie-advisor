package com.godeltech.controller.rest;

import com.godeltech.dto.MovieEvaluationRequest;
import com.godeltech.dto.MovieRequest;
import com.godeltech.dto.MovieResponse;
import com.godeltech.service.MovieDtoService;
import com.godeltech.service.MovieUserEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class RestMovieController {
    private final MovieDtoService movieDtoService;
    private final MovieUserEvaluationService mueService;

    @GetMapping()
    public List<MovieResponse> getAllMovies() {
        return movieDtoService.getAll();
    }


    @GetMapping("{id}")
    public MovieResponse getMovieWithEvaluationsById(@PathVariable Integer id) {
        return movieDtoService.getMovieWithEvaluationsById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public void saveMovie(@RequestBody MovieRequest request){
        movieDtoService.saveNewMovie(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{id}")
    public void updateMovie(@PathVariable Integer id, @RequestBody MovieResponse res){
        movieDtoService.update(id, res);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/evaluate")
    public void evaluateWithToken(@RequestBody MovieEvaluationRequest request, HttpServletRequest servletRequest){
        mueService.saveWithToken(request, servletRequest);
    }

}

