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
  void lefterDotPosition() {
    Day09Input[] array =
        List.of(Day09.Number.of(1), new Dot(), Day09.Number.of(2), new Dot())
            .toArray(new Day09Input[0]);
    assertThat(Day09.lefterDotPosition(array)).isEqualTo(1);
  }

  @Test
  void compactTest() {
    assertThat(String.join("", compact("12345"))).isEqualTo("022111222......");
    assertThat(String.join("", compact("2333133121414131402")))
        .isEqualTo("0099811188827773336446555566..............");
  }
}
