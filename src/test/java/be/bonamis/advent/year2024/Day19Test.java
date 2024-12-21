package be.bonamis.advent.year2024;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.util.stream.Stream;

class Day19Test {

  private String input;

  @BeforeEach
  void setUp() {
    input =
        """
          r, wr, b, g, bwu, rb, gb, br

          brwrr
          bggr
          gbbr
          rrbgbr
          ubwu
          bwurrg
          brgr
          bbrgwb
          """;
  }

  @Test
  void solve() {
    Day19 day19 = new Day19(new ByteArrayInputStream(input.getBytes()));
    assertThat(day19.getInput().patterns()).hasSize(8);
    assertThat(day19.getInput().designs()).hasSize(8);
    assertThat(day19.solvePart01()).isEqualTo(6);
  }

  @ParameterizedTest
  @MethodSource("canBeMade")
  void canBeMade(String design, boolean expected) {
    Day19 day19 = new Day19(new ByteArrayInputStream(input.getBytes()));
    assertThat(day19.canBeMade(design, day19.getInput().patterns())).isEqualTo(expected);
  }

  private static Stream<Arguments> canBeMade() {
    return Stream.of(
        Arguments.of("brwrr", true),
        Arguments.of("bggr", true),
        Arguments.of("gbbr", true),
        Arguments.of("rrbgbr", true),
        Arguments.of("ubwu", false),
        Arguments.of("bwurrg", true),
        Arguments.of("brgr", true),
        Arguments.of("bbrgwb", false));
  }

  @Test
  void solvePart02() {
    Day19 day19 = new Day19(new ByteArrayInputStream(input.getBytes()));
    //assertThat(day19.canBeMadePart02("brwrr", day19.getInput().patterns())).isEqualTo(2);
    //assertThat(day19.canBeMadePart02("bggr", day19.getInput().patterns())).isEqualTo(1);
    // to solve
    assertThat(day19.canBeMadePart0233("gbbr", day19.getInput().patterns())).isEqualTo(4);
  }
}
