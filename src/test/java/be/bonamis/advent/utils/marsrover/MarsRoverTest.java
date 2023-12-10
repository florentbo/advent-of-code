package be.bonamis.advent.utils.marsrover;

import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import be.bonamis.advent.utils.marsrover.Rover.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MarsRoverTest {

  @Test
  // A Rover has an initial position and direction it's facing
  void aRoverHasAnInitialPosition() {
    Rover rover = initialRover();
    assertThat(rover.position()).isEqualTo(new Position(0, 0));
  }

  @Test
  void aRoverHasAnInitialFacingDirection() {
    Rover rover = initialRover();
    assertThat(rover.direction()).isEqualTo(NORTH);
  }

  private Rover initialRover() {
    return new Rover(NORTH, new Position(0, 0));
  }

  @ParameterizedTest
  @MethodSource("provideRovers")
  void isBlank_ShouldReturnTrueForNullOrBlankStrings2(
      Rover initialRover, Command command, Rover expected) {
    Rover movedRover = initialRover.move(command);
    assertEquals(expected, movedRover);
  }

  private static Stream<Arguments> provideRovers() {
    return Stream.of(
        Arguments.of(
            new Rover(NORTH, new Position(0, 0)),
            Command.FORWARD,
            new Rover(NORTH, new Position(0, 1))),
        Arguments.of(
            new Rover(SOUTH, new Position(0, 0)),
            Command.FORWARD,
            new Rover(SOUTH, new Position(0, -1))),
        Arguments.of(
            new Rover(WEST, new Position(0, 0)),
            Command.FORWARD,
            new Rover(WEST, new Position(-1, 0))),
        Arguments.of(
            new Rover(EAST, new Position(0, 0)),
            Command.FORWARD,
            new Rover(EAST, new Position(1, 0))),
        Arguments.of(
            new Rover(NORTH, new Position(0, 0)),
            Command.LEFT,
            new Rover(WEST, new Position(0, 0))),
        Arguments.of(
            new Rover(WEST, new Position(0, 0)),
            Command.LEFT,
            new Rover(SOUTH, new Position(0, 0))),
        Arguments.of(
            new Rover(SOUTH, new Position(0, 0)),
            Command.LEFT,
            new Rover(EAST, new Position(0, 0))),
        Arguments.of(
            new Rover(EAST, new Position(0, 0)),
            Command.LEFT,
            new Rover(NORTH, new Position(0, 0))),
        Arguments.of(
            new Rover(NORTH, new Position(0, 0)),
            Command.RIGHT,
            new Rover(EAST, new Position(0, 0))),
        Arguments.of(
            new Rover(EAST, new Position(0, 0)),
            Command.RIGHT,
            new Rover(SOUTH, new Position(0, 0))),
        Arguments.of(
            new Rover(SOUTH, new Position(0, 0)),
            Command.RIGHT,
            new Rover(WEST, new Position(0, 0))),
        Arguments.of(
            new Rover(WEST, new Position(0, 0)),
            Command.RIGHT,
            new Rover(NORTH, new Position(0, 0))),
        Arguments.of(
            new Rover(NORTH, new Position(0, 0)),
            Command.BACKWARD,
            new Rover(NORTH, new Position(0, -1))));
  }

  @ParameterizedTest
  @MethodSource("provideRoversFotTwoMoves")
  void moveTwice(Rover initialRover, Command command01, Command command02, Rover expected) {
    Rover movedRover = initialRover.move(command01);
    Rover movedRoverTwice = movedRover.move(command02);
    assertEquals(expected, movedRoverTwice);
  }

  private static Stream<Arguments> provideRoversFotTwoMoves() {
    return Stream.of(
        Arguments.of(
            new Rover(NORTH, new Position(0, 0)),
            Command.FORWARD,
            Command.FORWARD,
            new Rover(NORTH, new Position(0, 2))),
        Arguments.of(
            new Rover(SOUTH, new Position(0, 0)),
            Command.FORWARD,
            Command.FORWARD,
            new Rover(SOUTH, new Position(0, -2))),
        Arguments.of(
            new Rover(WEST, new Position(0, 0)),
            Command.FORWARD,
            Command.FORWARD,
            new Rover(WEST, new Position(-2, 0))),
        Arguments.of(
            new Rover(EAST, new Position(0, 0)),
            Command.FORWARD,
            Command.FORWARD,
            new Rover(EAST, new Position(2, 0))),
        Arguments.of(
            new Rover(NORTH, new Position(0, 0)),
            Command.RIGHT,
            Command.RIGHT,
            new Rover(SOUTH, new Position(0, 0))),
        Arguments.of(
            new Rover(NORTH, new Position(0, 0)),
            Command.FORWARD,
            Command.RIGHT,
            new Rover(EAST, new Position(0, 1))),
        Arguments.of(
            new Rover(EAST, new Position(5, 7)),
            Command.FORWARD,
            Command.RIGHT,
            new Rover(SOUTH, new Position(6, 7))));
  }
}
