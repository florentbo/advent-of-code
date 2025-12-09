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

  record Input(List<Coordinate> coordinates) {
    record Coordinate(long x, long y) {
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
    return 10001;
  }
}
