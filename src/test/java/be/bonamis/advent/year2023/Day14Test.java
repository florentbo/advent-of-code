package be.bonamis.advent.year2023;

import be.bonamis.advent.common.CharGrid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day14Test {

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
  void moveRocks() {
    long sum = day14.solvePart01();
    assertThat(sum).isEqualTo(136);
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
      day14.cycle(grid);
    }
    log.debug("\n\nAfter cycle 03");
    log.info("rows: {}", grid.rowsAsLines());
    assertThat(grid.rowsAsLines()).isEqualTo(new CharGrid(expectedAfterCycle3).rowsAsLines());
  }

  @Test
  void solvePart2() {
    long result = day14.solvePart02();
    assertThat(result).isEqualTo(64);
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

  @Test
  void findSeries() {
    List<Integer> list = List.of(1, 2, 11, 12, 13, 14, 15, 16, 17);

    assertThat(day14.findSeries(list)).isEqualTo(Pair.of(2, 7));
  }

  private String array(Stream<Point> points, CharGrid grid) {
    return points.map(grid::get).map(String::valueOf).collect(Collectors.joining());
  }
}
