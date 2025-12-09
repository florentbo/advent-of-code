package be.bonamis.advent.year2025;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import be.bonamis.advent.year2025.Day09.Input;
import be.bonamis.advent.year2025.Day09.Input.Coordinate;

class Day09Test {

  private static final String INPUT =
      """
			7,1
			11,1
			11,7
			9,7
			9,5
			2,5
			2,3
			7,3
			""";
  private final Day09 day09 = new Day09(INPUT);

  @Test
  void solvePart01() {
    assertThat(day09.solvePart01()).isEqualTo(50);
  }

  @Test
  void area() {
    Coordinate a = new Coordinate(2, 5);
    Coordinate b = new Coordinate(11, 1);
    Input input = new Input(null);
    assertThat(input.area(a, b)).isEqualTo(50);
  }

  @Test
  void solvePart02() {
	  assertThat(day09.solvePart02()).isEqualTo(24);
  }
}
