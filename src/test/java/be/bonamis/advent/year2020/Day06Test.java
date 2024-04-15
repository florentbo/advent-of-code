package be.bonamis.advent.year2020;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class Day06Test {

  static final String INPUT =
      """
      abc

      a
      b
      c

      ab
      ac
      """;

  @Test
  void parseGroups() {
    List<String> lines = Arrays.asList(INPUT.split("\n"));
    assertThat(Day06.groups(lines)).isEqualTo(GROUPS);
  }

  private static final Day06.Groups GROUPS =
      Day06.Groups.of(
          List.of(
              Day06.Group.of(List.of("abc")),
              Day06.Group.of(List.of("a", "b", "c")),
              Day06.Group.of(List.of("ab", "ac"))));

  @Test
  void countAnswers() {
    Day06.Group group = Day06.Group.of(List.of("ab", "ac"));
    assertThat(group.countAnswers()).isEqualTo(3);
  }

  @Test
  void sumCounts() {
    assertThat(GROUPS.sumCounts()).isEqualTo(9);
  }
}
