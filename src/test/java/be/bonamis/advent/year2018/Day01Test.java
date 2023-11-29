package be.bonamis.advent.year2018;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

  @Test
  void solvePart01() {
    assertThat(new Day01(List.of("-2")).solvePart01()).isEqualTo(-2);
    assertThat(new Day01(List.of("+1", "-2", "+3", "+1")).solvePart01()).isEqualTo(3);
  }

  @Test
  void solvePart02() {
    assertThat(new Day01(List.of("+1", "-2", "+3", "+1")).solvePart02()).isEqualTo(2);
    assertThat(new Day01(List.of("+3", "+3", "+4", "-2", "-4")).solvePart02()).isEqualTo(10);
  }
}
