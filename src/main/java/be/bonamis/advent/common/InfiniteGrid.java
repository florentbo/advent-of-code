package be.bonamis.advent.common;

import be.bonamis.advent.utils.marsrover.Position;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@ToString
public class InfiniteGrid {

  private final LinkedHashMap<Point, Integer> data = new LinkedHashMap<>();

  public void addValue(Point point, int value) {
    data.put(point, value);
  }

  public int get(int x, int y) {
    return data.getOrDefault(new Point(x, y), 0);
  }

  public int get(Point point) {
    return data.getOrDefault(point, 0);
  }

  public Optional<Integer> find(int x, int y) {
    Point point = Point.of(x, y);
    return data.get(point) == null ? Optional.empty() : Optional.of(data.get(point));
  }

  public Point last() {
    return data.keySet().stream().reduce((first, second) -> second).orElse(Point.of(0, 0));
  }

  /*public Set<Point> neighbors(Point point) {
    return Stream.of(
        Point.of(point.x() - 1, point.y()),
        Point.of(point.x() + 1, point.y()),
        Point.of(point.x(), point.y() - 1),
        Point.of(point.x(), point.y() + 1))
       .filter(data::containsKey)
       .collect(Collectors.toSet());
  }*/

  public Set<Point> neighbors(Point point) {
    return Stream.of(
            Point.of(point.x() - 1, point.y() - 1),
            Point.of(point.x() - 1, point.y()),
            Point.of(point.x() - 1, point.y() + 1),
            Point.of(point.x(), point.y() - 1),
            Point.of(point.x(), point.y() + 1),
            Point.of(point.x() + 1, point.y() - 1),
            Point.of(point.x() + 1, point.y()),
            Point.of(point.x() + 1, point.y() + 1))
        .filter(data::containsKey)
        .collect(Collectors.toSet());
  }

  public BiFunction<Integer, Point, Integer> addNeighborsSum =
      (x, point) -> x + neighborsSum(point);

  public int neighborsSum(Point point) {
    return neighbors(point).stream().mapToInt(this::get).sum();
  }

  public record Point(int x, int y) {
    public static Point of(int x, int y) {
      return new Point(x, y);
    }

    public static Point from(Position position) {
      return Point.of((int) position.x(), (int) position.y());
    }
  }
}
