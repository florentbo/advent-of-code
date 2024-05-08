package be.bonamis.advent.year2017;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;

import be.bonamis.advent.common.InfiniteGrid;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


import static be.bonamis.advent.utils.marsrover.Rover.Direction.WEST;

@Slf4j
@Getter
public class Day03 extends TextDaySolver {

  public Day03(InputStream sample) {
    super(sample);
  }

  static int solveGrid(int input) {
    InfiniteGrid.Point last = createInfiniteGrid(input).last();
    return Math.abs(last.x()) + Math.abs(last.y());
  }

  static InfiniteGrid createInfiniteGrid(int max) {
    /*
    17  16  15  14  13
    18   5   4   3  12
    19   6   1   2  11
    20   7   8   9  10
    21  22  23---> ...
     */

    InfiniteGrid grid = new InfiniteGrid();

    Rover spiral = new Rover(WEST, Position.of(0, 1));
    int index = 1;
    log.debug("spiral initial      : {}", spiral);
    while (index <= max) {
      if (hasNoValueToTheLeft(grid, spiral)) {
        spiral = spiral.move(Rover.Command.LEFT);
      }
      log.debug("spiral after if     : {}", spiral);
      spiral = spiral.move(Rover.Command.FORWARD);
      log.debug("spiral after forward: {}", spiral);
      grid.addValue(InfiniteGrid.Point.from(spiral.position()), index);
      log.debug("grid: {}", grid);
      index++;
    }
    return grid;
  }

  static boolean hasNoValueToTheLeft(InfiniteGrid grid, Rover spiral) {
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
    return solveGrid(Integer.parseInt(this.puzzle.get(0)));
  }

  @Override
  public long solvePart02() {
    return 999;
  }
}
