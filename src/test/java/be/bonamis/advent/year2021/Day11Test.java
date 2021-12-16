package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.common.Grid;

class Day11Test {

    private Day11 day;

    @BeforeEach
    void setUp() {
        List<String> data = getLines("2021_day11_test.txt");
        day = new Day11(data);
    }

    @Test
    void solvePart01() {
        assertEquals(1656, day.solvePart01());
    }

    @Test
    void getAdjacentPoints() {
        int[][] input = {
                {1, 1, 1, 1, 1},
                {1, 9, 9, 9, 1},
                {1, 9, 1, 9, 1},
                {1, 9, 9, 9, 1},
                {1, 1, 1, 1, 1}};

        assertThat(day.getAdjacentPoints(new Point(0, 2), new Grid(input)).collect(Collectors.toList())).hasSize(5);
    }

    @Test
    void solvePart02() {
        assertEquals(195, day.solvePart02());
    }


}
