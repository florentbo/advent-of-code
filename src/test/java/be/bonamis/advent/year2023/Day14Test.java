package be.bonamis.advent.year2023;

import be.bonamis.advent.utils.FileHelper;
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
  void rocks2() {
    String content = FileHelper.content("2023/14/2023_14_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day14 day = new Day14(puzzle);
    List<List<Point>> columns = day.getGrid().columns();
    assertThat(day.rocks(columns.get(0))).contains(100L,99L,91L);
  }

  @Test
  void solvePart02() {}
}
