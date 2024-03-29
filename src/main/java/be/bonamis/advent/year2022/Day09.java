package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;

import java.util.*;
import java.util.stream.Collectors;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;

public class Day09 extends DaySolver<String> {

  public Day09(List<String> puzzle) {
    super(puzzle);
  }

  @Override
  public long solvePart01() {
    String collect =
        this.puzzle.stream().map(str -> str.replaceAll(" ", "")).collect(Collectors.joining(","));
    return new HashSet<>(new Rope().move(Rope.WirePath.from(collect)).tailPositions).size();
  }

  @Override
  public long solvePart02() {
    return this.puzzle.size() + 1;
  }

  record RopeTail(Position position, List<Position> positions) {}

  record Rope(Rover head, Position tail, List<Position> positions, List<Position> tailPositions) {
    public Rope() {
      this(initRover(), new Position(0, 0), new ArrayList<>(), initTailPositions());
    }

    private static List<Position> initTailPositions() {
      List<Position> arrayList = new ArrayList<>();
      arrayList.add(new Position(0, 0));
      return arrayList;
    }

    private static Rover initRover() {
      return new Rover(NORTH, new Position(0, 0));
    }

    public Rope move(List<WirePath> paths) {
      Rover previousRoverAfterPivoting = this.head;
      Position previousTail = this.tail;
      for (int ii = 0; ii < paths.size(); ii++) {
        WirePath path = paths.get(ii);
        WirePath previousPath = ii > 0 ? paths.get(ii - 1) : null;
        previousRoverAfterPivoting =
            new Rover(path.facingDirection(), previousRoverAfterPivoting.position());
        for (int i = 0; i < path.length(); i++) {
          previousRoverAfterPivoting = previousRoverAfterPivoting.move(Rover.Command.FORWARD);
          Position headNewPosition = previousRoverAfterPivoting.position();
          previousTail = handlingTail(previousTail, path, previousPath, headNewPosition);
          this.positions.add(headNewPosition);
        }
      }
      return new Rope(previousRoverAfterPivoting, previousTail, this.positions, this.tailPositions);
    }

    private Position handlingTail(
        Position previousTail, WirePath path, WirePath previousPath, Position headNewPosition) {
      long xDistance = Math.abs(previousTail.x() - headNewPosition.x());
      long yDistance = Math.abs(previousTail.y() - headNewPosition.y());
      if (xDistance > 1 || yDistance > 1) {
        Position lastHeadPosition =
            newPosition(xDistance, yDistance, previousTail, path, previousPath);
        this.tailPositions.add(lastHeadPosition);
        previousTail = lastHeadPosition;
      }
      return previousTail;
    }

    private Position newPosition(
        long xDistance, long yDistance, Position previousTail, WirePath path, WirePath previousPath) {
      Position previous = this.positions.get(this.positions().size() - 1);
      if (xDistance == 0 || yDistance == 0) {
        return previous;
      }
      Rover beforeFirstDiagMove = new Rover(previousPath.facingDirection(), previousTail);
      Rover firstDiagMove = beforeFirstDiagMove.move(Rover.Command.FORWARD);
      Rover rover2 = new Rover(path.facingDirection(), firstDiagMove.position());
      return rover2.move(Rover.Command.FORWARD).position();
    }

    public Rope move(WirePath... paths) {
      return move(Arrays.asList(paths));
    }

    public record WirePath(Rover.Direction facingDirection, int length) {
      public static WirePath of(String path) {
        return new WirePath(getFacingDirection(path.substring(0, 1)), getLength(path.substring(1)));
      }

      private static int getLength(String substring) {
        return Integer.parseInt(substring);
      }

      private static Rover.Direction getFacingDirection(String substring) {
        return switch (substring) {
          case "R" -> EAST;
          case "U" -> NORTH;
          case "L" -> WEST;
          case "D" -> SOUTH;
          default -> null;
        };
      }

      public static List<WirePath> from(String wirePaths) {
        return Arrays.stream(wirePaths.split(",")).map(WirePath::of).collect(Collectors.toList());
      }
    }
  }
}
