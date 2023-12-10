package be.bonamis.advent.year2022;

import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.year2022.Day09.Rope.WirePath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static be.bonamis.advent.utils.marsrover.Rover.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;

class Day09Test {

    private static final String CODE_TXT = "2022/09/2022_09_05_code.txt";

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(new Day09(lines).solvePart01()).isEqualTo(13);
    }

    @Test
    void aRoverMoveStepByStep() {
        Day09.Rope rope = new Day09.Rope();

        Day09.Rope wireAfterFirstMove = rope.move(WirePath.of("R4"));
        Rover movedRover = wireAfterFirstMove.head();
        assertThat(movedRover.facingDirection()).isEqualTo(EAST);
        assertThat(movedRover.position()).isEqualTo(new Position(4, 0));

        assertThat(wireAfterFirstMove.positions()).containsExactlyInAnyOrder(
                new Position(1, 0),
                new Position(2, 0),
                new Position(3, 0),
                new Position(4, 0));

    }

    @ParameterizedTest
    @MethodSource("generateArgumentsStream")
    public void aRoverTailISAlwaysBehindHead(List<WirePath> wirePaths, Position expected, int number) {
        Day09.Rope rope = new Day09.Rope();
        Day09.Rope wireAfterFirstMove = rope.move(wirePaths);
        assertThat(wireAfterFirstMove.tail()).isEqualTo(expected);
        assertThat(wireAfterFirstMove.tailPositions().size()).isEqualTo(number);
    }

    private static Stream<Arguments> generateArgumentsStream() {
        List<Arguments> listOfArguments = new LinkedList<>();
        listOfArguments.add(Arguments.of(WirePath.from("R2"), new Position(1, 0), 2));
        listOfArguments.add(Arguments.of(WirePath.from("R3"), new Position(2, 0), 3));
        listOfArguments.add(Arguments.of(WirePath.from("R4"), new Position(3, 0), 4));
        listOfArguments.add(Arguments.of(WirePath.from("R4,U1"), new Position(3, 0), 4));
        listOfArguments.add(Arguments.of(WirePath.from("R4,U2"), new Position(4, 1), 5));
        listOfArguments.add(Arguments.of(WirePath.from("R4,U3"), new Position(4, 2), 6));
        listOfArguments.add(Arguments.of(WirePath.from("R4,U4"), new Position(4, 3), 7));
        return listOfArguments.stream();
    }

    @Test
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(new Day09(lines).solvePart02()).isEqualTo(9);
    }
}
