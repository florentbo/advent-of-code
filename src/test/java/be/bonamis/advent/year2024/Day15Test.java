package be.bonamis.advent.year2024;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.*;
import be.bonamis.advent.utils.marsrover.Rover;

import java.awt.*;
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
  @Disabled
  void solvePart01() {
    final String input =
        """
      ##########
      #..O..O.O#
      #......O.#
      #.OO..O.O#
      #..O@..O.#
      #O#..O...#
      #O..O..O.#
      #.OO.O.OO#
      #....O...#
      ##########

      <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
      vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
      ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
      <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
      ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
      ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
      >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
      <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
      ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
      v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
      """;

    Day15 day15 = new Day15(List.of(input.split("\n")));
    Position start = day15.getInput().start();
    assertThat(start).isEqualTo(new Position(4, 4));
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

    Position actual = new Position(2, 0);
    Position next = new Position(3, 0);

    assertThat(start).isEqualTo(actual);
    Pair<Rover, CharGrid> boxMoved =
        day15.boxMove(new Rover(EAST, next), day15.getInput().grid(), new Rover(EAST, actual));
    assertThat(line(boxMoved)).isEqualTo("#..@OO.#");
    assertThat(boxMoved.getLeft().position()).isEqualTo(next);
  }

  @Test
  void boxMoveWithoutSpaceLeft() {
    final String input =
        """
#...@OO#

>
""";
    Day15 day15 = new Day15(List.of(input.split("\n")));
    var start = day15.getInput().start();

    Position actual = new Position(4, 0);
    Position next = new Position(4, 0);

    assertThat(start).isEqualTo(actual);
    Pair<Rover, CharGrid> boxMoved =
        day15.boxMove(new Rover(EAST, next), day15.getInput().grid(), new Rover(EAST, actual));
    assertThat(line(boxMoved)).isEqualTo("#...@OO#");
    assertThat(boxMoved.getLeft().position()).isEqualTo(next);
  }

  @Test
  void spaceMove() {
    final String input =
        """
#@.O.O.#

>
""";
    Day15 day15 = new Day15(List.of(input.split("\n")));
    var start = day15.getInput().start();

    Position actual = new Position(1, 0);
    Position next = new Position(2, 0);
    assertThat(start).isEqualTo(actual);
    Pair<Rover, CharGrid> boxMoved =
        day15.spaceMove(new Rover(EAST, next), day15.getInput().grid(), new Rover(EAST, start));
    assertThat(line(boxMoved)).isEqualTo("#.@O.O.#");
    assertThat(boxMoved.getLeft().position()).isEqualTo(new Position(2, 0));
  }

  private String line(Pair<Rover, CharGrid> boxMoved) {
    CharGrid grid = boxMoved.getRight();

    var rows2 = grid.row2(0);
    String line2 = grid.toLine2(rows2);
    log.debug("printLine: {}", line2);

    return line2;
  }

  @Test
  void solvePart01Small() {

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
  void run01() {
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

          <
          """;

    Day15 day15 = new Day15(List.of(input.split("\n")));
    Position start = day15.getInput().start();
    assertThat(start).isEqualTo(new Position(2, 2));
    day15.run();
    CharGrid grid = day15.getInput().grid();
    Point startAfterRun = Day15.findStart(grid);
    assertThat(startAfterRun).isEqualTo(new Point(2, 2));
  }

  @Test
  void run02() {
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

          <^
          """;

    Day15 day15 = new Day15(List.of(input.split("\n")));
    Position start = day15.getInput().start();
    assertThat(start).isEqualTo(new Position(2, 2));
    day15.run();
    CharGrid grid = day15.getInput().grid();
    Point startAfterRun = Day15.findStart(grid);
    assertThat(startAfterRun).isEqualTo(new Point(2, 1));
  }

  @Test
  void run03() {
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

          <^^
          """;

    Day15 day15 = new Day15(List.of(input.split("\n")));
    Position start = day15.getInput().start();
    assertThat(start).isEqualTo(new Position(2, 2));
    day15.run();
    CharGrid grid = day15.getInput().grid();
    Point startAfterRun = Day15.findStart(grid);
    assertThat(startAfterRun).isEqualTo(new Point(2, 1));
  }
}
