package be.bonamis.advent.year2023;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Day03 extends DaySolver<String> {

  private final CharGrid grid;
  private final List<Engine> engines;

  public Day03(List<String> puzzle) {
    super(puzzle);
    grid = new CharGrid(this.puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
    engines = grid.lines().stream().flatMap(this::engines).toList();
  }

  static boolean isSymbol(Character character) {
    boolean isNumberOrDot = Character.isDigit(character) || character == '.';
    return !isNumberOrDot;
  }

  static boolean isGearSymbol(Character character) {
    return character == '*';
  }

  record Engine(List<Point> points) {
    boolean isPartNumber(CharGrid grid) {
      return this.points.stream()
          .flatMap(point -> grid.neighbours(point).stream())
          .filter(o -> !this.points.contains(o))
          .anyMatch(o -> isSymbol(grid.get(o)));
    }

    List<Point> gearPoints(CharGrid grid) {
      return this.points.stream()
          .flatMap(point -> grid.neighbours(point).stream())
          .filter(o -> !this.points.contains(o))
          .filter(o -> isGearSymbol(grid.get(o)))
          .toList();
    }

    public long number(CharGrid grid) {
      return this.points.stream()
          .map(grid::get)
          .mapToInt(Character::getNumericValue)
          .reduce(0, (acc, digit) -> acc * 10 + digit);
    }
  }

  @Override
  public long solvePart01() {
    return engines.stream()
        .filter(engine -> engine.isPartNumber(grid))
        .map(engine -> engine.number(grid))
        .reduce(0L, Long::sum);
  }

  @Override
  public long solvePart02() {
    Multimap<Point, Engine> map = ArrayListMultimap.create();
    for (Engine engine : engines) {
      List<Point> points = engine.gearPoints(grid);
      points.forEach(point -> map.put(point, engine));
    }

    return map.asMap().values().stream()
        .map(
            engineCollection ->
                engineCollection.stream()
                    .map(engine -> engine.number(grid))
                    .collect(Collectors.toSet()))
        .filter(set -> set.size() == 2)
        .map(set -> set.stream().reduce(1L, (a, b) -> a * b))
        .reduce(0L, Long::sum);
  }

  private Stream<Engine> engines(List<Point> line) {
    List<Engine> engines = new ArrayList<>();
    List<Point> currentSeries = new ArrayList<>();
    for (Point point : line) {
      Character character = this.grid.get(point);
      if (Character.isDigit(character)) {
        currentSeries.add(point);
      } else {
        if (!currentSeries.isEmpty()) {
          List<Point> points = new ArrayList<>(currentSeries);
          engines.add(new Engine(points));
          currentSeries.clear();
        }
      }
    }
    if (!currentSeries.isEmpty()) {
      List<Point> points = new ArrayList<>(currentSeries);
      engines.add(new Engine(points));
    }
    return engines.stream();
  }

  public static void main(String[] args) {
    String content = FileHelper.content("2023/03/2023_03_input.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    Day03 day = new Day03(puzzle);
    log.info("solution part 1: {}", day.solvePart01());
    log.info("solution part 2: {}", day.solvePart02());
  }
}
