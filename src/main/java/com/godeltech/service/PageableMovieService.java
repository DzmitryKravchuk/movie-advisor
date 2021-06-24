package com.godeltech.service;

import com.godeltech.dto.MovieDTO;
import org.springframework.data.domain.Page;

public interface PageableMovieService {
    int pageSize = 10;
    Page<MovieDTO> listAll(int pageNum);
}
