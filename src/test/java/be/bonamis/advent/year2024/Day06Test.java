package be.bonamis.advent.year2024;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day06Test {

  private static final String INPUT =
      """
....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...
""";
  private static final List<String> LIST = List.of(INPUT.split("\n"));

  @Test
  void solvePart01() {
    assertThat(new Day06(LIST).solvePart01()).isEqualTo(41);
  }

  @Test
  void solvePart02() {
    assertThat(new Day06(LIST).solvePart02()).isEqualTo(6);
  }
}
