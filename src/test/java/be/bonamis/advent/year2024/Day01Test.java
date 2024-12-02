package be.bonamis.advent.year2024;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

  @Test
  void solvePart01() {
    List<String> input = List.of("3   4", "4   3", "2   5", "1   3", "3   9", "3   3");
    assertThat(new Day01(input).solvePart01()).isEqualTo(11);
  }

  @Test
  void solvePart02() {
    List<String> input = List.of("3   4", "4   3", "2   5", "1   3", "3   9", "3   3");
    assertThat(new Day01(input).solvePart02()).isEqualTo(31);
  }
}
