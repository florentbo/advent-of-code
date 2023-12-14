package be.bonamis.advent.year2023;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
  void solvePart02() {}
}
