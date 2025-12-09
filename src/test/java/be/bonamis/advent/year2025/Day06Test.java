package be.bonamis.advent.year2025;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day06Test {

  @Test
  void solvePart01() {
    /*
    	  123 328  51 64
     45 64  387 23
      6 98  215 314
    *   +   *   +

    	   */

    Day06 day06 =
        new Day06(
            """
		123 328   51 64
		45 64 387 23
		6 98 215 314
		* + * +
		""");
    assertThat(day06.solvePart01()).isEqualTo(4277556);
  }

  @Test
  void solvePart02() {
    String input =
"""
123 328  51 640
 45 64  387 230
  6 98  215 314
*   +   *   +
""";
    Day06 day06 = new Day06(input);
    assertThat(day06.solvePart02()).isEqualTo(3263827);
  }
}
