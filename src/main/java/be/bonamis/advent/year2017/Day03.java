package be.bonamis.advent.year2017;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.Set;
import java.util.function.*;

import be.bonamis.advent.common.InfiniteGrid;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.common.InfiniteGrid.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.WEST;

@Slf4j
@Getter
public class Day03 extends TextDaySolver {

  public static final BiFunction<Integer, Point, Integer> ADD_INDEX = (x, y) -> x + 1;
  private final InfiniteGrid grid = new InfiniteGrid();
  private final int input;

  public Day03(InputStream sample) {
    super(sample);
    input = Integer.parseInt(this.puzzle.get(0));
  }

  int solveGrid() {
    Point last = createInfiniteGrid(input, Day03.ADD_INDEX).last();
    return Math.abs(last.x()) + Math.abs(last.y());
  }

  InfiniteGrid otherGrid() {
    BiFunction<Integer, Point, Integer> neighborsSum = (x, point) -> neighborsSum(point);
    return createInfiniteGrid(input, neighborsSum);
  }

  private Integer neighborsSum(Point point) {
    Set<Point> neighbors = grid.neighbors(point);
    log.debug("Neighbors of {}: ", point);
    for (Point neighbor : neighbors) {
      log.debug("Neighbor: {} value: {}", neighbor, grid.get(neighbor));
    }
    return neighbors.isEmpty() ? 1 : neighbors.stream().mapToInt(grid::get).sum();
  }

  InfiniteGrid createInfiniteGrid(int max, BiFunction<Integer, Point, Integer> addIndex) {
    /*
    17  16  15  14  13
    18   5   4   3  12
    19   6   1   2  11
    20   7   8   9  10
    21  22  23---> ...
     */

    Rover spiral = new Rover(WEST, Position.of(0, 1));
    int index = 1;
    log.debug("spiral initial      : {}", spiral);
    while (index <= max) {
      if (hasNoValueToTheLeft(spiral)) {
        spiral = spiral.move(Rover.Command.LEFT);
      }
      log.debug("spiral after if     : {}", spiral);
      spiral = spiral.move(Rover.Command.FORWARD);
      log.debug("spiral after forward: {}", spiral);
      Point point = Point.from(spiral.position());
      grid.addValue(point, index);
      log.debug("grid: {}", grid);
      index = addIndex.apply(index, point);
    }
    return grid;
  }

  boolean hasNoValueToTheLeft(Rover spiral) {
    log.debug("hasNoValueToTheLeft spiral before left     : {}", spiral);
    Rover left = spiral.move(Rover.Command.LEFT);
    left = left.move(Rover.Command.FORWARD);
    log.debug("hasNoValueToTheLeft spiral after left      : {}", left);
    Position position = left.position();
    log.debug("position: {}", position);
    return grid.find((int) position.x(), (int) position.y()).isEmpty();
  }

  @Override
  public long solvePart01() {
    return solveGrid();
  }

  @Override
  public long solvePart02() {
    return 999;
  }
}
