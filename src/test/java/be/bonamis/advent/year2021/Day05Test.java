package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.*;

import be.bonamis.advent.year2021.Day05.LineSegment;
import be.bonamis.advent.year2021.Day05.LineSegment.Point;

class Day05Test {

    private Day05 day;

    @BeforeEach
    void setUp() {
        List<String> data = getLines("2021_day05_test.txt");
        day = new Day05(data);
    }

    @Test
    void solvePart01() {
        assertEquals(5, day.solvePart01());
    }

    @Test
    void solvePart02() {
        assertEquals(12, day.solvePart02());
    }

    @Test
    void point() {
        Point point = new Point("5,9");
        assertEquals(5, point.getX());
        assertEquals(9, point.getY());
    }

    @Test
    void lineSegment() {
        LineSegment lineSegment = new LineSegment("0,9 -> 5,9");
        assertEquals(0, lineSegment.getStart().getX());
        assertEquals(9, lineSegment.getStart().getY());
        assertEquals(5, lineSegment.getEnd().getX());
        assertEquals(9, lineSegment.getEnd().getY());
    }

    @Test
    void getLines_has_well_a_size_of_10() {
        assertThat(day.getLines()).hasSize(10);
    }

    @Test
    void getPoints() {
        assertThat(new LineSegment("2,2 -> 2,1").getPoints(false))
                .containsExactlyInAnyOrder(new Point(2, 2), new Point(2, 1));

        assertThat(new LineSegment("9,4 -> 8,4").getPoints(false))
                .containsExactlyInAnyOrder(new Point(9, 4), new Point(8, 4));
        assertThat(new LineSegment("6,4 -> 2,0").getPoints(false)).isEmpty();

        assertThat(new LineSegment("3,3 -> 1,1").getPoints(true))
                .containsExactlyInAnyOrder(new Point(1, 1), new Point(2, 2), new Point(3, 3));

        assertThat(new LineSegment("6,7 -> 8,9").getPoints(true))
                .containsExactlyInAnyOrder(new Point(6, 7), new Point(7, 8), new Point(8, 9));

        assertThat(new LineSegment("6,7 -> 8,5").getPoints(true))
                .containsExactlyInAnyOrder(new Point(6, 7), new Point(7, 6), new Point(8, 5));

    }

    @Test
    void isDiagonal() {
        Point a = new Point(0, 0);
        assertThat(a.isDiagonal(new Point(1, 1))).isTrue();
        assertThat(a.isDiagonal(new Point(2, 0))).isFalse();
    }
}
