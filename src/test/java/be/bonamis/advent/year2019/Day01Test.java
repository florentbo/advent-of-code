package be.bonamis.advent.year2019;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

    @Test
    void solvePart01() {
        assertThat(new Day01(List.of("12", "14", "1969")).solvePart01()).isEqualTo(658);
    }

    @ParameterizedTest
    @MethodSource("requiredFuelTestCases")
    void requiredFuelTest(int input, int expected) {
        assertThat(Day01.requiredFuel(input)).isEqualTo(expected);
    }

    private static Stream<Arguments> requiredFuelTestCases() {
        return Stream.of(
                Arguments.of(12, 2),
                Arguments.of(14, 2),
                Arguments.of(1969, 654),
                Arguments.of(100756, 33583));
    }

    @ParameterizedTest
    @MethodSource("totalFuelTestTestCases")
    void totalFuelTest(int input, int expected) {
        assertThat(Day01.totalFuel(input)).isEqualTo(expected);
    }

    private static Stream<Arguments> totalFuelTestTestCases() {
        return Stream.of(
                Arguments.of(12, 2),
                Arguments.of(1969, 966),
                Arguments.of(100756, 50346));
    }

    @Test
    void solvePart02() {
        assertThat(new Day01(List.of("12", "1969")).solvePart02()).isEqualTo(968);
    }

}
