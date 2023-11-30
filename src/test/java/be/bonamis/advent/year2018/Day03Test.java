package be.bonamis.advent.year2018;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class Day03Test {

  @Test
  void solve() {
    String line1 = "#1 @ 1,3: 4x4";
    String line2 = "#2 @ 3,1: 4x4";
    String line3 = "#3 @ 5,5: 2x2";

    List<String> lines = List.of(line1, line2, line3);
    assertThat(new Day03(lines).solvePart01()).isEqualTo(4);
    assertThat(new Day03(lines).solvePart02()).isEqualTo(3);
  }
}
