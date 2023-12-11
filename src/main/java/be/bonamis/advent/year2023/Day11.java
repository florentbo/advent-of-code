package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;

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
  private final int lineSize;

  public Day11(List<String> puzzle, int lineSize) {
    super(puzzle);
    this.lineSize = lineSize;
    this.grid = grid(this.puzzle);
  }

  void printPoints(List<Point> notDots) {
    for (Point point : notDots) {
      log.debug("not dot {} value {}", point, this.grid.get(point));
    }
  }

  @Override
  public long solvePart01() {
    List<Point> points = movedPoints();

    return lengthsSum(points);
  }

  private long lengthsSum(List<Point> points) {
    Set<Point> set = new HashSet<>(points);
    Set<Set<Point>> combinations = Sets.combinations(set, 2);
    log.debug("combinations size {}", combinations.size());

    return combinations.stream().map(this::distance).reduce(0L, Long::sum);
  }

  @Override
  public long solvePart02() {
    List<Point> points = movedPoints();
    return lengthsSum(points);
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
    points = moveDown(points, onlyDotsRows, lineSize - 1);
    log.debug("\n\nmove down result after downs");
    printPoints(points);
    points = moveRight(points, onlyDotsColumns, lineSize - 1);
    log.debug("\n\nmove down result after moveRight");
    printPoints(points);
    return points;
  }

  List<Point> notDots() {
    return this.grid.stream().filter(this::isNotDot).toList();
  }

  long distance(Set<Point> points) {
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

  private List<Point> moveDown(List<Point> points, List<Integer> rows, int lineSize) {
    return points.stream().map(point -> moveDown(point, rows, lineSize)).toList();
  }

  private List<Point> moveRight(List<Point> points, List<Integer> columns, int lineSize) {
    return points.stream().map(point -> moveRight(point, columns, lineSize)).toList();
  }

  private Point moveDown(Point point, List<Integer> heights, int lineSize) {
    int heightToAdd = (int) heights.stream().filter(height -> point.y > height).count();
    return new Point(point.x, point.y + heightToAdd * lineSize);
  }

  private Point moveRight(Point point, List<Integer> wides, int lineSize) {
    int wideToAdd = (int) wides.stream().filter(wide -> point.x > wide).count();
    return new Point(point.x + wideToAdd * lineSize, point.y);
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

  public static void main(String[] args) {
    String content = FileHelper.content("2023/11/2023_11_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));

    log.info("solution part 1: {}", new Day11(puzzle, 2).solvePart01());
    log.info("solution part 2: {}", new Day11(puzzle, 1000000).solvePart02());
  }
}
