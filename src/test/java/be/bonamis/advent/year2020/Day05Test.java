package be.bonamis.advent.year2020;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Day05Test {

  @ParameterizedTest
  @CsvSource({
    "F, 0,127,0,63",
    "B, 0,63,32,63",
    "F,32,63,32,47",
    "B,32,47,40,47",
    "B,40,47,44,47",
    "F,44,47,44,45",
    "R,0,7,4,7",
    "L,4,7,4,5",
  })
  void move(String direction, int start, int end, int expectedStart, int expectedEnd) {
    assertThat(Day05.move(direction, Pair.of(start, end)))
        .isEqualTo(Pair.of(expectedStart, expectedEnd));
  }

  @Test
  void line() {
    assertThat(Day05.seatId("FBFBBFFRLR")).isEqualTo(357);
    assertThat(Day05.seatId("FFFBBBFRRR")).isEqualTo(119);
    assertThat(Day05.seatId("BBFFBBFRLL")).isEqualTo(820);
  }

  @Test
  void solvePart01() {
    assertThat(new Day05(List.of("FBFBBFFRLR", "FFFBBBFRRR", "BBFFBBFRLL")).solvePart01())
        .isEqualTo(820);
  }
}
