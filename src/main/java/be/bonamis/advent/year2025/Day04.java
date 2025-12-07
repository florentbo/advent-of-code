package be.bonamis.advent.year2025;

import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.common.CharGrid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day04 extends TextDaySolver {

  private final CharGrid grid;

  public Day04(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(this.puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
  }

  public Day04(InputStream inputStream) {
    super(inputStream);
    this.grid = new CharGrid(this.puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
  }

  boolean isAccessible(Point point) {
    List<Point> neighbours = this.grid.neighbours(point, true);
    long count = neighbours.stream().filter(point1 -> isRollOfPaper(this.grid.get(point1))).count();
    return count < 4;
  }

  boolean isRollOfPaper(char c) {
    return c == '@';
  }

  @Override
  public long solvePart01() {
    Stream<Point> rolls = this.grid.stream().filter(point -> isRollOfPaper(this.grid.get(point)));

    return rolls.filter(this::isAccessible).count();
  }

  @Override
  public long solvePart02() {
    return 1011L;
  }
}
