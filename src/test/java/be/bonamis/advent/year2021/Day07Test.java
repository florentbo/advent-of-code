package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day07Test {

    private Day07 day;

    @BeforeEach
    void setUp() {
        List<String> data = getLines("2021_day07_test.txt");
        day = new Day07(data);
    }

    @Test
    void solvePart01() {
        assertEquals(37, day.solvePart01());
    }

    @Test
    void solvePart02() {
        assertEquals(168, day.solvePart02());
    }

    @Test
    void getSmallCost() {
        assertEquals(4, day.getSmallCost(1,5));
    }

    @Test
    void getBigCost() {
        assertEquals(10, day.getBigCost(1,5));
    }

    @Test
    void getBigCost2() {
        assertEquals(6, day.getBigCost(2,5));
    }
}
