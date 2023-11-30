package be.bonamis.advent.year2018;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class Day02Test {

  @Test
  void solvePart01Line() {
    String line = "bababc";
    Day02 day02 = new Day02(List.of());
    Map<String, Long> lettersInventory = day02.lettersInventory(line);

    assertThat(day02.map2(lettersInventory).getValue0()).isEqualTo(1);
    assertThat(day02.map2(lettersInventory).getValue1()).isEqualTo(1);
  }

  @Test
  void solvePart01() {
    List<String> lines =
        List.of("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab");
    long result = new Day02(lines).solvePart01();
    assertThat(result).isEqualTo(12);
  }

  @Test
  void solvePart02() {
    List<String> lines = List.of("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz");

    assertThat(new Day02(lines).solvePart02Bis()).isEqualTo("fgij");
  }
}
