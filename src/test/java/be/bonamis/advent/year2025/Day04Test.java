package be.bonamis.advent.year2025;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import be.bonamis.advent.common.CharGrid;

class Day04Test {

  @Test
  void solvePart01() {
    final String INPUT =
"""
..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.
							  """;
    final List<String> LIST = List.of(INPUT.split("\n"));

    Day04 day04 = new Day04(LIST);
    var grid = day04.getGrid();
    assertThat(grid.get(new Point(0, 0))).isEqualTo('.');
    Point accessible = new Point(0, 2);
    assertThat(grid.get(accessible)).isEqualTo('@');
    Point notAccessible = new Point(1, 2);
    assertThat(grid.get(notAccessible)).isEqualTo('@');

    assertThat(isAccessible(grid, accessible)).isTrue();
    assertThat(isAccessible(grid, notAccessible)).isFalse();

    Stream<Point> stream = grid.stream();
    Stream<Point> rolls = stream.filter(point -> isRollOfPaper(grid.get(point)));
	long count = rolls.filter(point -> isAccessible(grid, point)).count();
	assertThat(count).isEqualTo(13);

    assertThat(day04.solvePart01()).isEqualTo(13);
  }

  @Test
  void name() {
final String INPUT =
"""
..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.
""";

	  final List<String> LIST = List.of(INPUT.split("\n"));

	  Day04 day04 = new Day04(LIST);
	  assertThat(day04.solvePart02()).isEqualTo(43);

  }

	boolean isAccessible(CharGrid grid, Point point) {
    List<Point> neighbours = grid.neighbours(point, true);
    long count = neighbours.stream().filter(point1 -> isRollOfPaper(grid.get(point1))).count();
    return count < 4;
  }

  boolean isRollOfPaper(char c) {
    return c == '@';
  }
}
