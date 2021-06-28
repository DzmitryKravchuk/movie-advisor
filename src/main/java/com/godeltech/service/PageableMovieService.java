package com.godeltech.service;

import com.godeltech.dto.MovieResponse;
import org.springframework.data.domain.Page;

public interface PageableMovieService {
    int pageSize = 10;
    Page<MovieResponse> listAll(int pageNum);
}
