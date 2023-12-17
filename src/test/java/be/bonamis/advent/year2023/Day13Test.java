package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import java.util.*;
import java.util.List;

import be.bonamis.advent.common.CharGrid;
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
  void solvePart01Bis() {
    assertThat(day13.solvePart01Bis()).isEqualTo(405);
  }

  @Test
  void columnHandling() {
    CharGrid grid = day13.getGrids().get(0);

    List<String> columns = grid.columnsAsLines2();
    Pair<Integer, Integer> pair = Pair.of(5, 6);
    assertThat(day13.findReflectionLines(columns)).contains(pair);
    assertThat(day13.lineResult(columns, false)).hasValue(5);
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
    CharGrid grid = day13WithEdgeCase.getGrids().get(0);
    assertThat(day13.lineResult(grid.columnsAsLines2(), false)).hasValue(15);
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
    CharGrid grid = day13WithEdgeCase.getGrids().get(0);
    assertThat(day13.lineResult(grid.rowsAsLines2(), false)).hasValue(4);
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
    CharGrid grid = day13WithEdgeCase.getGrids().get(0);
    assertThat(day13.lineResult(grid.rowsAsLines2(), false)).hasValue(16);
  }

  @Test
  void findCommonElementsAndIndices() {
    assertThat(day13.findCommonElementsAndIndices(List.of("a", "b", "c", "c")))
        .isEqualTo(Map.of("c", List.of(2, 3)));

    assertThat(day13.findCommonElementsAndIndices(List.of("c", "b", "c", "c")))
        .isEqualTo(Map.of("c", List.of(0, 2, 3)));
  }



  @Test
  void solvePart02() {
    assertThat(new Day13(input).solvePart02()).isEqualTo(400);
  }

  @Test
  void solvePart02Bis() {
    assertThat(day13.solvePart02Bis()).isEqualTo(400);
  }


  @Test
  void lineHandlingPart02() {
    CharGrid grid = day13.getGrids().get(0);


    List<String> rows = grid.rowsAsLines2();
    Pair<Integer, Integer> pair = Pair.of(3, 4);
    assertThat(day13.findReflectionLines(rows)).contains(pair);
    assertThat(day13.lineResult(rows, false)).isEmpty();

    assertThat(day13.lineResult(rows, true)).hasValue(3);
  }

  @Test
  void linesHandlingWithSmudge() {
    CharGrid secondPattern = day13.getGrids().get(1);

    List<String> rows = secondPattern.rowsAsLines2();
    Pair<Integer, Integer> pair = Pair.of(4, 5);
    assertThat(day13.findReflectionLines(rows)).contains(pair);
    assertThat(day13.lineResult(rows, false)).hasValue(4);
    assertThat(day13.lineResult(rows, true)).hasValue(1);
  }

  @Test
  void part2EdgeCase() {
    String text = """
.####..#.#.#.##..
........#..##....
..##..#.....#..##
......##.##.#####
######.#.####....
..##....#..##.#..
.#..#..#####.#...
..##...#..#...#.#
#######.#....####
""";
    Day13 day13WithEdgeCase = new Day13(Arrays.asList(text.split("\n")));
    CharGrid grid = day13WithEdgeCase.getGrids().get(0);
    List<String> columns = grid.columnsAsLines2();
    assertThat(day13.lineResult(columns, true)).hasValue(16);
  }
}
