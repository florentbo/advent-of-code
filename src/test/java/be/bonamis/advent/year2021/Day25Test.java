package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.util.List;
import java.util.Map;

import org.jgrapht.alg.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.year2021.Day25.Move;

class Day25Test {

    private Day25 day;

    @BeforeEach
    void setUp() {
        List<String> data = getLines("2021_day25_test.txt");
        day = new Day25(data);
    }

    @Test
    void solvePart01() {
        assertEquals(58, day.solvePart01());
    }

    @Test
    void solvePart01Puzzle() {
        List<String> data = getLines("2021_day25_prod.txt");
        day = new Day25(data);
        assertEquals(406, day.solvePart01());
    }

    @Test
    void solvePart02() {
        assertEquals(10, day.solvePart02());
    }

    @Test
    void constructor() {
        assertThat(day.getCharGrid().getWidth()).isEqualTo(10);
        assertThat(day.getCharGrid().getHeight()).isEqualTo(9);
        assertThat(day.getMoves()).hasSize(49);
    }

    @Test
    void easterPoint() {
        Point originPoint = new Point(0, 0);
        assertThat(day.easterPoint(originPoint)).isEqualTo(new Point(0, 1));
        assertThat(day.getCharGrid().get(originPoint)).isNotNull();
        Point point = new Point(0, day.getCharGrid().getWidth() - 1);
        assertThat(day.getCharGrid().get(point)).isNotNull();
        assertThat(day.easterPoint(point)).isEqualTo(new Point(0, 0));
    }

    @Test
    void southernPoint() {
        Point originPoint = new Point(0, 0);
        assertThat(day.southernPoint(originPoint)).isEqualTo(new Point(1, 0));
        assertThat(day.getCharGrid().get(originPoint)).isNotNull();
        Point point = new Point(day.getCharGrid().getHeight() - 1, 0);
        assertThat(day.getCharGrid().get(point)).isNotNull();
        assertThat(day.southernPoint(point)).isEqualTo(new Point(0, 0));
    }

    @Test
    void constructor_sample() {
        List<String> data = getLines("2021_day25_test_sample.txt");
        day = new Day25(data);
        assertThat(day.getCharGrid().getHeight()).isEqualTo(7);
        assertThat(day.getCharGrid().getWidth()).isEqualTo(7);
        assertThat(day.getMoves()).hasSize(8);
        Map<Point, Move> moves = day.getMoves();
        assertThat(day.eastMoves(moves).count()).isEqualTo(4);
        assertThat(day.southMoves(moves).count()).isEqualTo(4);
        assertThat(day.getMoves()).contains(
                entry(new Point(0, 3), Move.EAST),
                entry(new Point(6, 2), Move.SOUTH),
                entry(new Point(3, 6), Move.EAST)
        );
    }

    @Test
    void eastStep() {
        List<String> data = getLines("2021_day25_test_sample.txt");
        day = new Day25(data);
        Map<Point, Move> initialMoves = day.getMoves();
        assertThat(day.getMoves()).hasSize(8);
        assertThat(initialMoves).contains(
                entry(new Point(2, 6), Move.EAST),
                entry(new Point(3, 6), Move.EAST),
                entry(new Point(4, 6), Move.EAST)
        );

        Pair<Map<Point, Move>, Integer> eastStepResult = day.eastStep(initialMoves);
        Map<Point, Move> movesAfterEastStep = eastStepResult.getFirst();
        assertThat(movesAfterEastStep).hasSize(8);
        assertThat(eastStepResult.getSecond()).isEqualTo(3);
        assertThat(movesAfterEastStep).contains(
                entry(new Point(2, 0), Move.EAST),
                entry(new Point(4, 0), Move.EAST)
        );
    }

    @Test
    void southStep() {
        List<String> data = getLines("2021_day25_test_sample.txt");
        day = new Day25(data);
        Map<Point, Move> initialMoves = day.getMoves();
        assertThat(initialMoves).contains(
                entry(new Point(3, 0), Move.SOUTH),
                entry(new Point(6, 2), Move.SOUTH),
                entry(new Point(6, 3), Move.SOUTH),
                entry(new Point(6, 4), Move.SOUTH)
        );

        Pair<Map<Point, Move>, Integer> stepMoves = day.southStep(initialMoves);
        assertThat(stepMoves.getSecond()).isEqualTo(3);
        assertThat(stepMoves.getFirst()).contains(
                entry(new Point(4, 0), Move.SOUTH),
                entry(new Point(0, 2), Move.SOUTH),
                entry(new Point(0, 4), Move.SOUTH)
        );
    }
}
