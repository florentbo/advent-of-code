package be.bonamis.advent.year2024;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day03Test {

  @Test
  void solvePart01() {
    List<String> input =
        List.of("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))");
    assertThat(new Day03(input).solvePart01()).isEqualTo(161);
  }

  @Test
  void solvePart02() {
    List<String> input =
        List.of("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))");
    assertThat(new Day03(input).solvePart02()).isEqualTo(48);
  }
}
