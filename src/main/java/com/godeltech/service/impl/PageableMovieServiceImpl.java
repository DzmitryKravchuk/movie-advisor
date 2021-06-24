package com.godeltech.service.impl;

import com.godeltech.dto.MovieDTO;
import com.godeltech.entity.Movie;
import com.godeltech.repository.PageableMovieRepository;
import com.godeltech.service.MovieUserEvaluationService;
import com.godeltech.service.PageableMovieService;
import com.godeltech.utils.AvgSatisfactionGradeCalc;
import com.godeltech.utils.MovieDtoConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public final class PageableMovieServiceImpl implements PageableMovieService {
    private final PageableMovieRepository repository;
    private final MovieUserEvaluationService mueService;

    @Override
    public Page<MovieDTO> listAll(final int pageNum) {
        Page<Movie> movies = findAll(pageNum);
        movies.forEach(m -> m.setAvgSatisfactionGrade(
                AvgSatisfactionGradeCalc.calculate(
                        mueService.getAllByMovieId(m.getId()))));
        return movies.map(MovieDtoConverter::convertToDTO);
    }

    private Page<Movie> findAll(final int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return repository.findAll(pageable);
    }

}
