package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;

import java.awt.*;
import java.util.*;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day18 extends DaySolver<String> {

  private final int dimensions;
  private final CharGrid grid;

  public Day18(List<String> puzzle, int dimensions) {
    super(puzzle);
    this.dimensions = dimensions;
    String result =
        IntStream.range(0, this.dimensions)
            .mapToObj(
                line ->
                    IntStream.range(0, this.dimensions)
                        .mapToObj(dot -> ".")
                        .collect(Collectors.joining("")))
            .collect(Collectors.joining("\n"));
    this.grid = new CharGrid(result);
  }

  @Override
  public long solvePart01() {
    createGrid(this.puzzle.stream().map(Dig::parse).toList());

    /*try {
      Path filePath = Paths.get("/home/florent/Documents/crt/output.txt");
      Files.deleteIfExists(filePath);
      Files.createFile(filePath);
      for (String str : rows) {
        Files.writeString(filePath, str + System.lineSeparator(), StandardOpenOption.APPEND);
      }
    } catch (Exception e) {
      log.error("problem", e);
    }*/

    return (long) this.grid.getHeight() * this.grid.getWidth() - externalPainted();
  }

  void createGrid(List<Dig> digs) {
    log.info("digs: {}", digs);

    Rover rover = new Rover(NORTH, new Position(dimensions / 2, dimensions / 2));
    grid.set(rover.position(), '#');
    for (Dig dig : digs) {
      rover = move(rover, dig);
    }
    List<String> rows = this.grid.rowsAsLines();
    for (String row : rows) {
      log.info(row);
    }
  }

  int externalPainted() {
    Set<Point> painted = new HashSet<>();
    for (int i = 0; i < this.grid.getHeight(); i++) {
      Set<Point> points = paintRow(i);
      painted.addAll(points);
    }
    Set<Point> paintedAfterRows = new HashSet<>(painted);

    for (int i = 0; i < this.grid.getWidth(); i++) {
      painted.addAll(paintColumn(i));
    }

    Set<Point> actualOnlyInSet1 =
        painted.stream().filter(e -> !paintedAfterRows.contains(e)).collect(Collectors.toSet());
    actualOnlyInSet1.forEach(p -> log.info("painted: {}", p));

    return painted.size();
  }

  Set<Point> paintRow(int rowNum) {
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
  }

  private Rover move(Rover rover, Dig dig) {
    Rover moved = move(rover.position(), dig);

    return moved;
  }

  private Rover move(Position position, Dig dig) {
    Rover roverToMove = new Rover(dig.direction().toRoverDirection(), position);
    for (int i = 0; i < dig.meters(); i++) {
      Rover movedRover = roverToMove.move(FORWARD, true);
      grid.set(movedRover.position(), '#');
      roverToMove = movedRover;
    }
    return roverToMove;
  }

  @Override
  public long solvePart02() {
    return 888;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/18/2023_18_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day18 day = new Day18(puzzle, 5000);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
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
