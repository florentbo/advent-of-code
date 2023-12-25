package be.bonamis.advent.year2023;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import be.bonamis.advent.year2023.Day24.*;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

@Slf4j
class Day24Test {

  private Day24 day;

  @BeforeEach
  void setUp() {
    String sample =
        """
                      19, 13, 30 @ -2,  1, -2
                      18, 19, 22 @ -1, -1, -2
                      20, 25, 34 @ -2, -2, -4
                      12, 31, 28 @ -1, -2, -1
                      20, 19, 15 @  1, -5, -3

                      """;
    day = new Day24(Arrays.asList(sample.split("\n")));
  }

  @Test
  void parse() {
    Hailstone hailstone = Hailstone.from("20, 19, 15 @ 1, -5, -3");
    assertThat(hailstone).isEqualTo(new Hailstone(Point.of(20, 19), Day24.LineSlope.of(1, -5, -3)));

    day.solvePart01();
    /*
        Hailstone A: 19, 13, 30 @ -2, 1, -2
    Hailstone B: 18, 19, 22 @ -1, -1, -2
    Hailstones' paths will cross inside the test area (at x=14.333, y=15.333).
    21, 14, 12
         */
  }

  @Test
  void toEquation() {
    Hailstone hailstone = Hailstone.from("20, 19, 15 @ 1, -5, -3");
    LinearEquation linearEquation = hailstone.toEquation();
    assertThat(linearEquation).isEqualTo(new LinearEquation(-5, -1, 119));
  }

  @Test
  void cross() {
    /*
    Hailstone A: 19, 13, 30 @ -2, 1, -2
    Hailstone B: 18, 19, 22 @ -1, -1, -2
     */
    Hailstone hailstoneA = Hailstone.from("19, 13, 30 @ -2, 1, -2");
    Hailstone hailstoneB = Hailstone.from("18, 19, 22 @ -1, -1, -2");
    Point cross = hailstoneA.cross(hailstoneB);
    assertThat(cross.x()).isCloseTo(14.333, offset(0.001));
    assertThat(cross.y()).isCloseTo(15.333, offset(0.001));
  }

  @Test@Disabled("not solved yet")
  void solvePart01() {

    assertThat(day.solvePart01()).isEqualTo(54);
  }
}
