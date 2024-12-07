package be.bonamis.advent.year2024;

import be.bonamis.advent.year2024.Day07.Equation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day07Test {
  private static final String INPUT =
      """
            190: 10 19
            3267: 81 40 27
            83: 17 5
            156: 15 6
            7290: 6 8 6 15
            161011: 16 10 13
            192: 17 8 14
            21037: 9 7 18 13
            292: 11 6 16 20
            """;
  private static final List<String> LIST = List.of(INPUT.split("\n"));

  @Test
  void solvePart01() {
    assertThat(new Day07(LIST).solvePart01()).isEqualTo(3749);
  }

  @Test
  void solvePart02() {
    assertThat(new Day07(LIST).solvePart02()).isEqualTo(11387);
  }

  @ParameterizedTest
  @MethodSource("canBeMadeTestCases")
  void canBeMadeTest(Equation input, boolean expected) {
    assertThat(Day07.canBeMade(input)).isEqualTo(expected);
  }

  private static Stream<Arguments> canBeMadeTestCases() {
    return Stream.of(
        Arguments.of(Equation.of("190: 10 19"), true),
        Arguments.of(Equation.of("292: 11 6 16 20"), true),
        Arguments.of(Equation.of("83: 17 5"), false));
  }

  @ParameterizedTest
  @MethodSource("canBeMadeWithConcatenationTestCases")
  void canBeMadeWithConcatenationTest(Equation input, boolean expected) {
    assertThat(Day07.canBeMadeWithConcatenation(input)).isEqualTo(expected);
  }

  private static Stream<Arguments> canBeMadeWithConcatenationTestCases() {
    return Stream.of(
        Arguments.of(Equation.of("156: 15 6"), true),
        Arguments.of(Equation.of("7290: 6 8 6 15"), true))
        ;
  }
}
