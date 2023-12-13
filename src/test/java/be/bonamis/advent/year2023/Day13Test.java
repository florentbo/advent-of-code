package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import java.util.*;
import java.util.List;
import lombok.extern.slf4j.*;
import org.apache.commons.lang3.tuple.Pair;
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
  void columnHandling() {
    assertThat(day13.columnHandling(day13.getGrids().get(0))).isEqualTo(5);
  }

  @Test
  void columnHandlingWithEdgeCase() {
    log.info("test case start");
    String text =
        """
####.#..#.####..#
####..##..#######
#.#.#.##.#.#.####
##.#..##..#.##..#
.#####...####....
.###.#..#.###....
.##..#..#..##....
..#.######.#..##.
#..#......#..#..#
######..######..#
#.##..##..##.####
..##.####.##..##.
...##.##.##...##.
..#.#....#.#..##.
.##..#..#..##.##.
#####....########
##..##..##..#####
""";
    Day13 day13WithEdgeCase = new Day13(Arrays.asList(text.split("\n")));
    assertThat(day13.columnHandling(day13WithEdgeCase.getGrids().get(0))).isEqualTo(15);
  }

  @Test
  void lineHandling() {
    String text =
        """
    #...##..#
    #....#..#
    ..##..###
    #####.##.
    #####.##.
    ..##..###
    #....#..#
    """;
    Day13 day13WithEdgeCase = new Day13(Arrays.asList(text.split("\n")));
    assertThat(day13.linesHandling(day13WithEdgeCase.getGrids().get(0))).isEqualTo(4);
  }

  @Test
  void lineHandling2() {
    String text =
        """
..##..#########
####.....#.#.#.
###.#..####.###
.#..##..####.#.
#.#.##.##......
....####..#..##
###.##..##.....
.#..##....###.#
.#..##....###.#
###.##..##.....
....####..#..##
#.#.##.##......
.#..##..####.#.
###.#..####.###
####....##.#.#.
..##..#########
..##..#########""";
    Day13 day13WithEdgeCase = new Day13(Arrays.asList(text.split("\n")));
    assertThat(day13.linesHandling(day13WithEdgeCase.getGrids().get(0))).isEqualTo(16);
  }

  @Test
  void findCommonElementsAndIndices() {
    assertThat(day13.findCommonElementsAndIndices(List.of("a", "b", "c", "c")))
        .isEqualTo(Map.of("c", List.of(2, 3)));

    assertThat(day13.findCommonElementsAndIndices(List.of("c", "b", "c", "c")))
        .isEqualTo(Map.of("c", List.of(0, 2, 3)));
  }

  @Test
  void findMiddles() {
    assertThat(day13.findMiddles(List.of(List.of(7, 9), List.of(0, 2, 3))))
        .containsExactly(Pair.of(2, 3));

    assertThat(day13.findMiddles(List.of(List.of(9, 10), List.of(0, 2, 3))))
        .containsExactly(Pair.of(9, 10), Pair.of(2, 3));
  }

  @Test
  void solvePart02() {
    assertThat(day13.solvePart02()).isEqualTo(16);
  }
}
