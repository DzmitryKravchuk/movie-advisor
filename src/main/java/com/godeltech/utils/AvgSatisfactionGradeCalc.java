package com.godeltech.utils;

import com.godeltech.entity.MovieUserEvaluation;

import java.util.List;

public class AvgSatisfactionGradeCalc {
    public static int calculate(final List<MovieUserEvaluation> allEvalByMovieId) {
        return allEvalByMovieId.stream().map(MovieUserEvaluation::getSatisfactionGrade)
                .reduce(new ImmutableAverage(0, 0),
                ImmutableAverage::accept,
                ImmutableAverage::combine)
                .average();
    }

    static final class ImmutableAverage {
        private final int total;
        private final int count;

        private ImmutableAverage(final int total, final int count) {
            this.total = total;
            this.count = count;
        }

        public int average() {
            return count > 0 ? (total) / count : 0;
        }

        public ImmutableAverage accept(final int i) {
            return new ImmutableAverage(total + i, count + 1);
        }

        public ImmutableAverage combine(final ImmutableAverage other) {
            return new ImmutableAverage(total + other.total, count + other.count);
        }
    }
}
