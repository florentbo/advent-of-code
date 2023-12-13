package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import java.util.*;
import java.util.List;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

@Slf4j
class Day13Test {

  private final List<String> input =
      Arrays.asList(
          """
#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#


                  """
              .split("\n"));
  private final Day13 day13 = new Day13(input);

  @Test
  void solvePart01() {
    assertThat(day13.solvePart01()).isEqualTo(405);
  }

  @Test
  void solvePart02() {
    assertThat(day13.solvePart02()).isEqualTo(16);
  }
}
