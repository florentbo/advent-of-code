package be.bonamis.advent.year2020;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.alg.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    assertThat(move(direction, Pair.of(start, end))).isEqualTo(Pair.of(expectedStart, expectedEnd));
  }

  @Test
  void line() {
    String line = "FBFBBFFRLR";
    String[] letters = line.split("");
    Pair<Integer, Integer> moved;
    for (int i = 0; i < letters.length - 3; i++) {
      log.info("char: i {} val {}", i, letters[i]);
      moved = move(letters[i], Pair.of(0, 127));
      log.info("row: {}", moved.getSecond());
    }
  }

  public static Pair<Integer, Integer> move(String direction, Pair<Integer, Integer> rows) {
    int i = rows.getSecond() - rows.getFirst();
    if ("F".equals(direction) || "L".equals(direction)) {
      return Pair.of(rows.getFirst(), i / 2 + rows.getFirst());
    } else {
      return Pair.of(roundUp(i, 2) + rows.getFirst(), rows.getSecond());
    }
  }

  public static int roundUp(int num, int divisor) {
    return (num + divisor - 1) / divisor;
  }
}
