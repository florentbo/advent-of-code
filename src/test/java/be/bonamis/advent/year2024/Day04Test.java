package be.bonamis.advent.year2024;

import static be.bonamis.advent.year2024.Day02.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class Day04Test {

  private static final String INPUT =
      """
              MMMSXXMASM
              MSAMXMSMSA
              AMXSXMAAMM
              MSAMASMSMX
              XMASAMXAMM
              XXAMMXXAMA
              SMSMSASXSS
              SAXAMASAAA
              MAMMMXMMMM
              MXMXAXMASX
              """;
  private static final List<String> LIST = List.of(INPUT.split("\n"));

  @Test
  void solvePart01() {
    assertThat(new Day04(LIST).solvePart01()).isEqualTo(18);
  }

  @Test
  void solvePart02() {
    List<String> split =
        List.of(
            """
                M.S
                .A.
                M.S
                """
                .split("\n"));

    assertThat(new Day04(split).solvePart02()).isEqualTo(1);
  }
}
