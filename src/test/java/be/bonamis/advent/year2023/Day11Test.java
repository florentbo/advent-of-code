package be.bonamis.advent.year2023;

import java.awt.*;
import java.util.*;
import java.util.List;

import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day11Test {

  private final Day11 day11 =
      new Day11(
          Arrays.asList(
              """
...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....
"""
                  .split("\n")));

  @Test
  void solvePart01() {
    assertThat(day11.solvePart01()).isEqualTo(374);
  }

  @Test
  void movedIsOK() {
    Day11 movedActual =
        new Day11(
            Arrays.asList(
                """
                            ....1........
                            .........2...
                            3............
                            .............
                            .............
                            ........4....
                            .5...........
                            ............6
                            .............
                            .............
                            .........7...
                            8....9.......
                            """
                    .split("\n")));
    List<Point> notDots = movedActual.notDots();
    movedActual.printPoints(notDots);

    List<Point> movedPoints = day11.movedPoints();
    assertThat(movedPoints).containsAll(notDots);

    Point point01 = movedActual.getGrid().find(1);
    log.debug("point {}", point01);
  }

  @Test
  void distance() {
    List<Point> movedPoints = day11.movedPoints();
    assertThat(movedPoints).hasSize(9);

    Point point5 = movedPoints.get(2);
    log.debug("point 5 {}", point5);
    Point point9 = movedPoints.get(4);
    log.debug("point 9 {}", point9);
    assertThat(movedPoints).contains(point5, point9);
    assertThat(day11.distance(point5, point9)).isEqualTo(9);

    Point point1 = movedPoints.get(3);
    log.debug("point 1 {}", point1);
    Point point7 = movedPoints.get(7);
    log.debug("point 7 {}", point7);
    assertThat(day11.distance(point1, point7)).isEqualTo(15);
  }
}
