package be.bonamis.advent.year2025;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.*;

class Day03Test {

  String sampleInput =
"""
987654321111111
811111111111119
234234234234278
818181911112111
""";

  @Test
  void largest() {
    Day03 day03 = new Day03(sampleInput);
    Day03.Input.Bank first = day03.getInput().banks().get(0);
    assertThat(first.largestJoltage()).isEqualTo(98);
  }

  @Test
  void solvePart01() {
    Day03 day03 = new Day03(sampleInput);
    long solvePart01 = day03.solvePart01();
    assertThat(solvePart01).isEqualTo(357L);
  }

	@Test
	void largestPart02() {
		Day03 day03 = new Day03(sampleInput);
		Day03.Input.Bank first = day03.getInput().banks().get(0);
		assertThat(first.largestJoltagePart2()).isEqualTo(987654321111L);
	}

  @Test
  void solvePart02() {
    Day03 day03 = new Day03(sampleInput);
    long solvePart01 = day03.solvePart02();
    assertThat(solvePart01).isEqualTo(3121910778619L);
  }
}
