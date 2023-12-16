package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    Rover rover = new Rover(EAST, new Position(-1, 0));
    return energisedCount(rover);
  }

  private int energisedCount(Rover rover) {
    final Set<Rover> energized = new HashSet<>();
    move(List.of(rover), energized);

    log.debug("energized size {}", energized.size());
    Set<Position> positions = energized.stream().map(Rover::position).collect(Collectors.toSet());
    return positions.size();
  }

  private void move(List<Rover> beams, Set<Rover> energized) {
    beams.forEach(rover -> move(rover, energized));
  }

  private void move(Rover rover, Set<Rover> energized) {
    log.debug("inside move data  rover {}", rover);
    Rover moved = moveRover(rover, FORWARD);
    if (isInTheGrid(moved)) {
      char data = data(moved);
      log.debug("adding point at {} with data {}", moved.position(), data);
      energized.add(moved);

      switch (data) {
        case DOT -> {
          log.debug("dot point at {} with data {}", moved.position(), data);
          move(moved, energized);
        }
        case VERTICAL_SPLITTER -> {
          log.debug("vert split point ");
          if (List.of(EAST, WEST).contains(moved.direction())) {
            facialSplit(moved, energized);
          } else {
            move(moved, energized);
          }
        }
        case HORIZONTAL_SPLITTER -> {
          log.debug("horiz split point ");
          if (List.of(SOUTH, NORTH).contains(moved.direction())) {
            facialSplit(moved, energized);
          } else {
            move(moved, energized);
          }
        }
        case RIGHT_MIRROR -> {
          log.debug("right mirror point ");
          switch (moved.direction()) {
            case WEST, EAST -> move(moveRover(moved, LEFT), energized);
            case NORTH, SOUTH -> move(moveRover(moved, RIGHT), energized);
          }
        }
        case LEFT_MIRROR -> {
          log.debug("left mirror point ");
          switch (moved.direction()) {
            case WEST, EAST -> move(moveRover(moved, RIGHT), energized);
            case NORTH, SOUTH -> move(moveRover(moved, LEFT), energized);
          }
        }
      }
    }
  }

  private void facialSplit(Rover rover, Set<Rover> energized) {
    List<Rover> beams = new ArrayList<>();
    addBeam(moveRover(rover, LEFT), beams, LEFT, energized);
    addBeam(moveRover(rover, RIGHT), beams, RIGHT, energized);
    move(beams, energized);
  }

  private void addBeam(Rover rover, List<Rover> beams, Command direction, Set<Rover> energized) {
    log.debug("\n\nmoved " + direction);
    logRover(rover, direction);
    if (!energized.contains(rover)) {
      energized.add(rover);
      beams.add(rover);
    }
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
    Stream<Rover> leftEdge =
        this.grid.leftEdge().map(point -> new Rover(EAST, new Position(point.x - 1, point.y)));
    Stream<Rover> rightEdge =
        this.grid.rightEdge().map(point -> new Rover(WEST, new Position(point.x + 1, point.y)));
    Stream<Rover> topEdge =
        this.grid.topEdge().map(point -> new Rover(SOUTH, new Position(point.x, point.y - 1)));
    Stream<Rover> bottomEdge =
        this.grid.bottomEdge().map(point -> new Rover(NORTH, new Position(point.x, point.y + 1)));
    Stream<Rover> puzzle =
        Stream.concat(leftEdge, Stream.concat(rightEdge, Stream.concat(topEdge, bottomEdge)));
    return puzzle.map(this::energisedCount).max(Long::compare).orElseThrow();
  }

  private boolean isInTheGrid(Point movedPoint) {
    return grid.isInTheGrid().test(movedPoint);
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/16/2023_16_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day16 day = new Day16(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
