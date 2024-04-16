package be.bonamis.advent.year2017;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

@Slf4j
class Day01Test {

  @Test
  void matchDigits() {
    String input = "1122";
    List<Integer> digits = Day01.digits(input);
    assertThat(Day01.matches(digits)).containsExactly(1, 2);
  }

  @Test
  void matchHalfwayAroundDigits() {
    String input = "1212";
    List<Integer> digits = Day01.digits(input);
    assertThat(Day01.halfwayAroundMatches(digits)).containsExactly(1, 2, 1, 2);
  }

  @ParameterizedTest
  @MethodSource
  void solvePart01(String input, int expected) {
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    assertThat(new Day01(inputStream).solvePart01()).isEqualTo(expected);
  }

  private static List<Arguments> solvePart01() {
    return List.of(
        Arguments.of("1122", 3),
        Arguments.of("1111", 4),
        Arguments.of("1234", 0),
        Arguments.of("91212129", 9));
  }

  @ParameterizedTest
  @MethodSource
  void solvePart02(String input, int expected) {
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    assertThat(new Day01(inputStream).solvePart02()).isEqualTo(expected);
  }

  private static List<Arguments> solvePart02() {
    return List.of(
        Arguments.of("1212", 6),
        Arguments.of("1221", 0),
        Arguments.of("123425", 4),
        Arguments.of("123123", 12),
        Arguments.of("12131415", 4));
  }
}
