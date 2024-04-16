package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.FORWARD;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static org.assertj.core.api.Assertions.*;

import java.awt.*;
import java.util.*;
import java.util.stream.*;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.utils.marsrover.Rover.Direction;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

@Slf4j
class Day10Test {
  private final String squareLoopText = """
.....
.S-7.
.|.|.
.L-J.
.....
""";

  @Test
  void solvePart01() {
    Day10 day = new Day10(squareLoopText);
    assertThat(day.solvePart01()).isEqualTo(4);
  }

  @Test
  void loopStarts() {
    Day10 day = new Day10(squareLoopText);
    assertThat(day.loopStarts())
        .containsExactlyInAnyOrder(
            new Rover(EAST, Position.of(2, 1)), new Rover(SOUTH, Position.of(1, 2)));
  }

  @Test
  void solvePart01Example2() {
    String text = """
  ...F7.
  ..FJ|.
  .SJ.L7
  .|F--J
  .LJ...
  """;

    Day10 day = new Day10(text);
    assertThat(day.solvePart01()).isEqualTo(8);
  }

  @Test
  void solvePart02() {
    String text =
        """
  ...........
  .S-------7.
  .|F-----7|.
  .||.....||.
  .||.....||.
  .|L-7.F-J|.
  .|..|.|..|.
  .L--J.L--J.
  ...........
  """;

    Day10 day = new Day10(text);
    assertThat(day.solvePart01()).isEqualTo(23);
    assertThat(day.solvePart02()).isEqualTo(4);
  }

  @Test
  void solvePart02OtherExample() {
    String text =
            """
      .F----7F7F7F7F-7....
      .|F--7||||||||FJ....
      .||.FJ||||||||L7....
      FJL7L7LJLJ||LJ.L-7..
      L--J.L7...LJS7F-7L7.
      ....F-J..F7FJ|L7L7L7
      ....L7.F7||L7|.L7L7|
      .....|FJLJ|FJ|F7|.LJ
      ....FJL-7.||.||||...
      ....L---J.LJ.LJLJ...
      """;

    Day10 day = new Day10(text);
    assertThat(day.solvePart01()).isEqualTo(70);
    assertThat(day.solvePart02()).isEqualTo(8);
  }

  static Set<Point> allowedPoints(Point point, CharGrid grid, Direction... directions) {
    return Arrays.stream(directions)
        .map(
            direction -> {
              Position position = position(point, direction.verticalInverse());
              // log.debug("position {} -> {}", point, position);
              return new Point((int) position.x(), (int) position.y());
            })
        .filter(grid.isInTheGrid())
        .filter(p -> isNotDot(p, grid))
        .collect(Collectors.toSet());
  }

  /*
    | is a vertical pipe connecting north and south.
  - is a horizontal pipe connecting east and west.
  L is a 90-degree bend connecting north and east.
  J is a 90-degree bend connecting north and west.
  7 is a 90-degree bend connecting south and west.
  F is a 90-degree bend connecting south and east.
     */

  private static Position position(Point point, Direction direction) {
    return new Rover(direction, new Position(point.x, point.y)).move(FORWARD).position();
  }

  @Test
  void allowedPoints() {
    CharGrid grid = new CharGrid(Arrays.asList(squareLoopText.split("\n")));
    assertThat(allowedPoints(new Point(1, 2), grid, NORTH)).containsExactly(new Point(1, 1));
    assertThat(allowedPoints(new Point(1, 2), grid, SOUTH)).containsExactly(new Point(1, 3));
    assertThat(allowedPoints(new Point(1, 2), grid, WEST)).isEmpty();
    assertThat(allowedPoints(new Point(1, 2), grid, EAST)).isEmpty();

    assertThat(allowedPoints(new Point(1, 1), grid, EAST)).containsExactly(new Point(2, 1));
    assertThat(allowedPoints(new Point(3, 1), grid, WEST)).containsExactly(new Point(2, 1));

    assertThat(allowedPoints(new Point(1, 1), grid, EAST, SOUTH))
        .containsExactlyInAnyOrder(new Point(1, 2), new Point(2, 1));
  }

  public static boolean isNotDot(Point point, CharGrid grid) {
    return grid.get(point) != '.';
  }
}
