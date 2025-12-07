package be.bonamis.advent.year2025;

import static java.util.List.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import be.bonamis.advent.year2025.Day05.Input.FreshIngredientsRange;
import be.bonamis.advent.year2025.Day05.Input.FreshIngredientsRanges;

class Day05Test {

  private static final String INPUT =
      """
			3-5
			10-14
			16-20
			12-18

			1
			5
			8
			11
			17
			32
			""";
  private final Day05 day05 = new Day05(INPUT);

  @Test
  void solvePart01() {
    assertThat(day05.solvePart01()).isEqualTo(3);
  }

  @Test
  void isFresh() {
    String input =
        """
				3-5

				1
				""";
    Day05 day05 = new Day05(input);
    Day05.Input day05Input = day05.getInput();
    assertThat(day05Input.ranges())
        .isEqualTo(new FreshIngredientsRanges(of(new FreshIngredientsRange(3, 5))));
    assertThat(day05Input.isFresh(1L)).isFalse();
    assertThat(day05Input.isFresh(3L)).isTrue();
  }

  @Test
  void solvePart02() {
    assertThat(day05.solvePart02()).isEqualTo(14);
  }
}
