package be.bonamis.advent.common;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class CharGrid {

  public static final char DOT = '.';
  private final char[][] data;
  private final int width;
  private final int height;

  public CharGrid(char[][] input) {
    this.data = input;
    this.height = input.length;
    this.width = input[0].length;
  }

  public CharGrid(List<String> text) {
    CharGrid grid = this.grid(text);
    this.data = grid.getData();
    this.height = grid.getHeight();
    this.width = grid.getWidth();
  }

  public CharGrid(String text) {
    CharGrid grid = this.grid(Arrays.asList(text.split("\n")));
    this.data = grid.getData();
    this.height = grid.getHeight();
    this.width = grid.getWidth();
  }

  private CharGrid grid(List<String> text) {
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

  public Stream<Point> stream() {
    return IntStream.range(0, data.length)
        .boxed()
        .flatMap(x -> IntStream.range(0, data[x].length).mapToObj(y -> new Point(x, y)));
  }

  public Stream<Point> streamFromDown() {
    int length = data.length;
    return IntStream.range(0, length)
        .boxed()
        .flatMap(
            x -> IntStream.range(0, data[x].length).mapToObj(y -> new Point(x, length - y - 1)));
  }

  public Stream<Point> streamFromLeft() {
    int length = data.length;
    return IntStream.range(0, length)
        .boxed()
        .flatMap(
            x -> IntStream.range(0, data[x].length).mapToObj(y -> new Point(length - x - 1, y)));
  }

  public Stream<Point> topEdge() {
    return IntStream.range(0, data.length).mapToObj(x -> new Point(x, 0));
  }

  public Stream<Point> bottomEdge() {
    return IntStream.range(0, data.length).mapToObj(x -> new Point(x, data[x].length - 1));
  }

  public Stream<Point> leftEdge() {
    return IntStream.range(0, data.length).mapToObj(y -> new Point(0, y));
  }

  public Stream<Point> rightEdge() {
    return IntStream.range(0, data.length).mapToObj(y -> new Point(data[y].length - 1, y));
  }

  public void printLines() {
    this.rows().forEach(line -> log.info(toLine(line)));
  }

  public CharGrid rotateCounterClockwise() {
    // yes strange char init probably
    return new CharGrid(rotateClockWise(this.data));
  }

  private char[][] rotateClockWise(char[][] matrix) {
    int size = matrix.length;
    char[][] ret = new char[size][size];

    for (int i = 0; i < size; ++i) {
      for (int j = 0; j < size; ++j) {
        ret[i][j] = matrix[size - j - 1][i];
      }
    }

    return ret;
  }

  public String toLine(List<Point> line) {
    Stream<Character> characterStream = line.stream().map(this::get);
    return characterStream.map(String::valueOf).collect(Collectors.joining());
  }

  public String toLine2(List<Point> line) {
    Stream<Character> characterStream = line.stream().map(this::get2);
    return characterStream.map(String::valueOf).collect(Collectors.joining());
  }

  public Character get(Point p) {
    if (p.x >= 0 && p.x < data.length && p.y >= 0 && p.y < data[0].length) {
      return data[p.x][p.y];
    }
    return null;
  }

  public Character get2(Point p) {
    return data[p.y][p.x];
  }

  public Long number(Point p) {
    return (long) Character.getNumericValue(get(p));
  }

  public void set(Point point, char i) {
    data[point.x][point.y] = i;
  }

  public void set(Position position, char i) {
    data[(int) position.x()][(int) position.y()] = i;
  }

  public void consume(Consumer<Point> consumer) {
    for (int x = 0; x < data.length; x++) {
      for (int y = 0; y < data[x].length; y++) {
        consumer.accept(new Point(x, y));
      }
    }
  }

  public List<List<Point>> lines() {
    List<List<Point>> lines = new ArrayList<>();
    for (int x = 0; x < data.length; x++) {
      List<Point> line = new ArrayList<>();
      for (int y = 0; y < data[x].length; y++) {
        line.add(new Point(x, y));
      }
      lines.add(line);
    }
    return lines;
  }

  public List<Point> neighbours(Point point, boolean withDiagonals) {
    List<Point> neighbours = new ArrayList<>();
    neighbours.add(new Point(point.x - 1, point.y));
    neighbours.add(new Point(point.x + 1, point.y));
    neighbours.add(new Point(point.x, point.y - 1));
    neighbours.add(new Point(point.x, point.y + 1));

    if (withDiagonals) {
      neighbours.add(new Point(point.x - 1, point.y - 1));
      neighbours.add(new Point(point.x - 1, point.y + 1));
      neighbours.add(new Point(point.x + 1, point.y - 1));
      neighbours.add(new Point(point.x + 1, point.y + 1));
    }
    return neighbours.stream().filter(isInTheGrid()).toList();
  }



  public Predicate<Point> isInTheGrid() {
    return p ->
        p.getX() >= 0 && p.getY() >= 0 && p.getX() < this.getHeight() && p.getY() < this.getWidth();
  }

  public boolean isPositionInTheGrid(Rover rover) {
    Position p = rover.position();
    return (int) p.x() >= 0
        && (int) p.y() >= 0
        && (int) p.x() < this.height
        && (int) p.y() < this.width;
  }

  public Point find(int i) {
    return stream().filter(p -> number(p) == i).findFirst().orElseThrow();
  }

  public List<List<Point>> rows() {
    return IntStream.range(0, getHeight()).mapToObj(this::row).toList();
  }

  public List<Point> row(int h) {
    return IntStream.range(0, getWidth()).mapToObj(w -> new Point(w, h)).toList();
  }

  public List<List<Point>> columns() {
    return IntStream.range(0, getWidth()).mapToObj(this::column).toList();
  }

  public List<List<Point>> diagonals() {
    List<List<Point>> allDiagonals = new ArrayList<>();

    // Main diagonals (top-left to bottom-right)
    allDiagonals.addAll(
        IntStream.range(0, width)
            .mapToObj(
                startCol ->
                    IntStream.range(0, Math.min(height, width - startCol))
                        .mapToObj(i -> new Point(i, startCol + i))
                        .toList())
            .filter(list -> list.size() > 1)
            .toList());

    allDiagonals.addAll(
        IntStream.range(1, height)
            .mapToObj(
                startRow ->
                    IntStream.range(0, Math.min(width, height - startRow))
                        .mapToObj(i -> new Point(startRow + i, i))
                        .toList())
            .filter(list -> list.size() > 1)
            .toList());

    // Anti-diagonals (top-right to bottom-left)
    allDiagonals.addAll(
        IntStream.range(0, width)
            .mapToObj(
                startCol ->
                    IntStream.range(0, Math.min(height, startCol + 1))
                        .mapToObj(i -> new Point(i, startCol - i))
                        .toList())
            .filter(list -> list.size() > 1)
            .toList());

    allDiagonals.addAll(
        IntStream.range(1, height)
            .mapToObj(
                startRow ->
                    IntStream.range(0, Math.min(width - startRow, height))
                        .mapToObj(i -> new Point(startRow + i, width - 1 - i))
                        .toList())
            .filter(list -> list.size() > 1)
            .toList());

    return allDiagonals;
  }

  public List<Point> column(int w) {
    return IntStream.range(0, getHeight()).mapToObj(h -> new Point(w, h)).toList();
  }

  public List<String> rowsAsLines() {
    return rows().stream().map(this::toLine).toList();
  }

  public List<String> columnsAsLines() {
    return columns().stream().map(this::toLine).toList();
  }

  public List<String> diagonalsAsLines() {
    return diagonals().stream().map(this::toLine).toList();
  }

  public List<String> rowsAsLines2() {
    return rows().stream().map(this::toLine2).toList();
  }

  public List<String> columnsAsLines2() {
    return columns().stream().map(this::toLine2).toList();
  }

  public char get(Position position) {
    return get(new Point((int) position.x(), (int) position.y()));
  }

  public List<CharGrid> subGrids(int size) {
    List<CharGrid> subGrids = new ArrayList<>();

    for (int startX = 0; startX <= height - size; startX++) {
      for (int startY = 0; startY <= width - size; startY++) {
        char[][] subgrid = new char[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(data[startX + i], startY, subgrid[i], 0, size);
        }
        subGrids.add(new CharGrid(subgrid));
      }
    }

    return subGrids;
  }
}
