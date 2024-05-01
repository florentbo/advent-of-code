package be.bonamis.advent.year2016;

import static be.bonamis.advent.utils.FileHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Slf4j
class Day02Test {

  @ParameterizedTest
  @MethodSource("part01TestCases")
  void lineMoves(String input, int expected) {
    assertThat(new Day02(inputStream(input)).solvePart01()).isEqualTo(expected);
  }

  static String text =
      """
          U
          """;

  static String text2 =
      """
          ULL
          """;

  static String text3 =
      """
          ULL
          RRDDD
          """;

  static String text4 =
      """
         ULL
         RRDDD
         LURDL
         UUUUD
         """;

  private static Stream<Arguments> part01TestCases() {

    return Stream.of(
        Arguments.of(text, "2"),
        Arguments.of(text2, "1"),
        Arguments.of(text3, "19"),
        Arguments.of(text4, "1985"));
  }

  @ParameterizedTest
  @MethodSource("part02TestCases")
  void lineMoves2(String input, String expected) {
    assertThat(new Day02(inputStream(input)).solvePart02String()).isEqualTo(expected);
  }

  private static Stream<Arguments> part02TestCases() {

    return Stream.of(
        Arguments.of(text, "5"),
        Arguments.of(text2, "5"),
        Arguments.of(text3, "5D"),
        Arguments.of(text4, "5DB3"));
  }
}
