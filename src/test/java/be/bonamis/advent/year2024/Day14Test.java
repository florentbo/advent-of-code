package be.bonamis.advent.year2024;

import static be.bonamis.advent.year2024.Day14.*;
import static org.assertj.core.api.Assertions.assertThat;

import be.bonamis.advent.common.CharGrid;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.year2024.Day14.Space.Quadrant;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

class Day14Test {

  @Test
  void parseInput() {
    final String line = "p=0,4 v=3,-3";
    Input input = Input.of(line);
    assertThat(input.position()).isEqualTo(new Position(0, 4));
    assertThat(input.velocity()).isEqualTo(new Input.Velocity(3, -3));
  }

  @Test
  void moveTest() {
    final String line = "p=2,4 v=2,-3";
    Input input = Input.of(line);

    assertThat(input.move(1, 11, 7).position()).isEqualTo(new Position(4, 1));
    assertThat(input.move(2, 11, 7).position()).isEqualTo(new Position(6, 5));
  }

  @Test
  void quadrantsTest() {
    Space space = Space.of(11, 7);
    Set<Quadrant> quadrants = space.quadrants();
    assertThat(quadrants)
        .contains(
            Quadrant.of(new Position(0, 0), new Position(4, 2)),
            Quadrant.of(new Position(6, 0), new Position(10, 2)),
            Quadrant.of(new Position(0, 4), new Position(4, 6)),
            Quadrant.of(new Position(6, 4), new Position(10, 6)));
  }

  @Test
  //@Disabled
  void solve() {

    String input =
        """
            p=0,4 v=3,-3
            p=6,3 v=-1,-3
            p=10,3 v=-1,2
            p=2,0 v=2,-1
            p=0,0 v=1,3
            p=3,0 v=-2,-2
            p=7,6 v=-1,-3
            p=3,0 v=-1,-2
            p=9,3 v=2,3
            p=7,3 v=-1,2
            p=2,4 v=2,-3
            p=9,5 v=-3,-3
            """;
    Day14 day14 = new Day14(List.of(input.split("\n")));
    assertThat(day14.solve(11, 7)).isEqualTo(12);
  }

  @Test
  void createDotsGrid() {
    // 11 tiles wide and 7 tiles tall.
    String input =
        """
                ...........
                ...........
                ...........
                ...........
                ...........
                ...........
                ...........""";
    CharGrid grid = new CharGrid(input);
    grid.printLines();

    List<String> lines = List.of(input.split("\n"));
    String joined = String.join("\n", lines);
    assertThat(joined).isEqualTo(input);
    List<String> dots = new ArrayList<>();
    int width = 7;
    int height = 11;
    for (int i = 0; i < width; i++) {
      dots.add(String.join("", Collections.nCopies(height, ".")));
    }
    assertThat(String.join("\n", dots)).isEqualTo(input);
  }
}
