package com.godeltech.utils;

import com.godeltech.entity.MovieUserEvaluation;

import java.util.Set;

public class AvgSatisfactionGradeCalc {
    public static int calculate(Set<MovieUserEvaluation> allEvalByMovieId) {
        return allEvalByMovieId.stream().map(MovieUserEvaluation::getSatisfactionGrade).reduce(new ImmutableAverage(),
                ImmutableAverage::accept,
                ImmutableAverage::combine)
                .average();
    }

    static class ImmutableAverage {
        private final int total;
        private final int count;

        public ImmutableAverage() {
            this.total = 0;
            this.count = 0;
        }

        public ImmutableAverage(int total, int count) {
            this.total = total;
            this.count = count;
        }

        public int average() {
            return count > 0 ? (total) / count : 0;
        }

        public ImmutableAverage accept(int i) {
            return new ImmutableAverage(total + i, count + 1);
        }

        public ImmutableAverage combine(ImmutableAverage other) {
            return new ImmutableAverage(total + other.total, count + other.count);
        }
    }
}
