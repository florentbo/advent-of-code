package be.bonamis.advent.year2025;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Day02Test {

  String sampleInput =
"""
11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
""";

  @Test
  void solvePart01() {
    Day02 day02 = new Day02(sampleInput);
    long solvePart01 = day02.solvePart01();
    assertThat(solvePart01).isEqualTo(1227775554);
  }

  @Test
  void solvePart02() {
    Day02 day02 = new Day02(sampleInput);
    assertThat(day02.solvePart02()).isEqualTo(4174379265L);
  }

  @Test
  void invalids() {
    Day02.Input.Range range = new Day02.Input.Range(11, 22);
    assertThat(range.invalids()).containsExactly(11L, 22L);
  }

  @Test
  void seqInvalids() {
    Day02.Input.Range range = new Day02.Input.Range(824824821, 824824827);
    assertThat(range.invalids()).isEmpty();
    assertThat(range.seqInvalids()).containsExactly(824824824L);
  }

  @Test
  void parse() {
    Day02 day02 = new Day02(sampleInput);
    Day02.Input input = day02.getInput();
    assertEquals(11, input.ranges().size());
  }
}
