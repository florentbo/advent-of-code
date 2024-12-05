package be.bonamis.advent.year2024;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static be.bonamis.advent.year2024.Day02.isSafe;
import static org.assertj.core.api.Assertions.assertThat;

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


    @ParameterizedTest
    @MethodSource("isSafeTestCases")
    void safeTest(String input, boolean expected) {
        assertThat(isSafe(input)).isEqualTo(expected);
    }

    private static Stream<Arguments> isSafeTestCases() {
        return Stream.of(
                Arguments.of("7 6 4 2 1", true),
                Arguments.of("1 2 7 8 9", false),
                Arguments.of("9 7 6 2 1", false),
                Arguments.of("1 3 2 4 5", false),
                Arguments.of("8 6 4 4 1", false),
                Arguments.of("1 3 6 7 9", true));
    }
}
