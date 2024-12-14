package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;

import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.Position;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day14 extends TextDaySolver {
  private final List<Input> inputs;

  public Day14(InputStream inputStream) {
    super(inputStream);
    this.inputs = this.puzzle.stream().map(Input::of).toList();
  }

  public Day14(List<String> puzzle) {
    super(puzzle);
    this.inputs = this.puzzle.stream().map(Input::of).toList();
  }

  @Override
  public long solvePart01() {
    List<Input> inputs = this.puzzle.stream().map(Input::of).toList();
    log.info("input size: {}", inputs.size());
    return solve(101, 103);
  }

  long solve(int width, int height) {
    List<Position> positions = movedInputs(width, height);
    Space space = Space.of(width, height);
    Set<Space.Quadrant> quadrants = space.quadrants();
    log.debug("quadrants: {}", quadrants);
    Stream<Long> longStream = quadrants.stream().map(quadrant -> quadrant.countRobots(positions));

    return longStream.reduce(1L, (a, b) -> a * b);
  }

  List<Position> movedInputs(int width, int height) {
    List<Input> movedInputs = inputs.stream().map(input -> input.move(100, width, height)).toList();
    List<Position> positions = movedInputs.stream().map(Input::position).toList();
    log.debug("positions: {}", positions);
    return positions;
  }

  @Override
  public long solvePart02() {
    return 2;
  }

  // 11 tiles wide and 7 tiles tall.
  CharGrid createDotsGrid(int width, int height) {
    List<String> dots = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      dots.add(String.join("", Collections.nCopies(width, ".")));
    }
    log.debug("dots: {}", dots);

    char[][] data = dots.stream().map(String::toCharArray).toArray(char[][]::new);

    return new CharGrid(data);
  }

  public record Space(int wide, int tall) {
    public static Space of(int wide, int tall) {
      return new Space(wide, tall);
    }

    public Set<Quadrant> quadrants() {
      int wideMiddle = (wide / 2);
      log.debug("wideMiddle: {}", wideMiddle);
      int tallMiddle = (tall / 2);
      log.debug("tallMiddle: {}", tallMiddle);

      Quadrant topLeftQuadrant =
          Quadrant.of(new Position(0, 0), new Position(wideMiddle - 1, tallMiddle - 1));

      Quadrant topRightQuadrant =
          Quadrant.of(new Position(wideMiddle + 1, 0), new Position(wide - 1, tallMiddle - 1));

      Quadrant bottomLeftQuadrant =
          Quadrant.of(new Position(0, tallMiddle + 1), new Position(wideMiddle - 1, tall - 1));

      Quadrant bottomRightQuadrant =
          Quadrant.of(
              new Position(wideMiddle + 1, tallMiddle + 1), new Position(wide - 1, tall - 1));

      return Set.of(topLeftQuadrant, topRightQuadrant, bottomLeftQuadrant, bottomRightQuadrant);
    }

    public record Quadrant(Position topLeft, Position bottomRight) {
      public static Quadrant of(Position topLeft, Position bottomRight) {
        return new Quadrant(topLeft, bottomRight);
      }

      long countRobots(List<Position> positions) {
        return positions.stream()
            .filter(position -> position.x() >= topLeft.x() && position.x() <= bottomRight.x())
            .filter(position -> position.y() >= topLeft.y() && position.y() <= bottomRight.y())
            .count();
      }
    }
  }

  public record Input(Position position, Velocity velocity) {

    public static Position toPosition(String position) {
      String[] parts = position.split("=");
      String[] xAndY = parts[1].split(",");
      return new Position(Integer.parseInt(xAndY[0]), Integer.parseInt(xAndY[1]));
    }

    public record Velocity(int x, int y) {
      public static Velocity from(String part) {
        String[] parts = part.split("=");
        String[] xAndY = parts[1].split(",");
        return new Velocity(Integer.parseInt(xAndY[0]), Integer.parseInt(xAndY[1]));
      }
    }

    // 11 tiles wide and 7 tiles tall.
    public Input move(int times, int wide, int tall) {
      int newX = (int) (position().x() + times * velocity().x());
      log.debug("newX: {}", newX);
      int x = (newX + 100000 * wide) % wide;
      log.debug("x: {}", x);
      int y = (((int) (position.y() + times * velocity.y())) + 100000 * tall) % tall;
      return new Input(new Position(x, y), velocity);
    }

    public static Input of(String input) {
      String[] parts = input.split(" ");
      String position = parts[0];
      String part = parts[1];
      return new Input(toPosition(position), Velocity.from(part));
    }
  }
}
