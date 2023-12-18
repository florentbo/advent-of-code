package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;

import java.util.*;

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
    return solvePoly();
  }

  @Override
  public long solvePart02() {
    createGrid(this.puzzle.stream().map(Dig::parse).map(Dig::transformColor).toList());
    return solvePoly();
  }

  private long solvePoly() {
    long a = calculateArea(poly);
    long perimeter = calculatePerimeter(this.poly.points());

    perimeter /= 2;
    log.debug("area {} half {}", a, perimeter);

    return a + perimeter + 1;
  }

  long calculatePerimeter(List<Point> points) {
    long perimeter = 0;

    for (int i = 0; i < points.size() - 1; i++) {
      Point currentPoint = points.get(i);
      Point nextPoint = points.get(i + 1);

      long distance = currentPoint.distance(nextPoint);

      perimeter += distance;
    }

    perimeter += points.get(points.size() - 1).distance(points.get(0));

    return perimeter;
  }

  long area(Point[] polyPoints) {

    int n = polyPoints.length;
    long area = 0;

    for (int i = 0; i < n; i++) {
      int j = (i + 1) % n;
      area += polyPoints[i].y() * polyPoints[j].x();
      area -= polyPoints[j].y() * polyPoints[i].x();
    }
    area /= 2;
    return (area < 0 ? -area : area);
  }

  long calculateArea(Polygon polygon) {
    List<Point> points = polygon.points();
    return Math.abs(points.stream()
            .mapToLong(point -> point.x * (getNextY(points, point) - point.y))
            .sum());
  }

  long getNextY(List<Point> points, Point currentPoint) {
    int currentIndex = points.indexOf(currentPoint);
    int nextIndex = (currentIndex + 1) % points.size();
    return points.get(nextIndex).y;
  }



  void createGrid(List<Dig> digs) {
    log.debug("digs: {}", digs);

    Rover rover = new Rover(NORTH, new Position(0, 0));
    for (Dig dig : digs) {
      rover = move(rover, dig);
    }
  }

  private Rover move(Rover rover, Dig dig) {
    return move(rover.position(), dig);
  }

  private Rover move(Position position, Dig dig) {
    Rover roverToMove = new Rover(dig.direction().toRoverDirection(), position);
    roverToMove = roverToMove.move(FORWARD, true, (int) dig.meters());
    Position movedPosition = roverToMove.position();
    this.poly.addPoint(new Point(movedPosition.x(), movedPosition.y()));
    return roverToMove;
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

  record Point(long x, long y) {

    long distance(Point p2) {
      long dx = p2.x - this.x;
      long dy = p2.y - this.y;
      return Math.abs(dx) + Math.abs(dy);
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

    public Dig transformColor() {
      String cleanedString = color.replaceAll("[()#]", "");
      String firstChar = cleanedString.substring(0, cleanedString.length() - 1);
      String lastChar = cleanedString.substring(cleanedString.length() - 1);
      Dig.Direction value = Dig.Direction.values()[Integer.parseInt(lastChar)];
      return new Dig(value, parseHexadecimal(firstChar), color);
    }

    public long parseHexadecimal(String s) {
      return Long.parseLong(s, 16);
    }

    @AllArgsConstructor
    public enum Direction {
      RIGHT("R"),
      DOWN("D"),
      LEFT("L"),
      UP("U");

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
