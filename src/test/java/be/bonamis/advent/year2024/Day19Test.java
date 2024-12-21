package be.bonamis.advent.year2024;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;

class Day19Test {

  @Test
  void solve() {
    final String input =
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
    Day19 day19 = new Day19(new ByteArrayInputStream(input.getBytes()));
    assertThat(day19.getInput().patterns()).hasSize(8);
    assertThat(day19.getInput().designs()).hasSize(8);

    assertThat(day19.canBeMade("brwrr", day19.getInput().patterns())).isTrue();
    assertThat(day19.canBeMade("bggr", day19.getInput().patterns())).isTrue();
    assertThat(day19.canBeMade("gbbr", day19.getInput().patterns())).isTrue();
    assertThat(day19.canBeMade("rrbgbr", day19.getInput().patterns())).isTrue();
    assertThat(day19.canBeMade("ubwu", day19.getInput().patterns())).isFalse();

    assertThat(day19.canBeMade("bwurrg", day19.getInput().patterns())).isTrue();

    assertThat(day19.canBeMade("brgr", day19.getInput().patterns())).isTrue();
    assertThat(day19.canBeMade("bbrgwb", day19.getInput().patterns())).isFalse();

    assertThat(day19.solvePart01()).isEqualTo(6);
  }

  @Test
  void couldBeMadePatterns() {
    final String input =
        """
              rrgbgg, gbgbgr, rrb

              rugbgbwwbbgrwrbubgugrgbrrbgwrbbgbwurwgrbr
              """;

    Day19 day19 = new Day19(new ByteArrayInputStream(input.getBytes()));
    Day19.Input day19Input = day19.getInput();
    assertThat(day19.couldBeMadePatterns(day19Input.designs().get(0), day19Input.patterns()))
        .containsExactly("rrb");
  }
}
