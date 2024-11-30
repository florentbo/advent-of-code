package be.bonamis.advent.year2024;

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
    assertThat(new Day01(List.of("1721")).solvePart01()).isEqualTo(2024);
  }

  @Test
  void solvePart02() {
    assertThat(new Day01(List.of("1721")).solvePart02()).isEqualTo(2025);
  }

  @ParameterizedTest
  @MethodSource("requiredFuelTestCases")
  void requiredFuelTest(int input, int expected) {
    //assertThat(be.bonamis.advent.year2019.Day01.requiredFuel(input)).isEqualTo(expected);
  }

  private static Stream<Arguments> requiredFuelTestCases() {
    return Stream.of(
            Arguments.of(12, 2),
            Arguments.of(14, 2),
            Arguments.of(1969, 654),
            Arguments.of(100756, 33583));
  }
}
