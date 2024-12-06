package be.bonamis.advent.year2024;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;

import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import java.awt.*;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day06 extends TextDaySolver {

  private final CharGrid grid;

  public Day06(InputStream inputStream) {
    super(inputStream);
    grid = new CharGrid(this.puzzle);
  }

  public Day06(List<String> puzzle) {
    super(puzzle);
    grid = new CharGrid(this.puzzle);
  }

  @Override
  public long solvePart01() {
    Point start = grid.stream().filter(p -> grid.get(p).equals('^')).findFirst().orElseThrow();
    //log.debug("start: {}", start);
    // grid.printLines();

    Set<Point> visited = getVisited(start);

    return visited.size();
  }

  private Set<Point> getVisited(Point start) {
    Rover rover = new Rover(NORTH, Position.of(start));
    Point point = toPoint(rover);
    Set<Point> visited = new HashSet<>();
    while (isInTheGrid(point)) {
      char value = grid.get(rover.position());
      //log.debug("grid value: {}", value);
      if (value == '#') {
        //log.debug("turning right +++++++++++++++++++++++++++++++++++++++++++");
        rover = rover.move(RIGHT);
        rover = rover.move(RIGHT);
        rover = rover.move(FORWARD, true);
        //log.debug("movedRover after BACKWARD: {}", rover);
        rover = rover.move(LEFT);
        //log.debug("movedRover after RIGHT: {}", rover);
        // grid.printLines();
        //log.debug("turning right  done +++++++++++++++++++++++++++++++++++++++++++");
      } else {
        grid.set(rover.position(), 'X');
        visited.add(rover.position().toPoint());
      }
      rover = rover.move(FORWARD, true);

      point = toPoint(rover);
      //log.debug("movedRover: {}", rover);
    }
    //log.debug("rover: {}", rover);
    return visited;
  }

  private Point toPoint(Rover rover) {
    return rover.position().toPoint();
  }

  private boolean isInTheGrid(Point movedPoint) {
    return grid.isInTheGrid().test(movedPoint);
  }

  @Override
  public long solvePart02() {
    Point start = grid.stream().filter(p -> grid.get(p).equals('^')).findFirst().orElseThrow();
    //log.debug("start: {}", start);
    // grid.printLines();

    //Set<Point> dots = getVisited(start);
    var dots = grid.stream().filter(p -> grid.get(p).equals('.')).toList();
    int count = 0;
    for (Point dot : dots) {
      grid.set(dot, '#');
      boolean b = searchStart(start, dot);
      count += b ? 1 : 0;
      log.debug("b: {}", b);
      grid.set(dot, '.');
    }
    return count;
  }

  private boolean searchStart(Point start, Point dot) {
    Rover rover = new Rover(NORTH, Position.of(start));
    Set<Rover> visited = new HashSet<>();
    Point point = toPoint(rover);
    boolean startFound = false;
    while (isInTheGrid(point) && !startFound && !visited.contains(rover)) {
      char value = grid.get(rover.position());
      //log.debug("grid value: {}", value);
      if (value == '#') {
        //log.debug("turning right +++++++++++++++++++++++++++++++++++++++++++");
        rover = rover.move(RIGHT);
        rover = rover.move(RIGHT);
        rover = rover.move(FORWARD, true);
        if (rover.position().toPoint().equals(start)) {
          startFound = true;
        }
        //log.debug("movedRover after BACKWARD: {}", rover);
        rover = rover.move(LEFT);
        //log.debug("movedRover after RIGHT: {}", rover);
        //grid.printLines();
        log.debug("dot: {}", dot);
        //log.debug("turning right  done +++++++++++++++++++++++++++++++++++++++++++");
      } else {
        visited.add(rover);
        grid.set(rover.position(), '|');
      }
      rover = rover.move(FORWARD, true);
      point = toPoint(rover);
      //log.debug("movedRover: {}", rover);
    }
    //log.debug("rover: {}", rover);
    return startFound;
  }
}
