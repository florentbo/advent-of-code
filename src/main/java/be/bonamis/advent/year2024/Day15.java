package be.bonamis.advent.year2024;

import static be.bonamis.advent.utils.marsrover.Rover.Command.FORWARD;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;

import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.utils.marsrover.Rover.Direction;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Getter
@Slf4j
public class Day15 extends TextDaySolver {
  private static final char SPACE = '.';
  private static final char BOX = 'O';
  private static final char WALL = '#';
  private static final char START = '@';
  private final Input input;

  public Day15(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(this.puzzle);
  }

  public Day15(List<String> puzzle) {
    super(puzzle);
    this.input = Input.of(this.puzzle);
  }

  record Input(CharGrid grid, char[] moves, Position start) {
    public static Input of(List<String> puzzle) {
      Integer blank =
          IntStream.range(0, puzzle.size())
              .filter(i -> puzzle.get(i).isEmpty())
              .boxed()
              .findFirst()
              .orElseThrow();
      log.debug("blank: {}", blank);
      List<String> lines = puzzle.subList(0, blank);

      CharGrid grid = new CharGrid(lines);

      Point start = findStart(grid);
      log.debug("start: {}", start);

      List<String> moves = puzzle.subList(blank + 1, puzzle.size());
      log.debug("moves : {}", moves);

      char[] data2 = moves.stream().map(String::toCharArray).toList().get(0);

      return new Input(grid, data2, Position.of(start));
    }
  }

  @Override
  public long solvePart01() {

    Rover rover = new Rover(Direction.NORTH, this.input.start());
    char[] moves = this.input.moves();
    for (int i = 0; i < moves.length; i++) {
      char c = moves[i];
      log.debug("i: {}, c: {}", i, c);
      Direction direction = direction(c);
      log.debug("direction: {}", direction);
      Rover futureRover = new Rover(direction, rover.position());
      var nextPosition = futureRover.move(FORWARD, true).position();
      log.debug("nextPosition: {}", nextPosition);
      char value = this.input.grid().get(nextPosition);
      Pair<Rover, CharGrid> moved = move(value, futureRover, this.input.grid());
      log.debug("move: {}", moved);
      rover = moved.getLeft();
      log.debug("rover after: {}", rover);
    }

    return 0;
  }

  static Point findStart(CharGrid grid) {
    return grid.stream().filter(p -> grid.get(p).equals(START)).findFirst().orElseThrow();
  }

  private Pair<Rover, CharGrid> move(char value, Rover rover, CharGrid grid) {
    switch (value) {
      case SPACE -> rover = rover.move(FORWARD, true);
      case BOX -> boxMove(rover, grid);
    }

    log.debug("move rover after: {}", rover);
    return Pair.of(rover, grid);
  }

  Pair<Rover, CharGrid> boxMove(Rover rover, CharGrid flo) {
    log.debug("boxMove rover before: {}", rover);
    Rover movingRover = new Rover(rover.direction(), rover.position());
    List<Point> points = new ArrayList<>();
    do {
      points.add(movingRover.position().toPoint());
      movingRover = movingRover.move(FORWARD, true);
    } while (flo.isPositionInTheGrid(movingRover));
    log.debug("points: {}", points);

    for (Point point : points) {
      Character c = flo.get(point);
      log.debug("c: {}", c);
    }

    var nextWallIndex =
        IntStream.range(0, points.size())
            .filter(i -> flo.get(points.get(i)) == WALL)
            .findFirst()
            .orElseThrow();

    var nextSpaceIndex =
        IntStream.range(0, points.size()).filter(i -> flo.get(points.get(i)) == SPACE).findFirst();

    log.debug("nextWallIndex: {}", nextWallIndex);
    log.debug("nextSpaceIndex: {}", nextSpaceIndex);

    nextSpaceIndex.ifPresent(
        i -> {
          log.debug("nextSpaceIndex: {}", i);
          if (i < nextWallIndex) {
            for (int j = i; j >= 1; j--) {
              log.debug("j: {}", j);
              Point point = points.get(j);
              char beforeValue = flo.get(points.get(j - 1));
              flo.set(point, beforeValue);
              log.debug("beforeValue: {}", beforeValue);
              flo.printLines2();
              log.debug("++++");
            }
            flo.set(points.get(0), SPACE);
          }
          log.debug("final grid print ");
          flo.printLines2();
        });

    return Pair.of(rover, flo);
  }

  private Direction direction(char move) {
    return switch (move) {
      case '^' -> NORTH;
      case 'v' -> SOUTH;
      case '>' -> EAST;
      case '<' -> WEST;
      default -> throw new IllegalStateException("Should not get here");
    };
  }

  @Override
  public long solvePart02() {
    return 0;
  }
}
