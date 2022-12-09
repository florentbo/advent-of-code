package be.bonamis.advent.utils.marsrover;

import static be.bonamis.advent.utils.marsrover.FacingDirection.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MarsRoverTest {

    @Test
        //A Rover has an initial position and direction it's facing
    void aRoverHasAnInitialPosition() {
        Rover rover = initialRover();
        assertThat(rover.position()).isEqualTo(new Position(0, 0));
    }

    @Test
    void aRoverHasAnInitialFacingDirection() {
        Rover rover = initialRover();
        assertThat(rover.facingDirection()).isEqualTo(NORTH);
    }

    private Rover initialRover() {
        return new Rover(NORTH, new Position(0, 0));
    }

    //lesson 1 record force us to have a contract

    @ParameterizedTest
    @MethodSource("provideRovers")
    void isBlank_ShouldReturnTrueForNullOrBlankStrings2(Rover initialRover, String command, Rover expected) {
        Rover movedRover = initialRover.move(command);
        assertEquals(expected, movedRover);
    }

    private static Stream<Arguments> provideRovers() {
        return Stream.of(
                Arguments.of(new Rover(NORTH, new Position(0, 0)), "f", new Rover(NORTH, new Position(0, 1))),
                Arguments.of(new Rover(SOUTH, new Position(0, 0)), "f", new Rover(SOUTH, new Position(0, -1))),
                Arguments.of(new Rover(WEST, new Position(0, 0)), "f", new Rover(WEST, new Position(-1, 0))),
                Arguments.of(new Rover(EAST, new Position(0, 0)), "f", new Rover(EAST, new Position(1, 0))),

                Arguments.of(new Rover(NORTH, new Position(0, 0)), "l", new Rover(WEST, new Position(0, 0))),
                Arguments.of(new Rover(WEST, new Position(0, 0)), "l", new Rover(SOUTH, new Position(0, 0))),
                Arguments.of(new Rover(SOUTH, new Position(0, 0)), "l", new Rover(EAST, new Position(0, 0))),
                Arguments.of(new Rover(EAST, new Position(0, 0)), "l", new Rover(NORTH, new Position(0, 0))),

                Arguments.of(new Rover(NORTH, new Position(0, 0)), "r", new Rover(EAST, new Position(0, 0))),
                Arguments.of(new Rover(EAST, new Position(0, 0)), "r", new Rover(SOUTH, new Position(0, 0))),
                Arguments.of(new Rover(SOUTH, new Position(0, 0)), "r", new Rover(WEST, new Position(0, 0))),
                Arguments.of(new Rover(WEST, new Position(0, 0)), "r", new Rover(NORTH, new Position(0, 0))),

                Arguments.of(new Rover(NORTH, new Position(0, 0)), "b", new Rover(NORTH, new Position(0, -1)))
        );
    }

    @ParameterizedTest
    @MethodSource("provideRoversFotTwoMoves")
    void moveTwice(Rover initialRover, String command01, String command02, Rover expected) {
        Rover movedRover = initialRover.move(command01);
        Rover movedRoverTwice = movedRover.move(command02);
        assertEquals(expected, movedRoverTwice);
    }

    private static Stream<Arguments> provideRoversFotTwoMoves() {
        return Stream.of(
                Arguments.of(new Rover(NORTH, new Position(0, 0)), "f", "f", new Rover(NORTH, new Position(0, 2))),
                Arguments.of(new Rover(SOUTH, new Position(0, 0)), "f", "f", new Rover(SOUTH, new Position(0, -2))),
                Arguments.of(new Rover(WEST, new Position(0, 0)), "f", "f", new Rover(WEST, new Position(-2, 0))),
                Arguments.of(new Rover(EAST, new Position(0, 0)), "f", "f", new Rover(EAST, new Position(2, 0))),

                Arguments.of(new Rover(NORTH, new Position(0, 0)), "r", "r", new Rover(SOUTH, new Position(0, 0))),

                Arguments.of(new Rover(NORTH, new Position(0, 0)), "f", "r", new Rover(EAST, new Position(0, 1))),

                Arguments.of(new Rover(EAST, new Position(5, 7)), "f", "r", new Rover(SOUTH, new Position(6, 7)))
        );
    }

}
