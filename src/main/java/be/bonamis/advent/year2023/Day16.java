package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

import be.bonamis.advent.utils.marsrover.Rover.Command;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;

@Slf4j
@Getter
public class Day16 extends DaySolver<String> {

  private static final char DOT = '.';
  private static final char VERTICAL_SPLITTER = '|';
  private static final char HORIZONTAL_SPLITTER = '-';
  private static final char RIGHT_MIRROR = '/';
  private static final char LEFT_MIRROR = '\\';

  private final CharGrid grid;

  public Day16(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(puzzle);
  }

  @Override
  public long solvePart01() {
    // grid.stream().skip(8).limit(15).forEach(x -> log.debug("point {} value {}", x ,
    // grid.get(x)));
    Position position = new Position(-1, 0);
    Rover rover = new Rover(EAST, position);
    Beam beam = Beam.of(rover);
    List<Beam> beams = new ArrayList<>(List.of(beam));
    move(beams);

    return this.puzzle.size();
  }

  private void move(List<Beam> beams) {
    beams.forEach(this::move);
  }

  private void move(Beam beam) {
    Rover rover = beam.rover();
    log.debug("inside single bean move rover {} data ", rover);
    move(rover);
  }

  record Beam(Rover rover, List<Position> positions) {
    public static Beam of(Rover rover) {
      return new Beam(rover, new ArrayList<>(List.of(rover.position())));
    }
  }

  private void move(Rover rover) {
    log.debug("inside move data  rover {}", rover);
    Rover moved = moveRover(rover, FORWARD);
    if (isInTheGrid(moved)) {
      char data = data(moved);
      log.debug("adding point at {} with data {}", moved.position(), data);

      switch (data) {
        case DOT -> {
          log.debug("dot point at {} with data {}", moved.position(), data);
          move(moved);
        }
        case VERTICAL_SPLITTER -> {
          log.debug("vert split point ");
          if (List.of(EAST, WEST).contains(moved.direction())) {
            facialSplit(moved);
          } else {
            move(moved);
          }
        }
        case HORIZONTAL_SPLITTER -> {
          log.debug("horiz split point ");
          if (List.of(SOUTH, NORTH).contains(moved.direction())) {
            facialSplit(moved);
          } else {
            move(moved);
          }
        }
        case RIGHT_MIRROR -> {
          log.debug("right mirror point ");
          switch (moved.direction()) {
            case WEST, EAST -> move(moveRover(moved, LEFT));
            case NORTH, SOUTH -> move(moveRover(moved, RIGHT));
          }
        }
        case LEFT_MIRROR -> {
          log.debug("left mirror point ");
          switch (moved.direction()) {
            case WEST, EAST -> move(moveRover(moved, RIGHT));
            case NORTH, SOUTH -> move(moveRover(moved, LEFT));
          }
        }
      }
    }
  }

  private void facialSplit(Rover rover) {
    List<Beam> beams = new ArrayList<>();
    addBeam(moveRover(rover, LEFT), beams, LEFT);
    addBeam(moveRover(rover, RIGHT), beams, RIGHT);
    move(beams);
  }

  private void addBeam(Rover rover, List<Beam> beams, Command direction) {
    log.debug("\n\nmoved " + direction);
    logRover(rover, direction);
    beams.add(Beam.of(rover));
  }

  private boolean isInTheGrid(Rover rover) {
    Position position = rover.position();
    return isInTheGrid(new Point(position.x(), position.y()));
  }

  private Rover moveRover(Rover rover, Command command) {
    return rover.move(command, true);
  }

  private void logRover(Rover rover, Command direction) {
    log.debug("{} moved {} content {}", direction, rover, grid.get(rover.position()));
  }

  private char data(Rover rover) {
    return grid.get(rover.position());
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size();
  }

  private boolean isInTheGrid(Point movedPoint) {
    return grid.isInTheGrid().test(movedPoint);
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/14/2023_14_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day16 day = new Day16(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
