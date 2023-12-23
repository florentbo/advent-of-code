package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.utils.marsrover.Rover.Command;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day23 extends DaySolver<String> {

  private static final char PATH = '.';
  private static final char FOREST = '#';
  private static final char NORTH_STEEP = '^';
  private static final char SOUTH_STEEP = 'v';
  private static final char EAST_STEEP = '>';
  private static final char WEST_STEEP = '<';

  private final CharGrid grid;

  public Day23(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(puzzle);
  }

  @Override
  public long solvePart01() {
    Rover rover = new Rover(EAST, new Position(1, 0));
    char data = data(rover);
    log.debug("data {}", data);
    return 9999L;
  }

  private char data(Rover rover) {
    return grid.get(rover.position());
  }

  @Override
  public long solvePart02() {
    return 9999L;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/23/2023_23_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day23 day = new Day23(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
