package be.bonamis.advent.year2023;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.SOUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class Day14Test {

  private static final char ROCK = 'O';
  private static final char DOT = '.';
  private final List<String> input =
      Arrays.asList(
          """
          O....#....
          O.OO#....#
          .....##...
          OO.#O....O
          .O.....O#.
          O.#..O.#.#
          ..O..#O..O
          .......O..
          #....###..
          #OO..#....                            """
              .split("\n"));
  private final Day14 day14 = new Day14(input);

  @Test
  void solvePart01() {
    assertThat(day14.solvePart01()).isEqualTo(136);
  }

  @Test
  void rocks() {
    List<List<Point>> columns = day14.getGrid().columns();
    assertThat(day14.rocks(columns.get(2))).isEqualTo(List.of(10L, 4L, 3L));
  }

  @Test
  void rocks2() {
    String content = FileHelper.content("2023/14/2023_14_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day14 day = new Day14(puzzle);
    List<List<Point>> columns = day.getGrid().columns();
    assertThat(day.rocks(columns.get(0))).contains(100L, 99L, 91L);
  }

  @Test
  void moveRocks() {
    String text =
        """
O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....
""";

    /*    text = """
    012
    345
    678
    """;*/
    CharGrid grid = new CharGrid(Arrays.asList(text.split("\n")));
    log.debug("before moved");
    grid.printLines();
    /*log.debug("\n\nafter  rotated\n\n");
    CharGrid rotated = grid.rotateCounterClockwise();
    rotated.printLines();*/

    /*log.debug("rotated");
    grid.stream2()
        .limit(10)
        .forEach(
            point -> {
              Character c = grid.get(point);
              log.debug("point {} value {}", point, c);
            });*/

    grid.stream()
        .forEach(
            point -> {
              Character c = grid.get(point);
              // log.debug("point {} value {}", point, c);
              if (c.equals(ROCK)) {
                Rover rover = new Rover(SOUTH, new Position(point.x, point.y));
                boolean canMove = true;
                while (canMove) {
                  Position newPosition = rover.move(Rover.Command.FORWARD).position();
                  Point movedPoint = new Point(newPosition.x(), newPosition.y());
                  canMove = isInTheGrid(grid, movedPoint) && isDot(grid, movedPoint);
                  if (canMove) {
                    grid.set(movedPoint, ROCK);
                    Position position = rover.position();
                    grid.set(new Point(position.x(), position.y()), DOT);
                    rover = rover.move(Rover.Command.FORWARD);
                    // grid.printArray();
                    // log.debug("moved");
                  }
                }
              }
            });
    // grid.printArray();

    Stream<Point> rocks = grid.stream().filter(p -> grid.get(p).equals('O'));
    int height = grid.getHeight();
    Integer sum = rocks.map(p -> height - p.y).reduce(Integer::sum).orElseThrow();
    assertThat(sum).isEqualTo(136);
  }

  private boolean isDot(CharGrid grid, Point movedPoint) {
    return grid.get(movedPoint).equals(DOT);
  }

  private boolean isInTheGrid(CharGrid grid, Point movedPoint) {
    return grid.isInTheGrid().test(movedPoint);
  }

  @Test
  void rotate() {
    String text = """
012
345
678
""";

    CharGrid grid = new CharGrid(text);

    assertThat(grid.rowsAsLines()).containsExactly("012", "345", "678");
    CharGrid rotated = grid.rotateCounterClockwise();
    assertThat(rotated.rowsAsLines()).containsExactly("258", "147", "036");
  }

  @Test
  void solvePart02() {}
}
