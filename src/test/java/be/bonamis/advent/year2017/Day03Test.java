package be.bonamis.advent.year2017;

import static be.bonamis.advent.utils.FileHelper.inputStream;
import static be.bonamis.advent.year2017.Day03.ADD_INDEX;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import be.bonamis.advent.common.InfiniteGrid;
import be.bonamis.advent.common.InfiniteGrid.Point;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

@Slf4j
class Day03Test {

  private Day03 day03;

  @BeforeEach
  void setUp() {
    InputStream inputStream = new ByteArrayInputStream("1".getBytes());
    day03 = new Day03(inputStream);
  }

  @Test
  void initGrid() {
    assertThat(new Day03(inputStream("1")).createInfiniteGrid(1, ADD_INDEX).get(0, 0)).isEqualTo(1);
    assertThat(new Day03(inputStream("2")).createInfiniteGrid(2, ADD_INDEX).get(1, 0)).isEqualTo(2);
    assertThat(new Day03(inputStream("3")).createInfiniteGrid(3, ADD_INDEX).get(1, 1)).isEqualTo(3);
    assertThat(new Day03(inputStream("11")).createInfiniteGrid(11, ADD_INDEX).get(2, 0))
        .isEqualTo(11);
    assertThat(new Day03(inputStream("12")).createInfiniteGrid(12, ADD_INDEX).get(2, 1))
        .isEqualTo(12);

    assertThat(new Day03(inputStream("12")).createInfiniteGrid(12, ADD_INDEX).last())
        .isEqualTo(Point.of(2, 1));
  }

  @Test
  void solveGrid() {
    assertThat(new Day03(inputStream("1")).solveGrid()).isZero();
    assertThat(new Day03(inputStream("12")).solveGrid()).isEqualTo(3);
    assertThat(new Day03(inputStream("23")).solveGrid()).isEqualTo(2);
    assertThat(new Day03(inputStream("1024")).solveGrid()).isEqualTo(31);
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
  void neighbors(String input, Point point, Set<Point> expected) {
    InfiniteGrid grid = day03.createInfiniteGrid(Integer.parseInt(input), ADD_INDEX);
    Set<Point> neighbors = grid.neighbors(point);
    assertThat(neighbors).isEqualTo(expected);
  }

  private static List<Arguments> neighbors() {
    return List.of(
        Arguments.of("1", Point.of(0, 0), emptySet()),
        Arguments.of("2", Point.of(1, 0), Set.of(Point.of(0, 0))),
        Arguments.of(
            "6", Point.of(-1, 0), Set.of(Point.of(-1, 1), Point.of(0, 0), Point.of(0, 1))));
  }

  @ParameterizedTest
  @MethodSource
  // @Disabled ("bad setup")
  void neighborsSum(String input, Point point, int expected) {
    /* InfiniteGrid grid = day03.createInfiniteGrid(Integer.parseInt(input), ADD_INDEX);
    int sum = grid.neighborsSum(point);
    assertThat(sum).isEqualTo(expected);//1 2 4 5 10 11 23 25*/

    assertThat(new Day03(inputStream(input)).otherGrid().get(point)).isEqualTo(expected);
  }

  private static List<Arguments> neighborsSum() {
    return List.of(
        Arguments.of("1", Point.of(0, 0), 1),
        Arguments.of("2", Point.of(1, 0), 1)
       /* Arguments.of("3", Point.of(1, 1), 2),
        Arguments.of("4", Point.of(0, 1), 4),
        Arguments.of("5", Point.of(-1, 1), 5),
        Arguments.of("6", Point.of(-1, 0), 10)*/
    );
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
