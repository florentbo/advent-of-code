package be.bonamis.advent.year2016;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Stream;

import be.bonamis.advent.utils.marsrover.Position;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static be.bonamis.advent.utils.marsrover.Rover.Command.*;
import static be.bonamis.advent.year2016.Day01.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class Day01Test {

  @ParameterizedTest
  @MethodSource("part01TestCases")
  void solve01_test(String input, int expected) {
    assertThat(new Day01(new ByteArrayInputStream(input.getBytes())).solvePart01())
        .isEqualTo(expected);
  }

  private static Stream<Arguments> part01TestCases() {
    return Stream.of(
        Arguments.of("R2, L3", 5),
        Arguments.of("R2, R2, R2", 2),
        Arguments.of("R5, L5, R5, R3", 12));
  }

  @Test
  void solvePart02() {
    assertThat(new Day01(new ByteArrayInputStream("R8, R4, R4, R8".getBytes())).solvePart02())
        .isEqualTo(4);
  }

  @Test
  void from_move() {
    assertEquals(Move.of(RIGHT, 1), Move.from("R1"));
    assertEquals(Move.of(LEFT, 1), Move.from("L1"));
  }

  @Test
  void from_moves() {
    assertEquals(Moves.from("R2, L3"), new Moves(List.of(Move.of(RIGHT, 2), Move.of(LEFT, 3))));
  }

  @Test
  void lastRoverPosition() {
    assertEquals(Position.of(2, 0), Moves.from("R2").lastRoverPosition());
    assertEquals(Position.of(2, 3), Moves.from("R2, L3").lastRoverPosition());
  }

  @Test
  void positionDistance() {
    assertThat(distance(Position.of(2, 3))).isEqualTo(5);
    assertThat(distance(Position.of(0, -2))).isEqualTo(2);
    assertThat(distance(Position.of(-1, -2))).isEqualTo(3);
  }

  @Test
  void firstLocationVisitedTwice() {
    assertEquals(Position.of(4, 0), Moves.from("R8, R4, R4, R8").firstLocationVisitedTwice());
  }
}
