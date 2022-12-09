package be.bonamis.advent.year2022;

import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import be.bonamis.advent.year2022.Day09.Rope.WirePath;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static be.bonamis.advent.utils.marsrover.FacingDirection.*;
import static org.assertj.core.api.Assertions.assertThat;

class Day09Test {

    /*
    R 4
U 4
L 3
     */
    private static final String CODE_TXT = "2022/09/2022_09_05_code.txt";

    @Test
    void solvePart01() {
        List<String> lines = getLines(CODE_TXT);
        String collect = lines.stream().map(str -> str.replaceAll(" ", "")).collect(Collectors.joining(","));
        System.out.println(collect);
        String wirePaths = "R4,U2";
        Day09.Rope rope = new Day09.Rope();
        Day09.Rope wireAfterFirstMove = rope.move(WirePath.from(collect));
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

        Day09.Rope wireAfterTwoMove = wireAfterFirstMove.move(WirePath.of("U4"));
        Rover roverAfterTwoMove = wireAfterTwoMove.head();
        assertThat(roverAfterTwoMove.facingDirection()).isEqualTo(NORTH);
        assertThat(roverAfterTwoMove.position()).isEqualTo(new Position(4, 4));

        assertThat(wireAfterFirstMove.positions()).containsExactlyInAnyOrder(
                new Position(1, 0),
                new Position(2, 0),
                new Position(3, 0),
                new Position(4, 0),
                new Position(4, 1),
                new Position(4, 2),
                new Position(4, 3),
                new Position(4, 4)
        );
    }

    @Test
    void aRoverTailISAlwaysBehindHead() {
        Day09.Rope rope = new Day09.Rope();
        Day09.Rope wireAfterFirstMove = rope.move(WirePath.of("R2"));
        assertThat(wireAfterFirstMove.head().position()).isEqualTo(new Position(2, 0));
        assertThat(wireAfterFirstMove.tail()).isEqualTo(new Position(1, 0));
    }

    @Test
    void aRoverTailISAlwaysBehindHead2() {
        Day09.Rope rope = new Day09.Rope();
        Day09.Rope wireAfterFirstMove = rope.move(WirePath.of("R4"));
        assertThat(wireAfterFirstMove.head().position()).isEqualTo(new Position(4, 0));
        assertThat(wireAfterFirstMove.tail()).isEqualTo(new Position(3, 0));
    }

    @Test
    void aRoverTailISAlwaysBehindHead3() {
        Day09.Rope rope = new Day09.Rope();
        Day09.Rope wireAfterFirstMove = rope.move(WirePath.from("R4,U1"));
        assertThat(wireAfterFirstMove.head().position()).isEqualTo(new Position(4, 1));
        assertThat(wireAfterFirstMove.tail()).isEqualTo(new Position(3, 0));
    }

    @Test
    void aRoverTailISAlwaysBehindHead4() {
        Day09.Rope rope = new Day09.Rope();
        Day09.Rope wireAfterFirstMove = rope.move(WirePath.from("R4,U2"));
        assertThat(wireAfterFirstMove.head().position()).isEqualTo(new Position(4, 2));
        assertThat(wireAfterFirstMove.tail()).isEqualTo(new Position(4, 1));
    }

    @Test
    void solvePart02() {
        List<String> lines = getLines(CODE_TXT);
        assertThat(new Day09(lines).solvePart02()).isEqualTo(9);
    }

    @Test
        //A Rover has an initial position and direction it's facing
    void aRoverHasAnInitialPosition() {
        Rover rover = initialRover();
        assertThat(rover.position()).isEqualTo(new Position(0, 0));
    }

    private Rover initialRover() {
        return new Rover(NORTH, new Position(0, 0));
    }

}
