package be.bonamis.advent.year2024;

import java.util.List;

import static be.bonamis.advent.year2024.Day11.blink;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;

class Day11Test {

  @Test
  void solvePart01() {
    final String INPUT =
        """
                125 17
                """;
    final List<String> LIST = List.of(INPUT.split("\n"));
    assertThat(new Day11(LIST).solvePart01()).isEqualTo(55312);
  }

  @Test
  void blinkTest() {
    assertThat(blink(List.of(0L, 1L, 10L, 99L, 999L)))
        .isEqualTo(List.of(1L, 2024L, 1L, 0L, 9L, 9L, 2021976L));

    assertThat(blink(List.of(125L, 17L))).isEqualTo(List.of(253000L, 1L, 7L));
  }


  @Test
  void blink_2_times_Test() {
    assertThat(blink(List.of(125L, 17L), 2))
        // 253 0 2024 14168
        .isEqualTo(List.of(253L, 0L, 2024L, 14168L));
  }

  @Test
  void blink_3_times_Test() {
    assertThat(blink(List.of(125L, 17L), 3))
        // 512072 1 20 24 28676032
        .isEqualTo(List.of(512072L, 1L, 20L, 24L, 28676032L));
  }
}
