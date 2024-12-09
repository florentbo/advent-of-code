package be.bonamis.advent.year2024;

import static be.bonamis.advent.year2024.Day09.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day09Test {
  private static final String INPUT =
      """
            2333133121414131402
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

  @Test
  void righterNumberPosition() {
    assertThat(Day09.righterNumberPosition("12345678")).isEqualTo(7);
    assertThat(Day09.righterNumberPosition("12345678.")).isEqualTo(7);
  }

  @Test
  void lefterDotPosition() {
    assertThat(Day09.lefterDotPosition("12345678.")).isEqualTo(8);
    assertThat(Day09.lefterDotPosition("1234.67.8")).isEqualTo(4);
  }

  @Test
  void compactTest() {
    assertThat(String.join("", compact("12345"))).isEqualTo("022111222......");
    assertThat(String.join("", compact("2333133121414131402")))
        .isEqualTo("0099811188827773336446555566..............");
  }
}
