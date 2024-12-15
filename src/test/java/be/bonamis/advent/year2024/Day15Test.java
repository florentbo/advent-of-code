package be.bonamis.advent.year2024;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.*;
import be.bonamis.advent.utils.marsrover.Rover;
import java.awt.*;
import java.util.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;

@Slf4j
class Day15Test {

  @Test
  void findStart() {
    final String input =
        """
      ########
      #..O.O.#
      ##..O..#
      #.@.O..#
      #.#.O..#
      #...O..#
      #......#
      ########

      <^^>>>vv<v>>v<<
      """;
    Day15 day15 = new Day15(List.of(input.split("\n")));
    Position start = day15.getInput().start();
    assertThat(start).isEqualTo(new Position(2, 3));
  }

  @Test
  void solvePart01() {
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
    Position start = day15.getInput().start();
    assertThat(start).isEqualTo(new Position(2, 2));
    day15.solvePart01();
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
    day15.getInput().grid().printLines2();
    log.debug("------------------");

    assertThat(start).isEqualTo(new Position(2, 0));
    Pair<Rover, CharGrid> boxMoved =
        day15.boxMove(new Rover(EAST, start), day15.getInput().grid());
    assertThat(line(boxMoved)).isEqualTo("#..@OO.#");
  }

  private String line(Pair<Rover, CharGrid> boxMoved) {
    CharGrid grid = boxMoved.getRight();

    var rows2 = grid.row2(0);
    String line2 = grid.toLine2(rows2);
    log.debug("printLine: {}", line2);

    return line2;
  }
}
