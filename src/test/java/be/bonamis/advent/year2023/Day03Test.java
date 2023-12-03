package be.bonamis.advent.year2023;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import be.bonamis.advent.year2023.Day03.Engine;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day03Test {

  private List<Engine> engines(List<Point> line, CharGrid grid) {
    List<Engine> engines = new ArrayList<>();
    List<Point> currentSeries = new ArrayList<>();
    for (Point point : line) {
      Character character = grid.get(point);
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
    return engines;
  }

  @Test
  void isSymbol() {
    assertThat(Day03.isSymbol('*')).isTrue();
    assertThat(Day03.isSymbol('7')).isFalse();
    assertThat(Day03.isSymbol('.')).isFalse();
  }

  @Test
  void sum() {
    String content = FileHelper.content("2023/03/2023_03_01_code.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    CharGrid grid = new CharGrid(puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
    List<Engine> engines = new ArrayList<>();
    List<List<Point>> lines = grid.lines();
    for (List<Point> line : lines) {
      List<Engine> engine = engines(line, grid);
      engines.addAll(engine);
    }

    Long sum =
        engines.stream()
            .filter(engine -> engine.isPartNumber(grid))
            .map(engine -> engine.number(grid))
            .reduce(0L, Long::sum);
    assertThat(sum).isEqualTo(4361L);
  }

  @Test
  void sum2() {
    String content = FileHelper.content("2023/03/2023_03_01_code.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    CharGrid grid = new CharGrid(puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
    List<Engine> engines = new ArrayList<>();
    List<List<Point>> lines = grid.lines();
    for (List<Point> line : lines) {
      List<Engine> engine = engines(line, grid);
      engines.addAll(engine);
    }

    Multimap<Point, Engine> map = ArrayListMultimap.create();
    for (Engine engine : engines) {
      List<Point> points = engine.gearPoints(grid);
      points.forEach(point -> map.put(point, engine));
    }

    Long sum =
        map.asMap().entrySet().stream()
            .map(
                entry -> {
                  Set<Long> collect =
                      entry.getValue().stream()
                          .map(engine -> engine.number(grid))
                          .collect(Collectors.toSet());
                  if (collect.size() == 2) {
                    return collect.stream().reduce(1L, (a, b) -> a * b);
                  } else {
                    return 0L;
                  }
                  // log.debug("entry {} {}", entry.getKey(), collect);
                })
            .reduce(0L, Long::sum);

    assertThat(sum).isEqualTo(467835L);
    ;

    /*Long sum =
        engines.stream()
            .filter(engine -> engine.isPartNumber(grid))
            .map(engine -> engine.number(grid))
            .reduce(0L, Long::sum);
    assertThat(sum).isEqualTo(4361L);*/
  }

  @Test
  void solveLine() {
    String content = FileHelper.content("2023/03/2023_03_01_code.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    CharGrid grid = new CharGrid(puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
    // grid.printArray();
    // System.out.println(grid.get(new Point(0, 0)));

    List<Engine> engines = new ArrayList<>();
    List<List<Point>> lines = grid.lines();
    for (List<Point> line : lines) {}

    List<Point> line0 = lines.get(0);
    List<Point> line2 = lines.get(2);
    List<Engine> enginesLine0 = engines(line0, grid);
    List<Engine> enginesLine2 = engines(line2, grid);
    // log.debug("engines {}", enginesLine0);

    Multimap<Point, Engine> map = ArrayListMultimap.create();

    Engine engine1Line0 = enginesLine0.get(0);
    List<Point> gearPoints = engine1Line0.gearPoints(grid);
    gearPoints.forEach(point -> map.put(point, engine1Line0));

    // log.debug("engine1Line0 {}", engine1Line0);

    assertThat(engine1Line0.isPartNumber(grid)).isTrue();

    log.debug("gearPoints {}", gearPoints);

    /*line0.forEach(
    new Consumer<Point>() {
      @Override
      public void accept(Point point) {
        log.debug(
            "Pont {} value {} neighbours {}", point, grid.get(point), grid.neighbours(point));
      }
    });*/
    /*grid.consume(new Consumer<Point>() {
      @Override
      public void accept(Point point) {
        log.debug("Pont {} value {} neighbours {}", point, grid.get(point), grid.neighbours(point));
      }
    });*/

    assertThat(new Day03(List.of("")).solvePart01()).isEqualTo(1);
  }

  @Test
  void solvePart02() {
    assertThat(new Day03(List.of("")).solvePart02()).isEqualTo(2);
  }
}
