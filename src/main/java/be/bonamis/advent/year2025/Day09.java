package be.bonamis.advent.year2025;

import static be.bonamis.advent.year2025.Day09.Input.*;

import be.bonamis.advent.TextDaySolver;
import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day09 extends TextDaySolver {

  private final Input input;

  public Day09(InputStream inputStream) {
    super(inputStream);
    this.input = of(this.puzzle);
  }

  public Day09(String input) {
    super(input);
    this.input = of(this.puzzle);
  }

  public record Input(List<Coordinate> coordinates) {
    public record Coordinate(long x, long y) {
      static Coordinate of(String input) {
        String[] split = input.split(",");
        return new Coordinate(Long.parseLong(split[0]), Long.parseLong(split[1]));
      }
    }

    long area(Coordinate a, Coordinate b) {
      return (Math.abs(a.x - b.x) + 1) * (Math.abs(a.y - b.y) + 1);
    }

    public static Input of(List<String> puzzle) {
      List<Coordinate> coordinates = puzzle.stream().map(Coordinate::of).toList();
      return new Input(coordinates);
    }
  }

  @Override
  public long solvePart01() {
    List<Coordinate> coordinates = this.input.coordinates();
    Stream<Stream<Long>> areas =
        IntStream.range(0, coordinates.size()).mapToObj(i -> areas(i, coordinates));

    return areas.flatMap(s -> s).max(Long::compareTo).orElseThrow();
  }

  private Stream<Long> areas(int i, List<Coordinate> coordinates) {
    return LongStream.range(i + 1, coordinates.size())
        .mapToObj(
            j -> {
              Coordinate a = coordinates.get(i);
              Coordinate b = coordinates.get((int) j);
              return this.input.area(a, b);
            });
  }

  @Override
  public long solvePart02() {
    List<Coordinate> cycle = loopOrder();
    Set<Coordinate> validTiles = buildValidTiles(cycle);

    Stream<Stream<RectangleCandidate>> rectangles =
        IntStream.range(0, cycle.size()).mapToObj(i -> rectangles(i, cycle));

    return rectangles
        .flatMap(s -> s)
        .filter(rect -> isRectangleValid(rect.a, rect.b, validTiles))
        .mapToLong(rect -> rect.area)
        .max()
        .orElseThrow();
  }

  record RectangleCandidate(Coordinate a, Coordinate b, long area) {}

  private Stream<RectangleCandidate> rectangles(int i, List<Coordinate> coordinates) {
    return LongStream.range(i + 1, coordinates.size())
        .mapToObj(
            j -> {
              Coordinate a = coordinates.get(i);
              Coordinate b = coordinates.get((int) j);
              long area = this.input.area(a, b);
              return new RectangleCandidate(a, b, area);
            });
  }

  private Set<Coordinate> buildValidTiles(List<Coordinate> cycle) {

    Set<Coordinate> valid = new HashSet<>(cycle);

    for (int i = 0; i < cycle.size(); i++) {
      Coordinate a = cycle.get(i);
      Coordinate b = cycle.get((i + 1) % cycle.size());
      addSegment(valid, a, b);
    }

    addInteriorTiles(valid, cycle);

    return valid;
  }

  private void addSegment(Set<Coordinate> tiles, Coordinate a, Coordinate b) {
    if (a.x == b.x) {
      long minY = Math.min(a.y, b.y);
      long maxY = Math.max(a.y, b.y);
      for (long y = minY; y <= maxY; y++) {
        tiles.add(new Coordinate(a.x, y));
      }
    } else {
      long minX = Math.min(a.x, b.x);
      long maxX = Math.max(a.x, b.x);
      for (long x = minX; x <= maxX; x++) {
        tiles.add(new Coordinate(x, a.y));
      }
    }
  }

  private void addInteriorTiles(Set<Coordinate> tiles, List<Coordinate> cycle) {
    long minX = cycle.stream().mapToLong(Coordinate::x).min().orElseThrow();
    long maxX = cycle.stream().mapToLong(Coordinate::x).max().orElseThrow();
    long minY = cycle.stream().mapToLong(Coordinate::y).min().orElseThrow();
    long maxY = cycle.stream().mapToLong(Coordinate::y).max().orElseThrow();

    for (long x = minX; x <= maxX; x++) {
      for (long y = minY; y <= maxY; y++) {
        Coordinate point = new Coordinate(x, y);
        if (!tiles.contains(point) && isPointInsidePolygon(point, cycle)) {
          tiles.add(point);
        }
      }
    }
  }

  private boolean isPointInsidePolygon(Coordinate point, List<Coordinate> polygon) {
    int intersections = 0;
    int n = polygon.size();

    for (int i = 0; i < n; i++) {
      Coordinate a = polygon.get(i);
      Coordinate b = polygon.get((i + 1) % n);

      if ((a.y > point.y) != (b.y > point.y)) {
        double xIntersection = a.x + (double) (point.y - a.y) / (b.y - a.y) * (b.x - a.x);

        if (point.x < xIntersection) {
          intersections++;
        }
      }
    }

    return (intersections % 2) == 1;
  }

  private boolean isRectangleValid(Coordinate a, Coordinate b, Set<Coordinate> validTiles) {
    long minX = Math.min(a.x, b.x);
    long maxX = Math.max(a.x, b.x);
    long minY = Math.min(a.y, b.y);
    long maxY = Math.max(a.y, b.y);

    for (long x = minX; x <= maxX; x++) {
      for (long y = minY; y <= maxY; y++) {
        if (!validTiles.contains(new Coordinate(x, y))) {
          return false;
        }
      }
    }
    return true;
  }

  public List<Coordinate> loopOrder() {
    List<Coordinate> coords = this.input.coordinates();
    List<Coordinate> ordered = new ArrayList<>();

    Coordinate start = coords.get(0);
    Coordinate current = start;
    Coordinate prev = null;

    do {
      ordered.add(current);
      Coordinate next = findNextNeighbor(coords, current, prev);
      prev = current;
      current = next;
    } while (!current.equals(start));

    return ordered;
  }

  private Coordinate findNextNeighbor(List<Coordinate> coords, Coordinate current, Coordinate prev) {
    for (Coordinate c : coords) {
      if (c.equals(current) || c.equals(prev)) continue;
      if (c.x == current.x || c.y == current.y) {
        return c;
      }
    }
    throw new IllegalStateException("No neighbor found for " + current);
  }
}
