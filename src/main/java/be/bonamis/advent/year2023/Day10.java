package be.bonamis.advent.year2023;

import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;

import java.awt.*;
import java.util.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.utils.marsrover.Rover.Direction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.common.CharGrid.DOT;
import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;

@Slf4j
@Getter
public class Day10 extends TextDaySolver {

  private final CharGrid grid;
  private final Point startingPoint;

  private static final char START = 'S';

  public Day10(String puzzle) {
    super(puzzle);
    grid = new CharGrid(puzzle);
    startingPoint = grid.stream().filter(p -> this.grid.get(p) == START).findFirst().orElseThrow();
  }

  static Set<Direction> allowedDirections(Character value) {
    return switch (value) {
      case '|' -> Set.of(NORTH, SOUTH);
      case '-' -> Set.of(EAST, WEST);
      case 'L' -> Set.of(NORTH, EAST);
      case 'J' -> Set.of(NORTH, WEST);
      case '7' -> Set.of(SOUTH, WEST);
      case 'F' -> Set.of(SOUTH, EAST);
      default -> throw new NoSuchElementException();
    };
  }

  @Override
  public long solvePart01() {
    log.debug("startingPoint: {}", startingPoint);
    return loopStarts().stream()
        .map(this::loopCount)
        .map(characters -> characters.size() / 2)
        .max(Comparator.naturalOrder())
        .orElseThrow();
  }

  private List<Point> loopCount(Rover rover) {
    Point point = rover.position().toPoint();
    Character c = grid.get(point);
    log.debug("c: {}", c);
    int count = 1;
    List<Point> loopPoints = new ArrayList<>();
    loopPoints.add(point);
    while (c != START) {
      rover = move(rover, c);
      point = rover.position().toPoint();
      log.debug("newPoint: {}", point);
      c = grid.get(point);
      log.debug("c: {}", c);
      loopPoints.add(point);
      count++;
    }
    log.debug("count: {}", count);
    return loopPoints;
  }

  private Rover move(Rover rover, Character c) {
    Direction inverse = rover.direction().inverse();
    log.debug("inverse: {}", inverse);

    Direction direction =
        allowedDirections(c).stream().filter(d -> d != inverse).findFirst().orElseThrow();
    log.debug("new allowedDirections: {}", direction);

    Rover turned = new Rover(direction, rover.position());
    log.debug("new rover after dir change: {}", turned);
    Rover moved = turned.move(FORWARD, true);
    log.debug("new rover after move change: {}", moved);
    return moved;
  }

  @Override
  public long solvePart02() {
    Stream<List<Point>> listStream = loopStarts().stream().map(this::loopCount);
    List<Point> longest = listStream.max(Comparator.comparing(List::size)).orElseThrow();
    List<Day18.Point> list =
        longest.stream().map(point -> new Day18.Point(point.x, point.y)).toList();
    Day18.Polygon poly = new Day18.Polygon(list);

    long area = new Day18(List.of()).polyArea(poly);
    log.debug("area: {}", area);
    return area - longest.size();
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/10/2023_10_input.txt");
    Day10 day = new Day10(content);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  public Collection<Rover> loopStarts() {
    return Arrays.stream(Direction.values())
        .map(
            d -> {
              Rover rover = new Rover(d, Position.of(startingPoint));
              return rover.move(FORWARD, true);
            })
        .filter(
            rover -> {
              Point newPoint = rover.position().toPoint();
              Character c = grid.get(newPoint);
              return c != DOT;
            })
        .toList();
  }
}
