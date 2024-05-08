package be.bonamis.advent.common;

import be.bonamis.advent.utils.marsrover.Position;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

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

  public Optional<Integer> find(int x, int y) {
    Point point = Point.of(x, y);
    return data.get(point) == null ? Optional.empty() : Optional.of(data.get(point));
  }

  public Point last() {
    return data.keySet().stream().reduce((first, second) -> second).orElse(Point.of(0, 0));
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
