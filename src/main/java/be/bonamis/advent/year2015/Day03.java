package be.bonamis.advent.year2015;

import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.HashSet;
import java.util.Set;

import be.bonamis.advent.utils.marsrover.*;
import be.bonamis.advent.utils.marsrover.Rover.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;

@Slf4j
@Getter
public class Day03 extends TextDaySolver {

  private final List<Direction> directions;

  public Day03(String sample) {
    super(sample);
    directions = this.puzzle.get(0).chars().mapToObj(c -> toDirection((char) c)).toList();
  }

  @Override
  public long solvePart01() {
    Rover santa = new Rover(NORTH, Position.of(0, 0));
    Set<Position> positions = new HashSet<>(Set.of(santa.position()));
    for (Direction direction : directions) {
      log.debug("direction: {}", direction);
      santa = moved(santa, direction, positions);
    }
    log.debug("positions: {}", positions);

    return positions.size();
  }

  private Rover move(Direction direction, Rover rover) {
    return new Rover(direction, rover.position()).move(Command.FORWARD);
  }

  @Override
  public long solvePart02() {
    Rover santa = new Rover(NORTH, Position.of(0, 0));
    Rover roboSanta = new Rover(NORTH, Position.of(0, 0));
    Set<Position> positions = new HashSet<>(Set.of(santa.position()));
    for (int i = 0; i < directions.size(); i++) {
      if (i % 2 == 0) {
        santa = moved(santa, directions.get(i), positions);
      } else {
        roboSanta = moved(roboSanta, directions.get(i), positions);
      }
    }

    log.debug("positions: {}", positions);

    return positions.size();
  }

  private Rover moved(Rover santa, Direction directions, Set<Position> positions) {
    santa = move(directions, santa);
    positions.add(santa.position());
    return santa;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2015/03/2015_03_input.txt");
    Day03 day = new Day03(content);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  public Direction toDirection(char c) {
    return switch (c) {
      case '^' -> NORTH;
      case '>' -> EAST;
      case 'v' -> SOUTH;
      case '<' -> WEST;
      default -> null;
    };
  }
}
