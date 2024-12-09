package be.bonamis.advent.year2024;

import be.bonamis.advent.TextDaySolver;

import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import be.bonamis.advent.common.CharGrid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.paukov.combinatorics.*;

import static org.paukov.combinatorics.CombinatoricsFactory.*;

@Slf4j
public class Day08 extends TextDaySolver {

  private final CharGrid grid;
  @Getter private final Map<Character, List<Point>> chars;

  public Day08(InputStream inputStream) {
    super(inputStream);
    this.grid = CharGrid.of(puzzle);
    this.chars =
        grid.stream().filter(p -> grid.get(p) != '.').collect(Collectors.groupingBy(grid::get));
  }

  public Day08(List<String> puzzle) {
    super(puzzle);
    this.grid = CharGrid.of(puzzle);
    this.chars =
        grid.stream().filter(p -> grid.get(p) != '.').collect(Collectors.groupingBy(grid::get));
  }

  @Override
  public long solvePart01() {
    List<Pair<Point, Point>> pairs = pairs();

    return opposites(pairs, grid, chars).size();
  }

  List<Pair<Point, Point>> pairs() {
    log.debug("chars: {}", chars);
    List<Pair<Point, Point>> pairs = chars.entrySet().stream().flatMap(combinations()).toList();
    log.debug("pairs: {}", pairs);
    return pairs;
  }

  private Function<Map.Entry<Character, List<Point>>, Stream<Pair<Point, Point>>> combinations() {
    return e -> pairCombinations(e.getValue()).stream();
  }

  static Point opposite(Pair<Point, Point> pair) {
    Point left = pair.getLeft();
    Point right = pair.getRight();
    var xDelta = right.x - left.x;
    var yDelta = right.y - left.y;
    Point point = new Point(left.x - xDelta, left.y - yDelta);
    log.debug("opposite point: {} from pair {}", point, pair);
    return point;
  }

  static Set<Point> opposites(
      List<Pair<Point, Point>> pairs, CharGrid grid, Map<Character, List<Point>> chars) {
    return pairs.stream()
        .map(Day08::opposite)
        .filter(p -> isInTheGrid(p, grid))
        .collect(Collectors.toSet());
  }

  private static boolean isInTheGrid(Point p, CharGrid grid) {
    return grid.isInTheGrid().test(p);
  }

  static <T> List<Pair<T, T>> pairCombinations(List<T> list) {
    ICombinatoricsVector<T> vector = createVector(list);
    Generator<T> gen = createPermutationWithRepetitionGenerator(vector, 2);
    return gen.generateAllObjects().stream()
        .map(ICombinatoricsVector::getVector)
        .map(l -> Pair.of(l.get(0), l.get(1)))
        .filter(p -> !p.getLeft().equals(p.getRight()))
        .toList();
  }

  CharGrid getGrid() {
    return grid;
  }

  @Override
  public long solvePart02() {
    return 1;
  }
}
