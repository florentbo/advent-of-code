package be.bonamis.advent.year2022;

import be.bonamis.advent.utils.marsrover.FacingDirection;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
        assertThat(new Day09(lines).solvePart01()).isEqualTo(8);
    }

    @Test
    void aRoverMoveStepByStep() {
        Rope wire = new Rope();

        Rope wireAfterFirstMove = wire.move(Rope.WirePath.of("R4"));
        Rover movedRover = wireAfterFirstMove.rover;
        assertThat(movedRover.facingDirection()).isEqualTo(EAST);
        assertThat(movedRover.position()).isEqualTo(new Position(4, 0));

        assertThat(wireAfterFirstMove.positions).containsExactlyInAnyOrder(
                                                                new Position(1, 0),
                                                                new Position(2, 0),
                                                                new Position(3, 0),
                                                                new Position(4, 0));

        Rope wireAfterTwoMove = wireAfterFirstMove.move(Rope.WirePath.of("U4"));
        Rover roverAfterTwoMove = wireAfterTwoMove.rover;
        assertThat(roverAfterTwoMove.facingDirection()).isEqualTo(NORTH);
        assertThat(roverAfterTwoMove.position()).isEqualTo(new Position(4, 4));

        assertThat(wireAfterFirstMove.positions).containsExactlyInAnyOrder(
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

    record Rope(Rover rover, List<Position> positions) {
        public Rope() {
            this(new Rover(NORTH, new Position(0, 0)), new ArrayList<>());
        }

        public Rope move(List<WirePath> paths) {
            Rover previousRoverAfterPivoting = this.rover;
            for (WirePath path : paths) {
                previousRoverAfterPivoting = new Rover(path.facingDirection(), previousRoverAfterPivoting.position());
                for (int i = 0; i < path.length(); i++) {
                    previousRoverAfterPivoting = previousRoverAfterPivoting.move("f");
                    positions.add(previousRoverAfterPivoting.position());
                }
            }
            return new Rope(previousRoverAfterPivoting, positions);
        }

        public Rope move(WirePath... paths) {
            return move(Arrays.asList(paths));
        }

        record WirePath(FacingDirection facingDirection, int length) {
            public static WirePath of(String path) {
                return new WirePath(getFacingDirection(path.substring(0, 1)), getLength(path.substring(1)));
            }

            private static int getLength(String substring) {
                return Integer.parseInt(substring);
            }

            private static FacingDirection getFacingDirection(String substring) {
                return switch (substring) {
                    case "R" -> EAST;
                    case "U" -> NORTH;
                    case "L" -> WEST;
                    case "D" -> SOUTH;
                    default -> null;
                };
            }

            private static List<WirePath> from(String wirePaths) {
                return Arrays.stream(wirePaths.split(",")).map(WirePath::of).collect(Collectors.toList());
            }
        }
    }
}
