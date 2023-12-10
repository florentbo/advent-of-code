package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;

import java.util.*;
import java.util.List;

import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;

@Slf4j
@Getter
public class Day10 extends DaySolver<String> {

  //private final CharGrid grid;
  //private final Point startingPoint;

  public Day10(List<String> puzzle) {
    super(puzzle);
    //grid = new CharGrid(this.puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
    //startingPoint = grid.stream().filter(p -> this.grid.get(p) == 'S').findFirst().orElseThrow();
  }

  @Override
  public long solvePart01() {
   /* log.debug("startingPoint: {}", startingPoint);
    List<Point> possibleMoves =
        grid.neighbours(startingPoint).stream().filter(p -> this.grid.get(p) != '.').toList();
    log.debug("possibleMoves: {}", possibleMoves);*/

    return this.puzzle.size();
  }

  record Rope(Rover head, List<Position> positions) {
    public Rope() {
      this(initRover(), new ArrayList<>());
    }

    private static Rover initRover() {
      return new Rover(NORTH, new Position(0, 0));
    }
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/10/2023_10_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day10 day = new Day10(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
