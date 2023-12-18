package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;

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
    double perimeter = calculatePerimeter(this.poly.points());

    float half = (float) (perimeter / 2);
    log.debug("area {} half {}", a, half);

    return (long) (a + half) + 1;
  }

  @Override
  public long solvePart02() {
    createGrid(this.puzzle.stream().map(Dig::parse).map(Dig::transformColor).toList());
    long a = (long) calculateArea(poly);
    double perimeter = calculatePerimeter(this.poly.points());

    float half = (float) (perimeter / 2);
    log.debug("area {} half {}", a, half);

    return (long) (a + half) + 1;
  }

  double calculatePerimeter(List<Point> points) {
    if (points == null || points.size() < 2) {
      return 0.0;
    }

    double perimeter = 0.0;

    for (int i = 0; i < points.size() - 1; i++) {
      Point currentPoint = points.get(i);
      Point nextPoint = points.get(i + 1);

      double distance = currentPoint.distance(nextPoint);

      perimeter += distance;
    }

    perimeter += points.get(points.size() - 1).distance(points.get(0));

    return perimeter;
  }

  double area(Point[] polyPoints) {

    int n = polyPoints.length;
    double area = 0;

    for (int i = 0; i < n; i++) {
      int j = (i + 1) % n;
      area += polyPoints[i].y() * polyPoints[j].x();
      area -= polyPoints[j].y() * polyPoints[i].x();
    }
    area /= 2.0;
    return (area < 0 ? -area : area);
  }

  double calculateArea(Polygon polygon) {
    List<Point> points = polygon.points();
    Point[] polyPoints = points.toArray(new Point[0]);

    return area(polyPoints);
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
    public double distance(Point nextPoint) {
      long dx = nextPoint.x() - this.x();
      long dy = nextPoint.y() - this.y();

      return Math.sqrt(dx * dx + dy * dy);
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
