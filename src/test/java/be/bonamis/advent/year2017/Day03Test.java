package be.bonamis.advent.year2017;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

@Slf4j
class Day03Test {

  @Test
  void horizontal_thickness() {
    int input = 12;
    int smallestCornerSquareRoot = 3;
    int horizontalThickness = horizontalThickness(input);
    assertThat(horizontalThickness).isEqualTo(2);
    assertThat(smallestCornerSquareRoot(input)).isEqualTo(smallestCornerSquareRoot);

    assertThat(solve(input)).isEqualTo(3);
    assertThat(solve(23)).isEqualTo(2);
    assertThat(solve(1024)).isEqualTo(31);
    assertThat(solve(347991)).isEqualTo(480);
  }

  private int solve(int input) {
    int horizontalThickness = horizontalThickness(input);
    log.info("horizontalThickness: {}", horizontalThickness);
    int smallestCornerSquareRoot = smallestCornerSquareRoot(input);
    int smallestCorner = smallestCornerSquareRoot * smallestCornerSquareRoot;
    int sideLength = smallestCornerSquareRoot + 1;
    List<Integer> list =
        IntStream.iterate(smallestCorner + sideLength / 2, i -> i + sideLength)
            .limit(4)
            .boxed()
            .toList();
    log.info("list: {}", list);

    Integer min =
        IntStream.iterate(smallestCorner + sideLength / 2, i -> i + sideLength)
            .limit(4)
            .mapToObj(corner -> Math.abs(input - corner))
            .min(Comparable::compareTo)
            .orElseThrow();
    log.info("min: {}", min);

    return horizontalThickness + min;
  }

  private int horizontalThickness(int i) {
    int squareRoot = smallestCornerSquareRoot(i);
    return (squareRoot - 1) / 2 + 1;
  }

  private int smallestCornerSquareRoot(int i) {
    double sqrt = Math.sqrt(i);
    int squareRoot = (int) sqrt;
    log.info("squareRoot: {}", squareRoot);
    return squareRoot % 2 == 0 ? squareRoot - 1 : squareRoot;
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
