package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.FileHelper;
import java.util.*;
import java.util.ArrayList;

import com.google.common.collect.Sets;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day24 extends DaySolver<String> {

  private final long minValueTestArea;
  private final long maxValueTestArea;

  public Day24(List<String> puzzle, long minValueTestArea, long maxValueTestArea) {
    super(puzzle);
    this.minValueTestArea = minValueTestArea;
    this.maxValueTestArea = maxValueTestArea;
  }

  @Override
  public long solvePart01() {
    Set<Set<String>> combinations = Sets.combinations(Sets.newTreeSet(this.puzzle), 2);
    int count = 0;
    for (Set<String> combination : combinations) {
     // log.info("combination: {}", combination.size());
      List<String> list = new ArrayList<>(combination);
      //log.info("list: {}", list);
      String next = combination.iterator().next();
      Hailstone hailstoneA = Hailstone.from(list.get(0));
      //log.info("hailstoneA: {}", hailstoneA);
      String b = combination.iterator().next();
      Hailstone hailstoneB = Hailstone.from(list.get(1));
      //log.info("hailstoneB: {}", hailstoneB);
      if (crossIsInsideTestArea(hailstoneA, hailstoneB)) {
        count++;
      }
    }

    return count;
  }

  public boolean crossIsInsideTestArea(Hailstone hailstoneA, Hailstone hailstoneB) {
    Point cross = hailstoneA.cross(hailstoneB);
    return cross.x() >= minValueTestArea
        && cross.x() <= maxValueTestArea
        && cross.y() >= minValueTestArea
        && cross.y() <= maxValueTestArea;
  }

  public record Point(double x, double y) {

    public static Point of(double x, double y) {
      return new Point(x, y);
    }
  }

  public record Hailstone(Point point, LineSlope lineSlope) {
    static Hailstone from(String line) {
      String[] coordinates = line.split("@")[0].trim().split(",");
      log.info("coordinates: {}", coordinates[0].trim());
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

  public record LinearEquation(double a, double b, double c) {

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
    String content = FileHelper.content("2023/24/2023_24_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day24 day = new Day24(puzzle, 200000000000000L, 400000000000000L);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
