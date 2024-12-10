package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;

import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.*;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.*;
import lombok.extern.slf4j.Slf4j;

import static be.bonamis.advent.utils.marsrover.Rover.Command.FORWARD;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.values;

@Slf4j
public class Day10 extends TextDaySolver {

  private final CharGrid grid;

  public Day10(InputStream inputStream) {
    super(inputStream);
    this.grid = CharGrid.of(puzzle);
  }

  public Day10(List<String> puzzle) {
    super(puzzle);
    this.grid = CharGrid.of(puzzle);
  }

  @Override
  public long solvePart01() {
    List<Point> starts = grid.stream().filter(p -> grid.get(p) == '0').toList();
    List<Point> ends = grid.stream().filter(p -> grid.get(p) == '9').toList();

    int count = 0;
    for (Point startPoint : starts) {
      for (Point endPoint : ends) {
        List<List<Point>> allPaths = dfs(startPoint, endPoint, new HashMap<>());
        if (!allPaths.isEmpty()) {
          count++;
        }
      }
    }
    return count;
  }

  List<List<Point>> dfs(Point start, Point end, Map<Point, Boolean> visited) {
    List<List<Point>> paths = new ArrayList<>();
    if (start.equals(end)) {
      paths.add(new ArrayList<>());
      return paths;
    }
    visited.put(start, true);
    Set<Position> allowedPositions = allowedPositions(Position.of(start));

    log.debug("allowedPositions for start {}: value of start {}", start, value(Position.of(start)));
    for (var allowedPosition : allowedPositions) {
      log.debug("allowedPosition {} and value {}", allowedPosition, value(allowedPosition));
      Point neighbor = allowedPosition.toPoint();
      if (!visited.getOrDefault(neighbor, false)) {
        List<List<Point>> neighborsPaths = dfs(neighbor, end, visited);
        for (List<Point> path : neighborsPaths) {
          path.add(0, start);
          paths.add(path);
        }
      }
    }
    return paths;
  }

  private Set<Position> allowedPositions(Position startPosition) {

    int startPositionValue = value(startPosition);

    Stream<Rover> rovers =
        Arrays.stream(values())
            .map(direction -> new Rover(direction, startPosition).move(FORWARD, true))
            .filter(grid::isPositionInTheGrid)
            .filter(rover -> value(rover.position()) - startPositionValue == 1);

    Set<Position> collect = rovers.map(Rover::position).collect(Collectors.toSet());
    log.debug("allowedPositions for start {}: value of start {}", startPosition, startPositionValue);
    return collect;
  }

  private int value(Position startPosition) {
    try {
      return Integer.parseInt(String.valueOf(grid.get(startPosition)));
    } catch (NumberFormatException e) {
      return -1000;
    }
  }

  @Override
  public long solvePart02() {
    List<Point> starts = grid.stream().filter(p -> grid.get(p) == '0').toList();
    List<Point> ends = grid.stream().filter(p -> grid.get(p) == '9').toList();

    int count = 0;
    for (Point startPoint : starts) {
      for (Point endPoint : ends) {
        log.debug("startPoint: {}", startPoint);
        log.debug("endPoint: {}", endPoint);
        List<List<Point>> allPaths = dfs(startPoint, endPoint, new HashMap<>());
        if (!allPaths.isEmpty()) {
          count += allPaths.size();
        }
      }
    }

    Point startPoint = starts.get(0);
    log.info("startPoint: {}", startPoint);
    Point endPoint = ends.get(0);
    log.info("endPoint: {}", endPoint);

    List<List<Point>> allPaths = dfs(startPoint, endPoint, new HashMap<>());
    allPaths.forEach(
        path -> {
          log.debug("path start ++++++++++++++++++++++");
          path.forEach(p -> log.debug("path {} and value {}", p, grid.get(p)));
          log.debug("path end ++++++++++++++++++++++");
        });
    return count;
  }
}
