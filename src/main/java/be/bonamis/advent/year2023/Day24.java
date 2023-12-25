package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;

@Slf4j
@Getter
public class Day24 extends DaySolver<String> {

  public Day24(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    String line = "20, 19, 15 @ 1, -5, -3";

    String[] coordinates = line.split("@")[0].trim().split(",");
    String secondPoint = "21, 14, 12";
    log.info("parts: {}", Arrays.toString(line.split("@")));
    String[] coordinates2 = secondPoint.split("@")[0].trim().split(",");
    Point p1 =
        Point.of(Long.parseLong(coordinates[0].trim()), Long.parseLong(coordinates[1].trim()));

    String[] lineSlopes = line.split("@")[1].trim().split(",");

    LineSlope lineSlope =
        LineSlope.of(
            Long.parseLong(lineSlopes[0].trim()),
            Long.parseLong(lineSlopes[1].trim()),
            Long.parseLong(lineSlopes[2].trim()));

    log.info("lineSlope: {}", lineSlope);

    Point p2 =
        Point.of(Long.parseLong(coordinates2[0].trim()), Long.parseLong(coordinates2[1].trim()));
    log.info("p1: {}, p2: {}", p1, p2);
    double slope = (double) (p2.y() - p1.y()) / (p2.x() - p1.x());
    log.info("slope: {}", slope);
    log.info("slope2: {}", lineSlope.slope());

    double intercept = p1.y() - slope * p1.x();
    log.info("intercept: {}", intercept);
    LinearEquation equation = LinearEquation.from(p1, p2);
    log.info("equation: {}", equation);
    log.info("equation2: {}", LinearEquation.from(p1, lineSlope));

    return 888;
  }

  record Point(double x, double y) {

    public static Point of(double x, double y) {
      return new Point(x, y);
    }
  }

  record Hailstone(Point point, LineSlope lineSlope) {
    static Hailstone from(String line) {
      String[] coordinates = line.split("@")[0].trim().split(",");
      String[] lineSlopes = line.split("@")[1].trim().split(",");
      return new Hailstone(
          Point.of(Long.parseLong(coordinates[0].trim()), Long.parseLong(coordinates[1].trim())),
          LineSlope.of(
              Long.parseLong(lineSlopes[0].trim()),
              Long.parseLong(lineSlopes[1].trim()),
              Long.parseLong(lineSlopes[2].trim())));
    }

    public LinearEquation toEquation() {

      return LinearEquation.from(this.point, this.lineSlope);
    }

    public Point cross(Hailstone otherHailstone) {
      return this.toEquation().cross(otherHailstone.toEquation());
    }
  }

  record LineSlope(long x, long y, long z) {
    static LineSlope of(long x, long y, long z) {
      return new LineSlope(x, y, z);
    }

    double slope() {
      return (double) y / x;
    }
  }

  record LinearEquation(double a, double b, double c) {
    static LinearEquation from(Point p1, Point p2) {
      return new LinearEquation(
          p2.y() - p1.y(), p1.x() - p2.x(), p1.y() * p2.x() - p1.x() * p2.y());
    }

    static LinearEquation from(Point point, LineSlope lineSlope) {
      double slope = lineSlope.slope();
      double intercept = point.y() - slope * point.x();
      return new LinearEquation(slope, -1, intercept);
    }

    public Point cross(LinearEquation second) {
      double determinant = this.a * second.b - second.a * this.b;
      double intersectionX = (this.b * second.c - second.b * this.c) / determinant;
      double intersectionY = (this.c * second.a - this.a * second.c) / determinant;
      return Point.of(intersectionX, intersectionY);
    }
  }

  @Override
  public long solvePart02() {
    return 1000L;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/25/2023_25_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day24 day = new Day24(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
