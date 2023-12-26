package be.bonamis.advent.year2023;

import static be.bonamis.advent.utils.marsrover.Rover.Command.FORWARD;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.utils.marsrover.*;

import java.awt.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day23 extends DaySolver<String> {

  private static final char PATH = '.';
  private static final char FOREST = '#';
  private static final char NORTH_STEEP = '^';
  private static final char SOUTH_STEEP = 'v';
  private static final char EAST_STEEP = '>';
  private static final char WEST_STEEP = '<';
  private static final List<Character> STEEP_DIRECTIONS =
      List.of(NORTH_STEEP, SOUTH_STEEP, EAST_STEEP, WEST_STEEP);

  private final CharGrid grid;

  public Day23(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(puzzle);
    log.info("grid  getHeight {} grid getWidth {}", grid.getHeight(), grid.getWidth());
  }

  @Override
  public long solvePart01() {
    Position startPosition = Position.of(1, 0);
    log.info("startPosition {}", startPosition);
    Position endPosition = Position.of(grid.getHeight() - 2, grid.getWidth() - 1);
    log.info("endPosition {}", endPosition);
    Rover start = new Rover(EAST, startPosition);
    char dataStart = data(start);
    log.info("dataStart {}", dataStart);

    Map<Position, Boolean> isVisited = new HashMap<>();
    List<Integer> allPaths = findAllPaths(startPosition, endPosition, isVisited);

    return allPaths.stream().mapToInt(positions -> positions - 1).max().orElseThrow();
  }

  List<Integer> findAllPaths(Position start, Position end, Map<Position, Boolean> isVisited) {
    List<Integer> allPaths = new ArrayList<>();
    Deque<PathInfo> stack = new ArrayDeque<>();
    stack.push(new PathInfo(start, isVisited, 0));

    while (!stack.isEmpty()) {
      PathInfo current = stack.pop();
      Position currentPosition = current.position();

      current.isVisited().put(currentPosition, true);

      int newPathSize = current.pathSize() + 1;
      if (currentPosition.equals(end)) {
        allPaths.add(newPathSize);
      } else {
        for (Position neighbor : getNeighbors(currentPosition)) {
          if (!current.isVisited().getOrDefault(neighbor, false)) {
            stack.push(new PathInfo(neighbor, new HashMap<>(current.isVisited()), newPathSize));
          }
        }
      }
    }

    return allPaths;
  }

  private Set<Position> getNeighbors(Position currentPosition) {
    return allowedPositions(currentPosition);
  }

  private Set<Position> allowedPositions(Position startPosition) {
    return Arrays.stream(values())
        .map(
            direction -> {
              Rover rover = new Rover(direction, startPosition);
              return rover.move(FORWARD, true);
            })
        .filter(this::isPositionInTheGrid)
        .filter(this::isNotForest)
        .filter(this::isInGoodDirection)
        .map(Rover::position)
        .collect(Collectors.toSet());
    // is in the grid filter
    // is not a forest filter
    // is slope in good direction
    // return new HashSet<>();
  }

  private boolean isInGoodDirection(Rover rover) {
    char data = data(rover);
    // log.debug("data {}", data);
    return !STEEP_DIRECTIONS.contains(data)
        || switch (rover.direction()) {
          case NORTH -> data == NORTH_STEEP;
          case SOUTH -> data == SOUTH_STEEP;
          case EAST -> data == EAST_STEEP;
          case WEST -> data == WEST_STEEP;
        };
  }

  private boolean isPositionInTheGrid(Rover rover) {
    return grid.isPositionInTheGrid().test(rover.position());
  }

  private boolean isNotForest(Rover rover) {
    char data = grid.get(rover.position());
    return data != FOREST;
  }

  boolean isNotForest(Point point) {
    char data = grid.get(point);
    return data != FOREST;
  }

  boolean isForest(Point point) {
    char data = grid.get(point);
    return data == FOREST;
  }

  private char data(Rover rover) {
    return grid.get(rover.position());
  }

  @Override
  public long solvePart02() {
    return 9999L;
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/23/2023_23_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day23 day = new Day23(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }

  public record PathInfo(Position position, Map<Position, Boolean> isVisited, int pathSize) {}
}
