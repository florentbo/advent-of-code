package be.bonamis.advent.year2017;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

import be.bonamis.advent.common.InfiniteGrid;
import be.bonamis.advent.common.InfiniteGrid.Point;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

@Slf4j
class Day03Test {

  @Test
  void initGrid() {
    assertThat(createInfiniteGrid(1).get(0, 0)).isEqualTo(1);
    assertThat(createInfiniteGrid(2).get(1, 0)).isEqualTo(2);
    assertThat(createInfiniteGrid(3).get(1, 1)).isEqualTo(3);
    assertThat(createInfiniteGrid(11).get(2, 0)).isEqualTo(11);
    assertThat(createInfiniteGrid(12).get(2, 1)).isEqualTo(12);

    assertThat(createInfiniteGrid(12).last()).isEqualTo(Point.of(2, 1));
  }

  @Test
  void solveGrid() {
    assertThat(solveGrid(1)).isZero();
    assertThat(solveGrid(12)).isEqualTo(3);
    assertThat(solveGrid(23)).isEqualTo(2);
    assertThat(solveGrid(1024)).isEqualTo(31);
  }

  static int solveGrid(int input) {
    Point last = createInfiniteGrid(input).last();
    return Math.abs(last.x()) + Math.abs(last.y());
  }

  static InfiniteGrid createInfiniteGrid(int max) {
    /*
    17  16  15  14  13
    18   5   4   3  12
    19   6   1   2  11
    20   7   8   9  10
    21  22  23---> ...
     */

    InfiniteGrid grid = new InfiniteGrid();

    Rover spiral = new Rover(WEST, Position.of(0, 1));
    int index = 1;
    log.debug("spiral initial      : {}", spiral);
    while (index <= max) {
      if (hasNoValueToTheLeft(grid, spiral)) {
        spiral = spiral.move(Rover.Command.LEFT);
      }
      log.debug("spiral after if     : {}", spiral);
      spiral = spiral.move(Rover.Command.FORWARD);
      log.debug("spiral after forward: {}", spiral);
      grid.addValue(Point.from(spiral.position()), index);
      log.debug("grid: {}", grid);
      index++;
    }
    return grid;
  }

  static boolean hasNoValueToTheLeft(InfiniteGrid grid, Rover spiral) {
    log.debug("hasNoValueToTheLeft spiral before left     : {}", spiral);
    Rover left = spiral.move(Rover.Command.LEFT);
    left = left.move(Rover.Command.FORWARD);
    log.debug("hasNoValueToTheLeft spiral after left      : {}", left);
    Position position = left.position();
    log.debug("position: {}", position);
    return grid.find((int) position.x(), (int) position.y()).isEmpty();
  }

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
    log.debug("horizontalThickness: {}", horizontalThickness);
    int smallestCornerSquareRoot = smallestCornerSquareRoot(input);
    int smallestCorner = smallestCornerSquareRoot * smallestCornerSquareRoot;
    int sideLength = smallestCornerSquareRoot + 1;
    List<Integer> list =
        IntStream.iterate(smallestCorner + sideLength / 2, i -> i + sideLength)
            .limit(4)
            .boxed()
            .toList();
    log.debug("list: {}", list);

    Integer min =
        IntStream.iterate(smallestCorner + sideLength / 2, i -> i + sideLength)
            .limit(4)
            .mapToObj(corner -> Math.abs(input - corner))
            .min(Comparable::compareTo)
            .orElseThrow();
    log.debug("min: {}", min);

    return horizontalThickness + min;
  }

  private int horizontalThickness(int i) {
    int squareRoot = smallestCornerSquareRoot(i);
    return (squareRoot - 1) / 2 + 1;
  }

  private int smallestCornerSquareRoot(int i) {
    double sqrt = Math.sqrt(i);
    int squareRoot = (int) sqrt;
    log.debug("squareRoot: {}", squareRoot);
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
