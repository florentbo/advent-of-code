package be.bonamis.advent.year2017;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import be.bonamis.advent.common.InfiniteGrid.Point;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

@Slf4j
class Day03Test {

  @Test
  void initGrid() {
    assertThat(Day03.createInfiniteGrid(1).get(0, 0)).isEqualTo(1);
    assertThat(Day03.createInfiniteGrid(2).get(1, 0)).isEqualTo(2);
    assertThat(Day03.createInfiniteGrid(3).get(1, 1)).isEqualTo(3);
    assertThat(Day03.createInfiniteGrid(11).get(2, 0)).isEqualTo(11);
    assertThat(Day03.createInfiniteGrid(12).get(2, 1)).isEqualTo(12);

    assertThat(Day03.createInfiniteGrid(12).last()).isEqualTo(Point.of(2, 1));
  }

  @Test
  void solveGrid() {
    assertThat(Day03.solveGrid(1)).isZero();
    assertThat(Day03.solveGrid(12)).isEqualTo(3);
    assertThat(Day03.solveGrid(23)).isEqualTo(2);
    assertThat(Day03.solveGrid(1024)).isEqualTo(31);
  }

  @ParameterizedTest
  @MethodSource
  void solvePart01(String input, int expected) {
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    assertThat(new Day03(inputStream).solvePart01()).isEqualTo(expected);
  }

  private static List<Arguments> solvePart01() {
    return List.of(
        Arguments.of("1", 0),
        Arguments.of("12", 3),
        Arguments.of("23", 2),
        Arguments.of("1024", 31));
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
