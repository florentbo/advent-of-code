package be.bonamis.advent.year2021;

import static be.bonamis.advent.utils.FileHelper.getLines;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Day20Test {

    private Day20 day;

    @BeforeEach
    void setUp() {
        List<String> data = getLines("2021_day20_test.txt");
        day = new Day20(data);
    }

    @Test
    void solvePart01() {
        assertEquals(7, day.solvePart01());
    }

    @Test
    void solvePart02() {
        assertEquals(8, day.solvePart02());
    }

	@Test
	void constructor() {
		assertThat(day.getImageEnhancementAlgorithm()).hasSize(512);
		assertThat(day.getGrid().getHeight()).isEqualTo(5);
		assertThat(day.getGrid().getWidth()).isEqualTo(5);
	}
}
