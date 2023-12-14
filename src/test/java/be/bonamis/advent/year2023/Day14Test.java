package be.bonamis.advent.year2023;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.utils.marsrover.Rover.Direction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;

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
          #OO..#...."""
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
    CharGrid grid = new CharGrid(input);
    int height = grid.getHeight();
    log.debug("before moved");
    grid.printLines();

    moveRocks(grid, SOUTH);
    Integer sum = loadsSum(grid);
    assertThat(sum).isEqualTo(136);
  }

  private Integer loadsSum(CharGrid grid) {
    return loads(grid).stream().reduce(Integer::sum).orElseThrow();
  }

  private List<Integer> loads(CharGrid grid) {
    return grid.stream()
        .filter(p1 -> grid.get(p1).equals('O'))
        .map(p -> grid.getHeight() - p.y)
        .toList();
  }

  @Test
  void moveRocksMultipleCycles() {
    String expectedAfterCycle3 =
        """
    .....#....
    ....#...O#
    .....##...
    ..O#......
    .....OOO#.
    .O#...O#.#
    ....O#...O
    .......OOO
    #...O###.O
    #.OOO#...O
    """;

    CharGrid grid = new CharGrid(input);
    log.debug("before moved");
    grid.printLines();

    for (int i = 0; i < 3; i++) {
      cycle(grid);
    }
    log.debug("\n\nAfter cycle 03");
    log.info("rows: {}", grid.rowsAsLines());
    assertThat(grid.rowsAsLines()).isEqualTo(new CharGrid(expectedAfterCycle3).rowsAsLines());
  }

  @Test
  void solvePart2() {
    CharGrid grid = new CharGrid(input);
    log.debug("before moved");
    grid.printLines();

    Map<List<String>, Integer> memo = getMemo(grid);
    int modulo = 1000000000 % memo.size();
    log.debug("memo {}", memo.size());
    log.debug("modulo {}", modulo);

    /*for (Map.Entry<List<String>, Integer> entry : memo.entrySet()) {
      List<String> key = entry.getKey();
      CharGrid charGrid = new CharGrid(key);
      Integer sum = loadsSum(charGrid);
      log.debug("sum: {}", sum);
    }*/
    log.debug("memo: {}", memo.values());

    List<String> key =
        memo.entrySet().stream()
            .filter(entry -> entry.getValue().equals(modulo + 1))
            .findFirst()
            .orElseThrow()
            .getKey();

    assertThat(loadsSum(new CharGrid(key))).isEqualTo(64);
  }

  private Map<List<String>, Integer> getMemo(CharGrid grid) {
    Map<List<String>, Integer> memo = new HashMap<>();

    /*for (int i = 1; i <= 10; i++) {
      cycle(grid);
    }*/
    for (int i = 1; i <= 30; i++) {
      cycle(grid);
      List<String> lines = grid.rowsAsLines();
      /*if (memo.containsKey(lines)) {
        return memo;
      }*/
      memo.put(lines, i);
      CharGrid charGrid = new CharGrid(lines);
      Integer sum = loadsSum(charGrid);
      if (sum == 64) {
        log.debug("sum: {} value {}", sum, i);
      }
    }
    return memo;
  }

  private void cycle(CharGrid grid) {
    moveRocks(grid, SOUTH);
    moveRocks(grid, WEST);
    moveRocks(grid, NORTH);
    moveRocks(grid, EAST);
  }

  private void moveRocks(CharGrid grid, Direction direction) {
    Stream<Point> stream = stream(grid, direction);
    stream.forEach(
        point -> {
          Character c = grid.get(point);
          // log.debug("point {} value {}", point, c);
          if (c.equals(ROCK)) {
            Rover rover = new Rover(direction, new Position(point.x, point.y));
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
              }
            }
          }
        });
  }

  private Stream<Point> stream(CharGrid grid, Direction direction) {
    return switch (direction) {
      case NORTH -> grid.streamFromDown();
      case EAST -> grid.streamFromLeft();
      default -> grid.stream();
    };
  }

  private boolean isDot(CharGrid grid, Point movedPoint) {
    return grid.get(movedPoint).equals(DOT);
  }

  private boolean isInTheGrid(CharGrid grid, Point movedPoint) {
    return grid.isInTheGrid().test(movedPoint);
  }

  @Test
  void rotate() {
    String text =
        """
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
  void stream() {
    String text = """
                    02
                    13
                    """;
    CharGrid grid = new CharGrid(text);
    assertThat(array(grid.stream(), grid)).isEqualTo("0123");
    assertThat(array(grid.streamFromDown(), grid)).isEqualTo("1032");
    assertThat(array(grid.streamFromLeft(), grid)).isEqualTo("2301");
  }

  private String array(Stream<Point> points, CharGrid grid) {
    return points.map(grid::get).map(String::valueOf).collect(Collectors.joining());
  }
}
