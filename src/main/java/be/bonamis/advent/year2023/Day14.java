package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.*;
import java.util.stream.Stream;

import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.EAST;

@Slf4j
@Getter
public class Day14 extends DaySolver<String> {

  private static final char ROCK = 'O';
  private static final char DOT = '.';

  private final CharGrid grid;

  public Day14(List<String> puzzle) {
    super(puzzle);
    this.grid = new CharGrid(puzzle);
  }

  @Override
  public long solvePart01() {
    moveRocks(grid, SOUTH);
    return loadsSum(new CharGrid(grid.rowsAsLines()));
  }

  @Override
  public long solvePart02() {
    return solve(grid);
  }

  private Integer solve(CharGrid grid) {
    Map<List<String>, Pair<Integer, Integer>> memo = memo(grid);
    log.debug("memo size {}", memo.size());
    List<Pair<Integer, Integer>> values = new ArrayList<>(memo.values());
    log.debug("unsorted values {}", values);
    List<Integer> memoValues = values.stream().map(Pair::getLeft).toList();
    log.debug("memo: {}", memoValues);
    Pair<Integer, Integer> series = findSeries(memoValues);
    log.debug("series {}", series);

    Integer seriesSize = series.getRight();
    Integer seriesPrefix = series.getLeft();
    int modulo = (1000000000 - seriesPrefix) % seriesSize;
    log.debug("modulo {}", modulo);

    return values.get(seriesPrefix + modulo - 1).getRight();
  }

  Map<List<String>, Pair<Integer, Integer>> memo(CharGrid grid) {
    Map<List<String>, Pair<Integer, Integer>> memo = new LinkedHashMap<>();

    for (int i = 1; i <= 500; i++) {
      cycle(grid);
      List<String> lines = grid.rowsAsLines();
      Integer loadsSum = loadsSum(new CharGrid(lines));
      memo.put(lines, Pair.of(i, loadsSum));
      // log.debug("sum: {} value {}", loadsSum, i);
    }
    return memo;
  }

  Integer loadsSum(CharGrid grid) {
    return loads(grid).stream().reduce(Integer::sum).orElseThrow();
  }

  private List<Integer> loads(CharGrid grid) {
    return grid.stream()
        .filter(p1 -> grid.get(p1).equals('O'))
        .map(p -> grid.getHeight() - p.y)
        .toList();
  }

  void cycle(CharGrid grid) {
    moveRocks(grid, SOUTH);
    moveRocks(grid, WEST);
    moveRocks(grid, NORTH);
    moveRocks(grid, EAST);
  }

  Pair<Integer, Integer> findSeries(List<Integer> list) {
    int size = list.size();
    OptionalInt first =
        IntStream.range(0, size)
            .map(i -> list.size() - i - 1)
            .filter(i -> list.get(i) - list.get(i - 1) > 1)
            .findFirst();
    if (first.isPresent()) {
      int index = first.getAsInt();
      return Pair.of(index, size - index);
    }
    return Pair.of(0, 0);
  }

  void moveRocks(CharGrid grid, Rover.Direction direction) {
    Stream<Point> stream = stream(grid, direction);
    stream.forEach(
        point -> {
          Character c = grid.get(point);
          // log.debug("point {} value {}", point, c);
          if (c.equals(ROCK)) {
            Rover rover = new Rover(direction, new Position(point.x, point.y));
            boolean canMove = true;
            while (canMove) {
              Position newPosition = rover.move(Rover.Command.FORWARD).position();
              Point movedPoint = new Point(newPosition.x(), newPosition.y());
              canMove = isInTheGrid(grid, movedPoint) && isDot(grid, movedPoint);
              if (canMove) {
                grid.set(movedPoint, ROCK);
                Position position = rover.position();
                grid.set(new Point(position.x(), position.y()), DOT);
                rover = rover.move(Rover.Command.FORWARD);
              }
            }
          }
        });
  }

  private Stream<Point> stream(CharGrid grid, Rover.Direction direction) {
    return switch (direction) {
      case NORTH -> grid.streamFromDown();
      case EAST -> grid.streamFromLeft();
      default -> grid.stream();
    };
  }

  private boolean isDot(CharGrid grid, Point movedPoint) {
    return grid.get(movedPoint).equals(DOT);
  }

  private boolean isInTheGrid(CharGrid grid, Point movedPoint) {
    return grid.isInTheGrid().test(movedPoint);
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/14/2023_14_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day14 day = new Day14(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
