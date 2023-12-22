package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day21 extends DaySolver<String> {
  private static final char GARDEN_PLOTS = '.';
  private static final char STARTING_POSITION = 'S';
  private static final char ROCKS = '#';

  private final CharGrid grid;

  public Day21(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(this.puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
  }

  @Override
  public long solvePart01() {
    return multiSteps(6, Set.of(startPosition())).size();
  }

  Point startPosition() {
    return grid.stream()
        .filter(point -> grid.get(point).equals(STARTING_POSITION))
        .findFirst()
        .orElseThrow();
  }

  Set<Point> multiSteps(int times, Set<Point> points) {

    // this.grid.stream().forEach(point -> log.debug("point {} val {}", point, grid.get2(point)));
    Set<Point> set = points;
    for (int i = 0; i < times; i++) {
      set = steps(set);
      log.debug("-------------------------------------------");
    }
    // this.grid.printArray();

    set.add(startPosition());
    return set;
  }

  Set<Point> steps(Set<Point> points) {
    return points.stream().flatMap(this::movedRovers).collect(Collectors.toSet());
  }

  private Stream<Point> movedRovers(Point point) {
    return grid.neighbours(point, false).stream()
        .filter(this::isInTheGrid)
        .filter(
            p -> {
              Character character = grid.get(p);
              log.debug("point {} val {}", p, character);

              return character == GARDEN_PLOTS || character == STARTING_POSITION;
            });
  }

  @Override
  public long solvePart02() {
    return 1000L;
  }

  private boolean isInTheGrid(Point movedPoint) {
    return grid.isInTheGrid().test(movedPoint);
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/21/2023_21_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day21 day = new Day21(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
