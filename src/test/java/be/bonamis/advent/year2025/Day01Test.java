package be.bonamis.advent.year2025;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day01Test {

  String sampleInput =
"""
L68
L30
R48
L5
R60
L55
L1
L99
R14
L82
""";

  @Test
  void solvePart01() {
    Day01 day01 = new Day01(sampleInput);
    long solvePart01 = day01.solvePart01();
    assertThat(solvePart01).isEqualTo(3);
  }

  @Test
  void solvePart02() {
    Day01 day01 = new Day01(sampleInput);
    long solvePart01 = day01.solvePart02();
    assertThat(solvePart01).isEqualTo(6);
  }
}
