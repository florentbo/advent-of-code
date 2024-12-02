package be.bonamis.advent.year2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static be.bonamis.advent.year2024.Day02.*;
import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {

  private static final String INPUT =
      """
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
""";
  private static final List<String> LIST = List.of(INPUT.split("\n"));

  @Test
  void solvePart01() {
    assertThat(new Day02(LIST).solvePart01()).isEqualTo(2);
  }

  @Test
  void solvePart02() {
    assertThat(new Day02(LIST).solvePart02()).isEqualTo(4);
  }

  @ParameterizedTest
  @MethodSource("isSafeTestCases")
  void safeTest(String input, boolean expected) {
    assertThat(isSafe(input)).isEqualTo(expected);
  }

  private static Stream<Arguments> isSafeTestCases() {
    return Stream.of(
        Arguments.of("7 6 4 2 1", true),
        Arguments.of("1 2 7 8 9", false),
        Arguments.of("9 7 6 2 1", false),
        Arguments.of("1 3 2 4 5", false),
        Arguments.of("8 6 4 4 1", false),
        Arguments.of("1 3 6 7 9", true));
  }

  @ParameterizedTest
  @MethodSource("isSafeWithToleranceTestCases")
  void isSafeWithToleranceTest(String input, boolean expected) {
    assertThat(isSafeWithTolerance(input)).isEqualTo(expected);
  }

  private static Stream<Arguments> isSafeWithToleranceTestCases() {
    return Stream.of(
        Arguments.of("7 6 4 2 1", true),
        Arguments.of("1 2 7 8 9", false),
        Arguments.of("9 7 6 2 1", false),
        Arguments.of("1 3 2 4 5", true),
        Arguments.of("8 6 4 4 1", true),
        Arguments.of("1 3 6 7 9", true));
  }
}
