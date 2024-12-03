package be.bonamis.advent.year2015;

import be.bonamis.advent.year2015.Day07.Instruction;
import be.bonamis.advent.year2015.Day07.Instruction.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day07Test {

  @Test
  void runInstructions() {
    String input =
        """
        123 -> x
        456 -> y
        x AND y -> d
        x OR y -> e
        x LSHIFT 2 -> f
        y RSHIFT 2 -> g
        NOT x -> h
        NOT y -> i
        """;

    /* String map =
        """
    d: 72
    e: 507
    f: 492
    g: 114
    h: 65412
    i: 65079
    x: 123
    y: 456
    """;*/
    Map<String, Integer> expected =
        Map.of("d", 72, "e", 507, "f", 492, "g", 114, "h", 65412, "i", 65079, "x", 123, "y", 456);

    assertThat(new Day07(Arrays.asList(input.split("\n"))).runInstructions()).isEqualTo(expected);
  }

  @Test
  void solvePart02() {
    List<String> input =
        List.of("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))");
    assertThat(new Day07(input).solvePart02()).isEqualTo(2024);
  }

  @ParameterizedTest
  @MethodSource("isSafeTestCases")
  void instructionFrom(String input, Instruction expected) {
    assertThat(Instruction.from(input)).isEqualTo(expected);
  }

  private static Stream<Arguments> isSafeTestCases() {
    /*
        123 -> x
    456 -> y
    x AND y -> d
    x OR y -> e
    x LSHIFT 2 -> f
    y RSHIFT 2 -> g
    NOT x -> h
    NOT y -> i
         */

    return Stream.of(
        Arguments.of("123 -> x", Instruction.of(Operation.of("123"), "x")),
        Arguments.of(
            "x AND y -> d", Instruction.of(Operation.of("x", Operation.Gate.AND, "y"), "d")),
        Arguments.of("x OR y -> e", Instruction.of(Operation.of("x", Operation.Gate.OR, "y"), "e")),
        Arguments.of("NOT x -> h", Instruction.of(Operation.of(Operation.Gate.NOT, "x"), "h")));
  }
}
