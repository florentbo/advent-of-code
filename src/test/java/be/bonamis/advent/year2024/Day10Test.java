package be.bonamis.advent.year2024;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Day10Test {

  @Test
  void solvePart01() {
    final String INPUT =
        """
                        0123
                        1234
                        8765
                        9876
                        """;
    final List<String> LIST = List.of(INPUT.split("\n"));
    assertThat(new Day10(LIST).solvePart01()).isEqualTo(1);
  }

  @Test
  void solvePart01WithoutEnd() {
    final String INPUT =
        """
                        0123
                        1234
                        4765
                        9576
                        """;
    final List<String> LIST = List.of(INPUT.split("\n"));
    assertThat(new Day10(LIST).solvePart01()).isEqualTo(0);
  }

  /*
    89010123
  78121874
  87430965
  96549874
  45678903
  32019012
  01329801
  10456732
     */

  @Test
  void solvePart01Sample() {
    final String INPUT =
        """
                        89010123
                        78121874
                        87430965
                        96549874
                        45678903
                        32019012
                        01329801
                        10456732
                        """;
    final List<String> LIST = List.of(INPUT.split("\n"));
    assertThat(new Day10(LIST).solvePart01()).isEqualTo(36);
  }

  @Test@Disabled
  void solvePart02() {
    final String INPUT =
        """
                            89010123
                            78121874
                            87430965
                            96549874
                            45678903
                            32019012
                            01329801
                            10456732
                            """;
    final List<String> LIST = List.of(INPUT.split("\n"));
    assertThat(new Day10(LIST).solvePart02()).isEqualTo(81);
  }

  @Test
  void solvePart02Sample() {
    final String INPUT =
        """
                        .....0.
                        ..4321.
                        ..5..2.
                        ..6543.
                        ..7..4.
                        ..8765.
                        ..9....
                        """;
    final List<String> LIST = List.of(INPUT.split("\n"));
    assertThat(new Day10(LIST).solvePart02()).isEqualTo(3);
  }
}
