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

  @Override
  public long solvePart02() {
    List<Coordinate> cycle = loopOrder();
    Stream<Stream<RectangleCandidate>> rectangles =
        IntStream.range(0, cycle.size()).mapToObj(i -> rectangles(i, cycle));

    return rectangles
        .flatMap(s -> s)
        .filter(rect -> isRectangleValid(rect.a, rect.b, cycle))
        .mapToLong(rect -> rect.area)
        .max()
        .orElseThrow();
  }

  record RectangleCandidate(Coordinate a, Coordinate b, long area) {}

  private Stream<Long> areas(int i, List<Coordinate> coordinates) {
    return rectangles(i, coordinates).map(rect -> rect.area);
  }

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

  private boolean isRectangleValid(Coordinate a, Coordinate b, List<Coordinate> cycle) {
    long minX = Math.min(a.x, b.x);
    long maxX = Math.max(a.x, b.x);
    long minY = Math.min(a.y, b.y);
    long maxY = Math.max(a.y, b.y);

    return IntStream.range(0, cycle.size())
        .noneMatch(
            i ->
                edgeIntersectsRectangleInterior(
                    cycle.get(i), cycle.get((i + 1) % cycle.size()), minX, maxX, minY, maxY));
  }

  private boolean edgeIntersectsRectangleInterior(
      Coordinate edgeA,
      Coordinate edgeB,
      long rectMinX,
      long rectMaxX,
      long rectMinY,
      long rectMaxY) {
    long x1 = edgeA.x, y1 = edgeA.y;
    long x2 = edgeB.x, y2 = edgeB.y;

    boolean verticalIntersects =
        x1 == x2
            && x1 > rectMinX
            && x1 < rectMaxX
            && Math.min(y1, y2) < rectMaxY
            && Math.max(y1, y2) > rectMinY;

    boolean horizontalIntersects =
        x1 != x2
            && y1 > rectMinY
            && y1 < rectMaxY
            && Math.min(x1, x2) < rectMaxX
            && Math.max(x1, x2) > rectMinX;

    return verticalIntersects || horizontalIntersects;
  }

  public List<Coordinate> loopOrder() {
    List<Coordinate> coords = this.input.coordinates();

    Map<Coordinate, List<Coordinate>> adjacency = new HashMap<>();
    for (Coordinate c : coords) {
      adjacency.put(c, new ArrayList<>());
    }

    for (int i = 0; i < coords.size(); i++) {
      for (int j = i + 1; j < coords.size(); j++) {
        Coordinate a = coords.get(i);
        Coordinate b = coords.get(j);
        if (a.x == b.x || a.y == b.y) {
          adjacency.get(a).add(b);
          adjacency.get(b).add(a);
        }
      }
    }

    List<Coordinate> ordered = new ArrayList<>();
    Coordinate start = coords.get(0);
    Coordinate current = start;
    Coordinate prev = null;

    do {
      ordered.add(current);
      List<Coordinate> neighbors = adjacency.get(current);
      Coordinate next = neighbors.get(0).equals(prev) ? neighbors.get(1) : neighbors.get(0);
      prev = current;
      current = next;
    } while (!current.equals(start));

    return ordered;
  }
}
