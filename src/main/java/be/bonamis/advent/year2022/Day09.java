package be.bonamis.advent.year2022;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.utils.marsrover.FacingDirection;
import be.bonamis.advent.utils.marsrover.Position;
import be.bonamis.advent.utils.marsrover.Rover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static be.bonamis.advent.utils.marsrover.FacingDirection.*;

public class Day09 extends DaySolver<String> {

    public Day09(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        String collect = this.puzzle.stream().map(str -> str.replaceAll(" ", "")).collect(Collectors.joining(","));
        Day09.Rope rope = new Day09.Rope();
        Day09.Rope wireAfterFirstMove = rope.move(Rope.WirePath.from(collect));

        List<Position> tailPositions = wireAfterFirstMove.tailPositions;
        System.out.println(tailPositions);
        return tailPositions.size();
    }

    @Override
    public long solvePart02() {
        return this.puzzle.size() + 1;
    }

    record Rope(Rover head, Position tail, List<Position> positions, List<Position> tailPositions) {
        public Rope() {
            this(initRover(), new Position(0, 0), new ArrayList<>(), initTailPositions());
        }

        private static List<Position> initTailPositions() {
            List<Position> arrayList = new ArrayList<>();
            arrayList.add(new Position(0, 0));
            return arrayList;
        }

        private static Rover initRover() {
            return new Rover(NORTH, new Position(0, 0));
        }

        public Rope move(List<WirePath> paths) {
            Rover previousRoverAfterPivoting = this.head;
            Position previousTail = this.tail;
            for (WirePath path : paths) {
                previousRoverAfterPivoting = new Rover(path.facingDirection(), previousRoverAfterPivoting.position());
                for (int i = 0; i < path.length(); i++) {
                    previousRoverAfterPivoting = previousRoverAfterPivoting.move("f");
                    Position headNewPosition = previousRoverAfterPivoting.position();
                    int xDistance = Math.abs(previousTail.x() - headNewPosition.x());
                    int yDistance = Math.abs(previousTail.y() - headNewPosition.y());
                    if (xDistance > 1 || yDistance > 1) {
                        Position lastHeadPosition = this.positions.get(this.positions().size() - 1);
                        this.tailPositions.add(lastHeadPosition);
                        previousTail = lastHeadPosition;
                    }
                    this.positions.add(headNewPosition);
                }
            }
            return new Rope(previousRoverAfterPivoting, previousTail, this.positions, this.tailPositions);
        }

        public Rope move(WirePath... paths) {
            return move(Arrays.asList(paths));
        }

        public record WirePath(FacingDirection facingDirection, int length) {
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

            public static List<WirePath> from(String wirePaths) {
                return Arrays.stream(wirePaths.split(",")).map(WirePath::of).collect(Collectors.toList());
            }
        }
    }
}
