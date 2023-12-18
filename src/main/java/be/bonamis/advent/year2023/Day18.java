package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;

import java.awt.*;
import java.util.*;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day18 extends DaySolver<String> {

  private final Polygon poly = new Polygon(new ArrayList<>());

  public Day18(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    createGrid(this.puzzle.stream().map(Dig::parse).toList());
    long a = (long) calculateArea(poly);
    int b = this.poly.points().size();
    log.debug("area {} size {}", a, b);

    return a + b + b / 2 - 1;
  }

  double calculateArea(Day18.Polygon polygon) {
    List<Point> points = polygon.points();
    int n = points.size();

    double area = 0.0;

    for (int i = 0; i < n - 1; i++) {
      Point current = points.get(i);
      Point next = points.get(i + 1);
      area += current.x * next.y - next.x * current.y;
    }

    Point first = points.get(0);
    Point last = points.get(n - 1);
    area += last.x * first.y - first.x * last.y;

    area = Math.abs(area) / 2.0;

    return area;
  }

  void createGrid(List<Dig> digs) {
    log.debug("digs: {}", digs);

    Rover rover = new Rover(NORTH, new Position(0, 0));
    // grid.set(rover.position(), '#');
    for (Dig dig : digs) {
      rover = move(rover, dig);
    }
    // List<String> rows = this.grid.rowsAsLines();
    /*for (String row : rows) {
      log.debug(row);
    }*/
  }

  /*int externalPainted() {
    Set<Point> painted = new HashSet<>();
    for (int i = 0; i < this.grid.getHeight(); i++) {
      Set<Point> points = paintRow(i);
      painted.addAll(points);
    }

    for (int i = 0; i < this.grid.getWidth(); i++) {
      painted.addAll(paintColumn(i));
    }

    return painted.size();
  }*/

  /*Set<Point> paintRow(int rowNum) {
    Set<Point> painted = new HashSet<>();

    Predicate<Point> rowFilter = p -> p.y == rowNum && this.grid.get(p) == '#';
    int width = this.grid.getWidth();
    int min = this.grid.stream().filter(rowFilter).mapToInt(p -> p.x).min().orElse(width);
    int max = this.grid.stream().filter(rowFilter).mapToInt(p -> p.x).max().orElse(0);

    paintRowPoints(rowNum, min, painted, max, width);
    return painted;
  }

  private void paintRowPoints(int lineNumber, int min, Set<Point> painted, int max, int size) {
    for (int i = 0; i < min; i++) {
      painted.add(new Point(i, lineNumber));
    }

    for (int i = max + 1; i < size; i++) {
      painted.add(new Point(i, lineNumber));
    }
  }

  private void paintColPoints(int lineNumber, int min, Set<Point> painted, int max, int size) {
    for (int i = 0; i < min; i++) {
      painted.add(new Point(lineNumber, i));
    }

    for (int i = max + 1; i < size; i++) {
      painted.add(new Point(lineNumber, i));
    }
  }

  Set<Point> paintColumn(int colNum) {
    Set<Point> painted = new HashSet<>();

    Predicate<Point> rowFilter = p -> p.x == colNum && this.grid.get(p) == '#';
    int height = this.grid.getHeight();
    int min = this.grid.stream().filter(rowFilter).mapToInt(p -> p.y).min().orElse(height);
    int max = this.grid.stream().filter(rowFilter).mapToInt(p -> p.y).max().orElse(0);

    paintColPoints(colNum, min, painted, max, height);
    return painted;
  }*/

  private Rover move(Rover rover, Dig dig) {

    return move(rover.position(), dig);
  }

  private Rover move(Position position, Dig dig) {
    Rover roverToMove = new Rover(dig.direction().toRoverDirection(), position);
    for (int i = 0; i < dig.meters(); i++) {

      roverToMove = roverToMove.move(FORWARD, true);
    }
    Position movedPosition = roverToMove.position();
    // grid.set(movedRover.position(), '#');
    this.poly.addPoint(new Point(movedPosition.x(), movedPosition.y()));
    return roverToMove;
  }

  @Override
  public long solvePart02() {
    return 888;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/18/2023_18_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day18 day = new Day18(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  record Polygon(List<Point> points) {
    void addPoint(Point point) {
      points.add(point);
    }
  }

  record Dig(Direction direction, long meters, String color) {
    public static Dig parse(String line) {
      String[] parts = line.split(" ");
      String direction = parts[0];
      String color = parts[2];
      long meters = Long.parseLong(parts[1]);
      return new Dig(Direction.of(direction), meters, color);
    }

    @AllArgsConstructor
    public enum Direction {
      UP("U"),
      DOWN("D"),
      LEFT("L"),
      RIGHT("R");

      private final String value;

      public static Direction of(String value) {
        return Arrays.stream(values()).filter(v -> v.value.equals(value)).findFirst().orElseThrow();
      }

      public Rover.Direction toRoverDirection() {
        return switch (this) {
          case UP -> Rover.Direction.NORTH;
          case DOWN -> Rover.Direction.SOUTH;
          case LEFT -> Rover.Direction.WEST;
          case RIGHT -> Rover.Direction.EAST;
        };
      }
    }
  }
}
