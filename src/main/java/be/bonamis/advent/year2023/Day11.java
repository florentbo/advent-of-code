package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day11 extends DaySolver<String> {

  private final CharGrid grid;

  public Day11(List<String> puzzle) {
    super(puzzle);
    this.grid = grid(this.puzzle);
  }

  void printPoints(List<Point> notDots) {
    for (Point point : notDots) {
      //log.debug("not dot {} value {}", point, this.grid.get(point));
    }
  }

  @Override
  public long solvePart01() {
    List<Point> points = movedPoints();

    Set<Point> set = new HashSet<>(points);
    Set<Set<Point>> combinations = Sets.combinations(set, 2);
    log.debug("combinations size {}", combinations.size());

    return combinations.stream().map(this::distance).reduce(0, Integer::sum);
  }

  List<Point> movedPoints() {
    log.debug("\n\nbefore movedPoints");
    List<Point> points = notDots();
    printPoints(points);
    List<List<Point>> rows = rows();
    List<List<Point>> columns = columns();

    List<Integer> onlyDotsRows = onlyDotsLines(rows);
    List<Integer> onlyDotsColumns = onlyDotsLines(columns);
    log.debug("onlyDotsLines {}", onlyDotsRows);
    log.debug("onlyDotsColumns {}", onlyDotsColumns);
    points = moveDown(points, onlyDotsRows);
    log.debug("move down result after downs{}", points);
    log.debug("\n\nmove right result before rights");
    printPoints(points);
    points = moveRight(points, onlyDotsColumns);
    log.debug("points after all moves {}", points);
    return points;
  }

  List<Point> notDots() {
    return this.grid.stream().filter(this::isNotDot).toList();
  }

  int distance(Set<Point> points) {
    Iterator<Point> iterator = points.iterator();
    Point point5 = iterator.next();
    Point point9 = iterator.next();
    return distance(point5, point9);
  }

  int distance(Point point5, Point point9) {
    int x = Math.abs(point5.x - point9.x);
    int y = Math.abs(point5.y - point9.y);
    return x + y;
  }

  private List<Point> moveDown(List<Point> points, List<Integer> rows) {
    return points.stream().map(point -> moveDown(point, rows)).toList();
  }

  private List<Point> moveRight(List<Point> points, List<Integer> columns) {
    return points.stream().map(point -> moveRight(point, columns)).toList();
  }

  private List<Point> moveDown(List<Point> points, Integer row) {
    return points.stream().map(point -> moveDown(point, row)).toList();
  }

  private Point moveDown(Point point, List<Integer> heights) {
    int heightToAdd = (int) heights.stream().filter(height -> point.y > height).count();
    return new Point(point.x, point.y + heightToAdd);
  }

  private Point moveRight(Point point, List<Integer> wides) {
    int wideToAdd = (int) wides.stream().filter(wide -> point.x > wide).count();
    return new Point(point.x + wideToAdd, point.y);
  }

  private Point moveDown(Point point, Integer height) {
    return point.y > height ? new Point(point.x, point.y + 1) : point;
  }

  private List<Point> moveRight(List<Point> points, Integer column) {
    return points.stream().map(point -> moveRight(point, column)).toList();
  }

  private Point moveRight(Point point, Integer column) {
    return point.x > column ? new Point(point.x + 1, point.y) : point;
  }

  private List<Integer> onlyDotsLines(List<List<Point>> lines) {
    return IntStream.range(0, lines.size())
        .filter(index -> containsOnlyDots(lines.get(index)))
        .boxed()
        .toList();
  }

  private List<List<Point>> rows() {
    return IntStream.range(0, this.grid.getHeight()).mapToObj(this::row).toList();
  }

  private List<Point> row(int h) {
    return IntStream.range(0, this.grid.getWidth()).mapToObj(w -> new Point(w, h)).toList();
  }

  private List<List<Point>> columns() {
    return IntStream.range(0, this.grid.getWidth()).mapToObj(this::column).toList();
  }

  private List<Point> column(int w) {
    return IntStream.range(0, this.grid.getHeight()).mapToObj(h -> new Point(w, h)).toList();
  }

  private boolean containsOnlyDots(List<Point> points) {
    return points.stream().allMatch(this::isDot);
  }

  boolean isDot(Point point) {
    return this.grid.get(point) == '.';
  }

  boolean isNotDot(Point point) {
    return !isDot(point);
  }

  private static CharGrid grid(List<String> text) {
    char[][] grid = text.stream().map(String::toCharArray).toArray(char[][]::new);

    // Swap rows and columns during reading
    char[][] swappedGrid = new char[grid[0].length][grid.length];

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        swappedGrid[j][i] = grid[i][j];
      }
    }
    return new CharGrid(swappedGrid);
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/11/2023_11_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day11 day = new Day11(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
