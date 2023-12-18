package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day18 extends DaySolver<String> {

  private static final int DIMENSIONS = 40;
  private final CharGrid grid;

  public Day18(List<String> puzzle) {
    super(puzzle);
    String result =
        IntStream.range(0, DIMENSIONS)
            .mapToObj(
                line ->
                    IntStream.range(0, DIMENSIONS)
                        .mapToObj(dot -> ".")
                        .collect(Collectors.joining("")))
            .collect(Collectors.joining("\n"));
    this.grid = new CharGrid(result);
  }

  @Override
  public long solvePart01() {
    List<Dig> digs = this.puzzle.stream().map(Dig::parse).toList();
    log.info("digs: {}", digs);

    Rover rover = new Rover(NORTH, new Position(DIMENSIONS / 2, DIMENSIONS / 2));
    grid.set(rover.position(), '#');
    for (Dig dig : digs) {
      rover = move(rover, dig);
    }
    List<String> rows = this.grid.rowsAsLines();
    for (String row : rows) {
      log.info(row);
    }

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

    return 999;
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
    Day18 day = new Day18(puzzle);
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
