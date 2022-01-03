package be.bonamis.advent.year2021;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgrapht.alg.util.Pair;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.CharGrid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day25 extends DaySolver<String> {

    private final CharGrid charGrid;
    private final Map<Point, Move> moves;

    public Day25(List<String> puzzle) {
        super(puzzle);
        charGrid = new CharGrid(this.puzzle.stream().map(String::toCharArray).toArray(char[][]::new));
        this.moves = charGrid
                .stream()
                .filter(point -> Move.from(charGrid.get(point)) != Move.NOTHING)
                .collect(Collectors.toMap(point -> point, point -> Move.from(charGrid.get(point))));
    }

    @Override
    public long solvePart01() {
        Map<Point, Move> actualMoves = new HashMap<>(this.moves);
        Integer movesCount = Integer.MAX_VALUE;
        int stepsCount = 0;
        while (movesCount > 0) {
            stepsCount++;
            movesCount = 0;
            Pair<Map<Point, Move>, Integer> eastStepResult = eastStep(actualMoves);
            movesCount += eastStepResult.getSecond();
            Pair<Map<Point, Move>, Integer> southStepResult = southStep(eastStepResult.getFirst());
            movesCount += southStepResult.getSecond();
            actualMoves = new HashMap<>(southStepResult.getFirst());
        }
        return stepsCount;
    }

    Pair<Map<Point, Move>, Integer> eastStep(Map<Point, Move> actualMoves) {
        return move(actualMoves, Move.EAST, this::easterPoint);
    }

    Pair<Map<Point, Move>, Integer> southStep(Map<Point, Move> actualMoves) {
        return move(actualMoves, Move.SOUTH, this::southernPoint);
    }

    private Pair<Map<Point, Move>, Integer> move(Map<Point, Move> actualMoves, Move direction, Function<Point, Point> toOtherPoint) {
        final int[] moves = {0};
        Stream<Map.Entry<Point, Move>> movesByDirection = movesByDirection(actualMoves, direction);
        //log.info("move direction:{} ", direction);
        Map<Point, Move> stepMoves = new HashMap<>(movesByDirection(actualMoves, otherDirection(direction)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        //log.info("stepMoves  size " + stepMoves.size());
        movesByDirection.forEach(pointMoveEntry -> {
            Point pointToMove = pointMoveEntry.getKey();
            Point otherPoint = toOtherPoint.apply(pointToMove);
            //log.info("Trying to move {} to {}", pointToMove, otherPoint);
            if (!actualMoves.containsKey(otherPoint)) {
                //log.info("Moving {} to {}", pointToMove, otherPoint);
                stepMoves.put(otherPoint, direction);
                moves[0]++;
            } else {
                //log.info("Not moving {}", pointToMove);
                stepMoves.put(pointToMove, direction);
            }

        });
        return new Pair<>(stepMoves, moves[0]);
    }

    private Move otherDirection(Move direction) {
        return direction == Move.EAST ? Move.SOUTH : Move.EAST;
    }

    Point easterPoint(Point point) {
        int nextY = (int) (point.getY() + 1);
        int x = (int) (point.getX());
        return nextY == charGrid.getWidth() ? new Point(x, 0) : new Point(x, nextY);
    }

    Point southernPoint(Point point) {
        int nextX = (int) (point.getX() + 1);
        int y = (int) point.getY();
        return nextX == charGrid.getHeight() ? new Point(0, y) : new Point(nextX, y);
    }

    Stream<Map.Entry<Point, Move>> eastMoves(Map<Point, Move> moves) {
        return movesByDirection(moves, Move.SOUTH);
    }

    Stream<Map.Entry<Point, Move>> southMoves(Map<Point, Move> moves) {
        return movesByDirection(moves, Move.SOUTH);
    }

    private Stream<Map.Entry<Point, Move>> movesByDirection(Map<Point, Move> moves, Move move) {
        return moves.entrySet().stream().filter(entry -> entry.getValue() == move);
    }

    @Override
    public long solvePart02() {
        return puzzle.size() + 1;
    }

    enum Move {
        EAST('>'),
        NOTHING('.'),
        SOUTH('v');

        private final char direction;

        Move(char direction) {
            this.direction = direction;
        }

        public static Move from(char direction) {
            return Stream.of(values()).filter(m -> m.direction == direction).findFirst().orElseThrow(() -> new IllegalArgumentException("No such direction: " + direction));
        }
    }
}

