package be.bonamis.advent.year2016;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;

import be.bonamis.advent.TextDaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.*;

import java.awt.*;
import java.io.InputStream;
import java.util.List;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day02 extends TextDaySolver {

  public Day02(InputStream sample) {
    super(sample);
  }

  @Override
  public long solvePart01() {
    String text =
        """
      123
      456
      789
      """;
    CharGrid grid = new CharGrid(text);
    return Long.parseLong(lineMoves(this.puzzle, grid, Position.of(1, 1)));
  }

  @Override
  public String solvePart02String() {
    String text =
        """
          ##1##
          #234#
          56789
          #ABC#
          ##D##
          """;
    CharGrid grid = new CharGrid(text);
    return lineMoves(this.puzzle, grid, Position.of(0, 2));
  }

  String lineMoves(List<String> moves, CharGrid grid, Position position) {
    Rover rover = new Rover(NORTH, position);

    StringBuilder result = new StringBuilder();
    for (String move : moves) {
      for (Rover.Direction direction : move.chars().mapToObj(this::toDirection).toList()) {
        log.info("direction: {}", direction);
        rover = new Rover(direction, rover.position());
        log.info("rover after direction change: {}", rover);
        Rover moved = rover.move(FORWARD, true);
        Character pointValue = grid.get(moved.position().toPoint());
        log.info("pointValue: {}", pointValue);
        if (pointValue != null) {
          boolean isEmpty = pointValue == '#';
          log.info("isEmpty: {}", isEmpty);
          if (!isEmpty) {
            rover = moved;
          }
        }
      }
      Point point = rover.position().toPoint();
      log.info("point: {}", point);
      Character value = grid.get(point);
      result.append(value);
    }

    return result.toString();
  }

  Rover.Direction toDirection(int i) {
    return switch (i) {
      case 'U' -> NORTH;
      case 'D' -> SOUTH;
      case 'L' -> WEST;
      case 'R' -> EAST;
      default -> throw new IllegalArgumentException("Invalid position " + i);
    };
  }

  @Override
  public long solvePart02() {
    return 9990;
  }
}
