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

@Slf4j
@Getter
public class Day16 extends DaySolver<String> {

  private static final char DOT = '.';
  private static final char VERTICAL_SPLITTER = '|';

  private final CharGrid grid;

  public Day16(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(puzzle);
  }

  @Override
  public long solvePart01() {
    // grid.stream().skip(8).limit(15).forEach(x -> log.debug("point {} value {}", x ,
    // grid.get(x)));
    Position position = new Position(0, 0);
    Rover rover = new Rover(Rover.Direction.EAST, position);
    Beam beam = Beam.of(rover);
    List<Beam> beams = new ArrayList<>(List.of(beam));
    move(beams);

    return this.puzzle.size();
  }

  private void move(List<Beam> beams) {
    beams.forEach(this::move);
  }

  private void move(Beam beam) {
    // log.debug("beam {}", beam);
    Rover rover = beam.rover();
    char data = data(rover);
    log.debug("inside single bean move rover {} data {}", rover, data);
    move(data, rover);
  }

  record Beam(Rover rover, List<Position> positions) {
    public static Beam of(Rover rover) {
      return new Beam(rover, new ArrayList<>(List.of(rover.position())));
    }
  }

  private void move(char data, Rover rover) {
    log.debug("inside move data {} rover {}", data, rover);
    switch (data) {
      case DOT -> {
        Rover moved = rover.move(FORWARD);
        Position position2 = moved.position();
        char newData = data(moved);
        log.debug("dot point at {} with data {}", position2, newData);
        move(newData, moved);
      }
      case VERTICAL_SPLITTER -> {
        log.debug("vert split point ");
        Rover.Direction direction = rover.direction();
        List<Beam> beams = new ArrayList<>();
        if (direction.equals(Rover.Direction.EAST)) {
          addBeam(rover.move(LEFT), beams, LEFT);
          addBeam(rover.move(RIGHT), beams, RIGHT);
        }
        move(beams);
      }
    }
  }

  private void addBeam(Rover rover, List<Beam> beams, Command direction) {
    Rover moved = rover.move(FORWARD);
    if (isInTheGrid(moved)) {
      log.debug("\n\nmoved " + direction);
      logRover(moved);
      beams.add(Beam.of(moved));
    }
  }

  private boolean isInTheGrid(Rover rover) {
    Position position = rover.position();
    return isInTheGrid(new Point(position.x(), position.y()));
  }

  private void logRover(Rover rover) {
    log.debug("left moved {} content {}", rover, grid.get(rover.position()));
  }

  private char data(Rover rover) {
    return grid.get(rover.position());
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size();
  }

  private boolean isDot(Point movedPoint) {
    return grid.get(movedPoint).equals(DOT);
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
