package be.bonamis.advent.year2023;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Day01Test {

  @Test
  void solvePart01() {
    assertThat(new Day01(List.of("", "", "")).solvePart01()).isEqualTo(3);
  }

  @Test
  void solvePart02() {
    assertThat(new Day01(List.of("", "", "")).solvePart02()).isEqualTo(4);
  }
}
