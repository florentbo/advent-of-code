package be.bonamis.advent.year2024;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.NORTH;
import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.year2024.Day14.Space.Quadrant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
class Day15Test {

/*
  final String input =
      """
########
#..O.O.#
##@.O..#
#...O..#
#.#.O..#
#...O..#
#......#
########

<^^>>>vv<v>>v<<
""";
  Day15 day15 = new Day15(List.of(input.split("\n")));
*/

  @Test
  void solve() {
//    day15.solvePart01();
  }

  @Test
  void boxMove() {
    final String input =
        """
#.@O.O.#

>
""";
    Day15 day15 = new Day15(List.of(input.split("\n")));
    var start = day15.getInput().start();

    log.debug("boxMove test grid");
    day15.getInput().grid().printLines();
    log.debug("------------------");


    assertThat(start).isEqualTo(new Position(2, 0));
    Pair<Rover, CharGrid> boxMoved =
        day15.boxMove(new Rover(NORTH, start), day15.getInput().grid());
    assertThat(line(boxMoved)).isEqualTo("#..@OO.#");
  }

  private String line(Pair<Rover, CharGrid> boxMoved) {
    boxMoved.getRight().printLines();
    List<Point> points = boxMoved.getRight().rows().get(0);
    Stream<Character> objectStream = points.stream().map(point -> boxMoved.getRight().get(point));
    return objectStream.map(String::valueOf).collect(Collectors.joining());
  }
}
