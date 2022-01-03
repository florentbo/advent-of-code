package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.bonamis.advent.year2021.Day17.TargetArea;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day17Test {

    private Day17 day;

    @BeforeEach
    void setUp() {
        List<String> data = getLines("2021_day17_test.txt");
        day = new Day17(data);
    }

    @Test
    void solvePart01() {
        assertEquals(45, day.solvePart01());
    }

    @Test
    void solvePart02() {
        assertEquals(112, day.solvePart02());
    }

	@Test
	void targetArea_constructor() {
		TargetArea targetArea = new TargetArea("target area: x=20..30, y=-10..-5");
		assertThat(targetArea.getXLimits()).containsExactly(20,30);
		assertThat(targetArea.getYLimits()).containsExactly(-10,-5);

		assertThat(targetArea.getXLimit().getMin()).isEqualTo(20);
		assertThat(targetArea.getXLimit().getMax()).isEqualTo(30);

		assertThat(targetArea.getYLimit().getMin()).isEqualTo(-10);
		assertThat(targetArea.getYLimit().getMax()).isEqualTo(-5);
	}
}
