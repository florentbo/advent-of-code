package be.bonamis.advent.year2023;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day03Test {

  private List<Day03.Engine> engines(List<Point> line, CharGrid grid) {
    List<Day03.Engine> engines = new ArrayList<>();
    List<Point> currentSeries = new ArrayList<>();
    for (Point point : line) {
      Character character = grid.get(point);
      if (Character.isDigit(character)) {
        currentSeries.add(point);
      } else {
        if (!currentSeries.isEmpty()) {
          List<Point> points = new ArrayList<>(currentSeries);
          engines.add(new Day03.Engine(points));
          currentSeries.clear();
        }
      }
    }
    if (!currentSeries.isEmpty()) {
      List<Point> points = new ArrayList<>(currentSeries);
      engines.add(new Day03.Engine(points));
    }
    return engines;
  }

  @Test
  void isSymbol() {
    Day03 day03 = new Day03(List.of(""));
    assertThat(day03.isSymbol('*')).isTrue();
    assertThat(day03.isSymbol('7')).isFalse();
    assertThat(day03.isSymbol('.')).isFalse();
  }

  @Test
  void solvePart01() {
    String content = FileHelper.content("2023/03/2023_03_01_code.txt");
    List<String> puzzle = Arrays.asList(content.split("\n"));
    CharGrid grid = new CharGrid(puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
    grid.printArray();
    System.out.println(grid.get(new Point(0, 0)));

    List<Day03.Engine> engines = new ArrayList<>();
    List<List<Point>> lines = grid.lines();
    for (List<Point> line : lines) {}

    List<Point> line = lines.get(0);
    List<Day03.Engine> engines1 = engines(line, grid);
    log.debug("engines {}", engines1);

    Day03.Engine engine = engines1.get(0);
    log.debug("engine {}", engine);

   /* List<Point> enginePoints = engine.points();
    enginePoints.stream()
        .flatMap(point -> grid.neighbours(point).stream())
        .filter(o -> !enginePoints.contains(o))
        .forEach(point -> log.debug("Pont {} value {} ", point, grid.get(point)));*/

    assertThat(engine.isPartNumber(grid)).isTrue();


    /*line.forEach(
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
