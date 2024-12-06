package be.bonamis.advent.year2024;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day05Test {

  private static final String INPUT =
      """
      47|53
      97|13
      97|61
      97|47
      75|29
      61|13
      75|53
      29|13
      97|29
      53|29
      61|53
      97|53
      61|29
      47|13
      75|47
      97|75
      47|61
      75|61
      47|29
      75|13
      53|13

      75,47,61,53,29
      97,61,53,29,13
      75,29,13
      75,97,47,61,53
      61,13,29
      97,13,75,29,47
      """;
  private static final List<String> LIST = List.of(INPUT.split("\n"));

  @Test
  void solvePart01() {
    assertThat(new Day05(LIST).solvePart01()).isEqualTo(143);
  }

  @Test
  void solvePart02() {
    assertThat(new Day05(LIST).solvePart02()).isEqualTo(123);
  }
}
