package be.bonamis.advent.year2024;

import be.bonamis.advent.common.CharGrid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day08Test {

  private static final String INPUT =
      """
            ..........
            ..........
            ..........
            ....a.....
            ..........
            .....a....
            ..........
            ..........
            ..........
            ..........
            """;
  private static final List<String> LIST = List.of(INPUT.split("\n"));

  @Test
  void solvePart01() {
    assertThat(new Day08(List.of(THIRD_INPUT.split("\n"))).solvePart01()).isEqualTo(14);
  }

  @Test
  void solvePart02() {
    assertThat(new Day08(LIST).solvePart02()).isEqualTo(1);
  }

  @Test
  void pairCombinationsTest() {
    assertThat(Day08.pairCombinations(List.of("red", "black", "white")))
        .containsExactly(
            Pair.of("black", "red"),
            Pair.of("white", "red"),
            Pair.of("red", "black"),
            Pair.of("white", "black"),
            Pair.of("red", "white"),
            Pair.of("black", "white"));
  }

  private static final String INPUT2 =
      """
                ..........
                ...#......
                ..........
                ....a.....
                ..........
                .....a....
                ..........
                ......#...
                ..........
                ..........
                """;

  @Test
  void oppositesTest() {
    CharGrid grid = new CharGrid(INPUT2);
    Map<Character, List<Point>> result =
        grid.stream().filter(p -> grid.get(p) == '#').collect(Collectors.groupingBy(grid::get));
    Set<Point> expected = new HashSet<>(result.get('#'));

    Day08 day08 = new Day08(LIST);
    List<Pair<Point, Point>> pairs = day08.pairs();

    assertThat(Day08.opposites(pairs, day08.getGrid(), day08.getChars()))
        .containsExactlyInAnyOrderElementsOf(expected);
  }

  private static final String SECOND_INPUT =
      """
                ..........
                ..........
                ..........
                ....a.....
                ........a.
                .....a....
                ..........
                ......A...
                ..........
                ..........
                """;

  private static final String EXPECTED_RESULT =
      """
                ..........
                ...#......
                #.........
                ....a.....
                ........a.
                .....a....
                ..#.......
                ......A...
                ..........
                ..........
                """;

  @Test
  void oppositesTest2() {
    CharGrid grid = new CharGrid(EXPECTED_RESULT);
    Map<Character, List<Point>> result =
        grid.stream().filter(p -> grid.get(p) == '#').collect(Collectors.groupingBy(grid::get));
    Set<Point> expected = new HashSet<>(result.get('#'));

    Day08 day08 = new Day08(List.of(SECOND_INPUT.split("\n")));
    List<Pair<Point, Point>> pairs = day08.pairs();

    assertThat(Day08.opposites(pairs, day08.getGrid(), day08.getChars()))
        .containsAnyElementsOf(expected);
    log.info("chars: {}", expected);
  }

  private static final String THIRD_INPUT =
      """
                ............
                ........0...
                .....0......
                .......0....
                ....0.......
                ......A.....
                ............
                ............
                ........A...
                .........A..
                ............
                ............
                """;

  private static final String EXPECTED_RESULT3 =
      """
                ......#....#
                ...#....0...
                ....#0....#.
                ..#....0....
                ....0....#..
                .#....A.....
                ...#........
                #......#....
                ........A...
                .........A..
                ..........#.
                ..........#.
                """;

  @Test
  void oppositesTest3() {
    CharGrid grid = new CharGrid(EXPECTED_RESULT3);
    Map<Character, List<Point>> result =
        grid.stream().filter(p -> grid.get(p) == '#').collect(Collectors.groupingBy(grid::get));
    Set<Point> expected = new HashSet<>(result.get('#'));

    Day08 day08 = new Day08(List.of(THIRD_INPUT.split("\n")));
    List<Pair<Point, Point>> pairs = day08.pairs();

    assertThat(Day08.opposites(pairs, day08.getGrid(), day08.getChars()))
            .containsAnyElementsOf(expected);
  }
}
