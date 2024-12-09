package be.bonamis.advent.year2024;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test {
  private static final String INPUT =
      """
            12345
            """;
  private static final List<String> LIST = List.of(INPUT.split("\n"));

  @Test
  void solvePart01() {
    assertThat(new Day09(LIST).solvePart01()).isEqualTo(1928);
  }

  @Test
  void solvePart02() {
    assertThat(new Day09(LIST).solvePart02()).isEqualTo(4);
  }
}
