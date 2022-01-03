package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.year2021.Day22.Cuboid.State;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day22Test {


    private Day22 day;

    @BeforeEach
    void setUp() {
        List<String> data = getLines("2021_day22_test.txt");
        day = new Day22(data);
    }

    @Test
    void solvePart01() {
        assertEquals(39, day.solvePart01());
    }

    @Test
    void solvePart01_with_bigger_data() {
        List<String> data = getLines("2021_day22_test_part1_sample02.txt");
        day = new Day22(data);
        assertEquals(590784, day.solvePart01());
    }

	@Test
    void solvePart01_with_another_bigger_data() {
        List<String> data = getLines("2021_day22_test_part2.txt");
        day = new Day22(data);
        assertEquals(474140, day.solvePart01());
    }

    @Test
    void solvePart02() {
        assertEquals(5, day.solvePart02());
    }

    @Test
    void cuboid_constructor() {
        assertThat(day.getCuboids()).hasSize(4);

        assertThat(day.getCuboids()).extracting("state").containsExactly(State.ON, State.ON, State.OFF, State.ON);
        assertThat(day.getCuboids().get(0).getCubes()).hasSize(27);
        //assertThat(day.getInitialCubes()).hasSize(27);
    }

    @Test
    void step() {
        //assertThat(day.getInitialCubes()).hasSize(27);
        assertThat(day.step(0)).hasSize(27);
        assertThat(day.step(1)).hasSize(46);
        assertThat(day.step(2)).hasSize(46 - 8);
        assertThat(day.step(3)).hasSize(46 - 8 + 1);
        assertThat(day.step(3)).hasSize(39);
    }
}
