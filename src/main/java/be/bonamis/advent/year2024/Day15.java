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
import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Getter
@Slf4j
public class Day15 extends TextDaySolver {
  private final Input input;

  public Day15(InputStream inputStream) {
    super(inputStream);
    this.input = Input.of(puzzle);
  }

  public Day15(List<String> puzzle) {
    super(puzzle);
    this.input = Input.of(puzzle);
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

      //char[][] data = lines.stream().map(String::toCharArray).toArray(char[][]::new);
      CharGrid grid = new CharGrid(lines);
      grid.printLines();
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

    for (char c : this.input.moves()) {
      Direction direction = direction(c);
      log.debug("direction: {}", direction);
      Rover futureRover = new Rover(direction, this.input.start());
      var nextPosition = futureRover.move(FORWARD, true).position();
      log.debug("nextPosition: {}", nextPosition);
      char value = this.input.grid().get(nextPosition);
      Pair<Rover, CharGrid> moved = move(value, futureRover, this.input.grid());
      log.debug("move: {}", moved);
    }

    return 0;
  }

  static Point findStart(CharGrid grid) {
    return grid.stream()
        .filter(p -> grid.get(p).equals('@'))
        .findFirst()
        .orElseThrow();
  }

  private Pair<Rover, CharGrid> move(char value, Rover rover, CharGrid grid) {
    switch (value) {
      case '.' -> rover = rover.move(FORWARD);
      case 'O' -> boxMove(rover, grid);
    }

    return Pair.of(rover, grid);
  }

  Pair<Rover, CharGrid> boxMove(Rover rover, CharGrid grid) {
    log.debug("boxMove grid before");
    grid.printLines();
    log.debug("boxMove rover before: {}", rover);
    return Pair.of(rover, grid);
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
